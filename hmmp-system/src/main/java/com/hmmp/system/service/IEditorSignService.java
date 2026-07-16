package com.hmmp.system.service;

import java.util.List;
import com.hmmp.system.domain.EditorSign;

/**
 * 稿件签发 业务层
 * 
 * @author hmmp
 */
public interface IEditorSignService {

    /**
     * 查询签发列表
     */
    public List<EditorSign> selectSignList(EditorSign sign);

    /**
     * 根据签发ID查询
     */
    public EditorSign selectSignById(Long signId);

    /**
     * 根据稿件ID查询签发记录
     */
    public EditorSign selectSignByManuscriptId(Long manuscriptId);

    /**
     * 新增签发记录
     */
    public int insertSign(EditorSign sign);

    /**
     * 修改签发记录
     */
    public int updateSign(EditorSign sign);

    /**
     * 删除签发记录
     */
    public int deleteSignById(Long signId);

    /**
     * 稿件签发
     */
    public int signManuscript(EditorSign sign);
}