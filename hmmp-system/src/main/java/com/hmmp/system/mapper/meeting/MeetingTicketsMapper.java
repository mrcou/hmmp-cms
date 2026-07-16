package com.hmmp.system.mapper.meeting;

import java.util.List;
import com.hmmp.system.domain.meeting.MeetingTickets;

/**
 * 票务表 数据层
 *
 * @author hmmp
 */
public interface MeetingTicketsMapper
{
    public List<MeetingTickets> selectTicketsList(MeetingTickets tickets);

    public MeetingTickets selectTicketsById(Long ticketId);

    public int insertTickets(MeetingTickets tickets);

    public int updateTickets(MeetingTickets tickets);

    public int deleteTicketsById(Long ticketId);

    public int deleteTicketsByIds(Long[] ticketIds);
}