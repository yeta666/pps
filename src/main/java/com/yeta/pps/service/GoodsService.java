package com.yeta.pps.service;

import com.alibaba.fastjson.JSON;
import com.yeta.pps.exception.CommonException;
import com.yeta.pps.mapper.MyGoodsMapper;
import com.yeta.pps.po.GoodsLabel;
import com.yeta.pps.po.GoodsPropertyKey;
import com.yeta.pps.po.GoodsType;
import com.yeta.pps.util.*;
import com.yeta.pps.vo.*;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 商品相关逻辑处理
 * @author YETA
 * @date 2018/12/02/23:30
 */
@Service
public class GoodsService {

    @Autowired
    private MyGoodsMapper myGoodsMapper;

    //商品标签

    /**
     * 新增商品标签
     * @param goodsLabelVo
     * @return
     */
    public CommonResponse addLabel(GoodsLabelVo goodsLabelVo) {
        //新增
        if (myGoodsMapper.addLabel(goodsLabelVo) != 1) {
            return new CommonResponse(CommonResponse.CODE7, null, CommonResponse.MESSAGE7);
        }
        return new CommonResponse(CommonResponse.CODE1, null, CommonResponse.MESSAGE1);
    }

    /**
     * 删除商品标签
     * 已经使用的商品标签不能删除，未使用的商品标签没有商品/商品标签关系，无需操作中间表
     * @param goodsLabelVos
     * @return
     */
    @Transactional
    public CommonResponse deleteLabel(List<GoodsLabelVo> goodsLabelVos) {
        goodsLabelVos.stream().forEach(goodsLabelVo -> {
            //判断商品标签是否使用
            GoodsGoodsLabelVo goodsGoodsLabelVo = new GoodsGoodsLabelVo(goodsLabelVo.getStoreId(), goodsLabelVo.getId());
            if (myGoodsMapper.findGoodsLabel(goodsGoodsLabelVo).size() > 0) {
                throw new CommonException(CommonResponse.CODE18, CommonResponse.MESSAGE18);
            }
            //删除商品标签
            if (myGoodsMapper.deleteLabel(goodsLabelVo) != 1) {
                throw new CommonException(CommonResponse.CODE8, CommonResponse.MESSAGE8);
            }
        });
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
        if (myGoodsMapper.updateLabel(goodsLabelVo) != 1) {
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
            pageVo.setTotalPage((int) Math.ceil(myGoodsMapper.findCountLabel(goodsLabelVo) * 1.0 / pageVo.getPageSize()));
            pageVo.setStart(pageVo.getPageSize() * (pageVo.getPage() - 1));
            List<GoodsLabel> goodsLabels = myGoodsMapper.findAllPagedLabel(goodsLabelVo, pageVo);
            //封装返回结果
            List<Title> titles = new ArrayList<>();
            titles.add(new Title("标签名", "name"));
            CommonResult commonResult = new CommonResult(titles, goodsLabels, pageVo);
            return new CommonResponse(CommonResponse.CODE1, commonResult, CommonResponse.MESSAGE1);
        }
        //不分页
        List<GoodsLabel> goodsLabels = myGoodsMapper.findAllLabel(goodsLabelVo);
        return new CommonResponse(CommonResponse.CODE1, goodsLabels, CommonResponse.MESSAGE1);
    }

    /**
     * 根据id查询商品标签
     * @param goodsLabelVo
     * @return
     */
    public CommonResponse findLabelById(GoodsLabelVo goodsLabelVo) {
        GoodsLabel goodsLabel = myGoodsMapper.findLabel(goodsLabelVo);
        if (goodsLabel == null) {
            return new CommonResponse(CommonResponse.CODE10, null, CommonResponse.MESSAGE10);
        }
        return new CommonResponse(CommonResponse.CODE1, goodsLabel, CommonResponse.MESSAGE1);
    }

    //商品类型

    /**
     * 新增商品类型
     * @param goodsTypeVo
     * @return
     */
    public CommonResponse addType(GoodsTypeVo goodsTypeVo) {
        //新增
        if (myGoodsMapper.addType(goodsTypeVo) != 1) {
            return new CommonResponse(CommonResponse.CODE7, null, CommonResponse.MESSAGE7);
        }
        return new CommonResponse(CommonResponse.CODE1, null, CommonResponse.MESSAGE1);
    }

