package com.yeta.pps.po;

import com.yeta.pps.util.CommonResponse;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class ClientLevel {

    /**
     * 客户级别编号
     */
    private Integer id;

    /**
     * 客户级别
     */
    @NotBlank(message = CommonResponse.MESSAGE3)
    private String name;

    /**
     * 级别价格类型，1：零售价，2：vip售价
     */
    @NotNull(message = CommonResponse.MESSAGE3)
    private Byte priceType;

    /**
     * 级别默认价格，级别价格类型*0.几
     */
    @NotNull(message = CommonResponse.MESSAGE3)
    private BigDecimal price;

    /**
     * 分店是否可以创建该级别客户，0：不能，1：能
     */
    @NotNull(message = CommonResponse.MESSAGE3)
    private Byte canUse;

    public ClientLevel() {
    }

    public ClientLevel(Integer id) {
        this.id = id;
    }

    public ClientLevel(@NotBlank(message = CommonResponse.MESSAGE3) String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Byte getPriceType() {
        return priceType;
    }

    public void setPriceType(Byte priceType) {
        this.priceType = priceType;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Byte getCanUse() {
        return canUse;
    }

    public void setCanUse(Byte canUse) {
        this.canUse = canUse;
    }

    @Override
    public String toString() {
        return "ClientLevel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", priceType=" + priceType +
                ", price=" + price +
                ", canUse=" + canUse +
                '}';
    }
}