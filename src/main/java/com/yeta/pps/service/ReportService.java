package com.yeta.pps.service;

import com.yeta.pps.exception.CommonException;
import com.yeta.pps.mapper.MyBankAccountMapper;
import com.yeta.pps.mapper.MyFundMapper;
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
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 报表相关逻辑处理
 *
 * @author YETA
 * @date 2019/01/07/13:29
 */
@Service
public class ReportService {

    @Autowired
    private MyReportMapper myReportMapper;

    @Autowired
    private MyGoodsMapper myGoodsMapper;

    @Autowired
    private MyFundMapper myFundMapper;

    @Autowired
    private MyBankAccountMapper myBankAccountMapper;

    /**
     * 返回四舍五入2位小数的方法
     *
     * @param number
     * @return
     */
    public double getNumberMethod(double number) {
        return (double) Math.round(number * 100) / 100;
    }

    /**
     * 获取率的方法
     * @param a
     * @param b
     * @return
     */
    public double getRateMethod(double a, double b) {
        if (b == 0 && a == 0) {
            return 0.00;
        } else if (b == 0 && a != 0) {
            return 100.00;
        } else {
            return a * 100 / b;
        }
    }

    //库存报表

    //库存报表-进销存分析

    /**
     * 库存报表-进销存分析-按商品
     *
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
     *
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
     *
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
     *
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
     *
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
     *
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
     *
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
     *
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
     *
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
     *
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
     *
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

    /**
     * 订单统计-订单分析
     *
     * @param reportOrderVo
     * @param pageVo
     * @return
     */
    public CommonResponse findReportOrderByOrder(ReportOrderVo reportOrderVo, PageVo pageVo) {
        //查询所有页数
        pageVo.setTotalPage((int) Math.ceil(myReportMapper.findCountOrderByOrder(reportOrderVo) * 1.0 / pageVo.getPageSize()));
        pageVo.setStart(pageVo.getPageSize() * (pageVo.getPage() - 1));
        List<ReportOrderVo> vos = myReportMapper.findPagedOrderByOrder(reportOrderVo, pageVo);

        //封装返回结果
        List<Title> titles = new ArrayList<>();
        titles.add(new Title("日期", "createTime"));
        titles.add(new Title("订单数", "orderQuantity"));
        titles.add(new Title("退货单数", "returnQuantity"));
        titles.add(new Title("换货单数", "exchangeQuantity"));
        titles.add(new Title("订单金额", "orderMoney"));
        titles.add(new Title("退货金额", "returnMoney"));
        titles.add(new Title("平均订单金额", "averageOrderMoney"));
        CommonResult commonResult = new CommonResult(titles, vos, pageVo);

        return CommonResponse.success(commonResult);
    }

    /**
     * 订单统计-商品订货分析
     * @param reportOrderVo
     * @param pageVo
     * @return
     */
    public CommonResponse findReportOrderByGoods(ReportOrderVo reportOrderVo, PageVo pageVo) {
        //查询所有页数
        pageVo.setTotalPage((int) Math.ceil(myReportMapper.findCountOrderByGoods(reportOrderVo) * 1.0 / pageVo.getPageSize()));
        pageVo.setStart(pageVo.getPageSize() * (pageVo.getPage() - 1));
        List<ReportOrderVo> vos = myReportMapper.findPagedOrderByGoods(reportOrderVo, pageVo);

        vos.stream().forEach(vo -> {
            //退货率
            vo.setAverageOrderMoney(getRateMethod(vo.getReturnQuantity(), vo.getOrderQuantity()));

            //金额合计
            vo.setNetMoney(vo.getOrderMoney() - vo.getReturnMoney());
        });

        //封装返回结果
        List<Title> titles = new ArrayList<>();
        titles.add(new Title("商品货号", "goodsId"));
        titles.add(new Title("商品名", "goodsName"));
        titles.add(new Title("商品条码", "goodsBarCode"));
        titles.add(new Title("商品分类", "goodsTypeName"));
        titles.add(new Title("商品图片", "goodsImage"));

        titles.add(new Title("销售数量", "orderQuantity"));
        titles.add(new Title("金额", "orderMoney"));
        titles.add(new Title("退货数量", "returnQuantity"));
        titles.add(new Title("退货金额", "returnMoney"));
        titles.add(new Title("退货率(%)", "averageOrderMoney"));
        titles.add(new Title("金额合计", "netMoney"));
        CommonResult commonResult = new CommonResult(titles, vos, pageVo);

        return CommonResponse.success(commonResult);
    }

