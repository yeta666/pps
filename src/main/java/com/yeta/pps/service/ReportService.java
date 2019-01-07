package com.yeta.pps.service;

import com.yeta.pps.mapper.MyGoodsMapper;
import com.yeta.pps.mapper.MyReportMapper;
import com.yeta.pps.util.CommonResponse;
import com.yeta.pps.util.CommonResult;
import com.yeta.pps.util.Title;
import com.yeta.pps.vo.GoodsVo;
import com.yeta.pps.vo.PageVo;
import com.yeta.pps.vo.ReportInventoryVo;
import com.yeta.pps.vo.StorageCheckOrderVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 报表相关逻辑处理
 * @author YETA
 * @date 2019/01/07/13:29
 */
@Service
public class ReportService {

    @Autowired
    private MyGoodsMapper myGoodsMapper;

    @Autowired
    private MyReportMapper myReportMapper;

    /**
     * 返回四舍五入2位小数的方法
     * @param number
     * @return
     */
    public double getNumberMethod(double number) {
        return (double) Math.round(number * 100) / 100;
    }

    //库存报表

    //库存报表-进销存分析

    /**
     * 库存报表-进销存分析-按商品
     * @param reportInventoryVo
     * @param pageVo
     * @return
     */
    public CommonResponse findReportInventoryByGoods(ReportInventoryVo reportInventoryVo, PageVo pageVo) {
        //获取参数
        Integer storeId = reportInventoryVo.getStoreId();
        Date startTime = reportInventoryVo.getStartTime();
        Date endTime = reportInventoryVo.getEndTime();
        GoodsVo goodsVo = new GoodsVo(storeId, reportInventoryVo.getId(), reportInventoryVo.getBarCode(), reportInventoryVo.getTypeId());

        //查询所有页数
        pageVo.setTotalPage((int) Math.ceil(myGoodsMapper.findCount(goodsVo) * 1.0 / pageVo.getPageSize()));
        pageVo.setStart(pageVo.getPageSize() * (pageVo.getPage() - 1));
        List<GoodsVo> gVos = myGoodsMapper.findAllPaged(goodsVo, pageVo);

        List<ReportInventoryVo> vos = new ArrayList<>();
        gVos.stream().forEach(gVo -> {
            ReportInventoryVo vo = new ReportInventoryVo(gVo.getId(), gVo.getName(), gVo.getBarCode(), gVo.getTypeId(), gVo.getTypeName(), gVo.getImage());

            reportInventoryVo.setId(gVo.getId());

            //采购入库数量、金额
            ReportInventoryVo procurementInVo = myReportMapper.findProcurementIn(reportInventoryVo);
            vo.setProcurementInQuantity(procurementInVo.getProcurementInQuantity());
            vo.setProcurementInMoney(procurementInVo.getProcurementInMoney());

            //其他入库数量、金额
            ReportInventoryVo otherInVo = myReportMapper.findOtherIn(reportInventoryVo);
            vo.setOtherInQuantity(otherInVo.getOtherInQuantity());
            vo.setOtherInMoney(otherInVo.getOtherInMoney());

            //总入库数量、金额
            vo.setTotalInQuantity(vo.getProcurementInQuantity() + vo.getOtherInQuantity());
            vo.setTotalInMoney(getNumberMethod(vo.getProcurementInMoney() + vo.getOtherInMoney()));

            //销售出库数量、金额
            ReportInventoryVo sellOutVo = myReportMapper.findSellOut(reportInventoryVo);
            vo.setSellOutQuantity(sellOutVo.getSellOutQuantity());
            vo.setSellOutMoney(sellOutVo.getSellOutMoney());

            //其他出库数量、金额
            ReportInventoryVo otherOutVo = myReportMapper.findOtherOut(reportInventoryVo);
            vo.setOtherOutQuantity(otherOutVo.getOtherOutQuantity());
            vo.setOtherOutMoney(otherOutVo.getOtherOutMoney());

            //总出库数量、金额
            vo.setTotalOutQuantity(vo.getSellOutQuantity() + vo.getOtherOutQuantity());
            vo.setTotalOutMoney(getNumberMethod(vo.getSellOutMoney() + vo.getOtherOutMoney()));

            gVo.getGoodsSkuVos().forEach(gsVo -> {
                //此前数量、金额
                StorageCheckOrderVo beforeOrEndingVo = myReportMapper.findBeforeOrEnding(new StorageCheckOrderVo(storeId, startTime, endTime, gsVo.getId(), 1));
                if (beforeOrEndingVo != null) {
                    vo.setBeforeQuantity(vo.getBeforeQuantity() + beforeOrEndingVo.getCheckQuantity());
                    vo.setBeforeMoney(getNumberMethod(vo.getBeforeMoney() + beforeOrEndingVo.getCheckTotalMoney()));
                }

                //期末数量、金额
                beforeOrEndingVo = myReportMapper.findBeforeOrEnding(new StorageCheckOrderVo(storeId, startTime, endTime, gsVo.getId(), 2));
                if (beforeOrEndingVo != null) {
                    vo.setEndingQuantity(vo.getEndingQuantity() + beforeOrEndingVo.getCheckQuantity());
                    vo.setEndingMoney(getNumberMethod(vo.getEndingMoney() + beforeOrEndingVo.getCheckTotalMoney()));
                }
            });

            vos.add(vo);
        });

        //封装返回结果
        List<Title> titles = new ArrayList<>();
        titles.add(new Title("商品货号", "id"));
        titles.add(new Title("商品名", "name"));
        titles.add(new Title("条码", "barCode"));
        titles.add(new Title("分类", "typeName"));
        titles.add(new Title("图片", "image"));

        titles.add(new Title("此前数量", "beforeQuantity"));
        titles.add(new Title("此前金额", "beforeMoney"));

        titles.add(new Title("采购入库数量", "procurementInQuantity"));
        titles.add(new Title("采购入库金额", "procurementInMoney"));
        titles.add(new Title("其他入库数量", "otherInQuantity"));
        titles.add(new Title("其他入库金额", "otherInMoney"));
        titles.add(new Title("总入库数量", "totalInQuantity"));
        titles.add(new Title("总入库金额", "totalInMoney"));

        titles.add(new Title("销售出库数量", "sellOutQuantity"));
        titles.add(new Title("销售出库金额", "sellOutMoney"));
        titles.add(new Title("其他出库数量", "otherOutQuantity"));
        titles.add(new Title("其他出库金额", "otherOutMoney"));
        titles.add(new Title("总出库数量", "totalOutQuantity"));
        titles.add(new Title("总出库金额", "totalOutMoney"));

        titles.add(new Title("净入库数量", "netInQuantity"));
        titles.add(new Title("净入库金额", "netInMoney"));

        titles.add(new Title("期末数量", "endingQuantity"));
        titles.add(new Title("期末金额", "endingMoney"));

        CommonResult commonResult = new CommonResult(titles, vos, pageVo);
        return CommonResponse.success(commonResult);
    }

