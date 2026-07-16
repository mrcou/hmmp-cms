package com.hmmp.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hmmp.system.domain.meeting.MeetingUserGroup;
import com.hmmp.system.mapper.meeting.MeetingUserGroupMapper;
import com.hmmp.system.service.IMeetingUserGroupService;

/**
 * 用户类型 服务层处理
 *
 * @author hmmp
 */
@Service
public class MeetingUserGroupServiceImpl implements IMeetingUserGroupService
{
    @Autowired
    private MeetingUserGroupMapper groupMapper;

    @Override
    public List<MeetingUserGroup> selectGroupList(MeetingUserGroup group)
    {
        return groupMapper.selectGroupList(group);
    }

    @Override
    public MeetingUserGroup selectGroupById(Long groupId)
    {
        return groupMapper.selectGroupById(groupId);
    }

    @Override
    public int insertGroup(MeetingUserGroup group)
    {
        return groupMapper.insertGroup(group);
    }

    @Override
    public int updateGroup(MeetingUserGroup group)
    {
        return groupMapper.updateGroup(group);
    }

    @Override
    public int deleteGroupByIds(Long[] groupIds)
    {
        return groupMapper.deleteGroupByIds(groupIds);
    }
}