    /**
     * 订单统计-客户订货分析
     *
     * @param reportOrderVo
     * @param pageVo
     * @return
     */
    public CommonResponse findReportOrderByClient(ReportOrderVo reportOrderVo, PageVo pageVo) {
        //查询所有页数
        pageVo.setTotalPage((int) Math.ceil(myReportMapper.findCountOrderByClient(reportOrderVo) * 1.0 / pageVo.getPageSize()));
        pageVo.setStart(pageVo.getPageSize() * (pageVo.getPage() - 1));
        List<ReportOrderVo> vos = myReportMapper.findPagedOrderByClient(reportOrderVo, pageVo);

        //封装返回结果
        List<Title> titles = new ArrayList<>();
        titles.add(new Title("日期", "createTime"));
        titles.add(new Title("消费客户数", "clientQuantity"));
        titles.add(new Title("订单数", "orderQuantity"));
        titles.add(new Title("订单金额", "orderMoney"));
        titles.add(new Title("平均客单量", "averageClientOrderQuantity"));
        titles.add(new Title("平均客单价", "averageClientOrderMoney"));
        CommonResult commonResult = new CommonResult(titles, vos, pageVo);

        return CommonResponse.success(commonResult);
    }

    //销售报表

    //销售报表-销售日月年报

    /**
     * 销售报表-销售日月年报-销售日报
     *
     * @param reportSellVo
     * @param pageVo
     * @return
     */
    public CommonResponse findReportSellByDay(ReportSellVo reportSellVo, PageVo pageVo) {
        //查询所有页数
        pageVo.setTotalPage((int) Math.ceil(myReportMapper.findCountSellByDay(reportSellVo) * 1.0 / pageVo.getPageSize()));
        pageVo.setStart(pageVo.getPageSize() * (pageVo.getPage() - 1));
        List<ReportSellVo> vos = myReportMapper.findPagedSellByDay(reportSellVo, pageVo);

        vos.stream().forEach(vo -> {
            //毛利
            vo.setGrossMarginMoney(vo.getSellProceedsMoney() - vo.getSellCostMoney());

            //毛利率
            vo.setGrossMarginRate(getRateMethod(vo.getGrossMarginMoney(), vo.getSellProceedsMoney()));
        });

        //封装返回结果
        List<Title> titles = new ArrayList<>();
        titles.add(new Title("单据编号", "orderId"));
        titles.add(new Title("单据类型", "orderTypeName"));
        titles.add(new Title("客户", "clientName"));
        titles.add(new Title("经手人", "userName"));
        titles.add(new Title("备注", "remark"));

        titles.add(new Title("销售回款", "sellInMoney"));
        titles.add(new Title("使用预收款", "advanceInMoney"));
        titles.add(new Title("新增应收款", "addNeedInMoney"));

        titles.add(new Title("销售收入", "sellProceedsMoney"));
        titles.add(new Title("销售成本", "sellCostMoney"));
        titles.add(new Title("毛利", "grossMarginMoney"));
        titles.add(new Title("毛利率(%)", "grossMarginRate"));

        CommonResult commonResult = new CommonResult(titles, vos, pageVo);

        return CommonResponse.success(commonResult);
    }

    /**
     * 销售报表-销售日月年报-销售月报
     *
     * @param reportSellVo
     * @param pageVo
     * @return
     */
    public CommonResponse findReportSellByMonth(ReportSellVo reportSellVo, PageVo pageVo) {
        //查询所有页数
        pageVo.setTotalPage((int) Math.ceil(myReportMapper.findCountSellByMonth(reportSellVo) * 1.0 / pageVo.getPageSize()));
        pageVo.setStart(pageVo.getPageSize() * (pageVo.getPage() - 1));
        List<ReportSellVo> vos = myReportMapper.findPagedSellByMonth(reportSellVo, pageVo);

        vos.stream().forEach(vo -> {
            //毛利
            vo.setGrossMarginMoney(vo.getSellProceedsMoney() - vo.getSellCostMoney());

            //毛利率
            vo.setGrossMarginRate(getRateMethod(vo.getGrossMarginMoney(), vo.getSellProceedsMoney()));
        });

        //封装返回结果
        List<Title> titles = new ArrayList<>();
        titles.add(new Title("日期", "createTime"));

        titles.add(new Title("销售单数量", "sellOrderQuantity"));
        titles.add(new Title("退货单数量", "returnOrderQuantity"));
        titles.add(new Title("换货单数量", "exchangeOrderQuantity"));

        titles.add(new Title("销售回款", "sellInMoney"));
        titles.add(new Title("使用预收款", "advanceInMoney"));
        titles.add(new Title("新增应收款", "addNeedInMoney"));

        titles.add(new Title("销售收入", "sellProceedsMoney"));
        titles.add(new Title("销售成本", "sellCostMoney"));
        titles.add(new Title("毛利", "grossMarginMoney"));
        titles.add(new Title("毛利率(%)", "grossMarginRate"));
        CommonResult commonResult = new CommonResult(titles, vos, pageVo);

        return CommonResponse.success(commonResult);
    }

