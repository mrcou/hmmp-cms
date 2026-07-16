package com.hmmp.system.service;

import java.util.List;
import com.hmmp.system.domain.message.MsgRemark;

/**
 * 用户留言 服务层
 *
 * @author hmmp
 */
public interface IMsgRemarkService
{
    public List<MsgRemark> selectRemarkList(MsgRemark remark);

    public MsgRemark selectRemarkById(Long remarkId);

    public int insertRemark(MsgRemark remark);

    public int updateRemark(MsgRemark remark);

    public int deleteRemarkByIds(Long[] remarkIds);
}