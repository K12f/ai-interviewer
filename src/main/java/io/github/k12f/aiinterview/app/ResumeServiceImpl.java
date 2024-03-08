package io.github.k12f.aiinterview.app;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileTypeUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import dev.langchain4j.data.document.loader.FileSystemDocumentLoader;
import dev.langchain4j.data.document.parser.apache.pdfbox.ApachePdfBoxDocumentParser;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.service.AiServices;
import io.github.k12f.aiinterview.app.vo.ResumeVo;
import io.github.k12f.aiinterview.client.api.ResumeService;
import io.github.k12f.aiinterview.infra.brain.prompt.Interviewer;
import io.github.k12f.aiinterview.infra.brain.prompt.ResumeExtractor;
import io.github.k12f.aiinterview.infra.config.OpenAiConfig;
import io.github.k12f.aiinterview.infra.config.UploadConfig;
import io.github.k12f.aiinterview.infra.constants.ResumeConstants;
import io.github.k12f.aiinterview.infra.util.Response;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Paths;
import java.time.Duration;
import java.util.List;

@Service
@Slf4j
public class ResumeServiceImpl implements ResumeService {
    private final List<String> allowedExt = List.of("pdf");

    OpenAiConfig openAiConfig;

    UploadConfig uploadConfig;

    MessageWindowChatMemory chatMemory = MessageWindowChatMemory.withMaxMessages(100);

    OpenAiChatModel openaiModel;

    Interviewer interviewer;

    @Resource
    RedisTemplate<String, Object> redisTemplate;

    @Autowired
    ResumeServiceImpl(OpenAiConfig openAiConfig, UploadConfig uploadConfig) {
        this.openAiConfig = openAiConfig;
        this.uploadConfig = uploadConfig;

        openaiModel = OpenAiChatModel
                .builder()
                .modelName(openAiConfig.modelName)
                .apiKey(openAiConfig.apiKey)
                .baseUrl(openAiConfig.baseURL)
                .logRequests(openAiConfig.debug)
                .logResponses(openAiConfig.debug)
                .maxRetries(3)
                .timeout(Duration.ofSeconds(100))
                .build();

        interviewer = AiServices.builder(Interviewer.class)
                .chatLanguageModel(openaiModel)
                .chatMemory(chatMemory)
                .build();
    }


    @Override
    public Response<ResumeVo> parse(MultipartFile file) {
        if (ObjectUtil.isEmpty(file)) {
            return Response.failed(404, "上传文件不能为空").body();
        }
        try {
            // 上传
            var fileType = FileTypeUtil.getType(file.getInputStream());
            if (!allowedExt.contains(fileType.toLowerCase())) {
                return Response.failed(401, "pdf格式的简历").body();
            }
            //
            var uuid = IdUtil.simpleUUID();
            var fileName = uuid + ".pdf";
            var filepath = uploadConfig.path + "/pdf/" + DateUtil.today() + "/" + fileName;
            var path = Paths.get(filepath);
            if (!FileUtil.exist(path.toString())) {
                FileUtil.mkParentDirs(path);
            }
            file.transferTo(path);

            // pdf解析
            var document = FileSystemDocumentLoader.loadDocument(filepath, new ApachePdfBoxDocumentParser());
            var resumeText = document.text();

            // openai 格式化
            var extractor = AiServices.create(ResumeExtractor.class, openaiModel);
            var resume = extractor.extractPersonFrom(resumeText);

            log.info("解析简历成功: " + uuid);
            // 组装vo
            var resumeVo = new ResumeVo();
            resumeVo.uuid = uuid;
            resumeVo.setResume(resume);

            // 存入redis中
            redisTemplate.opsForHash().put(ResumeConstants.RESUME_KEY, resumeVo.uuid, resumeVo.getResume());

            return Response.ok().body(resumeVo);
        } catch (Exception e) {
            return Response.failed(500, e.getMessage()).body();
        }
    }

    @Override
    public String chat(String message) {
        return interviewer.chat(message);
    }
}