    /**
     * 销售报表-销售日月年报-销售年报
     *
     * @param reportSellVo
     * @param pageVo
     * @return
     */
    public CommonResponse findReportSellByYear(ReportSellVo reportSellVo, PageVo pageVo) {
        //查询所有页数
        pageVo.setTotalPage((int) Math.ceil(myReportMapper.findCountSellByYear(reportSellVo) * 1.0 / pageVo.getPageSize()));
        pageVo.setStart(pageVo.getPageSize() * (pageVo.getPage() - 1));
        List<ReportSellVo> vos = myReportMapper.findPagedSellByYear(reportSellVo, pageVo);

        vos.stream().forEach(vo -> {
            //毛利
            vo.setGrossMarginMoney(vo.getSellProceedsMoney() - vo.getSellCostMoney());

            //毛利率
            vo.setGrossMarginRate(getRateMethod(vo.getGrossMarginMoney(), vo.getSellProceedsMoney()));
        });

        //封装返回结果
        List<Title> titles = new ArrayList<>();
        titles.add(new Title("月份", "createMonth"));

        titles.add(new Title("销售单数量", "sellOrderQuantity"));
        titles.add(new Title("退货单数量", "returnOrderQuantity"));
        titles.add(new Title("换货单数量", "exchangeOrderQuantity"));

        titles.add(new Title("销售回款", "sellInMoney"));
        titles.add(new Title("使用预收款", "advanceInMoney"));
        titles.add(new Title("新增应收款", "addNeedInMoney"));

        titles.add(new Title("销售收入", "sellProceedsMoney"));
        titles.add(new Title("销售成本", "sellCostMoney"));
        titles.add(new Title("毛利", "grossMarginMoney"));
        titles.add(new Title("毛利率(%)", "grossMarginRate"));
        CommonResult commonResult = new CommonResult(titles, vos, pageVo);

        return CommonResponse.success(commonResult);
    }

    //销售报表-商品销售分析

    /**
     * 销售报表-商品销售分析-按商品
     *
     * @param reportSellVo
     * @param pageVo
     * @return
     */
    public CommonResponse findReportSellByGoods(ReportSellVo reportSellVo, PageVo pageVo) {
        //查询所有页数
        pageVo.setTotalPage((int) Math.ceil(myReportMapper.findCountSellByGoods(reportSellVo) * 1.0 / pageVo.getPageSize()));
        pageVo.setStart(pageVo.getPageSize() * (pageVo.getPage() - 1));
        List<ReportSellVo> vos = myReportMapper.findPagedSellByGoods(reportSellVo, pageVo);

        vos.stream().forEach(vo -> {
            //毛利
            vo.setGrossMarginMoney(vo.getSellProceedsMoney() - vo.getSellCostMoney());

            //毛利率
            vo.setGrossMarginRate(getRateMethod(vo.getGrossMarginMoney(), vo.getSellProceedsMoney()));

            //销售数量
            vo.setSellQuantity(vo.getSellOutQuantity() - vo.getReturnQuantity());

            //退货率
            vo.setReturnRate(getRateMethod(vo.getReturnQuantity(), vo.getSellOutQuantity()));
        });

        //封装返回结果
        List<Title> titles = new ArrayList<>();
        titles.add(new Title("商品货号", "goodsId"));
        titles.add(new Title("商品名", "goodsName"));
        titles.add(new Title("商品条码", "goodsBarCode"));
        titles.add(new Title("商品分类", "goodsTypeName"));
        titles.add(new Title("商品图片", "goodsImage"));

        titles.add(new Title("销售收入", "sellProceedsMoney"));
        titles.add(new Title("销售成本", "sellCostMoney"));
        titles.add(new Title("毛利", "grossMarginMoney"));
        titles.add(new Title("毛利率(%)", "grossMarginRate"));

        titles.add(new Title("销售数量", "sellQuantity"));
        titles.add(new Title("销售出库数量", "sellOutQuantity"));
        titles.add(new Title("退货数量", "returnQuantity"));
        titles.add(new Title("退货金额", "returnMoney"));
        titles.add(new Title("退货率(%)", "returnRate"));
        CommonResult commonResult = new CommonResult(titles, vos, pageVo);

        return CommonResponse.success(commonResult);
    }

    //销售报表-客户销售分析

