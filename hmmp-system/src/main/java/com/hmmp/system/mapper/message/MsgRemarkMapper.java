package com.hmmp.system.mapper.message;

import java.util.List;
import com.hmmp.system.domain.message.MsgRemark;

/**
 * 用户留言表 数据层
 *
 * @author hmmp
 */
public interface MsgRemarkMapper
{
    public List<MsgRemark> selectRemarkList(MsgRemark remark);

    public MsgRemark selectRemarkById(Long remarkId);

    public int insertRemark(MsgRemark remark);

    public int updateRemark(MsgRemark remark);

    public int deleteRemarkById(Long remarkId);

    public int deleteRemarkByIds(Long[] remarkIds);
}