package io.github.k12f.aiinterview.infra.brain.prompt;

import dev.langchain4j.service.UserMessage;
import io.github.k12f.aiinterview.domain.model.Resume;

/**
 * 简历pdf解构
 */
public interface ResumeExtractor {

    @UserMessage("从 {{it}} 中提取出一个人的简历信息,禁止出现```json这种形式,only provide a  RFC8259 compliant JSON response  following this format without deviation.")
    Resume extractPersonFrom(String text);
}