    /**
     * 销售报表-客户销售分析-按客户
     * @param reportSellVo
     * @param pageVo
     * @return
     */
    public CommonResponse findReportSellByClient(ReportSellVo reportSellVo, PageVo pageVo) {
        //查询所有页数
        pageVo.setTotalPage((int) Math.ceil(myReportMapper.findCountSellByClient(reportSellVo) * 1.0 / pageVo.getPageSize()));
        pageVo.setStart(pageVo.getPageSize() * (pageVo.getPage() - 1));
        List<ReportSellVo> vos = myReportMapper.findPagedSellByClient(reportSellVo, pageVo);

        vos.stream().forEach(vo -> {
            //毛利
            vo.setGrossMarginMoney(vo.getSellProceedsMoney() - vo.getSellCostMoney());

            //毛利率
            vo.setGrossMarginRate(getRateMethod(vo.getGrossMarginMoney(), vo.getSellProceedsMoney()));

            //销售数量
            vo.setSellQuantity(vo.getSellOutQuantity() - vo.getReturnQuantity());

            //退货率
            vo.setReturnRate(getRateMethod(vo.getReturnQuantity(), vo.getSellOutQuantity()));

            //营业业绩
            vo.setSellPerformance(vo.getSellProceedsMoney() + vo.getOtherInMoney() - vo.getDiscountMoney());

            //营业利润
            vo.setSellProfit(vo.getSellPerformance() - vo.getSellCostMoney());
        });

        //封装返回结果
        List<Title> titles = new ArrayList<>();
        titles.add(new Title("客户编号", "clientId"));
        titles.add(new Title("客户名称", "clientName"));

        titles.add(new Title("业务回款", "sellInMoney"));
        titles.add(new Title("新增应收款", "addNeedInMoney"));
        titles.add(new Title("预收收款", "advanceMoney"));
        titles.add(new Title("使用预收款", "advanceInMoney"));

        titles.add(new Title("销售收入", "sellProceedsMoney"));
        titles.add(new Title("销售成本", "sellCostMoney"));
        titles.add(new Title("毛利", "grossMarginMoney"));
        titles.add(new Title("毛利率(%)", "grossMarginRate"));

        titles.add(new Title("其他收入", "otherInMoney"));
        titles.add(new Title("优惠", "discountMoney"));
        titles.add(new Title("营业业绩", "sellPerformance"));
        titles.add(new Title("营业利润", "sellProfit"));

        titles.add(new Title("销售数量", "sellQuantity"));
        titles.add(new Title("销售出库数量", "sellOutQuantity"));
        titles.add(new Title("退货数量", "returnQuantity"));
        titles.add(new Title("退货金额", "returnMoney"));
        titles.add(new Title("退货率(%)", "returnRate"));

        CommonResult commonResult = new CommonResult(titles, vos, pageVo);

        return CommonResponse.success(commonResult);
    }

    //销售报表-业绩统计

    /**
     * 销售报表-业绩统计-按职员
     * @param reportSellVo
     * @param pageVo
     * @return
     */
    public CommonResponse findReportSellByUser(ReportSellVo reportSellVo, PageVo pageVo) {
        //查询所有页数
        pageVo.setTotalPage((int) Math.ceil(myReportMapper.findCountSellByUser(reportSellVo) * 1.0 / pageVo.getPageSize()));
        pageVo.setStart(pageVo.getPageSize() * (pageVo.getPage() - 1));
        List<ReportSellVo> vos = myReportMapper.findPagedSellByUser(reportSellVo, pageVo);

        vos.stream().forEach(vo -> {
            //毛利
            vo.setGrossMarginMoney(vo.getSellProceedsMoney() - vo.getSellCostMoney());

            //毛利率
            vo.setGrossMarginRate(getRateMethod(vo.getGrossMarginMoney(), vo.getSellProceedsMoney()));

            //营业业绩
            vo.setSellPerformance(vo.getSellProceedsMoney() + vo.getOtherInMoney() - vo.getDiscountMoney());

            //营业利润
            vo.setSellProfit(vo.getSellPerformance() - vo.getSellCostMoney());
        });

        //封装返回结果
        List<Title> titles = new ArrayList<>();
        titles.add(new Title("职员编号", "userId"));
        titles.add(new Title("职员名称", "userName"));

        titles.add(new Title("销售单数量", "sellOrderQuantity"));
        titles.add(new Title("销售数量", "sellQuantity"));

        titles.add(new Title("业务回款", "sellInMoney"));
        titles.add(new Title("新增应收款", "addNeedInMoney"));
        titles.add(new Title("预收收款", "advanceMoney"));
        titles.add(new Title("使用预收款", "advanceInMoney"));

        titles.add(new Title("销售收入", "sellProceedsMoney"));
        titles.add(new Title("销售成本", "sellCostMoney"));
        titles.add(new Title("毛利", "grossMarginMoney"));
        titles.add(new Title("毛利率(%)", "grossMarginRate"));

        titles.add(new Title("其他收入", "otherInMoney"));
        titles.add(new Title("优惠", "discountMoney"));
        titles.add(new Title("营业业绩", "sellPerformance"));
        titles.add(new Title("营业利润", "sellProfit"));
        CommonResult commonResult = new CommonResult(titles, vos, pageVo);

        return CommonResponse.success(commonResult);
    }

    /**
     * 销售报表-业绩统计-按仓库
     * @param reportSellVo
     * @param pageVo
     * @return
     */
    public CommonResponse findReportSellByWarehouse(ReportSellVo reportSellVo, PageVo pageVo) {
        //查询所有页数
        pageVo.setTotalPage((int) Math.ceil(myReportMapper.findCountSellByWarehouse(reportSellVo) * 1.0 / pageVo.getPageSize()));
        pageVo.setStart(pageVo.getPageSize() * (pageVo.getPage() - 1));
        List<ReportSellVo> vos = myReportMapper.findPagedSellByWarehouse(reportSellVo, pageVo);

        vos.stream().forEach(vo -> {
            //毛利
            vo.setGrossMarginMoney(vo.getSellProceedsMoney() - vo.getSellCostMoney());

            //毛利率
            vo.setGrossMarginRate(getRateMethod(vo.getGrossMarginMoney(), vo.getSellProceedsMoney()));
        });

        //封装返回结果
        List<Title> titles = new ArrayList<>();
        titles.add(new Title("仓库编号", "warehouseId"));
        titles.add(new Title("仓库名称", "warehouseName"));

        titles.add(new Title("销售单数量", "sellOrderQuantity"));
        titles.add(new Title("销售数量", "sellQuantity"));

        titles.add(new Title("销售收入", "sellProceedsMoney"));
        titles.add(new Title("销售成本", "sellCostMoney"));
        titles.add(new Title("毛利", "grossMarginMoney"));
        titles.add(new Title("毛利率(%)", "grossMarginRate"));
        CommonResult commonResult = new CommonResult(titles, vos, pageVo);

        return CommonResponse.success(commonResult);
    }

