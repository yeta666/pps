package com.yeta.pps.service;

import com.alibaba.fastjson.JSON;
import com.yeta.pps.exception.CommonException;
import com.yeta.pps.mapper.MyGoodsMapper;
import com.yeta.pps.mapper.MyStorageMapper;
import com.yeta.pps.mapper.MyWarehouseMapper;
import com.yeta.pps.po.*;
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
import java.lang.System;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 商品相关逻辑处理
 * @author YETA
 * @date 2018/12/02/23:30
 */
@Service
public class GoodsService {

    @Autowired
    private MyGoodsMapper myGoodsMapper;

    @Autowired
    private MyWarehouseMapper myWarehouseMapper;

    @Autowired
    private MyStorageMapper myStorageMapper;

    //商品标签

    /**
     * 新增商品标签
     * @param goodsLabelVo
     * @return
     */
    public CommonResponse addLabel(GoodsLabelVo goodsLabelVo) {
        //新增
        if (myGoodsMapper.addLabel(goodsLabelVo) != 1) {
            return CommonResponse.error(CommonResponse.ADD_ERROR);
        }
        return CommonResponse.success();
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
                throw new CommonException(CommonResponse.DELETE_ERROR, CommonResponse.USED_ERROR);
            }
            //删除商品标签
            if (myGoodsMapper.deleteLabel(goodsLabelVo) != 1) {
                throw new CommonException(CommonResponse.DELETE_ERROR);
            }
        });
        return CommonResponse.success();
    }

    /**
     * 修改商品标签
     * @param goodsLabelVo
     * @return
     */
    public CommonResponse updateLabel(GoodsLabelVo goodsLabelVo) {
        //判断参数
        if (goodsLabelVo.getId() == null) {
            return CommonResponse.error(CommonResponse.PARAMETER_ERROR);
        }
        //修改
        if (myGoodsMapper.updateLabel(goodsLabelVo) != 1) {
            return CommonResponse.error(CommonResponse.UPDATE_ERROR);
        }
        return CommonResponse.success();
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
            return CommonResponse.success(commonResult);
        }
        //不分页
        List<GoodsLabel> goodsLabels = myGoodsMapper.findAllLabel(goodsLabelVo);
        return CommonResponse.success(goodsLabels);
    }

    /**
     * 根据id查询商品标签
     * @param goodsLabelVo
     * @return
     */
    public CommonResponse findLabelById(GoodsLabelVo goodsLabelVo) {
        GoodsLabel goodsLabel = myGoodsMapper.findLabel(goodsLabelVo);
        if (goodsLabel == null) {
            return CommonResponse.error(CommonResponse.FIND_ERROR);
        }
        return CommonResponse.success(goodsLabel);
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
            return CommonResponse.error(CommonResponse.ADD_ERROR);
        }
        return CommonResponse.success();
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
                throw new CommonException(CommonResponse.DELETE_ERROR, CommonResponse.USED_ERROR);
            }
            //删除商品类型
            if (myGoodsMapper.deleteType(goodsTypeVo) != 1) {
                throw new CommonException(CommonResponse.DELETE_ERROR);
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
        return CommonResponse.success();
    }

    /**
     * 修改商品类型
     * @param goodsTypeVo
     * @return
     */
    public CommonResponse updateType(GoodsTypeVo goodsTypeVo) {
        //判断参数
        if (goodsTypeVo.getId() == null) {
            return CommonResponse.error(CommonResponse.PARAMETER_ERROR);
        }
        //修改
        if (myGoodsMapper.updateType(goodsTypeVo) != 1) {
            return CommonResponse.error(CommonResponse.UPDATE_ERROR);
        }
        return CommonResponse.success();
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
            return CommonResponse.success(commonResult);
        }
        //不分页
        List<GoodsType> goodsTypes = myGoodsMapper.findAllType(goodsTypeVo);
        return CommonResponse.success(goodsTypes);
    }

    /**
     * 根据id查询商品类型
     * @param goodsTypeVo
     * @return
     */
    public CommonResponse findTypeById(GoodsTypeVo goodsTypeVo) {
        GoodsType goodsType = myGoodsMapper.findType(goodsTypeVo);
        if (goodsType == null) {
            return CommonResponse.error(CommonResponse.FIND_ERROR);
        }
        return CommonResponse.success(goodsType);
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
            return CommonResponse.error(CommonResponse.ADD_ERROR);
        }
        return CommonResponse.success();
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
                throw new CommonException(CommonResponse.DELETE_ERROR);
            }
            //删除该商品属性名对应的商品属性值
            GoodsPropertyValueVo goodsPropertyValueVo = new GoodsPropertyValueVo(goodsPropertyKeyVo.getStoreId(), null, goodsPropertyKeyVo.getId());
            myGoodsMapper.deletePropertyValueByPropertyKeyId(goodsPropertyValueVo);
        });
        return CommonResponse.success();
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
            return CommonResponse.error(CommonResponse.PARAMETER_ERROR);
        }
        //修改
        if (myGoodsMapper.updatePropertyKey(goodsPropertyKeyVo) != 1) {
            return CommonResponse.error(CommonResponse.UPDATE_ERROR);
        }
        return CommonResponse.success();
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
            return CommonResponse.success(commonResult);
        }
        //不分页
        List<GoodsPropertyKey> goodsPropertyKeys = myGoodsMapper.findAllPropertyKey(goodsPropertyKeyVo);
        return CommonResponse.success(goodsPropertyKeys);

    }

    /**
     * 根据id查询商品属性名
     * @param goodsPropertyKeyVo
     * @return
     */
    public CommonResponse findPropertyKeyById(GoodsPropertyKeyVo goodsPropertyKeyVo) {
        List<GoodsPropertyKeyVo> goodsPropertyKeyVos = myGoodsMapper.findPropertyKey(goodsPropertyKeyVo);
        if (goodsPropertyKeyVos.size() == 0) {
            return CommonResponse.error(CommonResponse.FIND_ERROR);
        }
        return CommonResponse.success(goodsPropertyKeyVos.get(0));
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
            return CommonResponse.error(CommonResponse.ADD_ERROR);
        }
        return CommonResponse.success();
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
                throw new CommonException(CommonResponse.DELETE_ERROR);
            }
        });
        return CommonResponse.success();
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
            return CommonResponse.error(CommonResponse.PARAMETER_ERROR);
        }
        //修改
        if (myGoodsMapper.updatePropertyValue(goodsPropertyValueVo) != 1) {
            return CommonResponse.error(CommonResponse.UPDATE_ERROR);
        }
        return CommonResponse.success();
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
        return CommonResponse.success(commonResult);
    }

    /**
     * 根据id查询商品属性值
     * @param goodsPropertyValueVo
     * @return
     */
    public CommonResponse findPropertyValueById(GoodsPropertyValueVo goodsPropertyValueVo) {
        List<GoodsPropertyValueVo> goodsPropertyValueVos = myGoodsMapper.findPropertyValue(goodsPropertyValueVo);
        if (goodsPropertyValueVos.size() == 0) {
            return CommonResponse.error(CommonResponse.FIND_ERROR);
        }
        return CommonResponse.success(goodsPropertyValueVos.get(0));
    }


    //商品类型/商品属性名/商品属性值

    /**
     * 查询所有商品属性
     * @param goodsTypeVo
     * @return
     */
    public CommonResponse findAllProperties(GoodsTypeVo goodsTypeVo) {
        List<GoodsTypeVo> goodsTypeVos = myGoodsMapper.findAllProperties(goodsTypeVo);
        return CommonResponse.success(goodsTypeVos);
    }

    //商品

    /**
     * 新增商品
     * @param goodsVo
     * @return
     */
    @Transactional
    public CommonResponse add(GoodsVo goodsVo) {
        //判断参数
        if (goodsVo.getTypeId() == null) {
            return CommonResponse.error(CommonResponse.PARAMETER_ERROR);
        }

        //设置初始属性
        goodsVo.setId(UUID.randomUUID().toString().replace("-", ""));
        goodsVo.setCreateTime(new Date());

        //新增商品
        if (myGoodsMapper.add(goodsVo) != 1) {
            return CommonResponse.error(CommonResponse.ADD_ERROR);
        }

        //判断是否关联商品标签
        if (goodsVo.getGoodsLabels() != null && goodsVo.getGoodsLabels().size() > 0) {
            List<GoodsLabel> goodsLabels = goodsVo.getGoodsLabels();
            goodsLabels.stream().forEach(goodsLabel -> {
                //判断商品标签参数
                if (goodsLabel.getId() == null) {
                    throw new CommonException(CommonResponse.PARAMETER_ERROR);
                }

                //新增商品/商品标签关系
                GoodsGoodsLabelVo goodsGoodsLabelVo = new GoodsGoodsLabelVo(goodsVo.getStoreId(), goodsVo.getId(), goodsLabel.getId());
                if (myGoodsMapper.addGoodsLabel(goodsGoodsLabelVo) != 1) {
                    throw new CommonException(CommonResponse.ADD_ERROR);
                }
            });
        }

        //关联商品规格
        List<Warehouse> warehouses = myWarehouseMapper.findAll(new WarehouseVo(goodsVo.getStoreId()));
        addGoodsSkuMethod(goodsVo.getGoodsSkuVos(), warehouses, goodsVo);
        return CommonResponse.success();
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
                throw new CommonException(CommonResponse.DELETE_ERROR);
            }
            //删除商品/商品标签关系
            GoodsGoodsLabelVo goodsGoodsLabelVo = new GoodsGoodsLabelVo(goodsVo.getStoreId(), goodsVo.getId());
            myGoodsMapper.deleteGoodsLabel(goodsGoodsLabelVo);
            //删除该商品对应的商品规格
            GoodsSkuVo goodsSkuVo = new GoodsSkuVo(goodsVo.getStoreId(), goodsVo.getId());
            myGoodsMapper.deleteGoodsSku(goodsSkuVo);
        });
        return CommonResponse.success();
    }

    /**
     * 修改商品
     * @param goodsVo
     * @return
     */
    @Transactional
    public CommonResponse update(GoodsVo goodsVo) {
        //判断参数
        if (goodsVo.getId() == null) {
            return CommonResponse.error(CommonResponse.PARAMETER_ERROR);
        }

        //修改商品
        if (myGoodsMapper.update(goodsVo) != 1) {
            return CommonResponse.error(CommonResponse.UPDATE_ERROR);
        }

        //删除商品/商品标签关系
        GoodsGoodsLabelVo goodsGoodsLabelVo = new GoodsGoodsLabelVo(goodsVo.getStoreId(), goodsVo.getId());
        myGoodsMapper.deleteGoodsLabel(goodsGoodsLabelVo);

        //判断是否关联商品标签
        if (goodsVo.getGoodsLabels() != null && goodsVo.getGoodsLabels().size() > 0) {
            List<GoodsLabel> goodsLabels = goodsVo.getGoodsLabels();
            goodsLabels.stream().forEach(goodsLabel -> {
                //判断商品标签参数
                if (goodsLabel.getId() == null) {
                    throw new CommonException(CommonResponse.PARAMETER_ERROR);
                }

                //新增商品/商品标签关系
                goodsGoodsLabelVo.setLabelId(goodsLabel.getId());
                if (myGoodsMapper.addGoodsLabel(goodsGoodsLabelVo) != 1) {
                    throw new CommonException(CommonResponse.UPDATE_ERROR);
                }
            });
        }

        //判断该商品原来是否关联商品规格
        List<GoodsSku> goodsSkus = myGoodsMapper.findAllGoodsSku(new GoodsSkuVo(goodsVo.getStoreId()));
        List<Warehouse> warehouses = myWarehouseMapper.findAll(new WarehouseVo(goodsVo.getStoreId()));
        if (goodsSkus.size() > 0) {     //数据库中有商品规格
            //过滤该商品的商品规格
            goodsSkus = goodsSkus.stream().filter(goodsSku -> goodsSku.getGoodsId().equals(goodsVo.getId())).collect(Collectors.toList());
            if (goodsSkus != null && goodsSkus.size() > 0) {        //原来关联了商品规格
                if (goodsVo.getSkus() != null && goodsVo.getGoodsSkuVos() != null && goodsVo.getGoodsSkuVos().size() > 0) {     //参数中的商品规格不为空
                    List<GoodsSkuVo> goodsSkuVos = goodsVo.getGoodsSkuVos();
                    int flag = 0;
                    for (GoodsSku goodsSku : goodsSkus) {
                        Iterator<GoodsSkuVo> it = goodsSkuVos.iterator();
                        while (it.hasNext()) {
                            GoodsSkuVo goodsSkuVo = it.next();
                            goodsSkuVo.setStoreId(goodsVo.getStoreId());

                            if (goodsSkuVo.getId() != null && goodsSku.getId() == goodsSkuVo.getId()) {
                                flag++;
                                //判断商品规格参数
                                if (goodsSkuVo.getSku() == null || goodsSkuVo.getPurchasePrice() == null || goodsSkuVo.getRetailPrice() == null || goodsSkuVo.getVipPrice() == null || goodsSkuVo.getIntegral() == null) {
                                    throw new CommonException(CommonResponse.PARAMETER_ERROR);
                                }
                                if (myGoodsMapper.updateGoodsSku(goodsSkuVo) != 1) {
                                    throw new CommonException(CommonResponse.UPDATE_ERROR);
                                }
                                it.remove();
                            }
                        }
                    }
                    if (flag != goodsSkus.size()) {
                        throw new CommonException(CommonResponse.UPDATE_ERROR);
                    }
                    addGoodsSkuMethod(goodsVo.getGoodsSkuVos(), warehouses, goodsVo);
                } else {        //原来关联了商品规格但是参数中的商品规格为空
                    throw new CommonException(CommonResponse.UPDATE_ERROR);
                }
            } else {        //原来没有关联商品规格
                addGoodsSkuMethod(goodsVo.getGoodsSkuVos(), warehouses, goodsVo);
            }
        } else {        //数据库中还没有商品规格
            addGoodsSkuMethod(goodsVo.getGoodsSkuVos(), warehouses, goodsVo);
        }
        return CommonResponse.success();
    }

    /**
     * 新增关联商品规格的方法
     * @param goodsSkuVos
     * @param warehouses
     * @param goodsVo
     */
    @Transactional
    public void addGoodsSkuMethod(List<GoodsSkuVo> goodsSkuVos, List<Warehouse> warehouses, GoodsVo goodsVo) {
        if (goodsVo.getSkus() != null && goodsVo.getGoodsSkuVos() != null && goodsVo.getGoodsSkuVos().size() > 0) {     //参数中的商品规格不为空
            //关联新添加的商品规格
            goodsSkuVos.stream().forEach(goodsSkuVo -> {
                //判断商品规格参数
                if (goodsSkuVo.getSku() == null || goodsSkuVo.getPurchasePrice() == null || goodsSkuVo.getRetailPrice() == null || goodsSkuVo.getVipPrice() == null || goodsSkuVo.getIntegral() == null) {
                    throw new CommonException(CommonResponse.PARAMETER_ERROR);
                }

                //设置初始属性
                goodsSkuVo.setStoreId(goodsVo.getStoreId());
                goodsSkuVo.setGoodsId(goodsVo.getId());

                //新增商品规格
                if (myGoodsMapper.addGoodsSku(goodsSkuVo) != 1) {
                    throw new CommonException(CommonResponse.ADD_ERROR, "新增商品规格失败");
                }

                warehouses.stream().forEach(warehouse -> {
                    //新增商品规格仓库关系
                    if (myGoodsMapper.initializeOpening(new WarehouseGoodsSkuVo(goodsVo.getStoreId(), warehouse.getId(), goodsSkuVo.getId())) != 1) {
                        throw new CommonException(CommonResponse.ADD_ERROR, "新增商品规格失败");
                    }
                });
            });
        }
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
        return CommonResponse.success(commonResult);
    }

    /**
     * 导出商品信息
     * @param goodsVo
     * @param response
     * @throws IOException
     */
    public void exportGoods(GoodsVo goodsVo, HttpServletResponse response) throws IOException {
        try {
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
        } catch (Exception e) {
            throw new CommonException(CommonResponse.IMPORT_ERROR, e.getMessage());
        }
    }

    /**
     * 导出商品信息及sku
     * @param goodsVo
     * @param response
     * @throws IOException
     */
    public void exportGoodsSku(GoodsVo goodsVo, HttpServletResponse response) throws IOException {
        try {
            //根据筛选条件查找商品
            List<GoodsVo> goodsVos = myGoodsMapper.findAll(goodsVo);
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
                            "商品货号", "商品名", "条码", "分类", "上架状态（0：不上架，1：上架）", "产地", "图片", "备注", "创建时间", "标签（多个用英文逗号隔开）", "进价", "零售价", "vip售价", "积分"
                    }));
                    System.out.println(goodsVo1.getSkus());
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
                        dataRowCell.set(13, goodsSkuVo.getIntegral().toString());
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
        } catch (Exception e) {
            throw new CommonException(CommonResponse.IMPORT_ERROR, e.getMessage());
        }
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
        //判断必填列
        //创建Excel工作簿
        HSSFWorkbook workbook = new HSSFWorkbook(multipartFile.getInputStream());
        //创建一个工作表sheet
        HSSFSheet sheet = workbook.getSheetAt(0);
        //获取数据
        for (int j = 3; j <= sheet.getLastRowNum(); j++) {
            HSSFRow row = sheet.getRow(sheet.getLastRowNum());
            GoodsVo goodsVo = new GoodsVo();
            goodsVo.setStoreId(storeId);
            goodsVo.setId(UUID.randomUUID().toString().replace("-", ""));
            goodsVo.setCreateTime(new Date());
            goodsVo.setName(CommonUtil.getCellValue(row.getCell(0)));
            goodsVo.setBarCode(CommonUtil.getCellValue(row.getCell(1)));
            //判断商品类型是否存在
            GoodsTypeVo goodsTypeVo = new GoodsTypeVo(storeId, CommonUtil.getCellValue(row.getCell(2)));
            GoodsType goodsType = myGoodsMapper.findType(goodsTypeVo);
            if (goodsType == null) {
                throw new CommonException(CommonResponse.IMPORT_ERROR, "商品类型错误");
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
                        throw new CommonException(CommonResponse.IMPORT_ERROR, "商品标签错误");
                    }
                    //新增商品/商品标签关系
                    GoodsGoodsLabelVo goodsGoodsLabelVo = new GoodsGoodsLabelVo(storeId, goodsVo.getId(), goodsLabel.getId());
                    if (myGoodsMapper.addGoodsLabel(goodsGoodsLabelVo) != 1) {
                        throw new CommonException(CommonResponse.IMPORT_ERROR);
                    }
                }
            }
            //保存商品
            if (myGoodsMapper.add(goodsVo) != 1) {
                throw new CommonException(CommonResponse.IMPORT_ERROR);
            }
        }
        return CommonResponse.success();
    }

    //下单查商品

    /**
     * 下单查商品
     * @param warehouseGoodsSkuVo
     * @return
     */
    public CommonResponse findCanUseByWarehouseId(WarehouseGoodsSkuVo warehouseGoodsSkuVo) {
        List<GoodsTypeVo> canUseGoods = myGoodsMapper.findCanUseByWarehouseId(warehouseGoodsSkuVo);
        //补上最新结存数量、结存成本单价、结存成本金额
        canUseGoods.stream().forEach(goodsTypeVo -> {
            goodsTypeVo.getGoodsVos().stream().forEach(goodsVo -> {
                goodsVo.getGoodsSkuVos().stream().forEach(goodsSkuVo -> {
                    StorageCheckOrderVo storageCheckOrderVo = myStorageMapper.findLastCheckMoneyByGoodsSkuIdAndWarehouseId(new StorageCheckOrderVo(warehouseGoodsSkuVo.getStoreId(), goodsSkuVo.getId(), warehouseGoodsSkuVo.getWarehouseId()));
                    goodsSkuVo.setCheckQuantity(storageCheckOrderVo.getCheckQuantity());
                    goodsSkuVo.setCheckMoney(storageCheckOrderVo.getCheckMoney());
                    goodsSkuVo.setCheckTotalMoney(storageCheckOrderVo.getCheckTotalMoney());
                });
            });
        });
        return CommonResponse.success(canUseGoods);
    }
}
