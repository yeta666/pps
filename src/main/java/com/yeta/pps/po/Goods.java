package com.yeta.pps.po;

import java.util.Date;

public class Goods {

    /**
     * 商品货号
     */
    private String id;

    /**
     * 商品名
     */
    private String name;

    /**
     * 条码
     */
    private String barCode;

    /**
     * 分类id
     */
    private Integer typeId;

    /**
     * 上架状态，0：未上架，1：已上架
     */
    private Byte putaway;

    /**
     * 是否提成
     */
    private Byte pushMoneyStatus;

    /**
     * 规格
     */
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
    private Date createTime;

    public Goods() {
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

    public Byte getPutaway() {
        return putaway;
    }

    public void setPutaway(Byte putaway) {
        this.putaway = putaway;
    }

    public Byte getPushMoneyStatus() {
        return pushMoneyStatus;
    }

    public void setPushMoneyStatus(Byte pushMoneyStatus) {
        this.pushMoneyStatus = pushMoneyStatus;
    }

    public String getSkus() {
        return skus;
    }

    public void setSkus(String skus) {
        this.skus = skus;
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
        return "Goods{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", barCode='" + barCode + '\'' +
                ", typeId=" + typeId +
                ", putaway=" + putaway +
                ", pushMoneyStatus=" + pushMoneyStatus +
                ", skus='" + skus + '\'' +
                ", origin='" + origin + '\'' +
                ", image='" + image + '\'' +
                ", remark='" + remark + '\'' +
                ", createTime=" + createTime +
                '}';
    }
}