    /**
     * 删除商品类型
     * @param goodsTypeVos
     * @return
     */
    @Transactional
    public CommonResponse deleteType(List<GoodsTypeVo> goodsTypeVos) {
        goodsTypeVos.stream().forEach(goodsTypeVo -> {
            //判断商品类型是否使用
            GoodsVo goodsVo = new GoodsVo(goodsTypeVo.getStoreId(), goodsTypeVo.getId());
            if (myGoodsMapper.findByTypeId(goodsVo).size() > 0) {
                throw new CommonException(CommonResponse.CODE18, CommonResponse.MESSAGE18);
            }
            //删除商品类型
            if (myGoodsMapper.deleteType(goodsTypeVo) != 1) {
                throw new CommonException(CommonResponse.CODE8, CommonResponse.MESSAGE8);
            }
            //获取该商品类型对应的商品属性名
            GoodsPropertyKeyVo goodsPropertyKeyVo = new GoodsPropertyKeyVo(goodsTypeVo.getStoreId(), null, goodsTypeVo.getId());
            List<GoodsPropertyKeyVo> goodsPropertyKeyVos = myGoodsMapper.findPropertyKey(goodsPropertyKeyVo);
            goodsPropertyKeyVos.stream().forEach(vo -> {
                //删除该商品属性名对应的商品属性值
                GoodsPropertyValueVo goodsPropertyValueVo = new GoodsPropertyValueVo(goodsTypeVo.getStoreId(), null, vo.getId());
                myGoodsMapper.deletePropertyValueByPropertyKeyId(goodsPropertyValueVo);
            });
            //删除该商品类型对应的商品属性名
            myGoodsMapper.deletePropertyKeyByTypeId(goodsPropertyKeyVo);
        });
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
        if (myGoodsMapper.updateType(goodsTypeVo) != 1) {
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
            pageVo.setTotalPage((int) Math.ceil(myGoodsMapper.findCountType(goodsTypeVo) * 1.0 / pageVo.getPageSize()));
            pageVo.setStart(pageVo.getPageSize() * (pageVo.getPage() - 1));
            List<GoodsType> goodsTypes = myGoodsMapper.findAllPagedType(goodsTypeVo, pageVo);
            //封装返回结果
            List<Title> titles = new ArrayList<>();
            titles.add(new Title("类型名", "name"));
            CommonResult commonResult = new CommonResult(titles, goodsTypes, pageVo);
            return new CommonResponse(CommonResponse.CODE1, commonResult, CommonResponse.MESSAGE1);
        }
        //不分页
        List<GoodsType> goodsTypes = myGoodsMapper.findAllType(goodsTypeVo);
        return new CommonResponse(CommonResponse.CODE1, goodsTypes, CommonResponse.MESSAGE1);
    }

    /**
     * 根据id查询商品类型
     * @param goodsTypeVo
     * @return
     */
    public CommonResponse findTypeById(GoodsTypeVo goodsTypeVo) {
        GoodsType goodsType = myGoodsMapper.findType(goodsTypeVo);
        if (goodsType == null) {
            return new CommonResponse(CommonResponse.CODE10, null, CommonResponse.MESSAGE10);
        }
        return new CommonResponse(CommonResponse.CODE1, goodsType, CommonResponse.MESSAGE1);
    }

    //商品属性名

    /**
     * 新增商品属性名
     * @param goodsPropertyKeyVo
     * @return
     */
    public CommonResponse addPropertyKey(GoodsPropertyKeyVo goodsPropertyKeyVo) {
        //新增
        if (myGoodsMapper.addPropertyKey(goodsPropertyKeyVo) != 1) {
            return new CommonResponse(CommonResponse.CODE7, null, CommonResponse.MESSAGE7);
        }
        return new CommonResponse(CommonResponse.CODE1, null, CommonResponse.MESSAGE1);
    }

    /**
     * 删除商品属性名
     * 是否要做删除商品属性名功能，删除商品属性名之后，有的商品规格可能还存在该商品属性名？？
     * @param goodsPropertyKeyVos
     * @return
     */
    @Transactional
    public CommonResponse deletePropertyKey(List<GoodsPropertyKeyVo> goodsPropertyKeyVos) {
        goodsPropertyKeyVos.stream().forEach(goodsPropertyKeyVo -> {
            //删除商品属性名
            if (myGoodsMapper.deletePropertyKeyById(goodsPropertyKeyVo) != 1) {
                throw new CommonException(CommonResponse.CODE8, CommonResponse.MESSAGE8);
            }
            //删除该商品属性名对应的商品属性值
            GoodsPropertyValueVo goodsPropertyValueVo = new GoodsPropertyValueVo(goodsPropertyKeyVo.getStoreId(), null, goodsPropertyKeyVo.getId());
            myGoodsMapper.deletePropertyValueByPropertyKeyId(goodsPropertyValueVo);
        });
        return new CommonResponse(CommonResponse.CODE1, null, CommonResponse.MESSAGE1);
    }

    /**
     * 修改商品属性名
     * 是否要做修改商品属性名功能，修改商品属性名之后，有的商品规格可能还存在该商品属性名？？
     * @param goodsPropertyKeyVo
     * @return
     */
    public CommonResponse updatePropertyKey(GoodsPropertyKeyVo goodsPropertyKeyVo) {
        //判断参数
        if (goodsPropertyKeyVo.getId() == null) {
            return new CommonResponse(CommonResponse.CODE3, null, CommonResponse.MESSAGE3);
        }
        //修改
        if (myGoodsMapper.updatePropertyKey(goodsPropertyKeyVo) != 1) {
            return new CommonResponse(CommonResponse.CODE9, null, CommonResponse.MESSAGE9);
        }
        return new CommonResponse(CommonResponse.CODE1, null, CommonResponse.MESSAGE1);
    }

