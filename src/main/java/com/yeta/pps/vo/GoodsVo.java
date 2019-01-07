package com.yeta.pps.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yeta.pps.po.GoodsLabel;
import com.yeta.pps.util.CommonResponse;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

public class GoodsVo {

    @NotNull(message = CommonResponse.PARAMETER_ERROR)
    private Integer storeId;

    private List<GoodsLabel> goodsLabels;

    /**
     * 商品货号
     */
    private String id;

    /**
     * 商品名
     */
    @NotBlank(message = CommonResponse.PARAMETER_ERROR)
    private String name;

    /**
     * 条码
     */
    @NotBlank(message = CommonResponse.PARAMETER_ERROR)
    private String barCode;

    /**
     * 分类编号
     */
    private Integer typeId;

    private String typeName;

    /**
     * 上架状态，0：未上架，1：已上架
     */
    @NotNull(message = CommonResponse.PARAMETER_ERROR)
    private Byte putaway;

    /**
     * skus
     */
    private String skus;

    private List<GoodsSkuVo> goodsSkuVos;

    /**
     * 产地
     */
    private String origin;

    /**
     * 图片
     */
    private String image;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    public GoodsVo() {
    }

    public GoodsVo(Integer storeId, Integer typeId) {
        this.storeId = storeId;
        this.typeId = typeId;
    }

    public GoodsVo(@NotNull(message = CommonResponse.PARAMETER_ERROR) Integer storeId, String id) {
        this.storeId = storeId;
        this.id = id;
    }

    public GoodsVo(@NotNull(message = CommonResponse.PARAMETER_ERROR) Integer storeId, String id, @NotBlank(message = CommonResponse.PARAMETER_ERROR) String barCode, Integer typeId, @NotBlank(message = CommonResponse.PARAMETER_ERROR) Byte putaway) {
        this.storeId = storeId;
        this.id = id;
        this.barCode = barCode;
        this.typeId = typeId;
        this.putaway = putaway;
    }

    public GoodsVo(@NotNull(message = CommonResponse.PARAMETER_ERROR) Integer storeId, String id, @NotBlank(message = CommonResponse.PARAMETER_ERROR) String barCode, Integer typeId) {
        this.storeId = storeId;
        this.id = id;
        this.barCode = barCode;
        this.typeId = typeId;
    }

    public Integer getStoreId() {
        return storeId;
    }

    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }

    public List<GoodsLabel> getGoodsLabels() {
        return goodsLabels;
    }

    public void setGoodsLabels(List<GoodsLabel> goodsLabels) {
        this.goodsLabels = goodsLabels;
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

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public Byte getPutaway() {
        return putaway;
    }

    public void setPutaway(Byte putaway) {
        this.putaway = putaway;
    }

    public String getSkus() {
        return skus;
    }

    public void setSkus(String skus) {
        this.skus = skus;
    }

    public List<GoodsSkuVo> getGoodsSkuVos() {
        return goodsSkuVos;
    }

    public void setGoodsSkuVos(List<GoodsSkuVo> goodsSkuVos) {
        this.goodsSkuVos = goodsSkuVos;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "GoodsVo{" +
                "storeId=" + storeId +
                ", goodsLabels=" + goodsLabels +
                ", id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", barCode='" + barCode + '\'' +
                ", typeId=" + typeId +
                ", typeName='" + typeName + '\'' +
                ", putaway=" + putaway +
                ", skus='" + skus + '\'' +
                ", goodsSkuVos=" + goodsSkuVos +
                ", origin='" + origin + '\'' +
                ", image='" + image + '\'' +
                ", remark='" + remark + '\'' +
                ", createTime=" + createTime +
                '}';
    }
}