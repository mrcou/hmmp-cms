package com.hmmp.system.service;

import java.util.List;
import com.hmmp.system.domain.meeting.MeetingTickets;

/**
 * 票务 服务层
 *
 * @author hmmp
 */
public interface IMeetingTicketsService
{
    public List<MeetingTickets> selectTicketsList(MeetingTickets tickets);

    public MeetingTickets selectTicketsById(Long ticketId);

    public int insertTickets(MeetingTickets tickets);

    public int updateTickets(MeetingTickets tickets);

    public int deleteTicketsByIds(Long[] ticketIds);
}