    /**
     * 查询所有商品属性名
     * @param goodsPropertyKeyVo
     * @return
     */
    public CommonResponse findAllPropertyKey(GoodsPropertyKeyVo goodsPropertyKeyVo, PageVo pageVo) {
        //分页
        if (pageVo.getPage() != null && pageVo.getPageSize() != null) {
            //查询所有页数
            pageVo.setTotalPage((int) Math.ceil(myGoodsMapper.findCountPropertyKey(goodsPropertyKeyVo) * 1.0 / pageVo.getPageSize()));
            pageVo.setStart(pageVo.getPageSize() * (pageVo.getPage() - 1));
            List<GoodsPropertyKeyVo> goodsPropertyKeyVos = myGoodsMapper.findAllPagedPropertyKey(goodsPropertyKeyVo, pageVo);
            //封装返回结果
            List<Title> titles = new ArrayList<>();
            titles.add(new Title("商品属性名", "name"));
            titles.add(new Title("所属商品分类", "typeName"));
            CommonResult commonResult = new CommonResult(titles, goodsPropertyKeyVos, pageVo);
            return new CommonResponse(CommonResponse.CODE1, commonResult, CommonResponse.MESSAGE1);
        }
        //不分页
        List<GoodsPropertyKey> goodsPropertyKeys = myGoodsMapper.findAllPropertyKey(goodsPropertyKeyVo);
        return new CommonResponse(CommonResponse.CODE1, goodsPropertyKeys, CommonResponse.MESSAGE1);

    }

    /**
     * 根据id查询商品属性名
     * @param goodsPropertyKeyVo
     * @return
     */
    public CommonResponse findPropertyKeyById(GoodsPropertyKeyVo goodsPropertyKeyVo) {
        List<GoodsPropertyKeyVo> goodsPropertyKeyVos = myGoodsMapper.findPropertyKey(goodsPropertyKeyVo);
        if (goodsPropertyKeyVos.size() == 0) {
            return new CommonResponse(CommonResponse.CODE10, null, CommonResponse.MESSAGE10);
        }
        return new CommonResponse(CommonResponse.CODE1, goodsPropertyKeyVos.get(0), CommonResponse.MESSAGE1);
    }

    //商品属性值

    /**
     * 新增商品属性值
     * @param goodsPropertyValueVo
     * @return
     */
    public CommonResponse addPropertyValue(GoodsPropertyValueVo goodsPropertyValueVo) {
        //新增
        if (myGoodsMapper.addPropertyValue(goodsPropertyValueVo) != 1) {
            return new CommonResponse(CommonResponse.CODE7, null, CommonResponse.MESSAGE7);
        }
        return new CommonResponse(CommonResponse.CODE1, null, CommonResponse.MESSAGE1);
    }

    /**
     * 删除商品属性值
     * 是否要做删除商品属性值功能，删除商品属性值之后，有的商品规格可能还存在该商品属性值？？
     * @param goodsPropertyValueVos
     * @return
     */
    @Transactional
    public CommonResponse deletePropertyValue(List<GoodsPropertyValueVo> goodsPropertyValueVos) {
        goodsPropertyValueVos.stream().forEach(goodsPropertyValueVo -> {
            //删除商品属性值
            if (myGoodsMapper.deletePropertyValueById(goodsPropertyValueVo) != 1) {
                throw new CommonException(CommonResponse.CODE8, CommonResponse.MESSAGE8);
            }
        });
        return new CommonResponse(CommonResponse.CODE1, null, CommonResponse.MESSAGE1);
    }

    /**
     * 修改商品属性值
     * 是否要做修改商品属性值功能，修改商品属性值之后，有的商品规格可能还存在该商品属性值？？
     * @param goodsPropertyValueVo
     * @return
     */
    public CommonResponse updatePropertyValue(GoodsPropertyValueVo goodsPropertyValueVo) {
        //判断参数
        if (goodsPropertyValueVo.getId() == null) {
            return new CommonResponse(CommonResponse.CODE3, null, CommonResponse.MESSAGE3);
        }
        //修改
        if (myGoodsMapper.updatePropertyValue(goodsPropertyValueVo) != 1) {
            return new CommonResponse(CommonResponse.CODE9, null, CommonResponse.MESSAGE9);
        }
        return new CommonResponse(CommonResponse.CODE1, null, CommonResponse.MESSAGE1);
    }

