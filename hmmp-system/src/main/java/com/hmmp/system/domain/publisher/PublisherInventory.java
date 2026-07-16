package com.hmmp.system.domain.publisher;

import java.util.Date;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.hmmp.common.core.domain.BaseEntity;

/**
 * 期刊库存对象 publisher_inventory
 * 
 * @author hmmp
 */
public class PublisherInventory extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 库存ID */
    private Long inventoryId;

    /** 刊期ID */
    private Long issueId;

    /** 年份 */
    private Integer year;

    /** 期数 */
    private Integer period;

    /** 库存量 */
    private Integer stockQty;

    /** 已售数量 */
    private Integer soldQty;

    /** 已分配数量 */
    private Integer allocatedQty;

    /** 仓库 */
    private String warehouse;

    /** 库位 */
    private String location;

    public Long getInventoryId()
    {
        return inventoryId;
    }

    public void setInventoryId(Long inventoryId)
    {
        this.inventoryId = inventoryId;
    }

    public Long getIssueId()
    {
        return issueId;
    }

    public void setIssueId(Long issueId)
    {
        this.issueId = issueId;
    }

    public Integer getYear()
    {
        return year;
    }

    public void setYear(Integer year)
    {
        this.year = year;
    }

    public Integer getPeriod()
    {
        return period;
    }

    public void setPeriod(Integer period)
    {
        this.period = period;
    }

    public Integer getStockQty()
    {
        return stockQty;
    }

    public void setStockQty(Integer stockQty)
    {
        this.stockQty = stockQty;
    }

    public Integer getSoldQty()
    {
        return soldQty;
    }

    public void setSoldQty(Integer soldQty)
    {
        this.soldQty = soldQty;
    }

    public Integer getAllocatedQty()
    {
        return allocatedQty;
    }

    public void setAllocatedQty(Integer allocatedQty)
    {
        this.allocatedQty = allocatedQty;
    }

    public String getWarehouse()
    {
        return warehouse;
    }

    public void setWarehouse(String warehouse)
    {
        this.warehouse = warehouse;
    }

    public String getLocation()
    {
        return location;
    }

    public void setLocation(String location)
    {
        this.location = location;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("inventoryId", getInventoryId())
            .append("issueId", getIssueId())
            .append("year", getYear())
            .append("period", getPeriod())
            .append("stockQty", getStockQty())
            .append("soldQty", getSoldQty())
            .append("allocatedQty", getAllocatedQty())
            .append("warehouse", getWarehouse())
            .append("location", getLocation())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}