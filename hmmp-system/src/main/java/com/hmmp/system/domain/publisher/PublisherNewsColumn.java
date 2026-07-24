package com.hmmp.system.domain.publisher;

import com.hmmp.common.core.domain.BaseEntity;

/**
 * 新闻栏目对象 publisher_news_column
 */
public class PublisherNewsColumn extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 栏目ID */
    private Long columnId;

    /** 栏目名称 */
    private String columnName;

    /** 排序号，数值越小越靠前 */
    private Integer sortOrder;

    /** 关联内容数（列表查询字段） */
    private Integer contentCount;

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

    public Integer getSortOrder()
    {
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder)
    {
        this.sortOrder = sortOrder;
    }

    public Integer getContentCount()
    {
        return contentCount;
    }

    public void setContentCount(Integer contentCount)
    {
        this.contentCount = contentCount;
    }
}
