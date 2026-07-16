package com.hmmp.system.service;

import java.util.List;
import com.hmmp.system.domain.message.MsgEmail;

/**
 * 邮件 服务层
 *
 * @author hmmp
 */
public interface IMsgEmailService
{
    public List<MsgEmail> selectEmailList(MsgEmail email);

    public MsgEmail selectEmailById(Long emailId);

    public int insertEmail(MsgEmail email);

    public int updateEmail(MsgEmail email);

    public int deleteEmailByIds(Long[] emailIds);
}