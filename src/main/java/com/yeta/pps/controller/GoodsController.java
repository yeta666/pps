package com.yeta.pps.controller;

import com.yeta.pps.service.GoodsService;
import com.yeta.pps.util.CommonResponse;
import com.yeta.pps.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 商品相关接口
 * @author YETA
 * @date 2018/12/02/23:37
 */
@RestController
public class GoodsController {

    @Autowired
    private GoodsService goodsService;

    /**
     * 新增商品品牌接口
     * @param goodsBrandVo
     * @return
     */
    @PostMapping(value = "/goods/brands")
    public CommonResponse addBrand(@RequestBody @Valid GoodsBrandVo goodsBrandVo) {
        return goodsService.addBrand(goodsBrandVo);
    }

    /**
     * 删除商品品牌接口
     * @param storeId
     * @param brandId
     * @return
     */
    @DeleteMapping(value = "/goods/brands/{brandId}")
    public CommonResponse deleteBrand(@RequestParam(value = "storeId") Integer storeId,
                                      @PathVariable(value = "brandId") Integer brandId) {
        return goodsService.deleteBrand(new GoodsBrandVo(storeId, brandId));
    }

    /**
     * 修改商品品牌接口
     * @param goodsBrandVo
     * @return
     */
    @PutMapping(value = "/goods/brands")
    public CommonResponse updateBrand(@RequestBody @Valid GoodsBrandVo goodsBrandVo) {
        return goodsService.updateBrand(goodsBrandVo);
    }

    /**
     * 查询所有商品品牌接口
     * @param storeId
     * @param page
     * @param pageSize
     * @return
     */
    @GetMapping(value = "/goods/brands")
    public CommonResponse findAllBrand(@RequestParam(value = "storeId") Integer storeId,
                                       @RequestParam(value = "page", required = false) Integer page,
                                       @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        return goodsService.findAllBrand(new GoodsBrandVo(storeId), new PageVo(page, pageSize));
    }

    /**
     * 根据id查询商品品牌接口
     * @param storeId
     * @param brandId
     * @return
     */
    @GetMapping(value = "/goods/brands/{brandId}")
    public CommonResponse findBrandById(@RequestParam(value = "storeId") Integer storeId,
                                        @PathVariable(value = "brandId") Integer brandId) {
        return goodsService.findBrandById(new GoodsBrandVo(storeId, brandId));
    }

    //

    /**
     * 新增商品标签接口
     * @param goodsLabelVo
     * @return
     */
    @PostMapping(value = "/goods/labels")
    public CommonResponse addLabel(@RequestBody @Valid GoodsLabelVo goodsLabelVo) {
        return goodsService.addLabel(goodsLabelVo);
    }

    /**
     * 删除商品标签接口
     * @param storeId
     * @param labelId
     * @return
     */
    @DeleteMapping(value = "/goods/labels/{labelId}")
    public CommonResponse deleteLabel(@RequestParam(value = "storeId") Integer storeId,
                                      @PathVariable(value = "labelId") Integer labelId) {
        return goodsService.deleteLabel(new GoodsLabelVo(storeId, labelId));
    }

    /**
     * 修改商品标签接口
     * @param goodsLabelVo
     * @return
     */
    @PutMapping(value = "/goods/labels")
    public CommonResponse updateLabel(@RequestBody @Valid GoodsLabelVo goodsLabelVo) {
        return goodsService.updateLabel(goodsLabelVo);
    }

    /**
     * 查询所有商品标签接口
     * @param storeId
     * @param page
     * @param pageSize
     * @return
     */
    @GetMapping(value = "/goods/labels")
    public CommonResponse findAllLabel(@RequestParam(value = "storeId") Integer storeId,
                                       @RequestParam(value = "page", required = false) Integer page,
                                       @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        return goodsService.findAllLabel(new GoodsLabelVo(storeId), new PageVo(page, pageSize));
    }

    /**
     * 根据id查询商品标签接口
     * @param storeId
     * @param labelId
     * @return
     */
    @GetMapping(value = "/goods/labels/{labelId}")
    public CommonResponse findLabelById(@RequestParam(value = "storeId") Integer storeId,
                                        @PathVariable(value = "labelId") Integer labelId) {
        return goodsService.findLabelById(new GoodsLabelVo(storeId, labelId));
    }

    //

    /**
     * 新增商品类型接口
     * @param goodsTypeVo
     * @return
     */
    @PostMapping(value = "/goods/types")
    public CommonResponse addType(@RequestBody @Valid GoodsTypeVo goodsTypeVo) {
        return goodsService.addType(goodsTypeVo);
    }

    /**
     * 删除商品类型接口
     * @param storeId
     * @param typeId
     * @return
     */
    @DeleteMapping(value = "/goods/types/{typeId}")
    public CommonResponse deleteType(@RequestParam(value = "storeId") Integer storeId,
                                     @PathVariable(value = "typeId") Integer typeId) {
        return goodsService.deleteType(new GoodsTypeVo(storeId, typeId));
    }