    /**
     * 销售报表-回款统计-按职员
     * @param reportSellVo
     * @param pageVo
     * @return
     */
    public CommonResponse findReportSellInByUser(ReportSellVo reportSellVo, PageVo pageVo) {
        //查询所有页数
        pageVo.setTotalPage((int) Math.ceil(myReportMapper.findCountSellInByUser(reportSellVo) * 1.0 / pageVo.getPageSize()));
        pageVo.setStart(pageVo.getPageSize() * (pageVo.getPage() - 1));
        List<ReportSellVo> vos = myReportMapper.findPagedSellInByUser(reportSellVo, pageVo);

        //封装返回结果
        List<Title> titles = new ArrayList<>();
        titles.add(new Title("职员编号", "userId"));
        titles.add(new Title("职员名称", "userName"));

        titles.add(new Title("单量", "orderQuantity"));
        titles.add(new Title("回款总额", "totalInMoney"));
        titles.add(new Title("现金回款", "bankCardInMoney"));
        titles.add(new Title("支付宝回款", "alipayInMoney"));
        titles.add(new Title("微信回款", "wechatInMoney"));
        titles.add(new Title("银行卡回款", "bankCardInMoney"));
        titles.add(new Title("使用预收款", "advanceInMoney"));
        CommonResult commonResult = new CommonResult(titles, vos, pageVo);

        return CommonResponse.success(commonResult);
    }

    //采购报表

    //采购报表-商品采购分析

    /**
     * 采购报表-商品采购分析-按商品
     * @param procurementVo
     * @param pageVo
     * @return
     */
    public CommonResponse findReportProcurementByGoods(ReportProcurementVo procurementVo, PageVo pageVo) {
        //查询所有页数
        pageVo.setTotalPage((int) Math.ceil(myReportMapper.findCountProcurementByGoods(procurementVo) * 1.0 / pageVo.getPageSize()));
        pageVo.setStart(pageVo.getPageSize() * (pageVo.getPage() - 1));
        List<ReportProcurementVo> vos = myReportMapper.findPagedProcurementByGoods(procurementVo, pageVo);

        vos.stream().forEach(vo -> {
            //采购数量
            vo.setProcurementQuantity(vo.getProcurementInQuantity() - vo.getReturnQuantity());

            //退货率
            vo.setReturnRate(getRateMethod(vo.getReturnQuantity(), vo.getProcurementInQuantity()));
        });

        //封装返回结果
        List<Title> titles = new ArrayList<>();
        titles.add(new Title("商品货号", "goodsId"));
        titles.add(new Title("商品名", "goodsName"));
        titles.add(new Title("商品条码", "goodsBarCode"));
        titles.add(new Title("商品分类", "goodsTypeName"));
        titles.add(new Title("商品图片", "goodsImage"));

        titles.add(new Title("采购数量", "procurementQuantity"));
        titles.add(new Title("采购金额", "procurementMoney"));
        titles.add(new Title("采购入库数量", "procurementInQuantity"));
        titles.add(new Title("退货数量", "returnQuantity"));
        titles.add(new Title("退货金额", "returnMoney"));
        titles.add(new Title("退货率(%)", "returnRate"));
        CommonResult commonResult = new CommonResult(titles, vos, pageVo);

        return CommonResponse.success(commonResult);
    }

    //采购报表-供应商采购分析

    /**
     * 采购报表-供应商采购分析
     * @param procurementVo
     * @param pageVo
     * @return
     */
    public CommonResponse findReportProcurementBySupplier(ReportProcurementVo procurementVo, PageVo pageVo) {
        //查询所有页数
        pageVo.setTotalPage((int) Math.ceil(myReportMapper.findCountProcurementBySupplier(procurementVo) * 1.0 / pageVo.getPageSize()));
        pageVo.setStart(pageVo.getPageSize() * (pageVo.getPage() - 1));
        List<ReportProcurementVo> vos = myReportMapper.findPagedProcurementBySupplier(procurementVo, pageVo);

        vos.stream().forEach(vo -> {
            //采购数量
            vo.setProcurementQuantity(vo.getProcurementInQuantity() - vo.getReturnQuantity());

            //退货率
            vo.setReturnRate(getRateMethod(vo.getReturnQuantity(), vo.getProcurementInQuantity()));
        });

        //封装返回结果
        List<Title> titles = new ArrayList<>();
        titles.add(new Title("供应商编号", "supplierId"));
        titles.add(new Title("供应商名称", "supplierName"));

        titles.add(new Title("采购数量", "procurementQuantity"));
        titles.add(new Title("采购金额", "procurementMoney"));
        titles.add(new Title("采购入库数量", "procurementInQuantity"));
        titles.add(new Title("退货数量", "returnQuantity"));
        titles.add(new Title("退货金额", "returnMoney"));
        titles.add(new Title("退货率(%)", "returnRate"));
        CommonResult commonResult = new CommonResult(titles, vos, pageVo);

        return CommonResponse.success(commonResult);
    }

