package com.hmmp.system.mapper.meeting;

import java.util.List;
import com.hmmp.system.domain.meeting.MeetingUserGroup;

/**
 * 参会用户类型表 数据层
 *
 * @author hmmp
 */
public interface MeetingUserGroupMapper
{
    public List<MeetingUserGroup> selectGroupList(MeetingUserGroup group);

    public MeetingUserGroup selectGroupById(Long groupId);

    public int insertGroup(MeetingUserGroup group);

    public int updateGroup(MeetingUserGroup group);

    public int deleteGroupById(Long groupId);

    public int deleteGroupByIds(Long[] groupIds);
}