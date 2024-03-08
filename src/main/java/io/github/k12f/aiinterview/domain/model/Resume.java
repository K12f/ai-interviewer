package io.github.k12f.aiinterview.domain.model;

import dev.langchain4j.model.output.structured.Description;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Data
public class Resume implements Serializable {

    @Serial
    private static final long serialVersionUID = -1210734983631927737L;
    @Description("姓名")
    public String name;

    @Description("性别")
    public String gender;

    @Description("是否在岗/离职状态/在职")
    public String onJob;

    @Description("出生日期")
    public String birthday;

    @Description("年龄")
    public Integer age;

    @Description("居住地")
    public String location;

    @Description("学历")
    public String degree;

    @Description("工作经验")
    public String experience;

    @Description("手机号码")
    public String mobile;

    @Description("邮箱")
    public String email;

    @Description("微信号")
    public String wechat;

    @Description("其他联系方式")
    public String otherContact;

    @Description("教育经历")
    public String education;

    @Description("个⼈技能列表,个人优势,所有的能力详细描述")
    public List<String> powerList;

    @Description("工作经历")
    public List<Company> companyList;

    @Description("项目经历")
    public List<Project> projectList;
}
