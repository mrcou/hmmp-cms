package com.hmmp.system.domain.publisher;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.hmmp.common.core.domain.BaseEntity;

/**
 * 虚拟专辑对象 publisher_virtual_album
 * 
 * @author hmmp
 */
public class PublisherVirtualAlbum extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 专辑ID */
    private Long albumId;

    /** 专辑名称 */
    private String albumName;

    /** 专辑编码 */
    private String albumCode;

    /** 封面图片 */
    private String coverImage;

    /** 专辑描述 */
    private String description;

    /** 状态 */
    private String status;

    public Long getAlbumId()
    {
        return albumId;
    }

    public void setAlbumId(Long albumId)
    {
        this.albumId = albumId;
    }

    public String getAlbumName()
    {
        return albumName;
    }

    public void setAlbumName(String albumName)
    {
        this.albumName = albumName;
    }

    public String getAlbumCode()
    {
        return albumCode;
    }

    public void setAlbumCode(String albumCode)
    {
        this.albumCode = albumCode;
    }

    public String getCoverImage()
    {
        return coverImage;
    }

    public void setCoverImage(String coverImage)
    {
        this.coverImage = coverImage;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("albumId", getAlbumId())
            .append("albumName", getAlbumName())
            .append("albumCode", getAlbumCode())
            .append("coverImage", getCoverImage())
            .append("description", getDescription())
            .append("status", getStatus())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}