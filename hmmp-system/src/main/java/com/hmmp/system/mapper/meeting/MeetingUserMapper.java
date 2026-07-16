package com.hmmp.system.mapper.meeting;

import java.util.List;
import com.hmmp.system.domain.meeting.MeetingUser;

/**
 * 参会代表表 数据层
 *
 * @author hmmp
 */
public interface MeetingUserMapper
{
    public List<MeetingUser> selectUserList(MeetingUser user);

    public MeetingUser selectUserById(Long userId);

    public int insertUser(MeetingUser user);

    public int updateUser(MeetingUser user);

    public int deleteUserById(Long userId);

    public int deleteUserByIds(Long[] userIds);
}