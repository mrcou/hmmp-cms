package com.hmmp.web.controller.message;

import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.hmmp.common.core.controller.BaseController;
import com.hmmp.common.core.domain.AjaxResult;
import com.hmmp.common.core.page.TableDataInfo;
import com.hmmp.system.domain.message.MsgRemark;
import com.hmmp.system.service.IMsgRemarkService;

/**
 * 留言管理
 *
 * @author hmmp
 */
@RestController
@RequestMapping("/message/article/remark")
public class MsgRemarkController extends BaseController
{
    @Autowired
    private IMsgRemarkService remarkService;

    /**
     * 获取用户留言列表
     */
    @PreAuthorize("@ss.hasPermi('message:remark:list')")
    @GetMapping("/list")
    public TableDataInfo list(MsgRemark remark)
    {
        startPage();
        List<MsgRemark> list = remarkService.selectRemarkList(remark);
        return getDataTable(list);
    }

    /**
     * 回复留言
     */
    @PreAuthorize("@ss.hasPermi('message:remark:reply')")
    @PutMapping("/reply")
    public AjaxResult reply(@RequestBody MsgRemark remark)
    {
        remark.setReplyBy(getUsername());
        remark.setReplyTime(new Date());
        remark.setStatus("1");
        return toAjax(remarkService.updateRemark(remark));
    }

    /**
     * 删除留言
     */
    @PreAuthorize("@ss.hasPermi('message:remark:remove')")
    @DeleteMapping("/{remarkIds}")
    public AjaxResult remove(@PathVariable Long[] remarkIds)
    {
        return toAjax(remarkService.deleteRemarkByIds(remarkIds));
    }
}