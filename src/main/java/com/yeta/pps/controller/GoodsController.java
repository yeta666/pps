package com.yeta.pps.controller;

import com.yeta.pps.po.GoodsBrand;
import com.yeta.pps.po.GoodsLabel;
import com.yeta.pps.po.GoodsType;
import com.yeta.pps.po.GoodsUnit;
import com.yeta.pps.service.GoodsService;
import com.yeta.pps.util.CommonResponse;
import com.yeta.pps.util.CommonResult;
import com.yeta.pps.vo.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

/**
 * 商品相关接口
 * @author YETA
 * @date 2018/12/02/23:37
 */
@Api(value = "商品相关接口")
@RestController
public class GoodsController {

    @Autowired
    private GoodsService goodsService;

    /**
     * 新增商品品牌接口
     * @param goodsBrandVo
     * @return
     */
    @ApiOperation(value = "新增商品品牌")
    @ApiImplicitParam(name = "goodsBrandVo", value = "storeId, name必填", required = true, paramType = "body", dataType = "GoodsBrandVo")
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
    @ApiOperation(value = "删除商品品牌")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "storeId", value = "店铺id", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "brandId", value = "品牌id", required = true, paramType = "path", dataType = "int")
    })
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
    @ApiOperation(value = "修改商品品牌")
    @ApiImplicitParam(name = "goodsBrandVo", value = "storeId, id, name必填", required = true, paramType = "body", dataType = "GoodsBrandVo")
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
    @ApiOperation(value = "查找商品品牌", notes = "可选择分页或不分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "storeId", value = "店铺id", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "page", value = "当前页码，从1开始", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "pageSize", value = "每页显示条数", required = false, paramType = "query", dataType = "int")
    })
    @GetMapping(value = "/goods/brands")
    public CommonResponse<CommonResult<List<GoodsBrand>>> findAllBrand(@RequestParam(value = "storeId") Integer storeId,
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
    @ApiOperation(value = "根据id查找商品品牌")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "storeId", value = "店铺id", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "brandId", value = "品牌id", required = true, paramType = "path", dataType = "int")
    })
    @GetMapping(value = "/goods/brands/{brandId}")
    public CommonResponse<GoodsBrand> findBrandById(@RequestParam(value = "storeId") Integer storeId,
                                                    @PathVariable(value = "brandId") Integer brandId) {
        return goodsService.findBrandById(new GoodsBrandVo(storeId, brandId));
    }

    //

    /**
     * 新增商品标签接口
     * @param goodsLabelVo
     * @return
     */
    @ApiOperation(value = "新增商品标签")
    @ApiImplicitParam(name = "goodsLabelVo", value = "storeId, name必填", required = true, paramType = "body", dataType = "GoodsLabelVo")
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
    @ApiOperation(value = "删除商品标签")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "storeId", value = "店铺id", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "labelId", value = "标签id", required = true, paramType = "path", dataType = "int")
    })
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
    @ApiOperation(value = "修改商品标签")
    @ApiImplicitParam(name = "goodsLabelVo", value = "storeId, id, name必填", required = true, paramType = "body", dataType = "GoodsLabelVo")
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
    @ApiOperation(value = "查找商品标签", notes = "可选择分页或不分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "storeId", value = "店铺id", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "page", value = "当前页码，从1开始", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "pageSize", value = "每页显示条数", required = false, paramType = "query", dataType = "int")
    })
    @GetMapping(value = "/goods/labels")
    public CommonResponse<CommonResult<List<GoodsLabel>>> findAllLabel(@RequestParam(value = "storeId") Integer storeId,
                                                                       @RequestParam(value = "page", required = false) Integer page,
                                                                       @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        return goodsService.findAllLabel(new GoodsLabelVo(storeId), new PageVo(page, pageSize));
    }

    /**
     * 根据id查询商品标签接口
     *
     * @param storeId
     * @param labelId
     * @return
     */
    @ApiOperation(value = "根据id查找商品标签")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "storeId", value = "店铺id", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "labelId", value = "标签id", required = true, paramType = "path", dataType = "int")
    })
    @GetMapping(value = "/goods/labels/{labelId}")
    public CommonResponse<GoodsLabel> findLabelById(@RequestParam(value = "storeId") Integer storeId,
                                                    @PathVariable(value = "labelId") Integer labelId) {
        return goodsService.findLabelById(new GoodsLabelVo(storeId, labelId));
    }

    //

    /**
     * 新增商品类型接口
     * @param goodsTypeVo
     * @return
     */
    @ApiOperation(value = "新增商品类型")
    @ApiImplicitParam(name = "goodsTypeVo", value = "storeId, name必填", required = true, paramType = "body", dataType = "GoodsTypeVo")
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
    @ApiOperation(value = "删除商品类型")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "storeId", value = "店铺id", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "typeId", value = "类型id", required = true, paramType = "path", dataType = "int")
    })
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
    @ApiOperation(value = "修改商品类型")
    @ApiImplicitParam(name = "goodsTypeVo", value = "storeId, id, name必填", required = true, paramType = "body", dataType = "GoodsTypeVo")
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
    @ApiOperation(value = "查找商品类型", notes = "可选择分页或不分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "storeId", value = "店铺id", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "page", value = "当前页码，从1开始", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "pageSize", value = "每页显示条数", required = false, paramType = "query", dataType = "int")
    })
    @GetMapping(value = "/goods/types")
    public CommonResponse<CommonResult<List<GoodsType>>> findAllType(@RequestParam(value = "storeId") Integer storeId,
                                                                     @RequestParam(value = "page", required = false) Integer page,
                                                                     @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        return goodsService.findAllType(new GoodsTypeVo(storeId), new PageVo(page, pageSize));
    }

    /**
     * 根据id查询商品类型接口
     *
     * @param storeId
     * @param typeId
     * @return
     */
    @ApiOperation(value = "根据id查找商品类型")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "storeId", value = "店铺id", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "typeId", value = "类型id", required = true, paramType = "path", dataType = "int")
    })
    @GetMapping(value = "/goods/types/{typeId}")
    public CommonResponse<GoodsType> findTypeById(@RequestParam(value = "storeId") Integer storeId,
                                                  @PathVariable(value = "typeId") Integer typeId) {
        return goodsService.findTypeById(new GoodsTypeVo(storeId, typeId));
    }

    //

    /**
     * 新增商品单位接口
     * @param goodsUnitVo
     * @return
     */
    @ApiOperation(value = "新增商品单位")
    @ApiImplicitParam(name = "goodsUnitVo", value = "storeId, name必填", required = true, paramType = "body", dataType = "GoodsUnitVo")
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
    @ApiOperation(value = "删除商品单位")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "storeId", value = "店铺id", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "unitId", value = "单位id", required = true, paramType = "path", dataType = "int")
    })
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
    @ApiOperation(value = "修改商品单位")
    @ApiImplicitParam(name = "goodsUnitVo", value = "storeId, id, name必填", required = true, paramType = "body", dataType = "GoodsUnitVo")
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
    @ApiOperation(value = "查找商品单位", notes = "可选择分页或不分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "storeId", value = "店铺id", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "page", value = "当前页码，从1开始", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "pageSize", value = "每页显示条数", required = false, paramType = "query", dataType = "int")
    })
    @GetMapping(value = "/goods/units")
    public CommonResponse<CommonResult<List<GoodsUnit>>> findAllUnit(@RequestParam(value = "storeId") Integer storeId,
                                                                     @RequestParam(value = "page", required = false) Integer page,
                                                                     @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        return goodsService.findAllUnit(new GoodsUnitVo(storeId), new PageVo(page, pageSize));
    }

    /**
     * 根据id查询商品单位接口
     *
     * @param storeId
     * @param unitId
     * @return
     */
    @ApiOperation(value = "根据id查找商品单位")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "storeId", value = "店铺id", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "unitId", value = "单位id", required = true, paramType = "path", dataType = "int")
    })
    @GetMapping(value = "/goods/units/{unitId}")
    public CommonResponse<GoodsUnit> findUnitById(@RequestParam(value = "storeId") Integer storeId,
                                                  @PathVariable(value = "unitId") Integer unitId) {
        return goodsService.findUnitById(new GoodsUnitVo(storeId, unitId));
    }

    //

    /**
     * 新增商品接口
     * @param goodsVo
     * @return
     */
    @ApiOperation(value = "新增商品")
    @ApiImplicitParam(name = "goodsVo",
            value = "storeId, name, code, barCode, typeId, brandId, unitId, labelId, purchasePrice, retailPrice, vipPrice, inventory, putaway必填；其他选填",
            required = true, paramType = "body", dataType = "GoodsVo")
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
    @ApiOperation(value = "删除商品")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "storeId", value = "店铺id", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "goodsId", value = "商品id", required = true, paramType = "path", dataType = "int")
    })
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
    @ApiOperation(value = "修改商品")
    @ApiImplicitParam(name = "goodsVo",
            value = "storeId, id, name, code, barCode, typeId, brandId, unitId, labelId, purchasePrice, retailPrice, vipPrice, inventory, putaway必填；其他选填",
            required = true, paramType = "body", dataType = "GoodsVo")
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
    @ApiOperation(value = "查找所有商品", notes = "可筛选、分页查找")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "storeId", value = "店铺id", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "brandId", value = "品牌id", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "typeId", value = "类型id", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "barCode", value = "条码", required = false, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "code", value = "货号", required = false, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "putaway", value = "上架状态，0：未上架，1：已上架", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "page", value = "当前页码，从1开始", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "pageSize", value = "每页显示条数", required = true, paramType = "query", dataType = "int")
    })
    @GetMapping(value = "/goods")
    public CommonResponse<CommonResult<GoodsVo>> findAll(@RequestParam(value = "storeId") Integer storeId,
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
    @ApiOperation(value = "根据id查找商品")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "storeId", value = "店铺id", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "goodsId", value = "商品id", required = true, paramType = "path", dataType = "int")
    })
    @GetMapping(value = "/goods/{goodsId}")
    public CommonResponse findAll(@RequestParam(value = "storeId") Integer storeId,
                                  @PathVariable(value = "goodsId") String goodsId) {
        return goodsService.findById(new GoodsVo(storeId, goodsId));
    }

    /**
     * 导出商品接口
     * @param storeId
     * @param brandId
     * @param typeId
     * @param barCode
     * @param code
     * @param putaway
     * @param response
     * @throws IOException
     */
    @ApiOperation(value = "导出商品", notes = "可筛选导出")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "storeId", value = "店铺id", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "brandId", value = "品牌id", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "typeId", value = "类型id", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "barCode", value = "条码", required = false, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "code", value = "货号", required = false, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "putaway", value = "上架状态，0：未上架，1：已上架", required = false, paramType = "query", dataType = "int")
    })
    @GetMapping(value = "/goods/export")
    public void exportGoods(@RequestParam(value = "storeId") Integer storeId,
                         @RequestParam(value = "brandId", required = false) Integer brandId,
                         @RequestParam(value = "typeId", required = false) Integer typeId,
                         @RequestParam(value = "barCode", required = false) String barCode,
                         @RequestParam(value = "code", required = false) String code,
                         @RequestParam(value = "putaway", required = false) Integer putaway,
                         HttpServletResponse response) throws IOException {
        goodsService.exportGoods(new GoodsVo(storeId, code, barCode, typeId, brandId, putaway), response);
    }

    /**
     * 获取导入模版接口
     * @param response
     * @throws IOException
     */
    @ApiOperation(value = "获取导入商品模版")
    @GetMapping(value = "/goods/import/template")
    public void getImportGoodsTemplate(HttpServletResponse response) throws IOException {
        goodsService.getImportGoodsTemplate(response);
    }

    /**
     * 导入商品接口
     * @param file
     * @return
     * @throws IOException
     */
    @ApiOperation(value = "导入商品")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "file", value = "文件", required = true, paramType = "form", dataType = "File"),
            @ApiImplicitParam(name = "storeId", value = "店铺id", required = true, paramType = "query", dataType = "int")
    })
    @PostMapping(value = "/goods/import")
    public CommonResponse importGoods(@RequestParam(value = "file") MultipartFile file,
                                      @RequestParam(value = "storeId") Integer storeId) throws IOException {
        return goodsService.importGoods(file, storeId);
    }
}
