package com.hmmp.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hmmp.system.domain.message.MsgRemark;
import com.hmmp.system.mapper.message.MsgRemarkMapper;
import com.hmmp.system.service.IMsgRemarkService;

/**
 * 用户留言 服务层处理
 *
 * @author hmmp
 */
@Service
public class MsgRemarkServiceImpl implements IMsgRemarkService
{
    @Autowired
    private MsgRemarkMapper remarkMapper;

    @Override
    public List<MsgRemark> selectRemarkList(MsgRemark remark)
    {
        return remarkMapper.selectRemarkList(remark);
    }

    @Override
    public MsgRemark selectRemarkById(Long remarkId)
    {
        return remarkMapper.selectRemarkById(remarkId);
    }

    @Override
    public int insertRemark(MsgRemark remark)
    {
        return remarkMapper.insertRemark(remark);
    }

    @Override
    public int updateRemark(MsgRemark remark)
    {
        return remarkMapper.updateRemark(remark);
    }

    @Override
    public int deleteRemarkByIds(Long[] remarkIds)
    {
        return remarkMapper.deleteRemarkByIds(remarkIds);
    }
}