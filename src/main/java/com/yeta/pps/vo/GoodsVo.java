package com.yeta.pps.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yeta.pps.po.GoodsLabel;
import com.yeta.pps.util.CommonResponse;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

public class GoodsVo {

    /**
     * 店铺id
     */
    @NotNull(message = CommonResponse.MESSAGE3)
    private Integer storeId;

    private String typeName;

    @NotNull(message = CommonResponse.MESSAGE3)
    private List<GoodsLabel> goodsLabels;

    private List<GoodsSkuVo> goodsSkuVos;

    /**
     * 商品货号
     */
    private String id;

    /**
     * 商品名
     */
    @NotBlank(message = CommonResponse.MESSAGE3)
    private String name;

    /**
     * 条码
     */
    @NotBlank(message = CommonResponse.MESSAGE3)
    private String barCode;

    /**
     * 分类id
     */
    private Integer typeId;

    /**
     * 上架状态，0：未上架，1：已上架
     */
    @NotNull(message = CommonResponse.MESSAGE3)
    private Byte putaway;

    private String skus;

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

    public GoodsVo(@NotNull(message = CommonResponse.MESSAGE3) Integer storeId, String id) {
        this.storeId = storeId;
        this.id = id;
    }

    public GoodsVo(@NotNull(message = CommonResponse.MESSAGE3) Integer storeId, String id, @NotBlank(message = CommonResponse.MESSAGE3) String barCode, Integer typeId, @NotBlank(message = CommonResponse.MESSAGE3) Byte putaway) {
        this.storeId = storeId;
        this.id = id;
        this.barCode = barCode;
        this.typeId = typeId;
        this.putaway = putaway;
    }

    public Integer getStoreId() {
        return storeId;
    }

    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public List<GoodsLabel> getGoodsLabels() {
        return goodsLabels;
    }

    public void setGoodsLabels(List<GoodsLabel> goodsLabels) {
        this.goodsLabels = goodsLabels;
    }

    public List<GoodsSkuVo> getGoodsSkuVos() {
        return goodsSkuVos;
    }

    public void setGoodsSkuVos(List<GoodsSkuVo> goodsSkuVos) {
        this.goodsSkuVos = goodsSkuVos;
    }

    /**
     * 获取商品货号
     *
     * @return id - 商品货号
     */
    public String getId() {
        return id;
    }

    /**
     * 设置商品货号
     *
     * @param id 商品货号
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 获取商品名
     *
     * @return name - 商品名
     */
    public String getName() {
        return name;
    }

    /**
     * 设置商品名
     *
     * @param name 商品名
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取条码
     *
     * @return bar_code - 条码
     */
    public String getBarCode() {
        return barCode;
    }

    /**
     * 设置条码
     *
     * @param barCode 条码
     */
    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    /**
     * 获取分类id
     *
     * @return type_id - 分类id
     */
    public Integer getTypeId() {
        return typeId;
    }

    /**
     * 设置分类id
     *
     * @param typeId 分类id
     */
    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    /**
     * 获取上架状态，0：未上架，1：已上架
     *
     * @return putaway - 上架状态，0：未上架，1：已上架
     */
    public Byte getPutaway() {
        return putaway;
    }

    /**
     * 设置上架状态，0：未上架，1：已上架
     *
     * @param putaway 上架状态，0：未上架，1：已上架
     */
    public void setPutaway(Byte putaway) {
        this.putaway = putaway;
    }

    public String getSkus() {
        return skus;
    }

    public void setSkus(String skus) {
        this.skus = skus;
    }

    /**
     * 获取产地
     *
     * @return origin - 产地
     */
    public String getOrigin() {
        return origin;
    }

    /**
     * 设置产地
     *
     * @param origin 产地
     */
    public void setOrigin(String origin) {
        this.origin = origin;
    }

    /**
     * 获取图片
     *
     * @return image - 图片
     */
    public String getImage() {
        return image;
    }

    /**
     * 设置图片
     *
     * @param image 图片
     */
    public void setImage(String image) {
        this.image = image;
    }

    /**
     * 获取备注
     *
     * @return remark - 备注
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 设置备注
     *
     * @param remark 备注
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * 获取创建时间
     *
     * @return create_time - 创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置创建时间
     *
     * @param createTime 创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "GoodsVo{" +
                "storeId=" + storeId +
                ", typeName='" + typeName + '\'' +
                ", goodsLabels=" + goodsLabels +
                ", goodsSkuVos=" + goodsSkuVos +
                ", id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", barCode='" + barCode + '\'' +
                ", typeId=" + typeId +
                ", putaway=" + putaway +
                ", skus='" + skus + '\'' +
                ", origin='" + origin + '\'' +
                ", image='" + image + '\'' +
                ", remark='" + remark + '\'' +
                ", createTime=" + createTime +
                '}';
    }
}