import { requestClient } from '#/api/request';

export namespace MessageApi {
  export interface Remark {
    remarkId?: number;
    articleId?: number;
    userId?: number;
    userName?: string;
    content?: string;
    auditStatus?: string;
    auditTime?: string;
    auditBy?: string;
    createTime?: string;
  }

  export interface Email {
    emailId?: number;
    toEmail?: string;
    subject?: string;
    content?: string;
    sendStatus?: string;
    sendTime?: string;
    failReason?: string;
    sendBy?: string;
  }

  export interface Sms {
    smsId?: number;
    phone?: string;
    content?: string;
    sendStatus?: string;
    sendTime?: string;
    failReason?: string;
    retryCount?: number;
    sendBy?: string;
    smsProvider?: string;
  }
}

/** 用户留言 */
export async function getRemarkList(params: any) {
  return requestClient.get('/message/article/remark/list', { params });
}
export async function replyRemark(data: MessageApi.Remark) {
  return requestClient.put('/message/article/remark/reply', data);
}
export async function deleteRemark(remarkIds: number[]) {
  return requestClient.delete(`/message/article/remark/${remarkIds.join(',')}`);
}
export async function auditRemark(data: MessageApi.Remark) {
  return requestClient.put('/message/article/remark/audit', data);
}

/** 待发送邮件 */
export async function getEmailSendingList(params: any) {
  return requestClient.get('/message/email/sending', { params });
}

/** 已发送邮件 */
export async function getEmailSendedList(params: any) {
  return requestClient.get('/message/email/sended', { params });
}

/** 发送邮件 */
export async function sendEmail(data: MessageApi.Email) {
  return requestClient.post('/message/email/send', data);
}

/** 待发送短信 */
export async function getSmsSendingList(params: any) {
  return requestClient.get('/message/msg/sending', { params });
}

/** 已发送短信 */
export async function getSmsSendedList(params: any) {
  return requestClient.get('/message/msg/sended', { params });
}

/** 失败的短信 */
export async function getSmsFailedList(params: any) {
  return requestClient.get('/message/msg/failed', { params });
}

/** 发送短信 */
export async function sendSms(data: MessageApi.Sms) {
  return requestClient.post('/message/msg/send', data);
}

/** 测试发送短信（指定服务商） */
export async function testSendSms(data: MessageApi.Sms) {
  return requestClient.post('/message/msg/test', data);
}

/** 删除短信 */
export async function deleteSms(smsIds: number[]) {
  return requestClient.delete(`/message/msg/${smsIds.join(',')}`);
}