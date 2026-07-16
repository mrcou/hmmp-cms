package com.hmmp.web.controller.meeting;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.hmmp.common.core.controller.BaseController;
import com.hmmp.common.core.domain.AjaxResult;
import com.hmmp.common.core.page.TableDataInfo;
import com.hmmp.system.domain.meeting.MeetingUser;
import com.hmmp.system.domain.meeting.MeetingUserGroup;
import com.hmmp.system.service.IMeetingUserGroupService;
import com.hmmp.system.service.IMeetingUserService;

/**
 * 参会代表/用户类型管理
 *
 * @author hmmp
 */
@RestController
@RequestMapping("/meeting/user")
public class MeetingUserController extends BaseController
{
    @Autowired
    private IMeetingUserService userService;

    @Autowired
    private IMeetingUserGroupService groupService;

    /**
     * 获取参会代表列表
     */
    @PreAuthorize("@ss.hasPermi('meeting:user:list')")
    @GetMapping("/list")
    public TableDataInfo list(MeetingUser user)
    {
        startPage();
        List<MeetingUser> list = userService.selectUserList(user);
        return getDataTable(list);
    }

    /**
     * 获取参会代表详细信息
     */
    @PreAuthorize("@ss.hasPermi('meeting:user:query')")
    @GetMapping(value = "/{userId}")
    public AjaxResult getInfo(@PathVariable Long userId)
    {
        return success(userService.selectUserById(userId));
    }

    /**
     * 新增参会代表
     */
    @PreAuthorize("@ss.hasPermi('meeting:user:add')")
    @PostMapping
    public AjaxResult add(@RequestBody MeetingUser user)
    {
        return toAjax(userService.insertUser(user));
    }

    /**
     * 修改参会代表
     */
    @PreAuthorize("@ss.hasPermi('meeting:user:edit')")
    @PutMapping
    public AjaxResult edit(@RequestBody MeetingUser user)
    {
        return toAjax(userService.updateUser(user));
    }

    /**
     * 删除参会代表
     */
    @PreAuthorize("@ss.hasPermi('meeting:user:remove')")
    @DeleteMapping("/{userIds}")
    public AjaxResult remove(@PathVariable Long[] userIds)
    {
        return toAjax(userService.deleteUserByIds(userIds));
    }

    /**
     * 获取用户类型列表
     */
    @PreAuthorize("@ss.hasPermi('meeting:user:group:list')")
    @GetMapping("/group/list")
    public TableDataInfo groupList(MeetingUserGroup group)
    {
        startPage();
        List<MeetingUserGroup> list = groupService.selectGroupList(group);
        return getDataTable(list);
    }

    /**
     * 获取用户类型详细信息
     */
    @PreAuthorize("@ss.hasPermi('meeting:user:group:query')")
    @GetMapping(value = "/group/{groupId}")
    public AjaxResult getGroupInfo(@PathVariable Long groupId)
    {
        return success(groupService.selectGroupById(groupId));
    }

    /**
     * 新增用户类型
     */
    @PreAuthorize("@ss.hasPermi('meeting:user:group:add')")
    @PostMapping("/group")
    public AjaxResult addGroup(@RequestBody MeetingUserGroup group)
    {
        return toAjax(groupService.insertGroup(group));
    }

    /**
     * 修改用户类型
     */
    @PreAuthorize("@ss.hasPermi('meeting:user:group:edit')")
    @PutMapping("/group")
    public AjaxResult editGroup(@RequestBody MeetingUserGroup group)
    {
        return toAjax(groupService.updateGroup(group));
    }

    /**
     * 删除用户类型
     */
    @PreAuthorize("@ss.hasPermi('meeting:user:group:remove')")
    @DeleteMapping("/group/{groupIds}")
    public AjaxResult removeGroup(@PathVariable Long[] groupIds)
    {
        return toAjax(groupService.deleteGroupByIds(groupIds));
    }
}