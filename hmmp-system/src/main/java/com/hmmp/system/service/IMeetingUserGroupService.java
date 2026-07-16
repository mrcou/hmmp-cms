package com.hmmp.system.service;

import java.util.List;
import com.hmmp.system.domain.meeting.MeetingUserGroup;

/**
 * 用户类型 服务层
 *
 * @author hmmp
 */
public interface IMeetingUserGroupService
{
    public List<MeetingUserGroup> selectGroupList(MeetingUserGroup group);

    public MeetingUserGroup selectGroupById(Long groupId);

    public int insertGroup(MeetingUserGroup group);

    public int updateGroup(MeetingUserGroup group);

    public int deleteGroupByIds(Long[] groupIds);
}