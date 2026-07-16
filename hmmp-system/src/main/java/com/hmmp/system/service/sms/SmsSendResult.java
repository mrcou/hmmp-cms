package com.hmmp.system.service.sms;

/**
 * 短信发送结果
 * 
 * @author hmmp
 */
public class SmsSendResult
{
    /** 是否成功 */
    private boolean success;

    /** 消息ID */
    private String messageId;

    /** 错误码 */
    private String errorCode;

    /** 错误信息 */
    private String errorMessage;

    public static SmsSendResult success(String messageId)
    {
        SmsSendResult result = new SmsSendResult();
        result.setSuccess(true);
        result.setMessageId(messageId);
        return result;
    }

    public static SmsSendResult fail(String errorCode, String errorMessage)
    {
        SmsSendResult result = new SmsSendResult();
        result.setSuccess(false);
        result.setErrorCode(errorCode);
        result.setErrorMessage(errorMessage);
        return result;
    }

    public boolean isSuccess()
    {
        return success;
    }

    public void setSuccess(boolean success)
    {
        this.success = success;
    }

    public String getMessageId()
    {
        return messageId;
    }

    public void setMessageId(String messageId)
    {
        this.messageId = messageId;
    }

    public String getErrorCode()
    {
        return errorCode;
    }

    public void setErrorCode(String errorCode)
    {
        this.errorCode = errorCode;
    }

    public String getErrorMessage()
    {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage)
    {
        this.errorMessage = errorMessage;
    }
}