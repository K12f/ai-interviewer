package io.github.k12f.aiinterview.domain.model;

import dev.langchain4j.model.output.structured.Description;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Data
public class Company implements Serializable {
    @Serial
    private static final long serialVersionUID = -5921258886168962976L;
    @Description("公司名称")
    public String companyName;

    @Description("担任职位")
    public String position;

    @Description("所属部门")
    public String department;

    @Description("在职时间")
    public String duration;

    @Description("工作内容")
    public List<String> jobDesc;

    @Description("工作业绩")
    public String performance;

    @Description("使用的技术/拥有的技能")
    public String kills;
}
