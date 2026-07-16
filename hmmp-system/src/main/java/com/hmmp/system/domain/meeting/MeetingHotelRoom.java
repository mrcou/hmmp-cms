package com.hmmp.system.domain.meeting;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.hmmp.common.core.domain.BaseEntity;

/**
 * 酒店房型表 meeting_hotel_room
 *
 * @author hmmp
 */
public class MeetingHotelRoom extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    private Long roomId;
    private Long hotelId;
    private String roomType;
    private BigDecimal price;
    private Integer maxPeople;
    private Integer totalQty;
    private Integer availableQty;
    private String description;

    public Long getRoomId()
    {
        return roomId;
    }

    public void setRoomId(Long roomId)
    {
        this.roomId = roomId;
    }

    public Long getHotelId()
    {
        return hotelId;
    }

    public void setHotelId(Long hotelId)
    {
        this.hotelId = hotelId;
    }

    public String getRoomType()
    {
        return roomType;
    }

    public void setRoomType(String roomType)
    {
        this.roomType = roomType;
    }

    public BigDecimal getPrice()
    {
        return price;
    }

    public void setPrice(BigDecimal price)
    {
        this.price = price;
    }

    public Integer getMaxPeople()
    {
        return maxPeople;
    }

    public void setMaxPeople(Integer maxPeople)
    {
        this.maxPeople = maxPeople;
    }

    public Integer getTotalQty()
    {
        return totalQty;
    }

    public void setTotalQty(Integer totalQty)
    {
        this.totalQty = totalQty;
    }

    public Integer getAvailableQty()
    {
        return availableQty;
    }

    public void setAvailableQty(Integer availableQty)
    {
        this.availableQty = availableQty;
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
        return "MeetingHotelRoom{" +
            "roomId=" + roomId +
            ", hotelId=" + hotelId +
            ", roomType='" + roomType + '\'' +
            ", price=" + price +
            ", totalQty=" + totalQty +
            ", availableQty=" + availableQty +
            '}';
    }
}