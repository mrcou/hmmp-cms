package com.hmmp.system.service;

import java.util.List;
import com.hmmp.system.domain.meeting.MeetingUser;

/**
 * 参会代表 服务层
 *
 * @author hmmp
 */
public interface IMeetingUserService
{
    public List<MeetingUser> selectUserList(MeetingUser user);

    public MeetingUser selectUserById(Long userId);

    public int insertUser(MeetingUser user);

    public int updateUser(MeetingUser user);

    public int deleteUserByIds(Long[] userIds);
}