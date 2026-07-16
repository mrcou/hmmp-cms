package com.hmmp.web.controller.message;

import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.hmmp.common.core.controller.BaseController;
import com.hmmp.common.core.domain.AjaxResult;
import com.hmmp.common.core.page.TableDataInfo;
import com.hmmp.system.domain.message.MsgEmail;
import com.hmmp.system.service.IMsgEmailService;

/**
 * 邮件管理
 *
 * @author hmmp
 */
@RestController
@RequestMapping("/message/email")
public class MsgEmailController extends BaseController
{
    @Autowired
    private IMsgEmailService emailService;

    /**
     * 获取待发送邮件列表
     */
    @PreAuthorize("@ss.hasPermi('message:email:list')")
    @GetMapping("/sending")
    public TableDataInfo sending(MsgEmail email)
    {
        startPage();
        email.setSendStatus("0");
        List<MsgEmail> list = emailService.selectEmailList(email);
        return getDataTable(list);
    }

    /**
     * 获取已发送邮件列表
     */
    @PreAuthorize("@ss.hasPermi('message:email:list')")
    @GetMapping("/sended")
    public TableDataInfo sended(MsgEmail email)
    {
        startPage();
        email.setSendStatus("1");
        List<MsgEmail> list = emailService.selectEmailList(email);
        return getDataTable(list);
    }

    /**
     * 发送邮件
     */
    @PreAuthorize("@ss.hasPermi('message:email:send')")
    @PostMapping("/send")
    public AjaxResult send(@RequestBody MsgEmail email)
    {
        email.setCreateBy(getUsername());
        email.setSendStatus("0");
        email.setSendTime(new Date());
        return toAjax(emailService.insertEmail(email));
    }
}