    /**
     * 查询所有商品属性值
     * @param goodsPropertyValueVo
     * @return
     */
    public CommonResponse findAllPropertyValue(GoodsPropertyValueVo goodsPropertyValueVo, PageVo pageVo) {
        //查询所有页数
        pageVo.setTotalPage((int) Math.ceil(myGoodsMapper.findCountPropertyValue(goodsPropertyValueVo) * 1.0 / pageVo.getPageSize()));
        pageVo.setStart(pageVo.getPageSize() * (pageVo.getPage() - 1));
        List<GoodsPropertyValueVo> goodsPropertyValueVos = myGoodsMapper.findAllPagedPropertyValue(goodsPropertyValueVo, pageVo);
        //封装返回结果
        List<Title> titles = new ArrayList<>();
        titles.add(new Title("商品属性值", "name"));
        titles.add(new Title("所属商品属性名", "propertyKeyName"));
        titles.add(new Title("所属商品类型", "typeName"));
        CommonResult commonResult = new CommonResult(titles, goodsPropertyValueVos, pageVo);
        return new CommonResponse(CommonResponse.CODE1, commonResult, CommonResponse.MESSAGE1);
    }

    /**
     * 根据id查询商品属性值
     * @param goodsPropertyValueVo
     * @return
     */
    public CommonResponse findPropertyValueById(GoodsPropertyValueVo goodsPropertyValueVo) {
        List<GoodsPropertyValueVo> goodsPropertyValueVos = myGoodsMapper.findPropertyValue(goodsPropertyValueVo);
        if (goodsPropertyValueVos.size() == 0) {
            return new CommonResponse(CommonResponse.CODE10, null, CommonResponse.MESSAGE10);
        }
        return new CommonResponse(CommonResponse.CODE1, goodsPropertyValueVos.get(0), CommonResponse.MESSAGE1);
    }


    //商品类型/商品属性名/商品属性值

    /**
     * 查询所有商品属性
     * @param goodsTypeVo
     * @return
     */
    public CommonResponse findAllProperties(GoodsTypeVo goodsTypeVo) {
        List<GoodsTypeVo> goodsTypeVos = myGoodsMapper.findAllProperties(goodsTypeVo);
        return new CommonResponse(CommonResponse.CODE1, goodsTypeVos, CommonResponse.MESSAGE1);
    }

    //商品

    /**
     * 新增商品
     * 商品规格至少要1条
     * @param goodsVo
     * @return
     */
     @Transactional
    public CommonResponse add(GoodsVo goodsVo) {
         //判断参数
         if (goodsVo.getTypeId() == null) {
             return new CommonResponse(CommonResponse.CODE3, null, CommonResponse.MESSAGE3);
         }
         //判断商品类型是否存在
         GoodsTypeVo goodsTypeVo = new GoodsTypeVo(goodsVo.getStoreId(), goodsVo.getTypeId());
         if (myGoodsMapper.findType(goodsTypeVo) == null) {
             return new CommonResponse(CommonResponse.CODE7, null, CommonResponse.MESSAGE7);
         }
         //设置初始属性
         goodsVo.setId(UUID.randomUUID().toString());
         goodsVo.setCreateTime(new Date());
         //新增商品
         if (myGoodsMapper.add(goodsVo) != 1) {
             throw new CommonException(CommonResponse.CODE7, CommonResponse.MESSAGE7);
         }
         //判断商品标签是否存在
         List<GoodsLabel> goodsLabels = goodsVo.getGoodsLabels();
         goodsLabels.stream().forEach(goodsLabel -> {
             GoodsLabelVo goodsLabelVo = new GoodsLabelVo(goodsVo.getStoreId(), goodsLabel.getId());
             if (myGoodsMapper.findLabel(goodsLabelVo) == null) {
                 throw new CommonException(CommonResponse.CODE7, CommonResponse.MESSAGE7);
             }
             //新增商品/商品标签关系
             GoodsGoodsLabelVo goodsGoodsLabelVo = new GoodsGoodsLabelVo(goodsVo.getStoreId(), goodsVo.getId(), goodsLabel.getId());
             if (myGoodsMapper.addGoodsLabel(goodsGoodsLabelVo) != 1) {
                 throw new CommonException(CommonResponse.CODE7, CommonResponse.MESSAGE7);
             }
         });
         //新增商品规格
         List<GoodsSkuVo> goodsSkuVos = goodsVo.getGoodsSkuVos();
         goodsSkuVos.stream().forEach(goodsSkuVo -> {
             goodsSkuVo.setStoreId(goodsVo.getStoreId());
             goodsSkuVo.setGoodsId(goodsVo.getId());
             if (myGoodsMapper.addGoodsSku(goodsSkuVo) != 1) {
                 throw new CommonException(CommonResponse.CODE7, CommonResponse.MESSAGE7);
             }
         });
         return new CommonResponse(CommonResponse.CODE1, null, CommonResponse.MESSAGE1);
    }

