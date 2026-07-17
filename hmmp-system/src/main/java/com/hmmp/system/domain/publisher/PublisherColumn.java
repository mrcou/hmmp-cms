package com.hmmp.system.domain.publisher;

import java.math.BigDecimal;
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

    /** 栏目主键 */
    private Long columnId;

    /** 杂志编号 */
    private String journalCode;

    /** 栏目名称 */
    private String columnName;

    /** 英文名 */
    private String nameEn;

    /** 英文栏目（旧字段 column_en_name，对应 name_en） */
    private String columnEnName;

    /** 父栏目ID */
    private Long parentId;

    /** 排序号/顺序 */
    private Integer orderNum;

    /** 顺序（旧字段 sequence，对应 order_num） */
    private Integer sequence;

    /** 是否需要推荐人(0否 1是) */
    private String needRecommender;

    /** 使用范围(投稿、内部查阅、网刊发布) */
    private String usageScope;

    /** 使用范围(1投稿、内部查询、网刊发布 0网刊发布 2内部查询、网刊发布) */
    private String isPostColumn;

    /** 稿件编号前缀 */
    private String fileNoPrefix;

    /** 英文摘要加工费 */
    private BigDecimal enAbstractMoney;

    /** 编辑账号 */
    private String editName;

    /** 栏目编码（业务栏目ID，如 lcst001） */
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

    public String getJournalCode()
    {
        return journalCode;
    }

    public void setJournalCode(String journalCode)
    {
        this.journalCode = journalCode;
    }

    public String getColumnName()
    {
        return columnName;
    }

    public void setColumnName(String columnName)
    {
        this.columnName = columnName;
    }

    public String getNameEn()
    {
        return nameEn;
    }

    public void setNameEn(String nameEn)
    {
        this.nameEn = nameEn;
    }

    public String getColumnEnName()
    {
        return columnEnName;
    }

    public void setColumnEnName(String columnEnName)
    {
        this.columnEnName = columnEnName;
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

    public Integer getSequence()
    {
        return sequence;
    }

    public void setSequence(Integer sequence)
    {
        this.sequence = sequence;
    }

    public String getNeedRecommender()
    {
        return needRecommender;
    }

    public void setNeedRecommender(String needRecommender)
    {
        this.needRecommender = needRecommender;
    }

    public String getUsageScope()
    {
        return usageScope;
    }

    public void setUsageScope(String usageScope)
    {
        this.usageScope = usageScope;
    }

    public String getIsPostColumn()
    {
        return isPostColumn;
    }

    public void setIsPostColumn(String isPostColumn)
    {
        this.isPostColumn = isPostColumn;
    }

    public String getFileNoPrefix()
    {
        return fileNoPrefix;
    }

    public void setFileNoPrefix(String fileNoPrefix)
    {
        this.fileNoPrefix = fileNoPrefix;
    }

    public BigDecimal getEnAbstractMoney()
    {
        return enAbstractMoney;
    }

    public void setEnAbstractMoney(BigDecimal enAbstractMoney)
    {
        this.enAbstractMoney = enAbstractMoney;
    }

    public String getEditName()
    {
        return editName;
    }

    public void setEditName(String editName)
    {
        this.editName = editName;
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
            .append("journalCode", getJournalCode())
            .append("columnName", getColumnName())
            .append("nameEn", getNameEn())
            .append("columnEnName", getColumnEnName())
            .append("parentId", getParentId())
            .append("orderNum", getOrderNum())
            .append("sequence", getSequence())
            .append("needRecommender", getNeedRecommender())
            .append("usageScope", getUsageScope())
            .append("isPostColumn", getIsPostColumn())
            .append("fileNoPrefix", getFileNoPrefix())
            .append("enAbstractMoney", getEnAbstractMoney())
            .append("editName", getEditName())
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
