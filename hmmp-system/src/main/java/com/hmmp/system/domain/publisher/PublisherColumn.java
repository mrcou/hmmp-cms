package com.hmmp.system.domain.publisher;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.hmmp.common.core.domain.BaseEntity;

/**
 * 期刊栏目对象 publisher_column
 * 
 * @author hmmp
 */
public class PublisherColumn extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 栏目ID */
    private Long columnId;

    /** 栏目名称 */
    private String columnName;

    /** 父栏目ID */
    private Long parentId;

    /** 排序号 */
    private Integer orderNum;

    /** 栏目编码 */
    private String columnCode;

    /** 栏目类型 */
    private String columnType;

    /** 状态 */
    private String status;

    /** 描述 */
    private String description;

    public Long getColumnId()
    {
        return columnId;
    }

    public void setColumnId(Long columnId)
    {
        this.columnId = columnId;
    }

    public String getColumnName()
    {
        return columnName;
    }

    public void setColumnName(String columnName)
    {
        this.columnName = columnName;
    }

    public Long getParentId()
    {
        return parentId;
    }

    public void setParentId(Long parentId)
    {
        this.parentId = parentId;
    }

    public Integer getOrderNum()
    {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum)
    {
        this.orderNum = orderNum;
    }

    public String getColumnCode()
    {
        return columnCode;
    }

    public void setColumnCode(String columnCode)
    {
        this.columnCode = columnCode;
    }

    public String getColumnType()
    {
        return columnType;
    }

    public void setColumnType(String columnType)
    {
        this.columnType = columnType;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("columnId", getColumnId())
            .append("columnName", getColumnName())
            .append("parentId", getParentId())
            .append("orderNum", getOrderNum())
            .append("columnCode", getColumnCode())
            .append("columnType", getColumnType())
            .append("status", getStatus())
            .append("description", getDescription())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}