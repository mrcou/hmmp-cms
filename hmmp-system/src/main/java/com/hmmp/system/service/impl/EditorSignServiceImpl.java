package com.hmmp.system.service.impl;

import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hmmp.common.utils.SecurityUtils;
import com.hmmp.system.domain.EditorSign;
import com.hmmp.system.mapper.EditorSignMapper;
import com.hmmp.system.service.IEditorSignService;

/**
 * 稿件签发 业务层实现
 * 
 * @author hmmp
 */
@Service
public class EditorSignServiceImpl implements IEditorSignService {

    @Autowired
    private EditorSignMapper signMapper;

    @Override
    public List<EditorSign> selectSignList(EditorSign sign) {
        return signMapper.selectSignList(sign);
    }

    @Override
    public EditorSign selectSignById(Long signId) {
        return signMapper.selectSignById(signId);
    }

    @Override
    public EditorSign selectSignByManuscriptId(Long manuscriptId) {
        return signMapper.selectSignByManuscriptId(manuscriptId);
    }

    @Override
    public int insertSign(EditorSign sign) {
        sign.setCreateBy(SecurityUtils.getUsername());
        sign.setCreateTime(new Date());
        return signMapper.insertSign(sign);
    }

    @Override
    public int updateSign(EditorSign sign) {
        sign.setUpdateBy(SecurityUtils.getUsername());
        return signMapper.updateSign(sign);
    }

    @Override
    public int deleteSignById(Long signId) {
        return signMapper.deleteSignById(signId);
    }

    @Override
    public int signManuscript(EditorSign sign) {
        sign.setCreateBy(SecurityUtils.getUsername());
        sign.setCreateTime(new Date());
        sign.setSignStatus("1"); // 已签发
        sign.setSignTime(new Date());
        return signMapper.insertSign(sign);
    }
}