    /**
     * 删除商品
     * @param goodsVos
     * @return
     */
    @Transactional
    public CommonResponse delete(List<GoodsVo> goodsVos) {
        goodsVos.stream().forEach(goodsVo -> {
            //删除商品
            if (myGoodsMapper.delete(goodsVo) != 1) {
                throw new CommonException(CommonResponse.CODE8, CommonResponse.MESSAGE8);
            }
            //删除商品/商品标签关系
            GoodsGoodsLabelVo goodsGoodsLabelVo = new GoodsGoodsLabelVo(goodsVo.getStoreId(), goodsVo.getId());
            myGoodsMapper.deleteGoodsLabel(goodsGoodsLabelVo);
            //删除该商品对应的商品规格
            GoodsSkuVo goodsSkuVo = new GoodsSkuVo(goodsVo.getStoreId(), goodsVo.getId());
            myGoodsMapper.deleteGoodsSku(goodsSkuVo);
        });
        return new CommonResponse(CommonResponse.CODE1, null, CommonResponse.MESSAGE1);
    }

    /**
     * 修改商品
     * 不能修改商品类型
     * @param goodsVo
     * @return
     */
     @Transactional
    public CommonResponse update(GoodsVo goodsVo) {
         //判断参数
         if (goodsVo.getId() == null) {
             return new CommonResponse(CommonResponse.CODE3, null, CommonResponse.MESSAGE3);
         }
         //修改商品
         if (myGoodsMapper.update(goodsVo) != 1) {
             throw new CommonException(CommonResponse.CODE9, CommonResponse.MESSAGE9);
         }
         //删除商品/商品标签关系
         GoodsGoodsLabelVo goodsGoodsLabelVo = new GoodsGoodsLabelVo(goodsVo.getStoreId(), goodsVo.getId());
         myGoodsMapper.deleteGoodsLabel(goodsGoodsLabelVo);
         //判断商品标签是否存在
         List<GoodsLabel> goodsLabels = goodsVo.getGoodsLabels();
         goodsLabels.stream().forEach(goodsLabel -> {
             GoodsLabelVo goodsLabelVo = new GoodsLabelVo(goodsVo.getStoreId(), goodsLabel.getId());
             if (myGoodsMapper.findLabel(goodsLabelVo) == null) {
                 throw new CommonException(CommonResponse.CODE9, CommonResponse.MESSAGE9);
             }
             //新增商品/商品标签关系
             goodsGoodsLabelVo.setLabelId(goodsLabel.getId());
             if (myGoodsMapper.addGoodsLabel(goodsGoodsLabelVo) != 1) {
                 throw new CommonException(CommonResponse.CODE9, CommonResponse.MESSAGE9);
             }
         });
         //删除该商品对应的商品规格
         myGoodsMapper.deleteGoodsSku(new GoodsSkuVo(goodsVo.getStoreId(), goodsVo.getId()));
         //新增商品规格
         List<GoodsSkuVo> goodsSkuVos = goodsVo.getGoodsSkuVos();
         goodsSkuVos.stream().forEach(goodsSkuVo -> {
             goodsSkuVo.setStoreId(goodsVo.getStoreId());
             goodsSkuVo.setGoodsId(goodsVo.getId());
             if (myGoodsMapper.addGoodsSku(goodsSkuVo) != 1) {
                 throw new CommonException(CommonResponse.CODE9, CommonResponse.MESSAGE9);
             }
         });
         return new CommonResponse(CommonResponse.CODE1, null, CommonResponse.MESSAGE1);
    }

    /**
     * 查询所有商品
     * @param goodsVo
     * @return
     */
    public CommonResponse findAll(GoodsVo goodsVo, PageVo pageVo) {
        //查询所有页数
        pageVo.setTotalPage((int) Math.ceil(myGoodsMapper.findCount(goodsVo) * 1.0 / pageVo.getPageSize()));
        pageVo.setStart(pageVo.getPageSize() * (pageVo.getPage() - 1));
        List<GoodsVo> goodsVos = myGoodsMapper.findAllPaged(goodsVo, pageVo);
        //封装返回结果
        List<Title> titles = new ArrayList<>();
        titles.add(new Title("商品货号", "id"));
        titles.add(new Title("商品名", "name"));
        titles.add(new Title("条码", "barCode"));
        titles.add(new Title("分类", "typeName"));
        titles.add(new Title("上架状态", "putaway"));
        titles.add(new Title("产地", "origin"));
        titles.add(new Title("图片", "image"));
        titles.add(new Title("备注", "remark"));
        titles.add(new Title("创建时间", "createTime"));
        titles.add(new Title("标签", "goodsLabels"));     //list
        CommonResult commonResult = new CommonResult(titles, goodsVos, pageVo);
        return new CommonResponse(CommonResponse.CODE1, commonResult, CommonResponse.MESSAGE1);
    }

