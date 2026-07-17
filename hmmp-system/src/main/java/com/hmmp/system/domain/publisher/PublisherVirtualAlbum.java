package com.hmmp.system.domain.publisher;

import java.util.Date;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.fasterxml.jackson.annotation.JsonFormat;
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

    /** 杂志编号 */
    private String journalCode;

    /** 中文名 */
    private String nameCn;

    /** 英文名 */
    private String nameEn;

    /** 完成日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date finishDate;

    /** 顺序 */
    private Integer orderNum;

    /** 类型 */
    private String albumType;

    /** 会议ID */
    private Long meetingId;

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

    public String getJournalCode()
    {
        return journalCode;
    }

    public void setJournalCode(String journalCode)
    {
        this.journalCode = journalCode;
    }

    public String getNameCn()
    {
        return nameCn;
    }

    public void setNameCn(String nameCn)
    {
        this.nameCn = nameCn;
    }

    public String getNameEn()
    {
        return nameEn;
    }

    public void setNameEn(String nameEn)
    {
        this.nameEn = nameEn;
    }

    public Date getFinishDate()
    {
        return finishDate;
    }

    public void setFinishDate(Date finishDate)
    {
        this.finishDate = finishDate;
    }

    public Integer getOrderNum()
    {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum)
    {
        this.orderNum = orderNum;
    }

    public String getAlbumType()
    {
        return albumType;
    }

    public void setAlbumType(String albumType)
    {
        this.albumType = albumType;
    }

    public Long getMeetingId()
    {
        return meetingId;
    }

    public void setMeetingId(Long meetingId)
    {
        this.meetingId = meetingId;
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
            .append("journalCode", getJournalCode())
            .append("nameCn", getNameCn())
            .append("nameEn", getNameEn())
            .append("finishDate", getFinishDate())
            .append("orderNum", getOrderNum())
            .append("albumType", getAlbumType())
            .append("meetingId", getMeetingId())
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
