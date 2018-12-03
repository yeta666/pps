package com.yeta.pps.service;

import com.yeta.pps.exception.CommonException;
import com.yeta.pps.mapper.GoodsMapper;
import com.yeta.pps.po.*;
import com.yeta.pps.util.CommonResponse;
import com.yeta.pps.util.CommonResult;
import com.yeta.pps.util.Title;
import com.yeta.pps.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * 商品相关逻辑处理
 * @author YETA
 * @date 2018/12/02/23:30
 */
@Service
public class GoodsService {

    @Autowired
    private GoodsMapper goodsMapper;

    /**
     * 新增商品品牌
     * @param goodsBrandVo
     * @return
     */
    public CommonResponse addBrand(GoodsBrandVo goodsBrandVo) {
        //新增
        if (goodsMapper.addBrand(goodsBrandVo) != 1) {
            return new CommonResponse(CommonResponse.CODE7, null, CommonResponse.MESSAGE7);
        }
        return new CommonResponse(CommonResponse.CODE1, null, CommonResponse.MESSAGE1);
    }

    /**
     * 删除商品品牌
     * @param goodsBrandVo
     * @return
     */
    public CommonResponse deleteBrand(GoodsBrandVo goodsBrandVo) {
        //删除商品品牌
        if (goodsMapper.deleteBrand(goodsBrandVo) != 1) {
            throw new CommonException(CommonResponse.CODE8, CommonResponse.MESSAGE8);
        }
        return new CommonResponse(CommonResponse.CODE1, null, CommonResponse.MESSAGE1);
    }

    /**
     * 修改商品品牌
     * @param goodsBrandVo
     * @return
     */
    public CommonResponse updateBrand(GoodsBrandVo goodsBrandVo) {
        //判断参数
        if (goodsBrandVo.getId() == null) {
            return new CommonResponse(CommonResponse.CODE3, null, CommonResponse.MESSAGE3);
        }
        //修改
        if (goodsMapper.updateBrand(goodsBrandVo) != 1) {
            return new CommonResponse(CommonResponse.CODE9, null, CommonResponse.MESSAGE9);
        }
        return new CommonResponse(CommonResponse.CODE1, null, CommonResponse.MESSAGE1);
    }

    /**
     * 查询所有商品品牌
     * @param goodsBrandVo
     * @return
     */
    public CommonResponse findAllBrand(GoodsBrandVo goodsBrandVo, PageVo pageVo) {
        //分页
        if (pageVo.getPage() != null && pageVo.getPageSize() != null) {
            //查询所有页数
            pageVo.setTotalPage(goodsMapper.findCountBrand(goodsBrandVo) / pageVo.getPageSize());
            List<GoodsBrand> goodsBrands = goodsMapper.findAllBrandPaged(goodsBrandVo, pageVo);
            //封装返回结果
            List<Title> titles = new ArrayList<>();
            titles.add(new Title("品牌名", "name"));
            CommonResult commonResult = new CommonResult(titles, goodsBrands, pageVo);
            return new CommonResponse(CommonResponse.CODE1, commonResult, CommonResponse.MESSAGE1);
        }
        //不分页
        List<GoodsBrand> goodsBrands = goodsMapper.findAllBrand(goodsBrandVo);
        return new CommonResponse(CommonResponse.CODE1, goodsBrands, CommonResponse.MESSAGE1);
    }

    /**
     * 根据id查询商品品牌
     * @param goodsBrandVo
     * @return
     */
    public CommonResponse findBrandById(GoodsBrandVo goodsBrandVo) {
        GoodsBrand goodsBrand = goodsMapper.findBrandById(goodsBrandVo);
        if (goodsBrand == null) {
            return new CommonResponse(CommonResponse.CODE10, null, CommonResponse.MESSAGE10);
        }
        return new CommonResponse(CommonResponse.CODE1, goodsBrand, CommonResponse.MESSAGE1);
    }

    //

    /**
     * 新增商品标签
     * @param goodsLabelVo
     * @return
     */
    public CommonResponse addLabel(GoodsLabelVo goodsLabelVo) {
        //新增
        if (goodsMapper.addLabel(goodsLabelVo) != 1) {
            return new CommonResponse(CommonResponse.CODE7, null, CommonResponse.MESSAGE7);
        }
        return new CommonResponse(CommonResponse.CODE1, null, CommonResponse.MESSAGE1);
    }

