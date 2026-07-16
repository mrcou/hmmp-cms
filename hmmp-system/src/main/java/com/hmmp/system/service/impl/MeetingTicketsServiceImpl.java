package com.hmmp.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hmmp.system.domain.meeting.MeetingTickets;
import com.hmmp.system.mapper.meeting.MeetingTicketsMapper;
import com.hmmp.system.service.IMeetingTicketsService;

/**
 * 票务 服务层处理
 *
 * @author hmmp
 */
@Service
public class MeetingTicketsServiceImpl implements IMeetingTicketsService
{
    @Autowired
    private MeetingTicketsMapper ticketsMapper;

    @Override
    public List<MeetingTickets> selectTicketsList(MeetingTickets tickets)
    {
        return ticketsMapper.selectTicketsList(tickets);
    }

    @Override
    public MeetingTickets selectTicketsById(Long ticketId)
    {
        return ticketsMapper.selectTicketsById(ticketId);
    }

    @Override
    public int insertTickets(MeetingTickets tickets)
    {
        return ticketsMapper.insertTickets(tickets);
    }

    @Override
    public int updateTickets(MeetingTickets tickets)
    {
        return ticketsMapper.updateTickets(tickets);
    }

    @Override
    public int deleteTicketsByIds(Long[] ticketIds)
    {
        return ticketsMapper.deleteTicketsByIds(ticketIds);
    }
}