    /**
     * 修改商品类型接口
     * @param goodsTypeVo
     * @return
     */
    @PutMapping(value = "/goods/types")
    public CommonResponse updateType(@RequestBody @Valid GoodsTypeVo goodsTypeVo) {
        return goodsService.updateType(goodsTypeVo);
    }

    /**
     * 查询所有商品类型接口
     * @param storeId
     * @param page
     * @param pageSize
     * @return
     */
    @GetMapping(value = "/goods/types")
    public CommonResponse findAllType(@RequestParam(value = "storeId") Integer storeId,
                                      @RequestParam(value = "page", required = false) Integer page,
                                      @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        return goodsService.findAllType(new GoodsTypeVo(storeId), new PageVo(page, pageSize));
    }

    /**
     * 根据id查询商品类型接口
     * @param storeId
     * @param typeId
     * @return
     */
    @GetMapping(value = "/goods/types/{typeId}")
    public CommonResponse findTypeById(@RequestParam(value = "storeId") Integer storeId,
                                       @PathVariable(value = "typeId") Integer typeId) {
        return goodsService.findTypeById(new GoodsTypeVo(storeId, typeId));
    }

    //

    /**
     * 新增商品单位接口
     * @param goodsUnitVo
     * @return
     */
    @PostMapping(value = "/goods/units")
    public CommonResponse addUnit(@RequestBody @Valid GoodsUnitVo goodsUnitVo) {
        return goodsService.addUnit(goodsUnitVo);
    }

    /**
     * 删除商品单位接口
     * @param storeId
     * @param unitId
     * @return
     */
    @DeleteMapping(value = "/goods/units/{unitId}")
    public CommonResponse deleteUnit(@RequestParam(value = "storeId") Integer storeId,
                                     @PathVariable(value = "unitId") Integer unitId) {
        return goodsService.deleteUnit(new GoodsUnitVo(storeId, unitId));
    }

    /**
     * 修改商品单位接口
     * @param goodsUnitVo
     * @return
     */
    @PutMapping(value = "/goods/units")
    public CommonResponse updateUnit(@RequestBody @Valid GoodsUnitVo goodsUnitVo) {
        return goodsService.updateUnit(goodsUnitVo);
    }

    /**
     * 查询所有商品单位接口
     * @param storeId
     * @param page
     * @param pageSize
     * @return
     */
    @GetMapping(value = "/goods/units")
    public CommonResponse findAllUnit(@RequestParam(value = "storeId") Integer storeId,
                                      @RequestParam(value = "page", required = false) Integer page,
                                      @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        return goodsService.findAllUnit(new GoodsUnitVo(storeId), new PageVo(page, pageSize));
    }

    /**
     * 根据id查询商品单位接口
     * @param storeId
     * @param unitId
     * @return
     */
    @GetMapping(value = "/goods/units/{unitId}")
    public CommonResponse findUnitById(@RequestParam(value = "storeId") Integer storeId,
                                       @PathVariable(value = "unitId") Integer unitId) {
        return goodsService.findUnitById(new GoodsUnitVo(storeId, unitId));
    }

    //

    /**
     * 新增商品接口
     * @param goodsVo
     * @return
     */
    @PostMapping(value = "/goods")
    public CommonResponse add(@RequestBody @Valid GoodsVo goodsVo) {
        return goodsService.add(goodsVo);
    }

    /**
     * 删除商品接口
     * @param storeId
     * @param goodsId
     * @return
     */
    @DeleteMapping(value = "/goods/{goodsId}")
    public CommonResponse delete(@RequestParam(value = "storeId") Integer storeId,
                                 @PathVariable(value = "goodsId") String goodsId) {
        return goodsService.delete(new GoodsVo(storeId, goodsId));
    }

    /**
     * 修改商品接口
     * @param goodsVo
     * @return
     */
    @PutMapping(value = "/goods")
    public CommonResponse update(@RequestBody @Valid GoodsVo goodsVo) {
        return goodsService.update(goodsVo);
    }

    /**
     * 查询所有商品接口
     * @param storeId
     * @param brandId
     * @param typeId
     * @param barCode
     * @param code
     * @param putaway
     * @param page
     * @param pageSize
     * @return
     */
    @GetMapping(value = "/goods")
    public CommonResponse findAll(@RequestParam(value = "storeId") Integer storeId,
                                  @RequestParam(value = "brandId", required = false) Integer brandId,
                                  @RequestParam(value = "typeId", required = false) Integer typeId,
                                  @RequestParam(value = "barCode", required = false) String barCode,
                                  @RequestParam(value = "code", required = false) String code,
                                  @RequestParam(value = "putaway", required = false) Integer putaway,
                                  @RequestParam(value = "page", required = false) Integer page,
                                  @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        return goodsService.findAll(new GoodsVo(storeId, code, barCode, typeId, brandId, putaway), new PageVo(page, pageSize));
    }

    /**
     * 根据id查询商品接口
     * @param storeId
     * @param goodsId
     * @return
     */
    @GetMapping(value = "/goods/{goodsId}")
    public CommonResponse findAll(@RequestParam(value = "storeId") Integer storeId,
                                  @PathVariable(value = "goodsId") String goodsId) {
        return goodsService.findById(new GoodsVo(storeId, goodsId));
    }
}
