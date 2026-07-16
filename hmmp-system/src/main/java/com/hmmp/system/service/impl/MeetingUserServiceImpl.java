package com.hmmp.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hmmp.system.domain.meeting.MeetingUser;
import com.hmmp.system.mapper.meeting.MeetingUserMapper;
import com.hmmp.system.service.IMeetingUserService;

/**
 * 参会代表 服务层处理
 *
 * @author hmmp
 */
@Service
public class MeetingUserServiceImpl implements IMeetingUserService
{
    @Autowired
    private MeetingUserMapper userMapper;

    @Override
    public List<MeetingUser> selectUserList(MeetingUser user)
    {
        return userMapper.selectUserList(user);
    }

    @Override
    public MeetingUser selectUserById(Long userId)
    {
        return userMapper.selectUserById(userId);
    }

    @Override
    public int insertUser(MeetingUser user)
    {
        return userMapper.insertUser(user);
    }

    @Override
    public int updateUser(MeetingUser user)
    {
        return userMapper.updateUser(user);
    }

    @Override
    public int deleteUserByIds(Long[] userIds)
    {
        return userMapper.deleteUserByIds(userIds);
    }
}