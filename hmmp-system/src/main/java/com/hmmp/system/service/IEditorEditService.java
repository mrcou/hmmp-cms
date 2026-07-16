package com.hmmp.system.service;

import java.util.List;
import com.hmmp.system.domain.EditorEdit;

/**
 * 编辑加工 业务层
 * 
 * @author hmmp
 */
public interface IEditorEditService {

    /**
     * 查询编辑加工列表
     */
    public List<EditorEdit> selectEditList(EditorEdit edit);

    /**
     * 根据编辑ID查询编辑详情
     */
    public EditorEdit selectEditById(Long editId);

    /**
     * 新增编辑加工记录
     */
    public int insertEdit(EditorEdit edit);

    /**
     * 修改编辑加工记录
     */
    public int updateEdit(EditorEdit edit);

    /**
     * 删除编辑加工记录
     */
    public int deleteEditById(Long editId);

    /**
     * 完成编辑
     */
    public int completeEdit(EditorEdit edit);
}