    /**
     * 根据id查询商品及sku
     * @param goodsVo
     * @return
     */
    public CommonResponse findSkuById(GoodsVo goodsVo, PageVo pageVo) {
        //查询所有页数
        pageVo.setTotalPage((int) Math.ceil(myGoodsMapper.findCountSkuById(goodsVo) * 1.0 / pageVo.getPageSize()));
        pageVo.setStart(pageVo.getPageSize() * (pageVo.getPage() - 1));
        GoodsVo goodsVoPaged = myGoodsMapper.findAllPagedSkuById(goodsVo, pageVo);
        if (goodsVoPaged == null) {
            return new CommonResponse(CommonResponse.CODE10, null, CommonResponse.MESSAGE10);
        }
        goodsVoPaged.setGoodsLabels(myGoodsMapper.findAll(goodsVo).get(0).getGoodsLabels());
        //封装返回结果
        List<Title> titles = new ArrayList<>();
        titles.add(new Title("商品货号", "id"));
        titles.add(new Title("商品名", "name"));
        titles.add(new Title("条码", "barCode"));
        titles.add(new Title("分类", "typeName"));
        titles.add(new Title("上架状态", "putaway"));
        titles.add(new Title("产地", "origin"));
        titles.add(new Title("图片", "image"));
        titles.add(new Title("备注", "remark"));
        titles.add(new Title("创建时间", "createTime"));
        titles.add(new Title("标签", "goodsLabels"));     //list
        titles.add(new Title("sku", "goodsSkuVos"));        //list
        CommonResult commonResult = new CommonResult(titles, goodsVoPaged, pageVo);
        return new CommonResponse(CommonResponse.CODE1, commonResult, CommonResponse.MESSAGE1);
    }

    /**
     * 导出商品信息
     * @param goodsVo
     * @param response
     * @throws IOException
     */
    public void exportGoods(GoodsVo goodsVo, HttpServletResponse response) throws IOException {
        //根据筛选条件查找商品
        List<GoodsVo> goodsVos = myGoodsMapper.findAll(goodsVo);
        GoodsVo vo = goodsVos.get(0);
        //备注
        String remark = "【筛选条件】" +
                "，货号：" + (goodsVo.getId() == null ? "无" : vo.getId()) +
                "，条码：" + (goodsVo.getBarCode() == null ? "无" : vo.getBarCode()) +
                "，类型：" + (goodsVo.getTypeId() == null ? "无" : vo.getTypeName()) +
                "，上架状态：" + (goodsVo.getPutaway() == null ? "无" : vo.getPutaway().toString());
        //标题行内容
        List<String> titleRowCell = Arrays.asList(new String[]{
                "商品货号", "商品名", "条码", "分类", "上架状态（0：不上架，1：上架）", "产地", "图片", "备注", "创建时间", "标签（多个用英文逗号隔开）"
        });
        //最后一个必填列列数
        int lastRequiredCol = -1;
        //数据行
        List<List<String>> dataRowCells = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        goodsVos.stream().forEach(gVo -> {
            List<String> dataRowCell = new ArrayList<>();
            dataRowCell.add(gVo.getId());
            dataRowCell.add(gVo.getName());
            dataRowCell.add(gVo.getBarCode());
            dataRowCell.add(gVo.getTypeName());
            dataRowCell.add(gVo.getPutaway().toString());
            dataRowCell.add(gVo.getOrigin());
            dataRowCell.add(gVo.getImage());
            dataRowCell.add(gVo.getRemark());
            dataRowCell.add(sdf.format(gVo.getCreateTime()));
            String label = "";
            List<GoodsLabel> goodsLabels = gVo.getGoodsLabels();
            for (int i = 0; i < goodsLabels.size(); i++) {
                if (i != goodsLabels.size() - 1) {
                    label += goodsLabels.get(i).getName() + ",";
                } else {
                    label += goodsLabels.get(i).getName();
                }
            }
            dataRowCell.add(label);
            dataRowCells.add(dataRowCell);
        });
        //文件名
        String fileName = "【商品导出】_" + System.currentTimeMillis() + ".xls";
        //输出excel
        List<List<String>> titleRowCells = new ArrayList<>();
        titleRowCells.add(titleRowCell);
        List<List<List<String>>> dataRowCellss = new ArrayList<>();
        dataRowCellss.add(dataRowCells);
        CommonUtil.outputExcel(remark, titleRowCells, lastRequiredCol, dataRowCellss, fileName, response);
    }

