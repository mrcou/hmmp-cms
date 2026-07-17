package com.hmmp.system.domain;

/**
 * 用户统计查询参数
 *
 * @author hmmp
 */
public class StatUserQuery
{
    /** 用户名（模糊） */
    private String userName;

    /** 姓名（模糊） */
    private String realName;

    /** 邮箱（模糊） */
    private String email;

    /**
     * 角色过滤：author 仅投稿用户 / reviewer 仅审稿用户 / 空表示全部
     */
    private String role;

    /** 起始日期 yyyy-MM-dd（作用于投稿收稿时间与审稿审回时间） */
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

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getRole()
    {
        return role;
    }

    public void setRole(String role)
    {
        this.role = role;
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
