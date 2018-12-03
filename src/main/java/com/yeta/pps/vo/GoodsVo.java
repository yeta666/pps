package com.yeta.pps.vo;

import com.yeta.pps.po.GoodsBrand;
import com.yeta.pps.po.GoodsLabel;
import com.yeta.pps.po.GoodsType;
import com.yeta.pps.po.GoodsUnit;
import com.yeta.pps.util.CommonResponse;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class GoodsVo {

    /**
     * 店铺id
     */
    @NotNull(message = CommonResponse.MESSAGE3)
    private Integer storeId;

    /**
     * 商品id
     */
    private String id;

    /**
     * 商品名
     */
    @NotBlank(message = CommonResponse.MESSAGE3)
    private String name;

    /**
     * 货号
     */
    @NotBlank(message = CommonResponse.MESSAGE3)
    private String code;

    /**
     * 条码
     */
    @NotBlank(message = CommonResponse.MESSAGE3)
    private String barCode;

    /**
     * 分类id
     */
    @NotNull(message = CommonResponse.MESSAGE3)
    private Integer typeId;

    /**
     * 品牌id
     */
    @NotNull(message = CommonResponse.MESSAGE3)
    private Integer brandId;

    /**
     * 单位id
     */
    @NotNull(message = CommonResponse.MESSAGE3)
    private Integer unitId;

    /**
     * 标签id
     */
    @NotNull(message = CommonResponse.MESSAGE3)
    private Integer labelId;

    private GoodsBrand goodsBrand;

    private GoodsLabel goodsLabel;

    private GoodsType goodsType;

    private GoodsUnit goodsUnit;

    /**
     * 零售价
     */
    @NotNull(message = CommonResponse.MESSAGE3)
    private BigDecimal retailPrice;

    /**
     * 批发价
     */
    @NotNull(message = CommonResponse.MESSAGE3)
    private BigDecimal tradePrice;

    /**
     * 预设进价
     */
    @NotNull(message = CommonResponse.MESSAGE3)
    private BigDecimal purchasePrice;

    /**
     * vip售价
     */
    @NotNull(message = CommonResponse.MESSAGE3)
    private BigDecimal vipPrice;

    /**
     * 可用库存
     */
    private Integer inventory;

    /**
     * 产地
     */
    private String origin;

    /**
     * 图片
     */
    private String image;

    /**
     * 香型
     */
    private String oderType;

    /**
     * 度数
     */
    private String degree;

    /**
     * 净含量
     */
    private String netContent;

    /**
     * 商品积分
     */
    private Integer integral;

    /**
     * 备注
     */
    private String remark;

    /**
     * 上架状态
     */
    @NotNull(message = CommonResponse.MESSAGE3)
    private Integer putaway;

    public GoodsVo() {
    }

    public GoodsVo(@NotNull(message = CommonResponse.MESSAGE3) Integer storeId, String id) {
        this.storeId = storeId;
        this.id = id;
    }

    public GoodsVo(@NotNull(message = CommonResponse.MESSAGE3) Integer storeId, @NotBlank(message = CommonResponse.MESSAGE3) String code, @NotBlank(message = CommonResponse.MESSAGE3) String barCode, @NotNull(message = CommonResponse.MESSAGE3) Integer typeId, @NotNull(message = CommonResponse.MESSAGE3) Integer brandId, @NotNull(message = CommonResponse.MESSAGE3) Integer putaway) {
        this.storeId = storeId;
        this.code = code;
        this.barCode = barCode;
        this.typeId = typeId;
        this.brandId = brandId;
        this.putaway = putaway;
    }

    public Integer getStoreId() {
        return storeId;
    }

    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public Integer getBrandId() {
        return brandId;
    }

    public void setBrandId(Integer brandId) {
        this.brandId = brandId;
    }

    public Integer getUnitId() {
        return unitId;
    }

    public void setUnitId(Integer unitId) {
        this.unitId = unitId;
    }

    public Integer getLabelId() {
        return labelId;
    }

    public void setLabelId(Integer labelId) {
        this.labelId = labelId;
    }

    public GoodsBrand getGoodsBrand() {
        return goodsBrand;
    }

    public void setGoodsBrand(GoodsBrand goodsBrand) {
        this.goodsBrand = goodsBrand;
    }

    public GoodsLabel getGoodsLabel() {
        return goodsLabel;
    }

    public void setGoodsLabel(GoodsLabel goodsLabel) {
        this.goodsLabel = goodsLabel;
    }

    public GoodsType getGoodsType() {
        return goodsType;
    }

    public void setGoodsType(GoodsType goodsType) {
        this.goodsType = goodsType;
    }

    public GoodsUnit getGoodsUnit() {
        return goodsUnit;
    }

    public void setGoodsUnit(GoodsUnit goodsUnit) {
        this.goodsUnit = goodsUnit;
    }

    public BigDecimal getRetailPrice() {
        return retailPrice;
    }

    public void setRetailPrice(BigDecimal retailPrice) {
        this.retailPrice = retailPrice;
    }

    public BigDecimal getTradePrice() {
        return tradePrice;
    }

    public void setTradePrice(BigDecimal tradePrice) {
        this.tradePrice = tradePrice;
    }

    public BigDecimal getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(BigDecimal purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public BigDecimal getVipPrice() {
        return vipPrice;
    }

    public void setVipPrice(BigDecimal vipPrice) {
        this.vipPrice = vipPrice;
    }

    public Integer getInventory() {
        return inventory;
    }

    public void setInventory(Integer inventory) {
        this.inventory = inventory;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getOderType() {
        return oderType;
    }

    public void setOderType(String oderType) {
        this.oderType = oderType;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getNetContent() {
        return netContent;
    }

    public void setNetContent(String netContent) {
        this.netContent = netContent;
    }

    public Integer getIntegral() {
        return integral;
    }

    public void setIntegral(Integer integral) {
        this.integral = integral;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getPutaway() {
        return putaway;
    }

    public void setPutaway(Integer putaway) {
        this.putaway = putaway;
    }

    @Override
    public String toString() {
        return "GoodsVo{" +
                "storeId=" + storeId +
                ", id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", code='" + code + '\'' +
                ", barCode='" + barCode + '\'' +
                ", typeId=" + typeId +
                ", brandId=" + brandId +
                ", unitId=" + unitId +
                ", labelId=" + labelId +
                ", goodsBrand=" + goodsBrand +
                ", goodsLabel=" + goodsLabel +
                ", goodsType=" + goodsType +
                ", goodsUnit=" + goodsUnit +
                ", retailPrice=" + retailPrice +
                ", tradePrice=" + tradePrice +
                ", purchasePrice=" + purchasePrice +
                ", vipPrice=" + vipPrice +
                ", inventory=" + inventory +
                ", origin='" + origin + '\'' +
                ", image='" + image + '\'' +
                ", oderType='" + oderType + '\'' +
                ", degree='" + degree + '\'' +
                ", netContent='" + netContent + '\'' +
                ", integral=" + integral +
                ", remark='" + remark + '\'' +
                ", putaway=" + putaway +
                '}';
    }
}