    /**
     * 导出商品信息及sku
     * @param goodsVo
     * @param response
     * @throws IOException
     */
    public void exportGoodsSku(GoodsVo goodsVo, HttpServletResponse response) throws IOException {
        //根据筛选条件查找商品
        List<GoodsVo> goodsVos = myGoodsMapper.findAllSku(goodsVo);
        GoodsVo vo = goodsVos.get(0);
        //备注
        String remark = "【筛选条件】" +
                "，货号：" + (goodsVo.getId() == null ? "无" : vo.getId()) +
                "，条码：" + (goodsVo.getBarCode() == null ? "无" : vo.getBarCode()) +
                "，类型：" + (goodsVo.getTypeId() == null ? "无" : vo.getTypeName()) +
                "，上架状态：" + (goodsVo.getPutaway() == null ? "无" : vo.getPutaway().toString());

        List<List<String>> titleRowCells = new ArrayList<>();
        List<List<List<String>>> dataRowCellss = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        goodsVos.stream().forEach(goodsVo1 -> {
            //标题行内容
            List<String> titleRowCell;
            if (goodsVo1.getSkus() != null && !goodsVo1.getSkus().equals("")) {
                titleRowCell = new ArrayList<>(Arrays.asList(new String[]{
                        "商品货号", "商品名", "条码", "分类", "上架状态（0：不上架，1：上架）", "产地", "图片", "备注", "创建时间", "标签（多个用英文逗号隔开）", "进价", "零售价", "vip售价", "库存", "积分"
                }));
                List<Skus> skuses = JSON.parseArray(goodsVo1.getSkus(), Skus.class);
                skuses.stream().forEach(skus -> {
                    titleRowCell.add(skus.getKey());
                });
            } else {
                titleRowCell = new ArrayList<>(Arrays.asList(new String[]{
                        "商品货号", "商品名", "条码", "分类", "上架状态（0：不上架，1：上架）", "产地", "图片", "备注", "创建时间", "标签（多个用英文逗号隔开）"
                }));
            }
            titleRowCells.add(titleRowCell);
            //数据行内容
            List<List<String>> dataRowCells = new ArrayList<>();
            List<GoodsSkuVo> goodsSkuVos = goodsVo1.getGoodsSkuVos();
            if (goodsSkuVos.size() == 0) {
                List<String> dataRowCell = new ArrayList<>();
                for (int i = 0; i < titleRowCell.size(); i++) {
                    dataRowCell.add("");
                }
                dataRowCell.set(0, goodsVo1.getId());
                dataRowCell.set(1, goodsVo1.getName());
                dataRowCell.set(2, goodsVo1.getBarCode());
                dataRowCell.set(3, goodsVo1.getTypeName());
                dataRowCell.set(4, goodsVo1.getPutaway().toString());
                dataRowCell.set(5, goodsVo1.getOrigin());
                dataRowCell.set(6, goodsVo1.getImage());
                dataRowCell.set(7, goodsVo1.getRemark());
                dataRowCell.set(8, sdf.format(goodsVo1.getCreateTime()));
                String label = "";
                List<GoodsLabel> goodsLabels = goodsVo1.getGoodsLabels();
                for (int i = 0; i < goodsLabels.size(); i++) {
                    if (i != goodsLabels.size() - 1) {
                        label += goodsLabels.get(i).getName() + ",";
                    } else {
                        label += goodsLabels.get(i).getName();
                    }
                }
                dataRowCell.set(9, label);
                dataRowCells.add(dataRowCell);
            } else {
                goodsSkuVos.stream().forEach(goodsSkuVo -> {
                    List<String> dataRowCell = new ArrayList<>();
                    for (int i = 0; i < titleRowCell.size(); i++) {
                        dataRowCell.add("");
                    }
                    dataRowCell.set(0, goodsVo1.getId());
                    dataRowCell.set(1, goodsVo1.getName());
                    dataRowCell.set(2, goodsVo1.getBarCode());
                    dataRowCell.set(3, goodsVo1.getTypeName());
                    dataRowCell.set(4, goodsVo1.getPutaway().toString());
                    dataRowCell.set(5, goodsVo1.getOrigin());
                    dataRowCell.set(6, goodsVo1.getImage());
                    dataRowCell.set(7, goodsVo1.getRemark());
                    dataRowCell.set(8, sdf.format(goodsVo1.getCreateTime()));
                    String label = "";
                    List<GoodsLabel> goodsLabels = goodsVo1.getGoodsLabels();
                    for (int i = 0; i < goodsLabels.size(); i++) {
                        if (i != goodsLabels.size() - 1) {
                            label += goodsLabels.get(i).getName() + ",";
                        } else {
                            label += goodsLabels.get(i).getName();
                        }
                    }
                    dataRowCell.set(9, label);
                    dataRowCell.set(10, goodsSkuVo.getPurchasePrice().toString());
                    dataRowCell.set(11, goodsSkuVo.getRetailPrice().toString());
                    dataRowCell.set(12, goodsSkuVo.getVipPrice().toString());
                    dataRowCell.set(13, goodsSkuVo.getInventory().toString());
                    dataRowCell.set(14, goodsSkuVo.getIntegral().toString());
                    List<Sku> skus = JSON.parseArray(goodsSkuVo.getSku(), Sku.class);
                    skus.stream().forEach(sku -> {
                        int pos = titleRowCell.indexOf(sku.getKey());
                        if (pos != -1) {
                            dataRowCell.set(pos, sku.getValue());
                        }
                    });
                    dataRowCells.add(dataRowCell);
                });
            }
            dataRowCellss.add(dataRowCells);
        });
        //最后一个必填列列数
        int lastRequiredCol = -1;
        //文件名
        String fileName = "【商品及sku导出】_" + System.currentTimeMillis() + ".xls";
        //输出excel
        CommonUtil.outputExcel(remark, titleRowCells, lastRequiredCol, dataRowCellss, fileName, response);
    }

