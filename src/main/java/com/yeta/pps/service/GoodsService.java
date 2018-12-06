package com.yeta.pps.service;

import com.yeta.pps.exception.CommonException;
import com.yeta.pps.mapper.MyGoodsMapper;
import com.yeta.pps.po.*;
import com.yeta.pps.util.CommonResponse;
import com.yeta.pps.util.CommonResult;
import com.yeta.pps.util.CommonUtil;
import com.yeta.pps.util.Title;
import com.yeta.pps.vo.*;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.yeta.pps.service.FileService.upload;

/**
 * 商品相关逻辑处理
 * @author YETA
 * @date 2018/12/02/23:30
 */
@Service
public class GoodsService {

    @Autowired
    private MyGoodsMapper myGoodsMapper;

    /**
     * 新增商品品牌
     * @param goodsBrandVo
     * @return
     */
    public CommonResponse addBrand(GoodsBrandVo goodsBrandVo) {
        //新增
        if (myGoodsMapper.addBrand(goodsBrandVo) != 1) {
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
        //判断商品品牌是否使用
        GoodsVo goodsVo = new GoodsVo(goodsBrandVo.getStoreId(), null, goodsBrandVo.getId(), null, null);
        goodsVo = myGoodsMapper.findBrandLabelTypeUnit(goodsVo);
        if (goodsVo != null) {
            return new CommonResponse(CommonResponse.CODE8, null, CommonResponse.MESSAGE8);
        }
        //删除商品品牌
        if (myGoodsMapper.deleteBrand(goodsBrandVo) != 1) {
            return new CommonResponse(CommonResponse.CODE8, null, CommonResponse.MESSAGE8);
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
        if (myGoodsMapper.updateBrand(goodsBrandVo) != 1) {
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
            pageVo.setTotalPage((int) Math.ceil(myGoodsMapper.findCountBrand(goodsBrandVo) * 1.0 / pageVo.getPageSize()));
            pageVo.setStart(pageVo.getPageSize() * (pageVo.getPage() - 1));
            List<GoodsBrand> goodsBrands = myGoodsMapper.findAllPagedBrand(goodsBrandVo, pageVo);
            //封装返回结果
            List<Title> titles = new ArrayList<>();
            titles.add(new Title("品牌名", "name"));
            CommonResult commonResult = new CommonResult(titles, goodsBrands, pageVo);
            return new CommonResponse(CommonResponse.CODE1, commonResult, CommonResponse.MESSAGE1);
        }
        //不分页
        List<GoodsBrand> goodsBrands = myGoodsMapper.findAllBrand(goodsBrandVo);
        return new CommonResponse(CommonResponse.CODE1, goodsBrands, CommonResponse.MESSAGE1);
    }

    /**
     * 根据id查询商品品牌
     * @param goodsBrandVo
     * @return
     */
    public CommonResponse findBrandById(GoodsBrandVo goodsBrandVo) {
        GoodsBrand goodsBrand = myGoodsMapper.findBrand(goodsBrandVo);
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
        if (myGoodsMapper.addLabel(goodsLabelVo) != 1) {
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
        //判断商品标签是否使用
        GoodsVo goodsVo = new GoodsVo(goodsLabelVo.getStoreId(), null, null, null, goodsLabelVo.getId());
        goodsVo = myGoodsMapper.findBrandLabelTypeUnit(goodsVo);
        if (goodsVo != null) {
            return new CommonResponse(CommonResponse.CODE8, null, CommonResponse.MESSAGE8);
        }
        //删除商品标签
        if (myGoodsMapper.deleteLabel(goodsLabelVo) != 1) {
            return new CommonResponse(CommonResponse.CODE8, null, CommonResponse.MESSAGE8);
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

    //

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
     * @param goodsTypeVo
     * @return
     */
    public CommonResponse deleteType(GoodsTypeVo goodsTypeVo) {
        //判断商品类型是否使用
        GoodsVo goodsVo = new GoodsVo(goodsTypeVo.getStoreId(), goodsTypeVo.getId(), null, null, null);
        goodsVo = myGoodsMapper.findBrandLabelTypeUnit(goodsVo);
        if (goodsVo != null) {
            return new CommonResponse(CommonResponse.CODE8, null, CommonResponse.MESSAGE8);
        }
        //删除商品类型
        if (myGoodsMapper.deleteType(goodsTypeVo) != 1) {
            return new CommonResponse(CommonResponse.CODE8, null, CommonResponse.MESSAGE8);
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

    //

    /**
     * 新增商品单位
     * @param goodsUnitVo
     * @return
     */
    public CommonResponse addUnit(GoodsUnitVo goodsUnitVo) {
        //新增
        if (myGoodsMapper.addUnit(goodsUnitVo) != 1) {
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
        //判断商品单位是否使用
        GoodsVo goodsVo = new GoodsVo(goodsUnitVo.getStoreId(), null, null, goodsUnitVo.getId(), null);
        goodsVo = myGoodsMapper.findBrandLabelTypeUnit(goodsVo);
        if (goodsVo != null) {
            return new CommonResponse(CommonResponse.CODE8, null, CommonResponse.MESSAGE8);
        }
        //删除商品单位
        if (myGoodsMapper.deleteUnit(goodsUnitVo) != 1) {
            return new CommonResponse(CommonResponse.CODE8, null, CommonResponse.MESSAGE8);
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
        if (myGoodsMapper.updateUnit(goodsUnitVo) != 1) {
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
            pageVo.setTotalPage((int) Math.ceil(myGoodsMapper.findCountUnit(goodsUnitVo) * 1.0 / pageVo.getPageSize()));
            pageVo.setStart(pageVo.getPageSize() * (pageVo.getPage() - 1));
            List<GoodsUnit> goodsUnits = myGoodsMapper.findAllPagedUnit(goodsUnitVo, pageVo);
            //封装返回结果
            List<Title> titles = new ArrayList<>();
            titles.add(new Title("单位名", "name"));
            CommonResult commonResult = new CommonResult(titles, goodsUnits, pageVo);
            return new CommonResponse(CommonResponse.CODE1, commonResult, CommonResponse.MESSAGE1);
        }
        //不分页
        List<GoodsUnit> goodsUnits = myGoodsMapper.findAllUnit(goodsUnitVo);
        return new CommonResponse(CommonResponse.CODE1, goodsUnits, CommonResponse.MESSAGE1);
    }

    /**
     * 根据id查询商品单位
     * @param goodsUnitVo
     * @return
     */
    public CommonResponse findUnitById(GoodsUnitVo goodsUnitVo) {
        GoodsUnit goodsUnit = myGoodsMapper.findUnit(goodsUnitVo);
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
        //判断品牌id是否存在
        GoodsBrandVo goodsBrandVo = new GoodsBrandVo(goodsVo.getStoreId(), goodsVo.getBrandId());
        if (myGoodsMapper.findBrand(goodsBrandVo) == null) {
            return new CommonResponse(CommonResponse.CODE7, null, CommonResponse.MESSAGE7);
        }
        //判断标签id是否存在
        GoodsLabelVo goodsLabelVo = new GoodsLabelVo(goodsVo.getStoreId(), goodsVo.getLabelId());
        if (myGoodsMapper.findLabel(goodsLabelVo) == null) {
            return new CommonResponse(CommonResponse.CODE7, null, CommonResponse.MESSAGE7);
        }
        //判断分类id是否存在
        GoodsTypeVo goodsTypeVo = new GoodsTypeVo(goodsVo.getStoreId(), goodsVo.getTypeId());
        if (myGoodsMapper.findType(goodsTypeVo) == null) {
            return new CommonResponse(CommonResponse.CODE7, null, CommonResponse.MESSAGE7);
        }
        //判断单位id是否存在
        GoodsUnitVo goodsUnitVo = new GoodsUnitVo(goodsVo.getStoreId(), goodsVo.getUnitId());
        if (myGoodsMapper.findUnit(goodsUnitVo) == null) {
            return new CommonResponse(CommonResponse.CODE7, null, CommonResponse.MESSAGE7);
        }
        //设置id
        goodsVo.setId(UUID.randomUUID().toString());
        //新增商品
        if (myGoodsMapper.add(goodsVo) != 1) {
            return new CommonResponse(CommonResponse.CODE7, null, CommonResponse.MESSAGE7);
        }
        return new CommonResponse(CommonResponse.CODE1, null, CommonResponse.MESSAGE1);
    }

    /**
     * 删除商品
     * @param goodsVo
     * @return
     */
    public CommonResponse delete(GoodsVo goodsVo) {
        //删除商品
        if (myGoodsMapper.delete(goodsVo) != 1) {
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
        //判断品牌id是否存在
        GoodsBrandVo goodsBrandVo = new GoodsBrandVo(goodsVo.getStoreId(), goodsVo.getBrandId());
        if (myGoodsMapper.findBrand(goodsBrandVo) == null) {
            return new CommonResponse(CommonResponse.CODE9, null, CommonResponse.MESSAGE9);
        }
        //判断标签id是否存在
        GoodsLabelVo goodsLabelVo = new GoodsLabelVo(goodsVo.getStoreId(), goodsVo.getLabelId());
        if (myGoodsMapper.findLabel(goodsLabelVo) == null) {
            return new CommonResponse(CommonResponse.CODE9, null, CommonResponse.MESSAGE9);
        }
        //判断分类id是否存在
        GoodsTypeVo goodsTypeVo = new GoodsTypeVo(goodsVo.getStoreId(), goodsVo.getTypeId());
        if (myGoodsMapper.findType(goodsTypeVo) == null) {
            return new CommonResponse(CommonResponse.CODE9, null, CommonResponse.MESSAGE9);
        }
        //判断单位id是否存在
        GoodsUnitVo goodsUnitVo = new GoodsUnitVo(goodsVo.getStoreId(), goodsVo.getUnitId());
        if (myGoodsMapper.findUnit(goodsUnitVo) == null) {
            return new CommonResponse(CommonResponse.CODE9, null, CommonResponse.MESSAGE9);
        }
        //修改商品
        if (myGoodsMapper.update(goodsVo) != 1) {
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
        pageVo.setTotalPage((int) Math.ceil(myGoodsMapper.findCount(goodsVo) * 1.0 / pageVo.getPageSize()));
        pageVo.setStart(pageVo.getPageSize() * (pageVo.getPage() - 1));
        List<GoodsVo> goodsVos = myGoodsMapper.findAllPaged(goodsVo, pageVo);
        //封装返回结果
        List<Title> titles = new ArrayList<>();
        titles.add(new Title("商品名", "name"));
        titles.add(new Title("货号", "code"));
        titles.add(new Title("条码", "barCode"));
        titles.add(new Title("分类", "typeName"));
        titles.add(new Title("品牌", "brandName"));
        titles.add(new Title("单位", "unitName"));
        titles.add(new Title("标签", "labelName"));
        titles.add(new Title("进价", "purchasePrice"));
        titles.add(new Title("零售价", "retailPrice"));
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
        CommonResult commonResult = new CommonResult(titles, goodsVos, pageVo);
        return new CommonResponse(CommonResponse.CODE1, commonResult, CommonResponse.MESSAGE1);
    }

    /**
     * 根据id查询商品
     * @param goodsVo
     * @return
     */
    public CommonResponse findById(GoodsVo goodsVo) {
        goodsVo = myGoodsMapper.findById(goodsVo);
        if (goodsVo == null) {
            return new CommonResponse(CommonResponse.CODE10, null, CommonResponse.MESSAGE10);
        }
        return new CommonResponse(CommonResponse.CODE1, goodsVo, CommonResponse.MESSAGE1);
    }

    //用于设置导出商品excel行号
    private static int i = 3;

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
                "，类型：" + (goodsVo.getTypeId() == null ? "无" : vo.getTypeName()) +
                "，品牌：" + (goodsVo.getBrandId() == null ? "无" : vo.getBrandName()) +
                "，货号：" + (goodsVo.getCode() == null ? "无" : vo.getCode()) +
                "，条码：" + (goodsVo.getBarCode() == null ? "无" : vo.getBarCode()) +
                "，上架状态0表示不上架，1表示上架：" + (goodsVo.getPutaway() == null ? "无" : vo.getPutaway().toString());
        //标题行内容
        List<String> titleRowCell = Arrays.asList(new String[]{
                "商品号", "货号", "条码", "分类", "品牌", "单位", "标签", "进价", "零售价", "vip售价", "上架状态", "可用库存", "产地", "图片", "香型", "度数", "净含量", "商品积分", "备注"
        });
        //最后一个必填列列数
        int lastRequiredCol = 11;
        //数据行
        List<List<String>> dataRowCells = new ArrayList<>();
        goodsVos.stream().forEach(gVo -> {
            List<String> dataRowCell = new ArrayList<>();
            dataRowCell.add(gVo.getName());
            dataRowCell.add(gVo.getCode());
            dataRowCell.add(gVo.getBarCode());
            dataRowCell.add(gVo.getTypeName());
            dataRowCell.add(gVo.getBrandName());
            dataRowCell.add(gVo.getUnitName());
            dataRowCell.add(gVo.getLabelName());
            dataRowCell.add(gVo.getPurchasePrice().toString());
            dataRowCell.add(gVo.getRetailPrice().toString());
            dataRowCell.add(gVo.getVipPrice().toString());
            dataRowCell.add(gVo.getPutaway().toString());
            dataRowCell.add(gVo.getInventory().toString());
            dataRowCell.add(gVo.getOrigin());
            dataRowCell.add(gVo.getImage());
            dataRowCell.add(gVo.getOderType());
            dataRowCell.add(gVo.getDegree());
            dataRowCell.add(gVo.getNetContent());
            dataRowCell.add(gVo.getIntegral().toString());
            dataRowCell.add(gVo.getRemark());
            dataRowCells.add(dataRowCell);
        });
        //文件名
        String fileName = "【商品导出】_" + System.currentTimeMillis() + ".xls";
        //输出excel
        CommonUtil.outputExcel(remark, titleRowCell, lastRequiredCol, dataRowCells, fileName, response);
    }

    /**
     * 获取导入商品模版
     * @param response
     * @throws IOException
     */
    public void getImportGoodsTemplate(HttpServletResponse response) throws IOException {
        //备注
        String remark = "【导入备注】，只能增加行数，按照标题填写，不能增加其他列。" +
                "必填列已标红，其中分类、品牌、单位、标签需要填写系统中已经存在的，否则会导致导入失败。" +
                "上架状态0表示不上架，1表示上架";
        //标题行内容
        List<String> titleRowCell = Arrays.asList(new String[]
                {"商品号", "货号", "条码", "分类", "品牌", "单位", "标签", "进价", "零售价", "vip售价", "上架状态", "可用库存", "产地", "图片", "香型", "度数", "净含量", "商品积分", "备注"});
        //最后一个必填列列数
        int lastRequiredCol = 11;
        //文件名
        String fileName = "【商品导入模版】_" + System.currentTimeMillis() + ".xls";
        //输出excel
        CommonUtil.outputExcel(remark, titleRowCell, lastRequiredCol, new ArrayList<>(), fileName, response);
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
            goodsVo.setName(CommonUtil.getCellValue(row.getCell(0)));
            goodsVo.setCode(CommonUtil.getCellValue(row.getCell(1)));
            goodsVo.setBarCode(CommonUtil.getCellValue(row.getCell(2)));
            //判断商品类型是否存在
            GoodsTypeVo goodsTypeVo = new GoodsTypeVo(storeId, CommonUtil.getCellValue(row.getCell(3)));
            GoodsType goodsType = myGoodsMapper.findType(goodsTypeVo);
            if (goodsType == null) {
                return new CommonResponse(CommonResponse.CODE17, null, CommonResponse.MESSAGE17);
            }
            goodsVo.setTypeId(goodsType.getId());
            //判断商品品牌是否存在
            GoodsBrandVo goodsBrandVo = new GoodsBrandVo(storeId, CommonUtil.getCellValue(row.getCell(4)));
            GoodsBrand goodsBrand = myGoodsMapper.findBrand(goodsBrandVo);
            if (goodsBrand == null) {
                return new CommonResponse(CommonResponse.CODE17, null, CommonResponse.MESSAGE17);
            }
            goodsVo.setBrandId(goodsBrand.getId());
            //判断商品单位是否存在
            GoodsUnitVo goodsUnitVo = new GoodsUnitVo(storeId, CommonUtil.getCellValue(row.getCell(5)));
            GoodsUnit goodsUnit = myGoodsMapper.findUnit(goodsUnitVo);
            if (goodsUnit == null) {
                return new CommonResponse(CommonResponse.CODE17, null, CommonResponse.MESSAGE17);
            }
            goodsVo.setUnitId(goodsUnit.getId());
            //判断商品标签是否存在
            GoodsLabelVo goodsLabelVo = new GoodsLabelVo(storeId, CommonUtil.getCellValue(row.getCell(6)));
            GoodsLabel goodsLabel = myGoodsMapper.findLabel(goodsLabelVo);
            if (goodsLabel == null) {
                return new CommonResponse(CommonResponse.CODE17, null, CommonResponse.MESSAGE17);
            }
            goodsVo.setLabelId(goodsLabel.getId());
            goodsVo.setPurchasePrice(new BigDecimal(CommonUtil.getCellValue(row.getCell(7))));
            goodsVo.setRetailPrice(new BigDecimal(CommonUtil.getCellValue(row.getCell(8))));
            goodsVo.setVipPrice(new BigDecimal(CommonUtil.getCellValue(row.getCell(9))));
            String putaway = CommonUtil.getCellValue(row.getCell(10));
            if (!putaway.equals("")) {
                goodsVo.setPutaway(Integer.valueOf(putaway));
            }
            String inventory = CommonUtil.getCellValue(row.getCell(11));
            if (!inventory.equals("")) {
                goodsVo.setInventory(Integer.valueOf(inventory));
            }
            goodsVo.setOrigin(CommonUtil.getCellValue(row.getCell(12)));
            goodsVo.setImage(CommonUtil.getCellValue(row.getCell(13)));
            goodsVo.setOderType(CommonUtil.getCellValue(row.getCell(14)));
            goodsVo.setDegree(CommonUtil.getCellValue(row.getCell(15)));
            goodsVo.setNetContent(CommonUtil.getCellValue(row.getCell(16)));
            String integral = CommonUtil.getCellValue(row.getCell(17));
            if (!integral.equals("")) {
                goodsVo.setIntegral(Integer.valueOf(integral));
            }
            goodsVo.setRemark(CommonUtil.getCellValue(row.getCell(18)));
            //保存商品
            if (myGoodsMapper.add(goodsVo) != 1) {
                throw new CommonException(CommonResponse.CODE7, CommonResponse.MESSAGE7);
            }
        }
        return new CommonResponse(CommonResponse.CODE1, null, CommonResponse.MESSAGE1);
    }
}
