package com.hmmp.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hmmp.system.domain.meeting.MeetingHotelRoom;
import com.hmmp.system.mapper.meeting.MeetingHotelRoomMapper;
import com.hmmp.system.service.IMeetingHotelRoomService;

/**
 * 酒店房型 服务层处理
 *
 * @author hmmp
 */
@Service
public class MeetingHotelRoomServiceImpl implements IMeetingHotelRoomService
{
    @Autowired
    private MeetingHotelRoomMapper roomMapper;

    @Override
    public List<MeetingHotelRoom> selectRoomList(MeetingHotelRoom room)
    {
        return roomMapper.selectRoomList(room);
    }

    @Override
    public MeetingHotelRoom selectRoomById(Long roomId)
    {
        return roomMapper.selectRoomById(roomId);
    }

    @Override
    public int insertRoom(MeetingHotelRoom room)
    {
        return roomMapper.insertRoom(room);
    }

    @Override
    public int updateRoom(MeetingHotelRoom room)
    {
        return roomMapper.updateRoom(room);
    }

    @Override
    public int deleteRoomByIds(Long[] roomIds)
    {
        return roomMapper.deleteRoomByIds(roomIds);
    }
}