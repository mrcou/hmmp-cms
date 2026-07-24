package com.hmmp.system.domain.vo;

/**
 * 站点安全配置
 */
public class SiteSecurityConfigVo
{
    /** 密码复杂度验证：空=不验证，1=一般，2=中等，3=高强度 */
    private String passwordCheck;

    /** 新用户强制改密：空=否，1=是 */
    private String forcedPasswordChange;

    /** 密码使用期限(天)，0=不过期 */
    private Integer passwordLife;

    /** 密码尝试次数，1-10 */
    private Integer passwordErrorNum;

    /** 锁定时长(分钟)，0=直到管理员解除 */
    private Integer lockTime;

    /** 重置计数器(分钟) */
    private Integer resetCounter;

    /** 登录超时时间(分钟) */
    private Integer cookieExpires;

    /** 登录提醒：空=不开启，1=全部开启，2=用户自行选择 */
    private String loginReminder;

    /** 审计日志容量上限(G)，0或空=永久保留 */
    private Integer saveAuditLogDayNum;

    public String getPasswordCheck()
    {
        return passwordCheck;
    }

    public void setPasswordCheck(String passwordCheck)
    {
        this.passwordCheck = passwordCheck;
    }

    public String getForcedPasswordChange()
    {
        return forcedPasswordChange;
    }

    public void setForcedPasswordChange(String forcedPasswordChange)
    {
        this.forcedPasswordChange = forcedPasswordChange;
    }

    public Integer getPasswordLife()
    {
        return passwordLife;
    }

    public void setPasswordLife(Integer passwordLife)
    {
        this.passwordLife = passwordLife;
    }

    public Integer getPasswordErrorNum()
    {
        return passwordErrorNum;
    }

    public void setPasswordErrorNum(Integer passwordErrorNum)
    {
        this.passwordErrorNum = passwordErrorNum;
    }

    public Integer getLockTime()
    {
        return lockTime;
    }

    public void setLockTime(Integer lockTime)
    {
        this.lockTime = lockTime;
    }

    public Integer getResetCounter()
    {
        return resetCounter;
    }

    public void setResetCounter(Integer resetCounter)
    {
        this.resetCounter = resetCounter;
    }

    public Integer getCookieExpires()
    {
        return cookieExpires;
    }

    public void setCookieExpires(Integer cookieExpires)
    {
        this.cookieExpires = cookieExpires;
    }

    public String getLoginReminder()
    {
        return loginReminder;
    }

    public void setLoginReminder(String loginReminder)
    {
        this.loginReminder = loginReminder;
    }

    public Integer getSaveAuditLogDayNum()
    {
        return saveAuditLogDayNum;
    }

    public void setSaveAuditLogDayNum(Integer saveAuditLogDayNum)
    {
        this.saveAuditLogDayNum = saveAuditLogDayNum;
    }
}