    /**
     * 删除商品标签
     * @param goodsLabelVo
     * @return
     */
    public CommonResponse deleteLabel(GoodsLabelVo goodsLabelVo) {
        //删除商品标签
        if (goodsMapper.deleteLabel(goodsLabelVo) != 1) {
            throw new CommonException(CommonResponse.CODE8, CommonResponse.MESSAGE8);
        }
        return new CommonResponse(CommonResponse.CODE1, null, CommonResponse.MESSAGE1);
    }

    /**
     * 修改商品标签
     * @param goodsLabelVo
     * @return
     */
    public CommonResponse updateLabel(GoodsLabelVo goodsLabelVo) {
        //判断参数
        if (goodsLabelVo.getId() == null) {
            return new CommonResponse(CommonResponse.CODE3, null, CommonResponse.MESSAGE3);
        }
        //修改
        if (goodsMapper.updateLabel(goodsLabelVo) != 1) {
            return new CommonResponse(CommonResponse.CODE9, null, CommonResponse.MESSAGE9);
        }
        return new CommonResponse(CommonResponse.CODE1, null, CommonResponse.MESSAGE1);
    }

    /**
     * 查询所有商品标签
     * @param goodsLabelVo
     * @return
     */
    public CommonResponse findAllLabel(GoodsLabelVo goodsLabelVo, PageVo pageVo) {
        //分页
        if (pageVo.getPage() != null && pageVo.getPageSize() != null) {
            //查询所有页数
            pageVo.setTotalPage(goodsMapper.findCountLabel(goodsLabelVo) / pageVo.getPageSize());
            List<GoodsLabel> goodsLabels = goodsMapper.findAllLabelPaged(goodsLabelVo, pageVo);
            //封装返回结果
            List<Title> titles = new ArrayList<>();
            titles.add(new Title("标签名", "name"));
            CommonResult commonResult = new CommonResult(titles, goodsLabels, pageVo);
            return new CommonResponse(CommonResponse.CODE1, commonResult, CommonResponse.MESSAGE1);
        }
        //不分页
        List<GoodsLabel> goodsLabels = goodsMapper.findAllLabel(goodsLabelVo);
        return new CommonResponse(CommonResponse.CODE1, goodsLabels, CommonResponse.MESSAGE1);
    }

    /**
     * 根据id查询商品标签
     * @param goodsLabelVo
     * @return
     */
    public CommonResponse findLabelById(GoodsLabelVo goodsLabelVo) {
        GoodsLabel goodsLabel = goodsMapper.findLabelById(goodsLabelVo);
        if (goodsLabel == null) {
            return new CommonResponse(CommonResponse.CODE10, null, CommonResponse.MESSAGE10);
        }
        return new CommonResponse(CommonResponse.CODE1, goodsLabel, CommonResponse.MESSAGE1);
    }

    //

    /**
     * 新增商品类型
     * @param goodsTypeVo
     * @return
     */
    public CommonResponse addType(GoodsTypeVo goodsTypeVo) {
        //新增
        if (goodsMapper.addType(goodsTypeVo) != 1) {
            return new CommonResponse(CommonResponse.CODE7, null, CommonResponse.MESSAGE7);
        }
        return new CommonResponse(CommonResponse.CODE1, null, CommonResponse.MESSAGE1);
    }

    /**
     * 删除商品类型
     * @param goodsTypeVo
     * @return
     */
    public CommonResponse deleteType(GoodsTypeVo goodsTypeVo) {
        //删除商品类型
        if (goodsMapper.deleteType(goodsTypeVo) != 1) {
            throw new CommonException(CommonResponse.CODE8, CommonResponse.MESSAGE8);
        }
        return new CommonResponse(CommonResponse.CODE1, null, CommonResponse.MESSAGE1);
    }

    /**
     * 修改商品类型
     * @param goodsTypeVo
     * @return
     */
    public CommonResponse updateType(GoodsTypeVo goodsTypeVo) {
        //判断参数
        if (goodsTypeVo.getId() == null) {
            return new CommonResponse(CommonResponse.CODE3, null, CommonResponse.MESSAGE3);
        }
        //修改
        if (goodsMapper.updateType(goodsTypeVo) != 1) {
            return new CommonResponse(CommonResponse.CODE9, null, CommonResponse.MESSAGE9);
        }
        return new CommonResponse(CommonResponse.CODE1, null, CommonResponse.MESSAGE1);
    }

