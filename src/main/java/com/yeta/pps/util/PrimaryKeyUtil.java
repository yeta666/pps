package com.yeta.pps.util;

import com.yeta.pps.exception.CommonException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author YETA
 * @date 2019/01/22/10:32
 */
@Component
public class PrimaryKeyUtil {

    /**
     * 获取主键的方法
     * 客户、商品、供应商
     * @param ids
     * @return
     */
    @Transactional
    public String getPrimaryKeyMethod(List<String> ids, String prefix) {
        int max = 0;
        for (String id : ids) {
            if (!id.contains(prefix)) {
                continue;
            }
            Integer temp = Integer.valueOf(id.replace(prefix, ""));
            max = temp > max ? temp : max;
        }
        String id = String.valueOf((max + 1));
        switch (id.length()) {
            case 1:
                id = prefix + "000" + id;
                break;
            case 2:
                id = prefix + "00" + id;
                break;
            case 3:
                id = prefix + "0" + id;
                break;
            case 4:
                id = prefix + id;
                break;
            default:
                throw new CommonException(CommonResponse.ADD_ERROR, "获取主键错误");
        }
        return id;
    }

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

    /**
     * 获取订单主键的方法
     * （预）收/付款单、其他收入单/费用单、采购/销售申请单、采购/销售结果单、收/发货单、其他出/入库单、报损/溢单、成本调价单、库存盘点单
     * @param ids
     * @param prefix
     * @return
     */
    @Transactional
    public String getOrderPrimaryKeyMethod(List<String> ids, String prefix) {
        String date = sdf.format(new Date());
        int max = 0;
        for (String id : ids) {
            if (!id.contains("-20")) {
                continue;
            }
            Integer temp = Integer.valueOf(id.substring(id.lastIndexOf("-") + 1));
            max = temp > max ? temp : max;
        }
        String id = String.valueOf(max + 1);
        switch (id.length()) {
            case 1:
                id = prefix + "-" + date + "-000" + id;
                break;
            case 2:
                id = prefix + "-" + date + "-00" + id;
                break;
            case 3:
                id = prefix + "-" + date + "-0" + id;
                break;
            case 4:
                id = prefix + "-" + date + "-" + id;
                break;
            default:
                throw new CommonException(CommonResponse.ADD_ERROR, "获取主键错误");
        }
        return id;
    }
}