    //采购报表-采购订单分析

    /**
     * 采购报表-采购订单分析-按商品
     * @param procurementVo
     * @param pageVo
     * @return
     */
    public CommonResponse findReportProcurementOrderByGoods(ReportProcurementVo procurementVo, PageVo pageVo) {
        //查询所有页数
        pageVo.setTotalPage((int) Math.ceil(myReportMapper.findCountProcurementOrderByGoods(procurementVo) * 1.0 / pageVo.getPageSize()));
        pageVo.setStart(pageVo.getPageSize() * (pageVo.getPage() - 1));
        List<ReportProcurementVo> vos = myReportMapper.findPagedProcurementOrderByGoods(procurementVo, pageVo);

        vos.stream().forEach(vo -> {
            //采购数量
            vo.setProcurementQuantity(vo.getProcurementInQuantity() - vo.getReturnQuantity());

            //退货率
            vo.setReturnRate(getRateMethod(vo.getReturnQuantity(), vo.getProcurementInQuantity()));
        });

        //封装返回结果
        List<Title> titles = new ArrayList<>();
        titles.add(new Title("商品货号", "goodsId"));
        titles.add(new Title("商品名", "goodsName"));
        titles.add(new Title("商品条码", "goodsBarCode"));
        titles.add(new Title("商品分类", "goodsTypeName"));
        titles.add(new Title("商品图片", "goodsImage"));

        titles.add(new Title("采购数量", "procurementQuantity"));
        titles.add(new Title("采购金额", "procurementMoney"));

        titles.add(new Title("已发货数量", "outFinishQuantity"));
        titles.add(new Title("待发货数量", "outNotFinishQuantity"));

        titles.add(new Title("已收货数量", "inFinishQuantity"));
        titles.add(new Title("待收货数量", "inNotFinishQuantity"));

        titles.add(new Title("订单采购总量", "procurementInQuantity"));
        titles.add(new Title("退货数量", "returnQuantity"));
        titles.add(new Title("退货金额", "returnMoney"));
        titles.add(new Title("退货率(%)", "returnRate"));
        CommonResult commonResult = new CommonResult(titles, vos, pageVo);

        return CommonResponse.success(commonResult);
    }

    /**
     * 采购报表-采购订单分析-按明细
     * @param procurementVo
     * @param pageVo
     * @return
     */
    public CommonResponse findReportProcurementOrderByDetail(ReportProcurementVo procurementVo, PageVo pageVo) {
        //查询所有页数
        pageVo.setTotalPage((int) Math.ceil(myReportMapper.findCountProcurementOrderByDetail(procurementVo) * 1.0 / pageVo.getPageSize()));
        pageVo.setStart(pageVo.getPageSize() * (pageVo.getPage() - 1));
        List<ReportProcurementVo> vos = myReportMapper.findPagedProcurementOrderByDetail(procurementVo, pageVo);

        //封装返回结果
        List<Title> titles = new ArrayList<>();
        titles.add(new Title("单据日期", "createTime"));
        titles.add(new Title("单据编号", "orderId"));
        titles.add(new Title("单据类型", "typeName"));
        titles.add(new Title("结算状态", "clearStatusName"));
        titles.add(new Title("仓库", "warehouseName"));
        titles.add(new Title("供应商", "supplierName"));

        titles.add(new Title("商品货号", "goodsId"));
        titles.add(new Title("商品名", "goodsName"));
        titles.add(new Title("商品条码", "goodsBarCode"));
        titles.add(new Title("商品分类", "goodsTypeName"));
        titles.add(new Title("商品规格", "goodsSku"));

        titles.add(new Title("采购数量", "procurementQuantity"));
        titles.add(new Title("采购金额", "procurementMoney"));

        titles.add(new Title("已发货数量", "outFinishQuantity"));
        titles.add(new Title("待发货数量", "outNotFinishQuantity"));

        titles.add(new Title("已收货数量", "inFinishQuantity"));
        titles.add(new Title("待收货数量", "inNotFinishQuantity"));

        titles.add(new Title("经手人", "userName"));
        titles.add(new Title("备注", "remark"));
        CommonResult commonResult = new CommonResult(titles, vos, pageVo);

        return CommonResponse.success(commonResult);
    }

    //经营中心

    //经营中心-销售经营分析