    /**
     * 查询所有商品类型
     * @param goodsTypeVo
     * @return
     */
    public CommonResponse findAllType(GoodsTypeVo goodsTypeVo, PageVo pageVo) {
        //分页
        if (pageVo.getPage() != null && pageVo.getPageSize() != null) {
            //查询所有页数
            pageVo.setTotalPage(goodsMapper.findCountType(goodsTypeVo) / pageVo.getPageSize());
            List<GoodsType> goodsTypes = goodsMapper.findAllTypePaged(goodsTypeVo, pageVo);
            //封装返回结果
            List<Title> titles = new ArrayList<>();
            titles.add(new Title("类型名", "name"));
            CommonResult commonResult = new CommonResult(titles, goodsTypes, pageVo);
            return new CommonResponse(CommonResponse.CODE1, commonResult, CommonResponse.MESSAGE1);
        }
        //不分页
        List<GoodsType> goodsTypes = goodsMapper.findAllType(goodsTypeVo);
        return new CommonResponse(CommonResponse.CODE1, goodsTypes, CommonResponse.MESSAGE1);
    }

    /**
     * 根据id查询商品类型
     * @param goodsTypeVo
     * @return
     */
    public CommonResponse findTypeById(GoodsTypeVo goodsTypeVo) {
        GoodsType goodsType = goodsMapper.findTypeById(goodsTypeVo);
        if (goodsType == null) {
            return new CommonResponse(CommonResponse.CODE10, null, CommonResponse.MESSAGE10);
        }
        return new CommonResponse(CommonResponse.CODE1, goodsType, CommonResponse.MESSAGE1);
    }

    //

    /**
     * 新增商品单位
     * @param goodsUnitVo
     * @return
     */
    public CommonResponse addUnit(GoodsUnitVo goodsUnitVo) {
        //新增
        if (goodsMapper.addUnit(goodsUnitVo) != 1) {
            return new CommonResponse(CommonResponse.CODE7, null, CommonResponse.MESSAGE7);
        }
        return new CommonResponse(CommonResponse.CODE1, null, CommonResponse.MESSAGE1);
    }

    /**
     * 删除商品单位
     * @param goodsUnitVo
     * @return
     */
    public CommonResponse deleteUnit(GoodsUnitVo goodsUnitVo) {
        //删除商品单位
        if (goodsMapper.deleteUnit(goodsUnitVo) != 1) {
            throw new CommonException(CommonResponse.CODE8, CommonResponse.MESSAGE8);
        }
        return new CommonResponse(CommonResponse.CODE1, null, CommonResponse.MESSAGE1);
    }

    /**
     * 修改商品单位
     * @param goodsUnitVo
     * @return
     */
    public CommonResponse updateUnit(GoodsUnitVo goodsUnitVo) {
        //判断参数
        if (goodsUnitVo.getId() == null) {
            return new CommonResponse(CommonResponse.CODE3, null, CommonResponse.MESSAGE3);
        }
        //修改
        if (goodsMapper.updateUnit(goodsUnitVo) != 1) {
            return new CommonResponse(CommonResponse.CODE9, null, CommonResponse.MESSAGE9);
        }
        return new CommonResponse(CommonResponse.CODE1, null, CommonResponse.MESSAGE1);
    }

    /**
     * 查询所有商品单位
     * @param goodsUnitVo
     * @return
     */
    public CommonResponse findAllUnit(GoodsUnitVo goodsUnitVo, PageVo pageVo) {
        //分页
        if (pageVo.getPage() != null && pageVo.getPageSize() != null) {
            //查询所有页数
            pageVo.setTotalPage(goodsMapper.findCountUnit(goodsUnitVo) / pageVo.getPageSize());
            List<GoodsUnit> goodsUnits = goodsMapper.findAllUnitPaged(goodsUnitVo, pageVo);
            //封装返回结果
            List<Title> titles = new ArrayList<>();
            titles.add(new Title("单位名", "name"));
            CommonResult commonResult = new CommonResult(titles, goodsUnits, pageVo);
            return new CommonResponse(CommonResponse.CODE1, commonResult, CommonResponse.MESSAGE1);
        }
        //不分页
        List<GoodsUnit> goodsUnits = goodsMapper.findAllUnit(goodsUnitVo);
        return new CommonResponse(CommonResponse.CODE1, goodsUnits, CommonResponse.MESSAGE1);
    }

