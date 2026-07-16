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
import com.hmmp.system.domain.meeting.MeetingMeeting;
import com.hmmp.system.service.IMeetingService;

/**
 * 会议管理
 *
 * @author hmmp
 */
@RestController
@RequestMapping("/meeting/meeting")
public class MeetingController extends BaseController
{
    @Autowired
    private IMeetingService meetingService;

    /**
     * 获取会议列表
     */
    @PreAuthorize("@ss.hasPermi('meeting:meeting:list')")
    @GetMapping("/list")
    public TableDataInfo list(MeetingMeeting meeting)
    {
        startPage();
        List<MeetingMeeting> list = meetingService.selectMeetingList(meeting);
        return getDataTable(list);
    }

    /**
     * 获取会议详细信息
     */
    @PreAuthorize("@ss.hasPermi('meeting:meeting:query')")
    @GetMapping(value = "/{meetingId}")
    public AjaxResult getInfo(@PathVariable Long meetingId)
    {
        return success(meetingService.selectMeetingById(meetingId));
    }

    /**
     * 新增会议
     */
    @PreAuthorize("@ss.hasPermi('meeting:meeting:add')")
    @PostMapping
    public AjaxResult add(@RequestBody MeetingMeeting meeting)
    {
        meeting.setCreateBy(getUsername());
        return toAjax(meetingService.insertMeeting(meeting));
    }

    /**
     * 修改会议
     */
    @PreAuthorize("@ss.hasPermi('meeting:meeting:edit')")
    @PutMapping
    public AjaxResult edit(@RequestBody MeetingMeeting meeting)
    {
        meeting.setUpdateBy(getUsername());
        return toAjax(meetingService.updateMeeting(meeting));
    }

    /**
     * 删除会议
     */
    @PreAuthorize("@ss.hasPermi('meeting:meeting:remove')")
    @DeleteMapping("/{meetingIds}")
    public AjaxResult remove(@PathVariable Long[] meetingIds)
    {
        return toAjax(meetingService.deleteMeetingByIds(meetingIds));
    }
}