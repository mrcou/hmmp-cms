package com.hmmp.system.mapper.message;

import java.util.List;
import com.hmmp.system.domain.message.MsgEmail;

/**
 * 邮件表 数据层
 *
 * @author hmmp
 */
public interface MsgEmailMapper
{
    public List<MsgEmail> selectEmailList(MsgEmail email);

    public MsgEmail selectEmailById(Long emailId);

    public int insertEmail(MsgEmail email);

    public int updateEmail(MsgEmail email);

    public int deleteEmailById(Long emailId);

    public int deleteEmailByIds(Long[] emailIds);
}