    /**
     * 经营中心-销售经营分析
     * @param reportManageVo
     * @param pageVo
     * @return
     */
    public CommonResponse findReportManageBySell(ReportManageVo reportManageVo, PageVo pageVo) {
        //查询所有页数
        pageVo.setTotalPage((int) Math.ceil(myReportMapper.findCountManageBySell(reportManageVo) * 1.0 / pageVo.getPageSize()));
        pageVo.setStart(pageVo.getPageSize() * (pageVo.getPage() - 1));
        List<ReportManageVo> vos = myReportMapper.findPagedManageBySell(reportManageVo, pageVo);

        vos.stream().forEach(vo -> {
            //毛利
            vo.setGrossMarginMoney(vo.getSellProceedsMoney() - vo.getSellCostMoney());

            //毛利率
            vo.setGrossMarginRate(getRateMethod(vo.getGrossMarginMoney(), vo.getSellProceedsMoney()));
        });

        //封装返回结果
        List<Title> titles = new ArrayList<>();
        titles.add(new Title("日期", "createTime"));

        titles.add(new Title("销售数量", "sellQuantity"));
        titles.add(new Title("销售收入", "sellProceedsMoney"));
        titles.add(new Title("销售成本", "sellCostMoney"));
        titles.add(new Title("毛利", "grossMarginMoney"));
        titles.add(new Title("毛利率(%)", "grossMarginRate"));
        CommonResult commonResult = new CommonResult(titles, vos, pageVo);

        return CommonResponse.success(commonResult);
    }

    //经营中心-资金经营分析

    /**
     * 经营中心-资金经营分析
     * @param reportManageVo
     * @param pageVo
     * @return
     */
    public CommonResponse findReportManageByFund(ReportManageVo reportManageVo, PageVo pageVo) {
        //查询所有页数
        pageVo.setTotalPage((int) Math.ceil(myReportMapper.findCountManageByFund(reportManageVo) * 1.0 / pageVo.getPageSize()));
        pageVo.setStart(pageVo.getPageSize() * (pageVo.getPage() - 1));
        List<ReportManageVo> vos = myReportMapper.findPagedManageByFund(reportManageVo, pageVo);

        //封装返回结果
        List<Title> titles = new ArrayList<>();
        titles.add(new Title("日期", "createTime"));

        titles.add(new Title("现金回款", "cashInMoney"));
        titles.add(new Title("支付宝回款", "alipayInMoney"));
        titles.add(new Title("微信回款", "wechatInMoney"));
        titles.add(new Title("银行卡回款", "bankCardInMoney"));
        titles.add(new Title("回款总额", "totalInMoney"));
        titles.add(new Title("付款总额", "totalOutMoney"));
        CommonResult commonResult = new CommonResult(titles, vos, pageVo);

        return CommonResponse.success(commonResult);
    }

    //经营中心-库存经营分析

    /**
     * 经营中心-库存经营分析
     * @param reportManageVo
     * @param pageVo
     * @return
     */
    public CommonResponse findReportManageByInventory(ReportManageVo reportManageVo, PageVo pageVo) {
        //查询所有页数
        pageVo.setTotalPage((int) Math.ceil(myReportMapper.findCountManageByInventory(reportManageVo) * 1.0 / pageVo.getPageSize()));
        pageVo.setStart(pageVo.getPageSize() * (pageVo.getPage() - 1));
        List<ReportManageVo> vos = myReportMapper.findPagedManageByInventory(reportManageVo, pageVo);

        //封装返回结果
        List<Title> titles = new ArrayList<>();
        titles.add(new Title("日期", "createTime"));

        titles.add(new Title("入库数量", "totalInQuantity"));
        titles.add(new Title("入库金额", "totalInMoney"));
        titles.add(new Title("出库数量", "totalOutQuantity"));
        titles.add(new Title("出库金额", "totalOutMoney"));
        CommonResult commonResult = new CommonResult(titles, vos, pageVo);

        return CommonResponse.success(commonResult);
    }

    //经营中心-利润经营分析

    /**
     * 经营中心-利润经营分析
     * @param reportManageVo
     * @param pageVo
     * @return
     */
    public CommonResponse findReportManageByProfit(ReportManageVo reportManageVo, PageVo pageVo) {
        //查询所有页数
        pageVo.setTotalPage((int) Math.ceil(myReportMapper.findCountManageByProfit(reportManageVo) * 1.0 / pageVo.getPageSize()));
        pageVo.setStart(pageVo.getPageSize() * (pageVo.getPage() - 1));
        List<ReportManageVo> vos = myReportMapper.findPagedManageByProfit(reportManageVo, pageVo);

        vos.stream().forEach(vo -> {
            //毛利
            vo.setGrossMarginMoney(vo.getSellProceedsMoney() - vo.getSellCostMoney());

            //净利润
            vo.setNetMoney(vo.getGrossMarginMoney() + vo.getOtherInMoney() - vo.getOtherOutMoney());
        });

        //封装返回结果
        List<Title> titles = new ArrayList<>();
        titles.add(new Title("日期", "createTime"));

        titles.add(new Title("销售收入", "sellProceedsMoney"));
        titles.add(new Title("销售成本", "sellCostMoney"));
        titles.add(new Title("毛利", "grossMarginMoney"));
        titles.add(new Title("其他收入", "otherInMoney"));
        titles.add(new Title("其他费用", "otherOutMoney"));
        titles.add(new Title("净利润", "netMoney"));
        CommonResult commonResult = new CommonResult(titles, vos, pageVo);

        return CommonResponse.success(commonResult);
    }

