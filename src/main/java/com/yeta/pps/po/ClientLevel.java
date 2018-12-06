package com.yeta.pps.po;

import com.yeta.pps.util.CommonResponse;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class ClientLevel {

    /**
     * 客户级别id
     */
    private Integer id;

    /**
     * 客户级别
     */
    @NotBlank(message = CommonResponse.MESSAGE3)
    private String name;

    /**
     * 级别价格类型，1：零售价， 2：vip售价
     */
    @NotNull(message = CommonResponse.MESSAGE3)
    private Byte priceType;

    /**
     * 级别默认价格，级别价格类型*0.几
     */
    @NotNull(message = CommonResponse.MESSAGE3)
    private BigDecimal price;

    public ClientLevel() {
    }

    public ClientLevel(Integer id) {
        this.id = id;
    }

    public ClientLevel(@NotBlank(message = CommonResponse.MESSAGE3) String name) {
        this.name = name;
    }

    /**
     * 获取客户级别id
     *
     * @return id - 客户级别id
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置客户级别id
     *
     * @param id 客户级别id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取客户级别
     *
     * @return name - 客户级别
     */
    public String getName() {
        return name;
    }

    /**
     * 设置客户级别
     *
     * @param name 客户级别
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取级别价格类型，1：零售价， 2：vip售价
     *
     * @return price_type - 级别价格类型，1：零售价， 2：vip售价
     */
    public Byte getPriceType() {
        return priceType;
    }

    /**
     * 设置级别价格类型，1：零售价， 2：vip售价
     *
     * @param priceType 级别价格类型，1：零售价， 2：vip售价
     */
    public void setPriceType(Byte priceType) {
        this.priceType = priceType;
    }

    /**
     * 获取级别默认价格，级别价格类型*0.几
     *
     * @return price - 级别默认价格，级别价格类型*0.几
     */
    public BigDecimal getPrice() {
        return price;
    }

    /**
     * 设置级别默认价格，级别价格类型*0.几
     *
     * @param price 级别默认价格，级别价格类型*0.几
     */
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "ClientLevel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", priceType=" + priceType +
                ", price=" + price +
                '}';
    }
}