    /**
     * 根据id查询商品单位
     * @param goodsUnitVo
     * @return
     */
    public CommonResponse findUnitById(GoodsUnitVo goodsUnitVo) {
        GoodsUnit goodsUnit = goodsMapper.findUnitById(goodsUnitVo);
        if (goodsUnit == null) {
            return new CommonResponse(CommonResponse.CODE10, null, CommonResponse.MESSAGE10);
        }
        return new CommonResponse(CommonResponse.CODE1, goodsUnit, CommonResponse.MESSAGE1);
    }

    //

    /**
     * 新增商品
     * @param goodsVo
     * @return
     */
    public CommonResponse add(GoodsVo goodsVo) {
        goodsVo.setId(UUID.randomUUID().toString());
        //新增
        if (goodsMapper.add(goodsVo) != 1) {
            return new CommonResponse(CommonResponse.CODE7, null, CommonResponse.MESSAGE7);
        }
        return new CommonResponse(CommonResponse.CODE1, null, CommonResponse.MESSAGE1);
    }

    //TODO
    //商品批量导入和导出

    /**
     * 删除商品
     * @param goodsVo
     * @return
     */
    public CommonResponse delete(GoodsVo goodsVo) {
        //删除商品
        if (goodsMapper.delete(goodsVo) != 1) {
            throw new CommonException(CommonResponse.CODE8, CommonResponse.MESSAGE8);
        }
        return new CommonResponse(CommonResponse.CODE1, null, CommonResponse.MESSAGE1);
    }

    /**
     * 修改商品
     * @param goodsVo
     * @return
     */
    public CommonResponse update(GoodsVo goodsVo) {
        //判断参数
        if (goodsVo.getId() == null) {
            return new CommonResponse(CommonResponse.CODE3, null, CommonResponse.MESSAGE3);
        }
        //修改
        if (goodsMapper.update(goodsVo) != 1) {
            return new CommonResponse(CommonResponse.CODE9, null, CommonResponse.MESSAGE9);
        }
        return new CommonResponse(CommonResponse.CODE1, null, CommonResponse.MESSAGE1);
    }

    /**
     * 查询所有商品
     * @param goodsVo
     * @return
     */
    public CommonResponse findAll(GoodsVo goodsVo, PageVo pageVo) {
        //查询所有页数
        pageVo.setTotalPage(goodsMapper.findCount(goodsVo) / pageVo.getPageSize());
        List<Goods> goodsList = goodsMapper.findAll(goodsVo, pageVo);
        //封装返回结果
        List<Title> titles = new ArrayList<>();
        titles.add(new Title("商品名", "name"));
        titles.add(new Title("货号", "code"));
        titles.add(new Title("条码", "barCode"));
        titles.add(new Title("分类", "goodsType.name"));
        titles.add(new Title("品牌", "goodsBrand.name"));
        titles.add(new Title("单位", "goodsUnit.name"));
        titles.add(new Title("标签", "goodsLabel.name"));
        titles.add(new Title("零售价", "retailPrice"));
        titles.add(new Title("批发价", "tradePrice"));
        titles.add(new Title("预设进价", "purchasePrice"));
        titles.add(new Title("vip售价", "vipPrice"));
        titles.add(new Title("可用库存", "inventory"));
        titles.add(new Title("产地", "origin"));
        titles.add(new Title("图片", "image"));
        titles.add(new Title("香型", "oderType"));
        titles.add(new Title("度数", "degree"));
        titles.add(new Title("净含量", "netContent"));
        titles.add(new Title("商品积分", "integral"));
        titles.add(new Title("备注", "remark"));
        titles.add(new Title("上架状态", "putaway"));
        CommonResult commonResult = new CommonResult(titles, goodsList, pageVo);
        return new CommonResponse(CommonResponse.CODE1, commonResult, CommonResponse.MESSAGE1);
    }

    /**
     * 根据id查询商品
     * @param goodsVo
     * @return
     */
    public CommonResponse findById(GoodsVo goodsVo) {
        Goods good = goodsMapper.findById(goodsVo);
        if (good == null) {
            return new CommonResponse(CommonResponse.CODE10, null, CommonResponse.MESSAGE10);
        }
        return new CommonResponse(CommonResponse.CODE1, good, CommonResponse.MESSAGE1);
    }
}