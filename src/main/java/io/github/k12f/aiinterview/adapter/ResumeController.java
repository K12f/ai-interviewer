package io.github.k12f.aiinterview.adapter;

import io.github.k12f.aiinterview.app.vo.ResumeVo;
import io.github.k12f.aiinterview.client.api.ResumeService;
import io.github.k12f.aiinterview.infra.util.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


@RequestMapping("/resume")
@Slf4j
@Tag(name = "简历工具")
@RestController
public class ResumeController {
    @Resource
    ResumeService resumeService;

    @PostMapping(path = "/parse", consumes = "multipart/*", headers = "content-type=multipart/form-data")
    @Operation(summary = "上传并解析pdf")
    public Response<ResumeVo> parse(@RequestParam("file") MultipartFile file) {
        return resumeService.parse(file);
    }

}
