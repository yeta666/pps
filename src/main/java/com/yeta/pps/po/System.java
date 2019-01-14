package com.yeta.pps.po;

/**
 * @author YETA
 * @date 2019/01/14/19:06
 */
public class System {

    /**
     * 系统设置编号
     */
    private Integer id;

    /**
     * 提成比例
     */
    private Double pushMoneyRate;

    public System() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getPushMoneyRate() {
        return pushMoneyRate;
    }

    public void setPushMoneyRate(Double pushMoneyRate) {
        this.pushMoneyRate = pushMoneyRate;
    }

    @Override
    public String toString() {
        return "System{" +
                "id=" + id +
                ", pushMoneyRate=" + pushMoneyRate +
                '}';
    }
}