    //经营中心-往来经营分析

    /**
     * 经营中心-往来经营分析
     * @param reportManageVo
     * @param pageVo
     * @return
     */
    public CommonResponse findReportManageByTarget(ReportManageVo reportManageVo, PageVo pageVo) {
        //查询所有页数
        pageVo.setTotalPage((int) Math.ceil(myReportMapper.findCountManageByTarget(reportManageVo) * 1.0 / pageVo.getPageSize()));
        pageVo.setStart(pageVo.getPageSize() * (pageVo.getPage() - 1));
        List<ReportManageVo> vos = myReportMapper.findPagedManageByTarget(reportManageVo, pageVo);

        vos.stream().forEach(vo -> {
            //期初应收、期初应付
            FundTargetCheckOrderVo fundTargetCheckOrderVo = new FundTargetCheckOrderVo(reportManageVo.getStoreId(), reportManageVo.getStartTime(), reportManageVo.getEndTime());
            fundTargetCheckOrderVo.setFlag(1);
            FundTargetCheckOrderVo last = myFundMapper.findLastFundTargetCheckOrder(fundTargetCheckOrderVo);
            if (last != null) {
                vo.setNeedInMoneyOpening(last.getNeedInMoneyOpening());
                vo.setNeedOutMoneyOpening(last.getNeedOutMoneyOpening());
            }

            //期末应收、期末应付
            fundTargetCheckOrderVo.setFlag(2);
            last = myFundMapper.findLastFundTargetCheckOrder(fundTargetCheckOrderVo);
            if (last != null) {
                vo.setNeedInMoney(last.getNeedInMoney());
                vo.setNeedOutMoney(last.getNeedOutMoney());
            }
        });

        //封装返回结果
        List<Title> titles = new ArrayList<>();
        titles.add(new Title("日期", "createTime"));

        titles.add(new Title("应收增加", "needInMoneyIncrease"));
        titles.add(new Title("应收减少", "needInMoneyDecrease"));
        titles.add(new Title("期末应收", "needInMoney"));
        titles.add(new Title("应付增加", "needOutMoneyIncrease"));
        titles.add(new Title("应付减少", "needOutMoneyDecrease"));
        titles.add(new Title("期末应付", "needOutMoney"));
        CommonResult commonResult = new CommonResult(titles, vos, pageVo);

        return CommonResponse.success(commonResult);
    }

    //经营中心-老板中心

    /**
     * 经营中心-老板中心
     * @param reportManageVo
     * @return
     */
    public CommonResponse findReportManageByBoss(ReportManageVo reportManageVo) {
        ReportManageVo vo = myReportMapper.findManageByBoss(reportManageVo);
        if (vo == null) {
            return CommonResponse.error(CommonResponse.FIND_ERROR);
        }

        //获取参数
        Integer storeId = reportManageVo.getStoreId();
        Date startTime = reportManageVo.getStartTime();
        Date endTime = reportManageVo.getEndTime();

        //资金余额
        myBankAccountMapper.findAll(new BankAccountVo(storeId)).stream().forEach(bankAccountVo -> {
            FundCheckOrderVo fundCheckOrderVo = myFundMapper.findLastBalanceMoney(new FundCheckOrderVo(storeId, startTime, endTime, bankAccountVo.getId()));
            if (fundCheckOrderVo == null) {
               throw new CommonException(CommonResponse.FIND_ERROR);
            }
            vo.setBalanceMoney(vo.getBalanceMoney() + fundCheckOrderVo.getBalanceMoney());
        });

        //应收余额、应付余额
        FundTargetCheckOrderVo fundTargetCheckOrderVo = new FundTargetCheckOrderVo(storeId, startTime, endTime);
        fundTargetCheckOrderVo.setFlag(2);
        fundTargetCheckOrderVo = myFundMapper.findLastFundTargetCheckOrder(fundTargetCheckOrderVo);
        if (fundTargetCheckOrderVo != null) {
            vo.setNeedInMoney(fundTargetCheckOrderVo.getNeedInMoney());
            vo.setNeedOutMoney(fundTargetCheckOrderVo.getNeedOutMoney());
        }

        return CommonResponse.success(vo);

        /*titles.add(new Title("销售收入", "sellProceedsMoney"));
        titles.add(new Title("销售成本", "sellCostMoney"));
        titles.add(new Title("毛利", "grossMarginMoney"));
        titles.add(new Title("其他收入", "otherInMoney"));
        titles.add(new Title("其他费用", "otherOutMoney"));
        titles.add(new Title("净利润", "netMoney"));
        titles.add(new Title("回款总额", "totalInMoney"));
        titles.add(new Title("付款总额", "totalOutMoney"));
        titles.add(new Title("资金余额", "balanceMoney"));
        titles.add(new Title("应收余额", "needInMoney"));
        titles.add(new Title("应付余额", "needOutMoney"));*/
    }
}
