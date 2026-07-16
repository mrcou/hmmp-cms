package com.hmmp.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hmmp.system.domain.message.MsgEmail;
import com.hmmp.system.mapper.message.MsgEmailMapper;
import com.hmmp.system.service.IMsgEmailService;

/**
 * 邮件 服务层处理
 *
 * @author hmmp
 */
@Service
public class MsgEmailServiceImpl implements IMsgEmailService
{
    @Autowired
    private MsgEmailMapper emailMapper;

    @Override
    public List<MsgEmail> selectEmailList(MsgEmail email)
    {
        return emailMapper.selectEmailList(email);
    }

    @Override
    public MsgEmail selectEmailById(Long emailId)
    {
        return emailMapper.selectEmailById(emailId);
    }

    @Override
    public int insertEmail(MsgEmail email)
    {
        return emailMapper.insertEmail(email);
    }

    @Override
    public int updateEmail(MsgEmail email)
    {
        return emailMapper.updateEmail(email);
    }

    @Override
    public int deleteEmailByIds(Long[] emailIds)
    {
        return emailMapper.deleteEmailByIds(emailIds);
    }
}