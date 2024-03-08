package io.github.k12f.aiinterview.domain.model;

import dev.langchain4j.model.output.structured.Description;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Data
public class Project implements Serializable {
    @Serial
    private static final long serialVersionUID = -4387300248968204939L;
    @Description("项目名称")
    public String projectName;

    @Description("项目角色")
    public String role;

    @Description("项目介绍")
    public String des;

    @Description("项目链接")
    public String url;

    @Description("项目持续时间")
    public String duration;

    @Description("项目描述")
    public List<String> jobDesc;

    @Description("项目业绩")
    public String performance;

    @Description("使用的技术")
    public String technology;
}
