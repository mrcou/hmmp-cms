package com.hmmp.system.service;

import java.util.List;
import com.hmmp.system.domain.meeting.MeetingHotelRoom;

/**
 * 酒店房型 服务层
 *
 * @author hmmp
 */
public interface IMeetingHotelRoomService
{
    public List<MeetingHotelRoom> selectRoomList(MeetingHotelRoom room);

    public MeetingHotelRoom selectRoomById(Long roomId);

    public int insertRoom(MeetingHotelRoom room);

    public int updateRoom(MeetingHotelRoom room);

    public int deleteRoomByIds(Long[] roomIds);
}