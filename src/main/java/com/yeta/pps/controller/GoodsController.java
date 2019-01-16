package com.yeta.pps.controller;

import com.yeta.pps.po.GoodsLabel;
import com.yeta.pps.po.GoodsPropertyKey;
import com.yeta.pps.po.GoodsType;
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
import java.util.ArrayList;
import java.util.Arrays;
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
     * @param ids
     * @return
     */
    @ApiOperation(value = "删除商品标签")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "storeId", value = "店铺id", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "ids", value = "标签id，英文逗号隔开", required = true, paramType = "query", dataType = "String")
    })
    @DeleteMapping(value = "/goods/labels")
    public CommonResponse deleteLabel(@RequestParam(value = "storeId") Integer storeId,
                                      @RequestParam(value = "ids") String ids) {
        List<GoodsLabelVo> goodsLabelVos = new ArrayList<>();
        Arrays.asList(ids.split(",")).stream().forEach(id -> {
            goodsLabelVos.add(new GoodsLabelVo(storeId, Integer.valueOf(id)));
        });
        return goodsService.deleteLabel(goodsLabelVos);
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
    @ApiOperation(value = "查找商品标签", notes = "可选择分页或不分页查询，分页查询用于表格展示，不分页查询用于增加商品的时候选择")
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
     * @param ids
     * @return
     */
    @ApiOperation(value = "删除商品类型")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "storeId", value = "店铺id", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "ids", value = "类型id，英文逗号隔开", required = true, paramType = "query", dataType = "String")
    })
    @DeleteMapping(value = "/goods/types")
    public CommonResponse deleteType(@RequestParam(value = "storeId") Integer storeId,
                                     @RequestParam(value = "ids") String ids) {
        List<GoodsTypeVo> goodsTypeVos = new ArrayList<>();
        Arrays.asList(ids.split(",")).stream().forEach(id -> {
            goodsTypeVos.add(new GoodsTypeVo(storeId, Integer.valueOf(id)));
        });
        return goodsService.deleteType(goodsTypeVos);
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
    @ApiOperation(value = "查询商品类型", notes = "分页查询用于表格，不分页查询用于修改或新增商品属性名的时候，注意两种情况返回格式不一样")
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

    //商品属性名

    /**
     * 新增商品属性名接口
     * @param goodsPropertyKeyVo
     * @return
     */
    @ApiOperation(value = "新增商品属性名")
    @ApiImplicitParam(name = "goodsPropertyKeyVo", value = "storeId, name, typeId必填", required = true, paramType = "body", dataType = "GoodsPropertyKeyVo")
    @PostMapping(value = "/goods/types/properties/keys")
    public CommonResponse addPropertyKey(@RequestBody @Valid GoodsPropertyKeyVo goodsPropertyKeyVo) {
        return goodsService.addPropertyKey(goodsPropertyKeyVo);
    }

    /**
     * 删除商品属性名接口
     * @param storeId
     * @param ids
     * @return
     */
    @ApiOperation(value = "删除商品属性名")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "storeId", value = "店铺id", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "ids", value = "属性名id", required = true, paramType = "query", dataType = "String")
    })
    @DeleteMapping(value = "/goods/types/properties/keys")
    public CommonResponse deletePropertyKey(@RequestParam(value = "storeId") Integer storeId,
                                            @RequestParam(value = "ids") String ids) {
        List<GoodsPropertyKeyVo> goodsPropertyKeyVos = new ArrayList<>();
        Arrays.asList(ids.split(",")).stream().forEach(id -> {
            goodsPropertyKeyVos.add(new GoodsPropertyKeyVo(storeId, Integer.valueOf(id), null));
        });
        return goodsService.deletePropertyKey(goodsPropertyKeyVos);
    }

    /**
     * 修改商品属性名接口
     * @param goodsPropertyKeyVo
     * @return
     */
    @ApiOperation(value = "修改商品属性名")
    @ApiImplicitParam(name = "goodsPropertyKeyVo", value = "storeId, id, name, typeId必填", required = true, paramType = "body", dataType = "GoodsPropertyKeyVo")
    @PutMapping(value = "/goods/types/properties/keys")
    public CommonResponse updatePropertyKey(@RequestBody @Valid GoodsPropertyKeyVo goodsPropertyKeyVo) {
        return goodsService.updatePropertyKey(goodsPropertyKeyVo);
    }

    /**
     * 查询所有商品属性名接口
     * @param storeId
     * @param page
     * @param pageSize
     * @return
     */
    @ApiOperation(value = "查询商品属性名", notes = "分页查询用于表格，不分页查询用于修改或新增商品属性值的时候，注意两种情况返回格式不一样")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "storeId", value = "店铺id", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "page", value = "当前页码，从1开始", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "pageSize", value = "每页显示条数", required = false, paramType = "query", dataType = "int")
    })
    @GetMapping(value = "/goods/types/properties/keys")
    public CommonResponse<CommonResult<List<GoodsPropertyKeyVo>>> findAllPropertyKey(@RequestParam(value = "storeId") Integer storeId,
                                                                                   @RequestParam(value = "page", required = false) Integer page,
                                                                                   @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        return goodsService.findAllPropertyKey(new GoodsPropertyKeyVo(storeId), new PageVo(page, pageSize));
    }

    /**
     * 根据id查询商品属性名接口
     * @param storeId
     * @param keyId
     * @return
     */
    @ApiOperation(value = "根据id查找商品属性名")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "storeId", value = "店铺id", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "keyId", value = "属性名id", required = true, paramType = "path", dataType = "int")
    })
    @GetMapping(value = "/goods/types/properties/keys/{keyId}")
    public CommonResponse<GoodsPropertyKey> findPropertyKeyById(@RequestParam(value = "storeId") Integer storeId,
                                                                @PathVariable(value = "keyId") Integer keyId) {
        return goodsService.findPropertyKeyById(new GoodsPropertyKeyVo(storeId, keyId, null));
    }

    //商品属性值

    /**
     * 新增商品属性值接口
     * @param goodsPropertyValueVo
     * @return
     */
    @ApiOperation(value = "新增商品属性值")
    @ApiImplicitParam(name = "goodsPropertyValueVo", value = "storeId, name, propertyKeyId必填", required = true, paramType = "body", dataType = "GoodsPropertyValueVo")
    @PostMapping(value = "/goods/types/properties/values")
    public CommonResponse addPropertyValue(@RequestBody @Valid GoodsPropertyValueVo goodsPropertyValueVo) {
        return goodsService.addPropertyValue(goodsPropertyValueVo);
    }

    /**
     * 删除商品属性值接口
     * @param storeId
     * @param ids
     * @return
     */
    @ApiOperation(value = "删除商品属性值")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "storeId", value = "店铺id", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "ids", value = "属性值id，英文逗号隔开", required = true, paramType = "query", dataType = "String")
    })
    @DeleteMapping(value = "/goods/types/properties/values")
    public CommonResponse deletePropertyValue(@RequestParam(value = "storeId") Integer storeId,
                                              @RequestParam(value = "ids") String ids) {
        List<GoodsPropertyValueVo> goodsPropertyValueVos = new ArrayList<>();
        Arrays.asList(ids.split(",")).stream().forEach(id -> {
            goodsPropertyValueVos.add(new GoodsPropertyValueVo(storeId, Integer.valueOf(id), null));
        });
        return goodsService.deletePropertyValue(goodsPropertyValueVos);
    }

    /**
     * 修改商品属性值接口
     * @param goodsPropertyValueVo
     * @return
     */
    @ApiOperation(value = "修改商品属性值")
    @ApiImplicitParam(name = "goodsPropertyValueVo", value = "storeId, id, name, propertyKeyId必填", required = true, paramType = "body", dataType = "GoodsPropertyValueVo")
    @PutMapping(value = "/goods/types/properties/values")
    public CommonResponse updatePropertyValue(@RequestBody @Valid GoodsPropertyValueVo goodsPropertyValueVo) {
        return goodsService.updatePropertyValue(goodsPropertyValueVo);
    }

    /**
     * 查询所有商品属性值接口
     * @param storeId
     * @param page
     * @param pageSize
     * @return
     */
    @ApiOperation(value = "查询商品属性值", notes = "分页查询用于表格")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "storeId", value = "店铺id", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "page", value = "当前页码，从1开始", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "pageSize", value = "每页显示条数", required = true, paramType = "query", dataType = "int")
    })
    @GetMapping(value = "/goods/types/properties/values")
    public CommonResponse<CommonResult<List<GoodsPropertyValueVo>>> findAllPropertyValue(@RequestParam(value = "storeId") Integer storeId,
                                                                                         @RequestParam(value = "page") Integer page,
                                                                                         @RequestParam(value = "pageSize") Integer pageSize) {
        return goodsService.findAllPropertyValue(new GoodsPropertyValueVo(storeId), new PageVo(page, pageSize));
    }

    /**
     * 根据id查询商品属性值接口
     * @param storeId
     * @param valueId
     * @return
     */
    @ApiOperation(value = "根据id查找商品属性值")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "storeId", value = "店铺id", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "valueId", value = "属性值id", required = true, paramType = "path", dataType = "int")
    })
    @GetMapping(value = "/goods/types/properties/values/{valueId}")
    public CommonResponse<GoodsPropertyValueVo> findPropertyValueById(@RequestParam(value = "storeId") Integer storeId,
                                                                      @PathVariable(value = "valueId") Integer valueId) {
        return goodsService.findPropertyValueById(new GoodsPropertyValueVo(storeId, valueId, null));
    }

    //商品类型/商品属性名/商品属性值

    /**
     * 查询所有商品属性
     * @param storeId
     * @return
     */
    @ApiOperation(value = "查询所有商品属性", notes = "将所有商品分类下的所有商品属性名、所有商品属性值都查出来")
    @ApiImplicitParam(name = "storeId", value = "店铺id", required = true, paramType = "query", dataType = "int")
    @GetMapping(value = "/goods/types/properties")
    public CommonResponse<GoodsTypeVo> findAllProperties(@RequestParam(value = "storeId") Integer storeId) {
        return goodsService.findAllProperties(new GoodsTypeVo(storeId));
    }

    //商品

    /**
     * 新增商品接口
     * @param goodsVo
     * @return
     */
    @ApiOperation(value = "新增商品")
    @ApiImplicitParam(name = "goodsVo", value = "storeId, name, barCode, typeId, putaway必填；商品规格skus, goodsSkuVos(sku, purchasePrice, retailPrice, vipPrice, integral), goodsLabels, origin, image, remark选填", required = true, paramType = "body", dataType = "GoodsVo")
    @PostMapping(value = "/goods")
    public CommonResponse add(@RequestBody @Valid GoodsVo goodsVo) {
        return goodsService.add(goodsVo);
    }

    /**
     * 删除商品接口
     * @param storeId
     * @param ids
     * @return
     */
    @ApiOperation(value = "删除商品")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "storeId", value = "店铺id", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "ids", value = "商品id，英文逗号隔开", required = true, paramType = "query", dataType = "String")
    })
    @DeleteMapping(value = "/goods")
    public CommonResponse delete(@RequestParam(value = "storeId") Integer storeId,
                                 @RequestParam(value = "ids") String ids) {
        List<GoodsVo> goodsVos = new ArrayList<>();
        Arrays.asList(ids.split(",")).stream().forEach(id -> {
            goodsVos.add(new GoodsVo(storeId, id));
        });
        return goodsService.delete(goodsVos);
    }

    /**
     * 修改商品接口
     * @param goodsVo
     * @return
     */
    @ApiOperation(value = "修改商品", notes = "不能修改商品类型，特别强调goodsSkuVos，之前已经存在的规格必须传id，新增的规格不传id，不能删除已经存在的规格")
    @ApiImplicitParam(name = "goodsVo", value = "storeId, id, name, barCode, putaway, goodsLabels, goodsSkuVos(id, goodsId, sku, purchasePrice, retailPrice, vipPrice, integral必填)必填；其他选填", required = true, paramType = "body", dataType = "GoodsVo")
    @PutMapping(value = "/goods")
    public CommonResponse update(@RequestBody @Valid GoodsVo goodsVo) {
        return goodsService.update(goodsVo);
    }

    /**
     * 查询所有商品接口
     * @param storeId
     * @param id
     * @param barCode
     * @param typeId
     * @param putaway
     * @param page
     * @param pageSize
     * @return
     */
    @ApiOperation(value = "查询所有商品", notes = "筛选、分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "storeId", value = "店铺id", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "id", value = "商品货号", required = false, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "barCode", value = "条码", required = false, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "typeId", value = "类型id", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "putaway", value = "上架状态，0：未上架，1：已上架", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "page", value = "当前页码，从1开始", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "pageSize", value = "每页显示条数", required = true, paramType = "query", dataType = "int")
    })
    @GetMapping(value = "/goods")
    public CommonResponse<CommonResult<GoodsVo>> findAll(@RequestParam(value = "storeId") Integer storeId,
                                                         @RequestParam(value = "id", required = false) String id,
                                                         @RequestParam(value = "barCode", required = false) String barCode,
                                                         @RequestParam(value = "typeId", required = false) Integer typeId,
                                                         @RequestParam(value = "putaway", required = false) Byte putaway,
                                                         @RequestParam(value = "page", required = true) Integer page,
                                                         @RequestParam(value = "pageSize", required = true) Integer pageSize) {
        return goodsService.findAll(new GoodsVo(storeId, id, barCode, typeId, putaway), new PageVo(page, pageSize));
    }

    /**
     * 导出商品接口
     * @param storeId
     * @param id
     * @param barCode
     * @param typeId
     * @param putaway
     * @param response
     * @throws IOException
     */
    @ApiOperation(value = "导出商品", notes = "可筛选导出")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "storeId", value = "店铺id", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "id", value = "商品货号", required = false, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "barCode", value = "条码", required = false, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "typeId", value = "类型id", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "putaway", value = "上架状态，0：未上架，1：已上架", required = false, paramType = "query", dataType = "int")
    })
    @GetMapping(value = "/goods/export")
    public void exportGoods(@RequestParam(value = "storeId") Integer storeId,
                            @RequestParam(value = "id", required = false) String id,
                            @RequestParam(value = "barCode", required = false) String barCode,
                            @RequestParam(value = "typeId", required = false) Integer typeId,
                            @RequestParam(value = "putaway", required = false) Byte putaway,
                            HttpServletResponse response) {
        goodsService.exportGoods(new GoodsVo(storeId, id, barCode, typeId, putaway), response);
    }

    /**
     * 导出商品及sku接口
     * @param storeId
     * @param id
     * @param barCode
     * @param typeId
     * @param putaway
     * @param response
     * @throws IOException
     */
    @ApiOperation(value = "导出商品及sku", notes = "可筛选导出")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "storeId", value = "店铺id", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "id", value = "商品货号", required = false, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "barCode", value = "条码", required = false, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "typeId", value = "类型id", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "putaway", value = "上架状态，0：未上架，1：已上架", required = false, paramType = "query", dataType = "int")
    })
    @GetMapping(value = "/goods/export/skus")
    public void exportGoodsSku(@RequestParam(value = "storeId") Integer storeId,
                               @RequestParam(value = "id", required = false) String id,
                               @RequestParam(value = "barCode", required = false) String barCode,
                               @RequestParam(value = "typeId", required = false) Integer typeId,
                               @RequestParam(value = "putaway", required = false) Byte putaway,
                               HttpServletResponse response) {
        goodsService.exportGoodsSku(new GoodsVo(storeId, id, barCode, typeId, putaway), response);
    }

    /**
     * 获取商品导入模版接口
     * @param storeId
     * @param response
     * @throws IOException
     */
    @ApiOperation(value = "获取商品导入模版")
    @GetMapping(value = "/goods/import/template")
    public void getImportGoodsTemplate(@RequestParam(value = "storeId") Integer storeId,
                                       HttpServletResponse response) throws IOException {
        goodsService.getImportGoodsTemplate(storeId, response);
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

    //下单查商品

    /**
     * 下单查商品接口
     * @param storeId
     * @param warehouseId
     * @return
     */
    @ApiOperation(value = "下单查商品")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "storeId", value = "店铺编号", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "warehouseId", value = "仓库编号", required = true, paramType = "query", dataType = "int")
    })
    @GetMapping(value = "/goods/canUse")
    public CommonResponse findCanUseByWarehouseId(@RequestParam(value = "storeId") Integer storeId,
                                                  @RequestParam(value = "warehouseId") Integer warehouseId) {
        return goodsService.findCanUseByWarehouseId(new WarehouseGoodsSkuVo(storeId, warehouseId));
    }
}
