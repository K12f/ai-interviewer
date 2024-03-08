package io.github.k12f.aiinterview.client.api;

import io.github.k12f.aiinterview.app.vo.ResumeVo;
import io.github.k12f.aiinterview.infra.util.Response;
import org.springframework.web.multipart.MultipartFile;

public interface ResumeService {
    Response<ResumeVo> parse(MultipartFile file);

    String chat(String message);
}