    /**
     * 获取导入商品模版
     * @param response
     * @throws IOException
     */
    public void getImportGoodsTemplate(HttpServletResponse response) throws IOException {
        //备注
        String remark = "【导入备注】，只能增加行数，按照标题填写，不能增加其他列。" +
                "必填列已标红，其中分类、标签需要填写系统中已经存在的，否则会导致导入失败。";
        //标题行内容
        List<String> titleRowCell = Arrays.asList(new String[]{
                "商品名", "条码", "分类", "上架状态（0：不上架，1：上架）", "产地", "图片", "备注", "标签（多个用英文逗号隔开）"
        });
        //最后一个必填列列数
        int lastRequiredCol = 3;
        //文件名
        String fileName = "【商品导入模版】_" + System.currentTimeMillis() + ".xls";
        //输出excel
        List<List<String>> titleRowCells = new ArrayList<>();
        titleRowCells.add(titleRowCell);
        CommonUtil.outputExcel(remark, titleRowCells, lastRequiredCol, new ArrayList<>(), fileName, response);
    }

    /**
     * 导入商品
     * @param multipartFile
     * @param storeId
     * @return
     * @throws IOException
     */
    @Transactional
    public CommonResponse importGoods(MultipartFile multipartFile, Integer storeId) throws IOException {
        //创建Excel工作簿
        HSSFWorkbook workbook = new HSSFWorkbook(multipartFile.getInputStream());
        //创建一个工作表sheet
        HSSFSheet sheet = workbook.getSheetAt(0);
        //获取数据
        for (int j = 3; j <= sheet.getLastRowNum(); j++) {
            HSSFRow row = sheet.getRow(sheet.getLastRowNum());
            GoodsVo goodsVo = new GoodsVo();
            goodsVo.setStoreId(storeId);
            goodsVo.setId(UUID.randomUUID().toString());
            goodsVo.setCreateTime(new Date());
            goodsVo.setName(CommonUtil.getCellValue(row.getCell(0)));
            goodsVo.setBarCode(CommonUtil.getCellValue(row.getCell(1)));
            //判断商品类型是否存在
            GoodsTypeVo goodsTypeVo = new GoodsTypeVo(storeId, CommonUtil.getCellValue(row.getCell(2)));
            GoodsType goodsType = myGoodsMapper.findType(goodsTypeVo);
            if (goodsType == null) {
                throw new CommonException(CommonResponse.CODE17, CommonResponse.MESSAGE17);
            }
            goodsVo.setTypeId(goodsType.getId());
            String putaway = CommonUtil.getCellValue(row.getCell(3));
            if (!putaway.equals("")) {
                goodsVo.setPutaway(Byte.valueOf(putaway));
            }
            goodsVo.setOrigin(CommonUtil.getCellValue(row.getCell(4)));
            goodsVo.setImage(CommonUtil.getCellValue(row.getCell(5)));
            goodsVo.setRemark(CommonUtil.getCellValue(row.getCell(6)));
            //判断商品标签是否存在
            String label = CommonUtil.getCellValue(row.getCell(7));
            if (!label.equals("")) {
                String[] labels = label.split(",");
                for (int i = 0; i < labels.length; i++) {
                    GoodsLabelVo goodsLabelVo = new GoodsLabelVo(storeId, labels[i]);
                    GoodsLabel goodsLabel = myGoodsMapper.findLabel(goodsLabelVo);
                    if (goodsLabel == null) {
                        throw new CommonException(CommonResponse.CODE17, CommonResponse.MESSAGE17);
                    }
                    //新增商品/商品标签关系
                    GoodsGoodsLabelVo goodsGoodsLabelVo = new GoodsGoodsLabelVo(storeId, goodsVo.getId(), goodsLabel.getId());
                    if (myGoodsMapper.addGoodsLabel(goodsGoodsLabelVo) != 1) {
                        throw new CommonException(CommonResponse.CODE7, CommonResponse.MESSAGE7);
                    }
                }
            }
            //保存商品
            if (myGoodsMapper.add(goodsVo) != 1) {
                throw new CommonException(CommonResponse.CODE7, CommonResponse.MESSAGE7);
            }
        }
        return new CommonResponse(CommonResponse.CODE1, null, CommonResponse.MESSAGE1);
    }
}