    /**
     * 库存报表-进销存分析-按商品-仓库
     * @param reportInventoryVo
     * @param pageVo
     * @return
     */
    public CommonResponse findReportInventoryByGoodsWarehouse(ReportInventoryVo reportInventoryVo, PageVo pageVo) {
        //获取参数
        Integer storeId = reportInventoryVo.getStoreId();
        Date startTime = reportInventoryVo.getStartTime();
        Date endTime = reportInventoryVo.getEndTime();
        GoodsVo goodsVo = new GoodsVo(storeId, reportInventoryVo.getId(), reportInventoryVo.getBarCode(), reportInventoryVo.getTypeId());

        //查询所有页数
        pageVo.setTotalPage((int) Math.ceil(myGoodsMapper.findCount(goodsVo) * 1.0 / pageVo.getPageSize()));
        pageVo.setStart(pageVo.getPageSize() * (pageVo.getPage() - 1));
        List<GoodsVo> gVos = myGoodsMapper.findAllPaged(goodsVo, pageVo);

        List<ReportInventoryVo> vos = new ArrayList<>();
        gVos.stream().forEach(gVo -> {
            ReportInventoryVo vo = new ReportInventoryVo(gVo.getId(), gVo.getName(), gVo.getBarCode(), gVo.getTypeId(), gVo.getTypeName(), gVo.getImage());

            reportInventoryVo.setId(gVo.getId());

            //采购入库数量、金额
            ReportInventoryVo procurementInVo = myReportMapper.findProcurementIn(reportInventoryVo);
            vo.setProcurementInQuantity(procurementInVo.getProcurementInQuantity());
            vo.setProcurementInMoney(procurementInVo.getProcurementInMoney());

            //其他入库数量、金额
            ReportInventoryVo otherInVo = myReportMapper.findOtherIn(reportInventoryVo);
            vo.setOtherInQuantity(otherInVo.getOtherInQuantity());
            vo.setOtherInMoney(otherInVo.getOtherInMoney());

            //总入库数量、金额
            vo.setTotalInQuantity(vo.getProcurementInQuantity() + vo.getOtherInQuantity());
            vo.setTotalInMoney(getNumberMethod(vo.getProcurementInMoney() + vo.getOtherInMoney()));

            //销售出库数量、金额
            ReportInventoryVo sellOutVo = myReportMapper.findSellOut(reportInventoryVo);
            vo.setSellOutQuantity(sellOutVo.getSellOutQuantity());
            vo.setSellOutMoney(sellOutVo.getSellOutMoney());

            //其他出库数量、金额
            ReportInventoryVo otherOutVo = myReportMapper.findOtherOut(reportInventoryVo);
            vo.setOtherOutQuantity(otherOutVo.getOtherOutQuantity());
            vo.setOtherOutMoney(otherOutVo.getOtherOutMoney());

            //总出库数量、金额
            vo.setTotalOutQuantity(vo.getSellOutQuantity() + vo.getOtherOutQuantity());
            vo.setTotalOutMoney(getNumberMethod(vo.getSellOutMoney() + vo.getOtherOutMoney()));

            gVo.getGoodsSkuVos().forEach(gsVo -> {
                //此前数量、金额
                StorageCheckOrderVo beforeOrEndingVo = myReportMapper.findBeforeOrEnding(new StorageCheckOrderVo(storeId, startTime, endTime, gsVo.getId(), 1));
                if (beforeOrEndingVo != null) {
                    vo.setBeforeQuantity(vo.getBeforeQuantity() + beforeOrEndingVo.getCheckQuantity());
                    vo.setBeforeMoney(getNumberMethod(vo.getBeforeMoney() + beforeOrEndingVo.getCheckTotalMoney()));
                }

                //期末数量、金额
                beforeOrEndingVo = myReportMapper.findBeforeOrEnding(new StorageCheckOrderVo(storeId, startTime, endTime, gsVo.getId(), 2));
                if (beforeOrEndingVo != null) {
                    vo.setEndingQuantity(vo.getEndingQuantity() + beforeOrEndingVo.getCheckQuantity());
                    vo.setEndingMoney(getNumberMethod(vo.getEndingMoney() + beforeOrEndingVo.getCheckTotalMoney()));
                }
            });

            vos.add(vo);
        });

        //封装返回结果
        List<Title> titles = new ArrayList<>();
        titles.add(new Title("商品货号", "id"));
        titles.add(new Title("商品名", "name"));
        titles.add(new Title("条码", "barCode"));
        titles.add(new Title("分类", "typeName"));
        titles.add(new Title("图片", "image"));

        titles.add(new Title("此前数量", "beforeQuantity"));
        titles.add(new Title("此前金额", "beforeMoney"));

        titles.add(new Title("采购入库数量", "procurementInQuantity"));
        titles.add(new Title("采购入库金额", "procurementInMoney"));
        titles.add(new Title("其他入库数量", "otherInQuantity"));
        titles.add(new Title("其他入库金额", "otherInMoney"));
        titles.add(new Title("总入库数量", "totalInQuantity"));
        titles.add(new Title("总入库金额", "totalInMoney"));

        titles.add(new Title("销售出库数量", "sellOutQuantity"));
        titles.add(new Title("销售出库金额", "sellOutMoney"));
        titles.add(new Title("其他出库数量", "otherOutQuantity"));
        titles.add(new Title("其他出库金额", "otherOutMoney"));
        titles.add(new Title("总出库数量", "totalOutQuantity"));
        titles.add(new Title("总出库金额", "totalOutMoney"));

        titles.add(new Title("净入库数量", "netInQuantity"));
        titles.add(new Title("净入库金额", "netInMoney"));

        titles.add(new Title("期末数量", "endingQuantity"));
        titles.add(new Title("期末金额", "endingMoney"));

        CommonResult commonResult = new CommonResult(titles, vos, pageVo);
        return CommonResponse.success(commonResult);
    }

    //库存报表-出入库明细

    //资金报表

    //订单统计

    //销售报表

    //采购报表

    //经营中心
}
