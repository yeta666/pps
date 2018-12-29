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
    @NotBlank(message = CommonResponse.PARAMETER_ERROR)
    private String name;

    /**
     * 级别价格类型，1：零售价，2：vip售价
     */
    @NotNull(message = CommonResponse.PARAMETER_ERROR)
    private Byte priceType;

    /**
     * 级别默认价格，级别价格类型*0.几
     */
    @NotNull(message = CommonResponse.PARAMETER_ERROR)
    private BigDecimal price;

    public ClientLevel() {
    }

    public ClientLevel(Integer id) {
        this.id = id;
    }

    public ClientLevel(@NotBlank(message = CommonResponse.PARAMETER_ERROR) String name) {
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