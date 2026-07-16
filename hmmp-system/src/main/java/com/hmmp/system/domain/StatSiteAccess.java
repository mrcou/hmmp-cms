package com.hmmp.system.domain;

import java.util.Date;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.hmmp.common.core.domain.BaseEntity;

/**
 * 网站访问日志实体 stat_site_access
 * 
 * @author hmmp
 */
public class StatSiteAccess extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 访问ID */
    private Long accessId;

    /** 用户ID */
    private Long userId;

    /** 用户名 */
    private String userName;

    /** 访问页面URL */
    private String pageUrl;

    /** 页面名称 */
    private String pageName;

    /** 来源URL */
    private String refererUrl;

    /** 访问IP */
    private String ipAddr;

    /** 浏览器 */
    private String browser;

    /** 操作系统 */
    private String os;

    /** 访问时间 */
    private Date accessTime;

    /** 停留时间(秒) */
    private Integer stayTime;

    public Long getAccessId()
    {
        return accessId;
    }

    public void setAccessId(Long accessId)
    {
        this.accessId = accessId;
    }

    public Long getUserId()
    {
        return userId;
    }

    public void setUserId(Long userId)
    {
        this.userId = userId;
    }

    public String getUserName()
    {
        return userName;
    }

    public void setUserName(String userName)
    {
        this.userName = userName;
    }

    public String getPageUrl()
    {
        return pageUrl;
    }

    public void setPageUrl(String pageUrl)
    {
        this.pageUrl = pageUrl;
    }

    public String getPageName()
    {
        return pageName;
    }

    public void setPageName(String pageName)
    {
        this.pageName = pageName;
    }

    public String getRefererUrl()
    {
        return refererUrl;
    }

    public void setRefererUrl(String refererUrl)
    {
        this.refererUrl = refererUrl;
    }

    public String getIpAddr()
    {
        return ipAddr;
    }

    public void setIpAddr(String ipAddr)
    {
        this.ipAddr = ipAddr;
    }

    public String getBrowser()
    {
        return browser;
    }

    public void setBrowser(String browser)
    {
        this.browser = browser;
    }

    public String getOs()
    {
        return os;
    }

    public void setOs(String os)
    {
        this.os = os;
    }

    public Date getAccessTime()
    {
        return accessTime;
    }

    public void setAccessTime(Date accessTime)
    {
        this.accessTime = accessTime;
    }

    public Integer getStayTime()
    {
        return stayTime;
    }

    public void setStayTime(Integer stayTime)
    {
        this.stayTime = stayTime;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("accessId", getAccessId())
            .append("userId", getUserId())
            .append("userName", getUserName())
            .append("pageUrl", getPageUrl())
            .append("pageName", getPageName())
            .append("refererUrl", getRefererUrl())
            .append("ipAddr", getIpAddr())
            .append("browser", getBrowser())
            .append("os", getOs())
            .append("accessTime", getAccessTime())
            .append("stayTime", getStayTime())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .toString();
    }
}