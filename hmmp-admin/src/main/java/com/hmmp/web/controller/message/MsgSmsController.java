package com.hmmp.web.controller.message;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.hmmp.common.annotation.Log;
import com.hmmp.common.core.controller.BaseController;
import com.hmmp.common.core.domain.AjaxResult;
import com.hmmp.common.core.page.TableDataInfo;
import com.hmmp.common.enums.BusinessType;
import com.hmmp.system.domain.message.MsgSms;
import com.hmmp.system.service.IMsgSmsService;
import com.hmmp.system.service.impl.MsgSmsSendService;

/**
 * 短信管理
 *
 * @author hmmp
 */
@RestController
@RequestMapping("/message/msg")
public class MsgSmsController extends BaseController
{
    @Autowired
    private IMsgSmsService smsService;

    @Autowired
    private MsgSmsSendService smsSendService;

    /**
     * 获取待发送短信列表
     */
    @PreAuthorize("@ss.hasPermi('message:sms:list')")
    @GetMapping("/sending")
    public TableDataInfo sending(MsgSms sms)
    {
        startPage();
        sms.setSendStatus("0");
        List<MsgSms> list = smsService.selectSmsList(sms);
        return getDataTable(list);
    }

    /**
     * 获取已发送短信列表
     */
    @PreAuthorize("@ss.hasPermi('message:sms:list')")
    @GetMapping("/sended")
    public TableDataInfo sended(MsgSms sms)
    {
        startPage();
        sms.setSendStatus("1");
        List<MsgSms> list = smsService.selectSmsList(sms);
        return getDataTable(list);
    }

    /**
     * 获取失败的短信列表
     */
    @PreAuthorize("@ss.hasPermi('message:sms:list')")
    @GetMapping("/failed")
    public TableDataInfo failed(MsgSms sms)
    {
        startPage();
        sms.setSendStatus("2");
        List<MsgSms> list = smsService.selectSmsList(sms);
        return getDataTable(list);
    }

    /**
     * 发送短信
     */
    @PreAuthorize("@ss.hasPermi('message:msg:send')")
    @Log(title = "短信发送", businessType = BusinessType.INSERT)
    @PostMapping("/send")
    public AjaxResult send(@RequestBody MsgSms sms)
    {
        sms.setSendBy(getUsername());
        if (sms.getSmsProvider() != null && !sms.getSmsProvider().isEmpty())
        {
            return smsSendService.sendSms(sms, sms.getSmsProvider());
        }
        return smsSendService.sendSms(sms);
    }

    /**
     * 测试发送短信（可选阿里云/腾讯云/火山云）
     */
    @PreAuthorize("@ss.hasPermi('message:msg:test')")
    @Log(title = "短信测试发送", businessType = BusinessType.INSERT)
    @PostMapping("/test")
    public AjaxResult test(@RequestBody MsgSms sms)
    {
        sms.setSendBy(getUsername());
        String provider = sms.getSmsProvider();
        if (provider == null || provider.isEmpty())
        {
            provider = "aliyun";
            sms.setSmsProvider(provider);
        }
        return smsSendService.sendSms(sms, provider);
    }

    /**
     * 删除短信
     */
    @PreAuthorize("@ss.hasPermi('message:sms:remove')")
    @Log(title = "短信管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{smsIds}")
    public AjaxResult remove(@PathVariable Long[] smsIds)
    {
        return toAjax(smsService.deleteSmsByIds(smsIds));
    }
}