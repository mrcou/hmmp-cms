package com.hmmp.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.hmmp.common.core.domain.BaseEntity;

/**
 * 广告位对象 ad_position
 * 
 * @author hmmp
 */
public class AdPosition extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 广告位ID */
    private Long positionId;

    /** 广告位名称 */
    private String positionName;

    /** 广告位类型ID */
    private Long typeId;

    /** 广告位类型名称 */
    private String typeName;

    /** 宽度 */
    private Integer width;

    /** 高度 */
    private Integer height;

    /** 广告位编码 */
    private String positionCode;

    /** 页面位置 */
    private String pageLocation;

    /** 描述 */
    private String description;

    /** 状态 */
    private String status;

    /** 排序号 */
    private Integer sortOrder;

    public Long getPositionId()
    {
        return positionId;
    }

    public void setPositionId(Long positionId)
    {
        this.positionId = positionId;
    }

    public String getPositionName()
    {
        return positionName;
    }

    public void setPositionName(String positionName)
    {
        this.positionName = positionName;
    }

    public Long getTypeId()
    {
        return typeId;
    }

    public void setTypeId(Long typeId)
    {
        this.typeId = typeId;
    }

    public String getTypeName()
    {
        return typeName;
    }

    public void setTypeName(String typeName)
    {
        this.typeName = typeName;
    }

    public Integer getWidth()
    {
        return width;
    }

    public void setWidth(Integer width)
    {
        this.width = width;
    }

    public Integer getHeight()
    {
        return height;
    }

    public void setHeight(Integer height)
    {
        this.height = height;
    }

    public String getPositionCode()
    {
        return positionCode;
    }

    public void setPositionCode(String positionCode)
    {
        this.positionCode = positionCode;
    }

    public String getPageLocation()
    {
        return pageLocation;
    }

    public void setPageLocation(String pageLocation)
    {
        this.pageLocation = pageLocation;
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

    public Integer getSortOrder()
    {
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder)
    {
        this.sortOrder = sortOrder;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("positionId", getPositionId())
            .append("positionName", getPositionName())
            .append("typeId", getTypeId())
            .append("typeName", getTypeName())
            .append("width", getWidth())
            .append("height", getHeight())
            .append("positionCode", getPositionCode())
            .append("pageLocation", getPageLocation())
            .append("description", getDescription())
            .append("status", getStatus())
            .append("sortOrder", getSortOrder())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}