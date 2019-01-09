package com.yeta.pps.service;

import com.yeta.pps.mapper.MyGoodsMapper;
import com.yeta.pps.mapper.MyReportMapper;
import com.yeta.pps.po.GoodsSku;
import com.yeta.pps.util.CommonResponse;
import com.yeta.pps.util.CommonResult;
import com.yeta.pps.util.Title;
import com.yeta.pps.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 报表相关逻辑处理
 * @author YETA
 * @date 2019/01/07/13:29
 */
@Service
public class ReportService {

    @Autowired
    private MyReportMapper myReportMapper;

    @Autowired
    private MyGoodsMapper myGoodsMapper;

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

        //查询所有页数
        pageVo.setTotalPage((int) Math.ceil(myReportMapper.findCountGoods(reportInventoryVo) * 1.0 / pageVo.getPageSize()));
        pageVo.setStart(pageVo.getPageSize() * (pageVo.getPage() - 1));
        List<ReportInventoryVo> vos = myReportMapper.findPagedGoods(reportInventoryVo, pageVo);

        vos.stream().forEach(vo -> {

            reportInventoryVo.setId(vo.getId());
            StorageCheckOrderVo beforeOrEndingVo = myReportMapper.findBeforeOrEnding(new StorageCheckOrderVo(storeId, startTime, endTime, vo.getId(), (byte) 1));

            //此前数量、金额
            if (beforeOrEndingVo != null) {
                vo.setBeforeQuantity(beforeOrEndingVo.getCheckQuantity2());
                vo.setBeforeMoney(beforeOrEndingVo.getCheckTotalMoney2());
            }

            //采购入库数量、金额
            ReportInventoryVo procurementInVo = myReportMapper.findProcurementIn(reportInventoryVo);
            if (procurementInVo != null) {
                vo.setProcurementInQuantity(procurementInVo.getProcurementInQuantity());
                vo.setProcurementInMoney(procurementInVo.getProcurementInMoney());
            }

            //其他入库数量、金额
            ReportInventoryVo otherInVo = myReportMapper.findOtherIn(reportInventoryVo);
            if (otherInVo != null) {
                vo.setOtherInQuantity(otherInVo.getOtherInQuantity());
                vo.setOtherInMoney(otherInVo.getOtherInMoney());
            }

            //总入库数量、金额
            vo.setTotalInQuantity(vo.getProcurementInQuantity() + vo.getOtherInQuantity());
            vo.setTotalInMoney(getNumberMethod(vo.getProcurementInMoney() + vo.getOtherInMoney()));

            //销售出库数量、金额
            ReportInventoryVo sellOutVo = myReportMapper.findSellOut(reportInventoryVo);
            if (sellOutVo != null) {
                vo.setSellOutQuantity(sellOutVo.getSellOutQuantity());
                vo.setSellOutMoney(sellOutVo.getSellOutMoney());
            }

            //其他出库数量、金额
            ReportInventoryVo otherOutVo = myReportMapper.findOtherOut(reportInventoryVo);
            if (otherOutVo != null) {
                vo.setOtherOutQuantity(otherOutVo.getOtherOutQuantity());
                vo.setOtherOutMoney(otherOutVo.getOtherOutMoney());
            }

            //总出库数量、金额
            vo.setTotalOutQuantity(vo.getSellOutQuantity() + vo.getOtherOutQuantity());
            vo.setTotalOutMoney(getNumberMethod(vo.getSellOutMoney() + vo.getOtherOutMoney()));

            //净入库数量、金额
            vo.setNetInQuantity(vo.getTotalInQuantity() - vo.getTotalOutQuantity());
            vo.setNetInMoney(vo.getTotalInMoney() - vo.getTotalOutMoney());

            //期末数量、金额
            beforeOrEndingVo = myReportMapper.findBeforeOrEnding(new StorageCheckOrderVo(storeId, startTime, endTime, vo.getId(), (byte) 2));
            if (beforeOrEndingVo != null) {
                vo.setEndingQuantity(beforeOrEndingVo.getCheckQuantity2());
                vo.setEndingMoney(beforeOrEndingVo.getCheckTotalMoney2());
            }
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

        //查询所有页数
        pageVo.setTotalPage((int) Math.ceil(myReportMapper.findCountGoodsWarehouse(reportInventoryVo) * 1.0 / pageVo.getPageSize()));
        pageVo.setStart(pageVo.getPageSize() * (pageVo.getPage() - 1));
        List<ReportInventoryVo> vos = myReportMapper.findPagedGoodsWarehouse(reportInventoryVo, pageVo);

        //查询所有商品规格
        List<GoodsSku> goodsSkus = myGoodsMapper.findAllGoodsSku(new GoodsSkuVo(storeId));

        vos.stream().forEach(vo -> {

            reportInventoryVo.setId(vo.getId());
            reportInventoryVo.setWarehouseId(vo.getWarehouseId());

            //过滤该商品货号的商品规格
            goodsSkus.stream().filter(goodsSku -> goodsSku.getGoodsId().equals(vo.getId())).collect(Collectors.toList()).stream().forEach(goodsSku -> {
                StorageCheckOrderVo beforeOrEndingVo = myReportMapper.findBeforeOrEnding(new StorageCheckOrderVo(storeId, startTime, endTime, goodsSku.getId(), vo.getWarehouseId(), (byte) 1));

                //此前数量、金额
                if (beforeOrEndingVo != null) {
                    vo.setBeforeQuantity(vo.getBeforeQuantity() + beforeOrEndingVo.getCheckQuantity());
                    vo.setBeforeMoney(vo.getBeforeMoney() + beforeOrEndingVo.getCheckTotalMoney());
                }

                //期末数量、金额
                beforeOrEndingVo = myReportMapper.findBeforeOrEnding(new StorageCheckOrderVo(storeId, startTime, endTime, goodsSku.getId(), vo.getWarehouseId(), (byte) 2));
                if (beforeOrEndingVo != null) {
                    vo.setEndingQuantity(vo.getEndingQuantity() + beforeOrEndingVo.getCheckQuantity());
                    vo.setEndingMoney(vo.getEndingMoney() + beforeOrEndingVo.getCheckTotalMoney());
                }
            });

            //采购入库数量、金额
            ReportInventoryVo procurementInVo = myReportMapper.findProcurementIn(reportInventoryVo);
            if (procurementInVo != null) {
                vo.setProcurementInQuantity(procurementInVo.getProcurementInQuantity());
                vo.setProcurementInMoney(procurementInVo.getProcurementInMoney());
            }

            //其他入库数量、金额
            ReportInventoryVo otherInVo = myReportMapper.findOtherIn(reportInventoryVo);
            if (otherInVo != null) {
                vo.setOtherInQuantity(otherInVo.getOtherInQuantity());
                vo.setOtherInMoney(otherInVo.getOtherInMoney());
            }

            //总入库数量、金额
            vo.setTotalInQuantity(vo.getProcurementInQuantity() + vo.getOtherInQuantity());
            vo.setTotalInMoney(getNumberMethod(vo.getProcurementInMoney() + vo.getOtherInMoney()));

            //销售出库数量、金额
            ReportInventoryVo sellOutVo = myReportMapper.findSellOut(reportInventoryVo);
            if (sellOutVo != null) {
                vo.setSellOutQuantity(sellOutVo.getSellOutQuantity());
                vo.setSellOutMoney(sellOutVo.getSellOutMoney());
            }

            //其他出库数量、金额
            ReportInventoryVo otherOutVo = myReportMapper.findOtherOut(reportInventoryVo);
            if (otherOutVo != null) {
                vo.setOtherOutQuantity(otherOutVo.getOtherOutQuantity());
                vo.setOtherOutMoney(otherOutVo.getOtherOutMoney());
            }

            //总出库数量、金额
            vo.setTotalOutQuantity(vo.getSellOutQuantity() + vo.getOtherOutQuantity());
            vo.setTotalOutMoney(getNumberMethod(vo.getSellOutMoney() + vo.getOtherOutMoney()));

            //净入库数量、金额
            vo.setNetInQuantity(vo.getTotalInQuantity() - vo.getTotalOutQuantity());
            vo.setNetInMoney(vo.getTotalInMoney() - vo.getTotalOutMoney());
        });

        //封装返回结果
        List<Title> titles = new ArrayList<>();
        titles.add(new Title("商品货号", "id"));
        titles.add(new Title("商品名", "name"));
        titles.add(new Title("条码", "barCode"));
        titles.add(new Title("分类", "typeName"));
        titles.add(new Title("图片", "image"));
        titles.add(new Title("仓库", "warehouseName"));

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
     * 库存报表-进销存分析-其他出入库分析/报损报溢分析
     * @param reportInventoryVo
     * @param pageVo
     * @return
     */
    public CommonResponse findReportInventoryAnalysis(ReportInventoryVo reportInventoryVo, PageVo pageVo) {
        //查询所有页数
        pageVo.setTotalPage((int) Math.ceil(myReportMapper.findCountGoods(reportInventoryVo) * 1.0 / pageVo.getPageSize()));
        pageVo.setStart(pageVo.getPageSize() * (pageVo.getPage() - 1));
        List<ReportInventoryVo> vos = myReportMapper.findPagedGoods(reportInventoryVo, pageVo);

        vos.stream().forEach(vo -> {
            reportInventoryVo.setId(vo.getId());
            ReportInventoryVo analysis = myReportMapper.findAnalysis(reportInventoryVo);
            vo.setTotalOutQuantity(analysis.getTotalOutQuantity());
            vo.setTotalOutMoney(analysis.getTotalOutMoney());
            vo.setTotalInQuantity(analysis.getTotalInQuantity());
            vo.setTotalInMoney(analysis.getTotalInMoney());
        });

        //封装返回结果
        List<Title> titles = new ArrayList<>();
        titles.add(new Title("商品货号", "id"));
        titles.add(new Title("商品名", "name"));
        titles.add(new Title("条码", "barCode"));
        titles.add(new Title("分类", "typeName"));
        titles.add(new Title("图片", "image"));
        titles.add(new Title("仓库", "warehouseName"));

        switch (reportInventoryVo.getFlag()) {
            case 1:     //其他出入库分析
                titles.add(new Title("出库数量", "totalOutQuantity"));
                titles.add(new Title("出库金额", "totalOutMoney"));
                titles.add(new Title("入库数量", "totalInQuantity"));
                titles.add(new Title("入库金额", "totalInMoney"));
                break;

            case 2:     //报损报溢分析
                titles.add(new Title("报损数量", "totalOutQuantity"));
                titles.add(new Title("报损金额", "totalOutMoney"));
                titles.add(new Title("报溢数量", "totalInQuantity"));
                titles.add(new Title("报溢金额", "totalInMoney"));
                break;

            default:
                return CommonResponse.error(CommonResponse.PARAMETER_ERROR);
        }

        CommonResult commonResult = new CommonResult(titles, vos, pageVo);
        return CommonResponse.success(commonResult);
    }

    //库存报表-出入库明细

    /**
     * 库存报表-出入库明细
     * @param reportInventoryVo
     * @param pageVo
     * @return
     */
    public CommonResponse findReportInventoryDetails(ReportInventoryVo reportInventoryVo, PageVo pageVo) {
        //查询所有页数
        pageVo.setTotalPage((int) Math.ceil(myReportMapper.findCountDetails(reportInventoryVo) * 1.0 / pageVo.getPageSize()));
        pageVo.setStart(pageVo.getPageSize() * (pageVo.getPage() - 1));
        List<StorageCheckOrderVo> vos = myReportMapper.findPagedDetails(reportInventoryVo, pageVo);

        //封装返回结果
        List<Title> titles = new ArrayList<>();
        titles.add(new Title("单据编号", "orderId"));
        titles.add(new Title("单据类型", "typeName"));
        titles.add(new Title("创建时间", "createTime"));
        titles.add(new Title("来源订单", "applyOrderId"));
        titles.add(new Title("往来单位", "targetName"));
        titles.add(new Title("商品规格", "sku"));
        titles.add(new Title("仓库", "warehouseName"));
        titles.add(new Title("入库数量", "inQuantity"));
        titles.add(new Title("入库成本单价", "inMoney"));
        titles.add(new Title("入库成本金额", "inTotalMoney"));
        titles.add(new Title("出库数量", "outQuantity"));
        titles.add(new Title("出库成本单价", "outMoney"));
        titles.add(new Title("出库成本金额", "outTotalMoney"));
        titles.add(new Title("经手人", "userName"));
        titles.add(new Title("单据备注", "remark"));
        CommonResult commonResult = new CommonResult(titles, vos, pageVo);

        return CommonResponse.success(commonResult);
    }

    //资金报表

    //资金报表-查回款

    /**
     * 资金报表-查回款-按账户
     * @param reportFundVo
     * @param pageVo
     * @return
     */
    public CommonResponse findReportFundInByBankAccount(ReportFundVo reportFundVo, PageVo pageVo) {
        //查询所有页数
        pageVo.setTotalPage((int) Math.ceil(myReportMapper.findCountFundInByBankAccount(reportFundVo) * 1.0 / pageVo.getPageSize()));
        pageVo.setStart(pageVo.getPageSize() * (pageVo.getPage() - 1));
        List<ReportFundVo> vos = myReportMapper.findPagedFundInByBankAccount(reportFundVo, pageVo);

        //统计数据
        reportFundVo.setFlag(1);
        findOrderQuantityAndFindInMehtod(vos, reportFundVo);

        //封装返回结果
        List<Title> titles = new ArrayList<>();
        titles.add(new Title("日期", "createTime"));
        titles.add(new Title("单量", "orderQuantity"));
        titles.add(new Title("回款总额", "totalInMoney"));
        titles.add(new Title("现金回款", "bankCardInMoney"));
        titles.add(new Title("支付宝回款", "alipayInMoney"));
        titles.add(new Title("微信回款", "wechatInMoney"));
        titles.add(new Title("银行卡回款", "bankCardInMoney"));
        CommonResult commonResult = new CommonResult(titles, vos, pageVo);

        return CommonResponse.success(commonResult);
    }

    /**
     * 统计单量、详细回款的方法
     * @param vos
     * @param reportFundVo
     */
    public void findOrderQuantityAndFindInMehtod(List<ReportFundVo> vos, ReportFundVo reportFundVo) {
        //单量
        List<Integer> orderQuantityVos = myReportMapper.findOrderQuantity(reportFundVo);
        if (orderQuantityVos.size() != vos.size()) {

        }
        for (int i = 0; i < vos.size(); i++) {
            vos.get(i).setOrderQuantity(orderQuantityVos.get(i));
        }

        //查询时间段内所有记录
        List<FundCheckOrderVo> allVos = myReportMapper.findAllFundIn(reportFundVo);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        vos.stream().forEach(vo -> {
            List<FundCheckOrderVo> filterVos;
            if (reportFundVo.getFlag() == 1) {
                //同一个日期的记录
                filterVos = allVos.stream().filter(filterVo -> sdf.format(filterVo.getCreateTime()).equals(sdf.format(vo.getCreateTime()))).collect(Collectors.toList());
            } else {
                //同一个职员的记录
                filterVos = allVos.stream().filter(filterVo -> filterVo.getUserId().equals(vo.getUserId())).collect(Collectors.toList());
            }

            //现金回款
            List<FundCheckOrderVo> cashInMoneyVos = filterVos.stream().filter(cashInMoneyVo -> cashInMoneyVo.getBankAccountId().equals("1001")).collect(Collectors.toList());
            cashInMoneyVos.stream().forEach(cashInMoneyVo -> vo.setCashInMoney(vo.getCashInMoney() + cashInMoneyVo.getInMoney()));

            //支付宝回款
            List<FundCheckOrderVo> alipayInMoneyVos = filterVos.stream().filter(cashInMoneyVo -> cashInMoneyVo.getBankAccountId().equals("1002")).collect(Collectors.toList());
            alipayInMoneyVos.stream().forEach(alipayInMoneyVo -> vo.setAlipayInMoney(vo.getAlipayInMoney() + alipayInMoneyVo.getInMoney()));

            //微信回款
            List<FundCheckOrderVo> wechatInMoneyVos = filterVos.stream().filter(cashInMoneyVo -> cashInMoneyVo.getBankAccountId().equals("1003")).collect(Collectors.toList());
            wechatInMoneyVos.stream().forEach(wechatInMoneyVo -> vo.setWechatInMoney(vo.getWechatInMoney() + wechatInMoneyVo.getInMoney()));

            //银行卡回款
            List<FundCheckOrderVo> bankCardInMoneyVos = filterVos.stream().filter(cashInMoneyVo -> cashInMoneyVo.getBankAccountId().substring(0, 4).equals("1004")).collect(Collectors.toList());
            bankCardInMoneyVos.stream().forEach(bankCardInMoneyVo -> vo.setBankCardInMoney(vo.getBankCardInMoney() + bankCardInMoneyVo.getInMoney()));
        });
    }

    /**
     * 资金报表-查回款-按职员
     * @param reportFundVo
     * @param pageVo
     * @return
     */
    public CommonResponse findReportFundInByUser(ReportFundVo reportFundVo, PageVo pageVo) {
        //查询所有页数
        pageVo.setTotalPage((int) Math.ceil(myReportMapper.findCountFundInByUser(reportFundVo) * 1.0 / pageVo.getPageSize()));
        pageVo.setStart(pageVo.getPageSize() * (pageVo.getPage() - 1));
        List<ReportFundVo> vos = myReportMapper.findPagedFundInByUser(reportFundVo, pageVo);

        //统计数据
        reportFundVo.setFlag(2);
        findOrderQuantityAndFindInMehtod(vos, reportFundVo);

        //封装返回结果
        List<Title> titles = new ArrayList<>();
        titles.add(new Title("职员编号", "userId"));
        titles.add(new Title("职员名称", "userName"));
        titles.add(new Title("单量", "orderQuantity"));
        titles.add(new Title("回款总额", "totalInMoney"));
        titles.add(new Title("现金回款", "bankCardInMoney"));
        titles.add(new Title("支付宝回款", "alipayInMoney"));
        titles.add(new Title("微信回款", "wechatInMoney"));
        CommonResult commonResult = new CommonResult(titles, vos, pageVo);

        return CommonResponse.success(commonResult);
    }

    //资金报表-查费用

    /**
     * 资金报表-查费用-按分类
     * @param reportFundVo
     * @param pageVo
     * @return
     */
    public CommonResponse findReportFundOutByType(ReportFundVo reportFundVo, PageVo pageVo) {
        //查询所有页数
        pageVo.setTotalPage((int) Math.ceil(myReportMapper.findCountFundOutByType(reportFundVo) * 1.0 / pageVo.getPageSize()));
        pageVo.setStart(pageVo.getPageSize() * (pageVo.getPage() - 1));
        List<ReportFundVo> vos = myReportMapper.findPagedFundOutByType(reportFundVo, pageVo);

        //统计费用占比
        double totalOutMoney = vos.stream().mapToDouble(ReportFundVo::getTotalOutMoney).sum();
        vos.stream().forEach(vo -> vo.setProportion(totalOutMoney == 0 ? 0 : vo.getTotalOutMoney() * 100 / totalOutMoney));

        //封装返回结果
        List<Title> titles = new ArrayList<>();
        titles.add(new Title("费用编号", "userId"));
        titles.add(new Title("费用名称", "userName"));
        titles.add(new Title("费用金额", "totalOutMoney"));
        titles.add(new Title("占比(%)", "proportion"));
        CommonResult commonResult = new CommonResult(titles, vos, pageVo);

        return CommonResponse.success(commonResult);
    }

    /**
     * 资金报表-查费用-按往来单位
     * @param reportFundVo
     * @param pageVo
     * @return
     */
    public CommonResponse findReportFundOutByTarget(ReportFundVo reportFundVo, PageVo pageVo) {
        //查询所有页数
        pageVo.setTotalPage((int) Math.ceil(myReportMapper.findCountFundOutByTarget(reportFundVo) * 1.0 / pageVo.getPageSize()));
        pageVo.setStart(pageVo.getPageSize() * (pageVo.getPage() - 1));
        List<ReportFundVo> vos = myReportMapper.findPagedFundOutByTarget(reportFundVo, pageVo);

        if (vos.size() > 0) {
            //统计优惠金额
            vos.stream().forEach(vo -> {
                reportFundVo.setFlag(1);
                reportFundVo.setTargetId(vo.getTargetId());
                vo.setTotalOutMoney(vo.getTotalOutMoney() + myReportMapper.findDiscountMoney(reportFundVo));
            });

            //统计费用占比
            double totalOutMoney = vos.stream().mapToDouble(ReportFundVo::getTotalOutMoney).sum();
            vos.stream().forEach(vo -> vo.setProportion(totalOutMoney == 0 ? 0 : vo.getTotalOutMoney() * 100 / totalOutMoney));
        }

        //封装返回结果
        List<Title> titles = new ArrayList<>();
        titles.add(new Title("单位编号", "targetId"));
        titles.add(new Title("单位名称", "targetName"));
        titles.add(new Title("费用金额", "totalOutMoney"));
        titles.add(new Title("占比(%)", "proportion"));
        CommonResult commonResult = new CommonResult(titles, vos, pageVo);

        return CommonResponse.success(commonResult);
    }

    /**
     * 资金报表-查费用-按职员
     * @param reportFundVo
     * @param pageVo
     * @return
     */
    public CommonResponse findReportFundOutByUser(ReportFundVo reportFundVo, PageVo pageVo) {
        //查询所有页数
        pageVo.setTotalPage((int) Math.ceil(myReportMapper.findCountFundOutByUser(reportFundVo) * 1.0 / pageVo.getPageSize()));
        pageVo.setStart(pageVo.getPageSize() * (pageVo.getPage() - 1));
        List<ReportFundVo> vos = myReportMapper.findPagedFundOutByUser(reportFundVo, pageVo);

        if (vos.size() > 0) {
            //统计优惠金额
            vos.stream().forEach(vo -> {
                reportFundVo.setFlag(2);
                reportFundVo.setUserId(vo.getUserId());
                vo.setTotalOutMoney(vo.getTotalOutMoney() + myReportMapper.findDiscountMoney(reportFundVo));
            });

            //统计费用占比
            double totalOutMoney = vos.stream().mapToDouble(ReportFundVo::getTotalOutMoney).sum();
            vos.stream().forEach(vo -> vo.setProportion(totalOutMoney == 0 ? 0 : vo.getTotalOutMoney() * 100 / totalOutMoney));
        }

        //封装返回结果
        List<Title> titles = new ArrayList<>();
        titles.add(new Title("职员编号", "userId"));
        titles.add(new Title("职员名称", "userName"));
        titles.add(new Title("费用金额", "totalOutMoney"));
        titles.add(new Title("占比(%)", "proportion"));
        CommonResult commonResult = new CommonResult(titles, vos, pageVo);

        return CommonResponse.success(commonResult);
    }

    /**
     * 资金报表-查费用-按明细
     * @param reportFundVo
     * @param pageVo
     * @return
     */
    public CommonResponse findReportFundOutByDetails(ReportFundVo reportFundVo, PageVo pageVo) {
        //查询所有页数
        pageVo.setTotalPage((int) Math.ceil(myReportMapper.findCountFundOutByDetails(reportFundVo) * 1.0 / pageVo.getPageSize()));
        pageVo.setStart(pageVo.getPageSize() * (pageVo.getPage() - 1));
        List<FundCheckOrderVo> vos = myReportMapper.findPagedFundOutByDetails(reportFundVo, pageVo);

        //封装返回结果
        List<Title> titles = new ArrayList<>();
        titles.add(new Title("单据编号", "orderId"));
        titles.add(new Title("单据日期", "createTime"));
        titles.add(new Title("单据类型", "typeName"));
        titles.add(new Title("往来单位", "targetName"));
        titles.add(new Title("科目", "expensesName"));
        titles.add(new Title("金额", "outMoney"));
        titles.add(new Title("经手人", "userName"));
        titles.add(new Title("备注", "remark"));
        CommonResult commonResult = new CommonResult(titles, vos, pageVo);

        return CommonResponse.success(commonResult);
    }

    //订单统计

    //销售报表

    //采购报表

    //经营中心
}
