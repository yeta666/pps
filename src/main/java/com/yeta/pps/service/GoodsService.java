package com.yeta.pps.service;

import com.alibaba.fastjson.JSON;
import com.yeta.pps.exception.CommonException;
import com.yeta.pps.mapper.MyGoodsMapper;
import com.yeta.pps.mapper.MyOrderGoodsSkuMapper;
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

    @Autowired
    private MyOrderGoodsSkuMapper myOrderGoodsSkuMapper;

    @Autowired
    private PrimaryKeyUtil primaryKeyUtil;

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
        goodsVo.setId(primaryKeyUtil.getPrimaryKeyMethod(myGoodsMapper.findPrimaryKey(goodsVo), "sp"));
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
        //查询所有商品规格
        List<GoodsSku> goodsSkus = myGoodsMapper.findAllGoodsSku(new GoodsSkuVo(goodsVos.get(0).getStoreId()));
        //查询所有单据/商品规格关系
        List<OrderGoodsSkuVo> orderGoodsSkuVos = myOrderGoodsSkuMapper.findAllOrderGoodsSku(new OrderGoodsSkuVo(goodsVos.get(0).getStoreId()));
        goodsVos.stream().forEach(goodsVo -> {
            //判断商品是否使用
            //1. storage_check_order
            if (myStorageMapper.findLastCheckMoneyByGoodsId(new StorageCheckOrderVo(goodsVo.getStoreId(), goodsVo.getId())) != null) {
                throw new CommonException(CommonResponse.DELETE_ERROR, CommonResponse.USED_ERROR);
            }
            //2. order_goods_sku
            goodsSkus.stream().filter(goodsSku -> goodsSku.getGoodsId().equals(goodsVo.getId())).collect(Collectors.toList()).stream().forEach(goodsSku -> {
                Optional<OrderGoodsSkuVo> optional = orderGoodsSkuVos.stream().filter(orderGoodsSkuVo -> orderGoodsSkuVo.getGoodsSkuId().toString().equals(goodsSku.getId().toString())).findFirst();
                if (optional.isPresent()) {
                    throw new CommonException(CommonResponse.DELETE_ERROR, CommonResponse.USED_ERROR);
                }
            });

            //删除商品
            if (myGoodsMapper.delete(goodsVo) != 1) {
                throw new CommonException(CommonResponse.DELETE_ERROR);
            }
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
                                if (goodsSkuVo.getSku() == null || goodsSkuVo.getPurchasePrice() == null || goodsSkuVo.getRetailPrice() == null || goodsSkuVo.getVipPrice() == null || goodsSkuVo.getBossPrice() == null || goodsSkuVo.getIntegral() == null) {
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
                if (goodsSkuVo.getSku() == null || goodsSkuVo.getPurchasePrice() == null || goodsSkuVo.getRetailPrice() == null || goodsSkuVo.getVipPrice() == null || goodsSkuVo.getBossPrice() == null || goodsSkuVo.getIntegral() == null) {
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
                    WarehouseGoodsSkuVo warehouseGoodsSkuVo = new WarehouseGoodsSkuVo(goodsVo.getStoreId(), warehouse.getId(), goodsSkuVo.getId(), 0, goodsSkuVo.getPurchasePrice(), 0.0);
                    if (myGoodsMapper.addWarehouseGoodsSku(warehouseGoodsSkuVo) != 1) {
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
    public void exportGoods(GoodsVo goodsVo, HttpServletResponse response) {
        try {
            //根据筛选条件查找商品
            List<GoodsVo> goodsVos = myGoodsMapper.findAll(goodsVo);

            //备注
            GoodsVo vo = goodsVos.get(0);
            String remark = "【筛选条件】" +
                    "\n货号：" + (goodsVo.getId() == null ? "无" : vo.getId()) +
                    "条码：" + (goodsVo.getBarCode() == null ? "无" : vo.getBarCode()) +
                    "类型：" + (goodsVo.getTypeId() == null ? "无" : vo.getTypeName()) +
                    "上架状态：" + (goodsVo.getPutaway() == null ? "无" : vo.getPutaway().toString()) +
                    "\n【导出备注】" +
                    "\n上架状态 ==> 0：不上架, 1：上架";

            //标题行
            List<String> titleRowCell = Arrays.asList(new String[]{
                    "商品货号", "商品名", "条码", "分类", "上架状态", "产地", "图片", "备注", "创建时间", "标签"
            });

            //最后一个必填列列数
            int lastRequiredCol = -1;

            //数据行
            List<List<String>> dataRowCells = new ArrayList<>();
            goodsVos.stream().forEach(gVo -> dataRowCells.add(commonMethod(gVo)));

            //输出excel
            String fileName = "【商品导出】_" + System.currentTimeMillis() + ".xls";
            CommonUtil.outputExcelMethod(remark, titleRowCell, lastRequiredCol, dataRowCells, fileName, response);
        } catch (Exception e) {
            throw new CommonException(CommonResponse.EXPORT_ERROR, e.getMessage());
        }
    }

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public List<String> commonMethod(GoodsVo goodsVo) {
        List<String> dataRowCell = new ArrayList<>();
        dataRowCell.add(goodsVo.getId());
        dataRowCell.add(goodsVo.getName());
        dataRowCell.add(goodsVo.getBarCode());
        dataRowCell.add(goodsVo.getTypeName());
        dataRowCell.add(goodsVo.getPutaway().toString());
        dataRowCell.add(goodsVo.getOrigin());
        dataRowCell.add(goodsVo.getImage());
        dataRowCell.add(goodsVo.getRemark());
        dataRowCell.add(sdf.format(goodsVo.getCreateTime()));
        String label = "";
        for (int i = 0; i < goodsVo.getGoodsLabels().size(); i++) {
            if (i != 0) {
                label = label + "," + goodsVo.getGoodsLabels().get(i).getName();
            } else {
                label = goodsVo.getGoodsLabels().get(i).getName();
            }
        }
        dataRowCell.add(label);
        return dataRowCell;
    }

    /**
     * 导出商品信息及sku
     * @param goodsVo
     * @param response
     * @throws IOException
     */
    public void exportGoodsSku(GoodsVo goodsVo, HttpServletResponse response) {
        try {
            //根据筛选条件查找商品
            List<GoodsVo> goodsVos = myGoodsMapper.findAll(goodsVo);

            //备注
            GoodsVo vo = goodsVos.get(0);
            String remark = "【筛选条件】" +
                    "\n货号：" + (goodsVo.getId() == null ? "无" : vo.getId()) +
                    "条码：" + (goodsVo.getBarCode() == null ? "无" : vo.getBarCode()) +
                    "类型：" + (goodsVo.getTypeId() == null ? "无" : vo.getTypeName()) +
                    "上架状态：" + (goodsVo.getPutaway() == null ? "无" : vo.getPutaway().toString()) +
                    "\n【导出备注】" +
                    "\n上架状态 ==> 0：不上架, 1：上架";

            //标题行
            List<String> titleRowCell = new ArrayList<>(Arrays.asList(new String[]{
                    "商品货号", "商品名", "条码", "分类", "上架状态", "产地", "图片", "备注", "创建时间", "标签", "规格", "进价", "零售价", "vip售价", "店长售价", "积分"
            }));

            //最后一个必填列列数
            int lastRequiredCol = -1;

            //数据行
            List<List<String>> dataRowCells = new ArrayList<>();
            goodsVos.stream().forEach(gVo -> {
                List<GoodsSkuVo> goodsSkuVos = gVo.getGoodsSkuVos();
                if (goodsSkuVos.size() == 0) {
                    dataRowCells.add(commonMethod(gVo));
                } else {
                    goodsSkuVos.stream().forEach(goodsSkuVo -> {
                        List<String> dataRowCell = commonMethod(gVo);
                        List<Sku> skus = JSON.parseArray(goodsSkuVo.getSku(), Sku.class);

                        String sku = "";
                        for (int i = 0; i < skus.size(); i++) {
                            if (i != 0) {
                                sku = sku + "," + skus.get(i).getKey() + ":" + skus.get(i).getValue();
                            } else {
                                sku = skus.get(i).getKey() + ":" + skus.get(i).getValue();
                            }
                        }
                        dataRowCell.add(sku);
                        dataRowCell.add(goodsSkuVo.getPurchasePrice().toString());
                        dataRowCell.add(goodsSkuVo.getRetailPrice().toString());
                        dataRowCell.add(goodsSkuVo.getVipPrice().toString());
                        dataRowCell.add(goodsSkuVo.getBossPrice().toString());
                        dataRowCell.add(goodsSkuVo.getIntegral().toString());
                        dataRowCells.add(dataRowCell);
                    });
                }
            });

            //输出excel
            String fileName = "【商品及sku导出】_" + System.currentTimeMillis() + ".xls";
            CommonUtil.outputExcelMethod(remark, titleRowCell, lastRequiredCol, dataRowCells, fileName, response);
        } catch (Exception e) {
            throw new CommonException(CommonResponse.EXPORT_ERROR, e.getMessage());
        }
    }

    /**
     * 获取商品导入商品模版
     * @param storeId
     * @param response
     * @throws IOException
     */
    public void getImportGoodsTemplate(Integer storeId, HttpServletResponse response) throws IOException {
        //获取所有商品分类
        List<GoodsType> goodsTypes = myGoodsMapper.findAllType(new GoodsTypeVo(storeId));
        String types = "";
        for (int i = 0; i < goodsTypes.size(); i++) {
            if (i != 0) {
                types = types + ", " + goodsTypes.get(i).getId() + ":" + goodsTypes.get(i).getName();
            } else {
                types = goodsTypes.get(i).getId() + ":" + goodsTypes.get(i).getName();
            }
        }

        //获取所有商品标签
        List<GoodsLabel> goodsLabels = myGoodsMapper.findAllLabel(new GoodsLabelVo(storeId));
        String labels = "";
        for (int i = 0; i < goodsLabels.size(); i++) {
            if (i != 0) {
                labels = labels + ", " + goodsLabels.get(i).getId() + ":" + goodsLabels.get(i).getName();
            } else {
                labels = goodsLabels.get(i).getId() + ":" + goodsLabels.get(i).getName();
            }
        }

        //备注
        String remark = "【导入备注】" +
                "\n必填列已标红，其中“分类”、“标签”填写对应编号，分类只能填写一个，标签多个用英文逗号隔开" +
                "\n分类 ==> " + types +
                "\n标签 ==> " + labels +
                "\n上架状态 ==> 0：不上架, 1：上架";

        //标题行
        List<String> titleRowCell = Arrays.asList(new String[]{
                "商品名", "条码", "分类", "上架状态", "产地", "备注", "标签"
        });

        //最后一个必填列列数
        int lastRequiredCol = 3;

        //输出excel
        String fileName = "【商品导入模版】_" + System.currentTimeMillis() + ".xls";
        CommonUtil.outputExcelMethod(remark, titleRowCell, lastRequiredCol, new ArrayList<>(), fileName, response);
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
        HSSFWorkbook workbook = new HSSFWorkbook(multipartFile.getInputStream());
        HSSFSheet sheet = workbook.getSheetAt(0);

        //获取所有商品分类
        List<GoodsType> goodsTypes = myGoodsMapper.findAllType(new GoodsTypeVo(storeId));

        //获取数据
        for (int j = 3; j <= sheet.getLastRowNum(); j++) {
            HSSFRow row = sheet.getRow(j);
            GoodsVo goodsVo = new GoodsVo();
            goodsVo.setStoreId(storeId);
            goodsVo.setId(primaryKeyUtil.getPrimaryKeyMethod(myGoodsMapper.findPrimaryKey(goodsVo), "sp"));
            goodsVo.setCreateTime(new Date());

            String name = CommonUtil.getCellValue(row.getCell(0));
            if ("".equals(name)) {
                throw new CommonException(CommonResponse.IMPORT_ERROR, "商品名错误");
            }
            goodsVo.setName(name);

            String barCode = CommonUtil.getCellValue(row.getCell(1));
            if ("".equals(barCode)) {
                throw new CommonException(CommonResponse.IMPORT_ERROR, "条码错误");
            }
            goodsVo.setBarCode(barCode);

            String type = CommonUtil.getCellValue(row.getCell(2));
            Optional<GoodsType> typeOptional = goodsTypes.stream().filter(goodsType -> goodsType.getId().toString().equals(type)).findFirst();
            if (!typeOptional.isPresent()) {
                throw new CommonException(CommonResponse.IMPORT_ERROR, "分类错误");
            }
            goodsVo.setTypeId(Integer.valueOf(type));

            String putaway = CommonUtil.getCellValue(row.getCell(3));
            if ("".equals(putaway) || (putaway.equals("0") && putaway.equals("1"))) {
                throw new CommonException(CommonResponse.IMPORT_ERROR, "上架状态错误");
            }
            goodsVo.setPutaway(Byte.valueOf(putaway));

            goodsVo.setOrigin(CommonUtil.getCellValue(row.getCell(4)));

            goodsVo.setRemark(CommonUtil.getCellValue(row.getCell(5)));

            //判断商品标签是否存在
            String label = CommonUtil.getCellValue(row.getCell(6));
            if (!label.equals("")) {
                String[] labels = label.split(",");
                for (int i = 0; i < labels.length; i++) {
                    if (myGoodsMapper.findLabel( new GoodsLabelVo(storeId, Integer.valueOf(labels[i]))) == null) {
                        throw new CommonException(CommonResponse.IMPORT_ERROR, "标签错误");
                    }
                    GoodsGoodsLabelVo goodsGoodsLabelVo = new GoodsGoodsLabelVo(storeId, goodsVo.getId(), Integer.valueOf(labels[i]));
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
                    if (storageCheckOrderVo != null) {
                        goodsSkuVo.setCheckQuantity(storageCheckOrderVo.getCheckQuantity());
                        goodsSkuVo.setCheckMoney(storageCheckOrderVo.getCheckMoney());
                        goodsSkuVo.setCheckTotalMoney(storageCheckOrderVo.getCheckTotalMoney());
                    } else {
                        goodsSkuVo.setCheckQuantity(0);
                        goodsSkuVo.setCheckMoney(0.0);
                        goodsSkuVo.setCheckTotalMoney(0.0);
                    }
                });
            });
        });
        return CommonResponse.success(canUseGoods);
    }
}
