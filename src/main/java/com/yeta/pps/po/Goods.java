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
        return "Goods{" +
                "id='" + id + '\'' +
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