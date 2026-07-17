package com.hmmp.system.domain;

/**
 * 审稿统计查询参数
 *
 * @author hmmp
 */
public class StatReviewerAuditQuery
{
    /** 用户名（模糊） */
    private String userName;

    /** 姓名（模糊） */
    private String realName;

    /** 单位（模糊） */
    private String affiliation;

    /** 邮箱（模糊） */
    private String email;

    /** 手机（模糊） */
    private String mobile;

    /** 审稿领域（模糊） */
    private String expertiseFields;

    /** 查询关键词（模糊，匹配研究方向） */
    private String searchKeywords;

    /** 权限级别 */
    private String permissionLevel;

    /** 起始日期 yyyy-MM-dd（作用于审回/邀请时间） */
    private String beginTime;

    /** 截止日期 yyyy-MM-dd */
    private String endTime;

    public String getUserName()
    {
        return userName;
    }

    public void setUserName(String userName)
    {
        this.userName = userName;
    }

    public String getRealName()
    {
        return realName;
    }

    public void setRealName(String realName)
    {
        this.realName = realName;
    }

    public String getAffiliation()
    {
        return affiliation;
    }

    public void setAffiliation(String affiliation)
    {
        this.affiliation = affiliation;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getMobile()
    {
        return mobile;
    }

    public void setMobile(String mobile)
    {
        this.mobile = mobile;
    }

    public String getExpertiseFields()
    {
        return expertiseFields;
    }

    public void setExpertiseFields(String expertiseFields)
    {
        this.expertiseFields = expertiseFields;
    }

    public String getSearchKeywords()
    {
        return searchKeywords;
    }

    public void setSearchKeywords(String searchKeywords)
    {
        this.searchKeywords = searchKeywords;
    }

    public String getPermissionLevel()
    {
        return permissionLevel;
    }

    public void setPermissionLevel(String permissionLevel)
    {
        this.permissionLevel = permissionLevel;
    }

    public String getBeginTime()
    {
        return beginTime;
    }

    public void setBeginTime(String beginTime)
    {
        this.beginTime = beginTime;
    }

    public String getEndTime()
    {
        return endTime;
    }

    public void setEndTime(String endTime)
    {
        this.endTime = endTime;
    }
}
