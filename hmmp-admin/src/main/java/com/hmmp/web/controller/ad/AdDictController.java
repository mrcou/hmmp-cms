package com.hmmp.web.controller.ad;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.hmmp.common.annotation.Log;
import com.hmmp.common.core.controller.BaseController;
import com.hmmp.common.core.domain.AjaxResult;
import com.hmmp.common.core.page.TableDataInfo;
import com.hmmp.common.enums.BusinessType;
import com.hmmp.system.domain.AdArea;
import com.hmmp.system.domain.AdPosition;
import com.hmmp.system.domain.AdPositionType;
import com.hmmp.system.domain.AdType;
import com.hmmp.system.service.IAdAreaService;
import com.hmmp.system.service.IAdPositionService;
import com.hmmp.system.service.IAdPositionTypeService;
import com.hmmp.system.service.IAdTypeService;

/**
 * 广告字典配置
 * 
 * @author hmmp
 */
@RestController
@RequestMapping("/ad/dict")
public class AdDictController extends BaseController
{
    @Autowired
    private IAdAreaService areaService;

    @Autowired
    private IAdPositionTypeService positionTypeService;

    @Autowired
    private IAdPositionService positionService;

    @Autowired
    private IAdTypeService typeService;

    // ========== 区域管理 ==========

    /**
     * 获取区域列表
     */
    @PreAuthorize("@ss.hasPermi('ad:dict:area:list')")
    @GetMapping("/area/list")
    public TableDataInfo areaList(AdArea area)
    {
        startPage();
        List<AdArea> list = areaService.selectAreaList(area);
        return getDataTable(list);
    }

    /**
     * 新增区域
     */
    @PreAuthorize("@ss.hasPermi('ad:dict:area:add')")
    @Log(title = "区域管理", businessType = BusinessType.INSERT)
    @PostMapping("/area")
    public AjaxResult areaAdd(@RequestBody AdArea area)
    {
        area.setCreateBy(getUsername());
        return toAjax(areaService.insertArea(area));
    }

    /**
     * 修改区域
     */
    @PreAuthorize("@ss.hasPermi('ad:dict:area:edit')")
    @Log(title = "区域管理", businessType = BusinessType.UPDATE)
    @PutMapping("/area")
    public AjaxResult areaEdit(@RequestBody AdArea area)
    {
        area.setUpdateBy(getUsername());
        return toAjax(areaService.updateArea(area));
    }

    /**
     * 删除区域
     */
    @PreAuthorize("@ss.hasPermi('ad:dict:area:remove')")
    @Log(title = "区域管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/area/{areaIds}")
    public AjaxResult areaRemove(@PathVariable Long[] areaIds)
    {
        areaService.deleteAreaByIds(areaIds);
        return success();
    }

    // ========== 广告位类型管理 ==========

    /**
     * 获取广告位类型列表
     */
    @PreAuthorize("@ss.hasPermi('ad:dict:positionType:list')")
    @GetMapping("/positionType/list")
    public TableDataInfo positionTypeList(AdPositionType positionType)
    {
        startPage();
        List<AdPositionType> list = positionTypeService.selectPositionTypeList(positionType);
        return getDataTable(list);
    }

    /**
     * 新增广告位类型
     */
    @PreAuthorize("@ss.hasPermi('ad:dict:positionType:add')")
    @Log(title = "广告位类型管理", businessType = BusinessType.INSERT)
    @PostMapping("/positionType")
    public AjaxResult positionTypeAdd(@RequestBody AdPositionType positionType)
    {
        positionType.setCreateBy(getUsername());
        return toAjax(positionTypeService.insertPositionType(positionType));
    }

    /**
     * 修改广告位类型
     */
    @PreAuthorize("@ss.hasPermi('ad:dict:positionType:edit')")
    @Log(title = "广告位类型管理", businessType = BusinessType.UPDATE)
    @PutMapping("/positionType")
    public AjaxResult positionTypeEdit(@RequestBody AdPositionType positionType)
    {
        positionType.setUpdateBy(getUsername());
        return toAjax(positionTypeService.updatePositionType(positionType));
    }

    /**
     * 删除广告位类型
     */
    @PreAuthorize("@ss.hasPermi('ad:dict:positionType:remove')")
    @Log(title = "广告位类型管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/positionType/{typeIds}")
    public AjaxResult positionTypeRemove(@PathVariable Long[] typeIds)
    {
        positionTypeService.deletePositionTypeByIds(typeIds);
        return success();
    }

    // ========== 广告位管理 ==========

    /**
     * 获取广告位列表
     */
    @PreAuthorize("@ss.hasPermi('ad:dict:position:list')")
    @GetMapping("/position/list")
    public TableDataInfo positionList(AdPosition position)
    {
        startPage();
        List<AdPosition> list = positionService.selectPositionList(position);
        return getDataTable(list);
    }

    /**
     * 新增广告位
     */
    @PreAuthorize("@ss.hasPermi('ad:dict:position:add')")
    @Log(title = "广告位管理", businessType = BusinessType.INSERT)
    @PostMapping("/position")
    public AjaxResult positionAdd(@RequestBody AdPosition position)
    {
        position.setCreateBy(getUsername());
        return toAjax(positionService.insertPosition(position));
    }

    /**
     * 修改广告位
     */
    @PreAuthorize("@ss.hasPermi('ad:dict:position:edit')")
    @Log(title = "广告位管理", businessType = BusinessType.UPDATE)
    @PutMapping("/position")
    public AjaxResult positionEdit(@RequestBody AdPosition position)
    {
        position.setUpdateBy(getUsername());
        return toAjax(positionService.updatePosition(position));
    }

    /**
     * 删除广告位
     */
    @PreAuthorize("@ss.hasPermi('ad:dict:position:remove')")
    @Log(title = "广告位管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/position/{positionIds}")
    public AjaxResult positionRemove(@PathVariable Long[] positionIds)
    {
        positionService.deletePositionByIds(positionIds);
        return success();
    }

    // ========== 广告类型管理 ==========

    /**
     * 获取广告类型列表
     */
    @PreAuthorize("@ss.hasPermi('ad:dict:type:list')")
    @GetMapping("/type/list")
    public TableDataInfo typeList(AdType type)
    {
        startPage();
        List<AdType> list = typeService.selectTypeList(type);
        return getDataTable(list);
    }

    /**
     * 新增广告类型
     */
    @PreAuthorize("@ss.hasPermi('ad:dict:type:add')")
    @Log(title = "广告类型管理", businessType = BusinessType.INSERT)
    @PostMapping("/type")
    public AjaxResult typeAdd(@RequestBody AdType type)
    {
        type.setCreateBy(getUsername());
        return toAjax(typeService.insertType(type));
    }

    /**
     * 修改广告类型
     */
    @PreAuthorize("@ss.hasPermi('ad:dict:type:edit')")
    @Log(title = "广告类型管理", businessType = BusinessType.UPDATE)
    @PutMapping("/type")
    public AjaxResult typeEdit(@RequestBody AdType type)
    {
        type.setUpdateBy(getUsername());
        return toAjax(typeService.updateType(type));
    }

    /**
     * 删除广告类型
     */
    @PreAuthorize("@ss.hasPermi('ad:dict:type:remove')")
    @Log(title = "广告类型管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/type/{typeIds}")
    public AjaxResult typeRemove(@PathVariable Long[] typeIds)
    {
        typeService.deleteTypeByIds(typeIds);
        return success();
    }
}