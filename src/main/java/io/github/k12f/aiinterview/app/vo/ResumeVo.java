package io.github.k12f.aiinterview.app.vo;

import io.github.k12f.aiinterview.domain.model.Resume;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class ResumeVo implements Serializable {

    @Serial
    private static final long serialVersionUID = -4588338734444563706L;
    public String uuid;

    private Resume resume;
}
