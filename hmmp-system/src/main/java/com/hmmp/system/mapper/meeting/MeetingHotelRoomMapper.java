package com.hmmp.system.mapper.meeting;

import java.util.List;
import com.hmmp.system.domain.meeting.MeetingHotelRoom;

/**
 * 酒店房型表 数据层
 *
 * @author hmmp
 */
public interface MeetingHotelRoomMapper
{
    public List<MeetingHotelRoom> selectRoomList(MeetingHotelRoom room);

    public MeetingHotelRoom selectRoomById(Long roomId);

    public int insertRoom(MeetingHotelRoom room);

    public int updateRoom(MeetingHotelRoom room);

    public int deleteRoomById(Long roomId);

    public int deleteRoomByIds(Long[] roomIds);
}