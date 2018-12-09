package com.yeta.pps.vo;

public class GoodsGoodsLabelVo {

    /**
     * 店铺id
     */
    private Integer storeId;

    /**
     * 商品/商品标签关系id
     */
    private Integer id;

    /**
     * 商品货号
     */
    private String goodsId;

    /**
     * 商品标签id
     */
    private Integer labelId;

    public GoodsGoodsLabelVo() {
    }

    public GoodsGoodsLabelVo(Integer storeId, String goodsId) {
        this.storeId = storeId;
        this.goodsId = goodsId;
    }

    public GoodsGoodsLabelVo(Integer storeId, Integer labelId) {
        this.storeId = storeId;
        this.labelId = labelId;
    }

    public GoodsGoodsLabelVo(Integer storeId, String goodsId, Integer labelId) {
        this.storeId = storeId;
        this.goodsId = goodsId;
        this.labelId = labelId;
    }

    public Integer getStoreId() {
        return storeId;
    }

    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }

    /**
     * 获取商品/商品标签关系id
     *
     * @return id - 商品/商品标签关系id
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置商品/商品标签关系id
     *
     * @param id 商品/商品标签关系id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取商品货号
     *
     * @return goods_id - 商品货号
     */
    public String getGoodsId() {
        return goodsId;
    }

    /**
     * 设置商品货号
     *
     * @param goodsId 商品货号
     */
    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    /**
     * 获取商品标签id
     *
     * @return label_id - 商品标签id
     */
    public Integer getLabelId() {
        return labelId;
    }

    /**
     * 设置商品标签id
     *
     * @param labelId 商品标签id
     */
    public void setLabelId(Integer labelId) {
        this.labelId = labelId;
    }

    @Override
    public String toString() {
        return "GoodsGoodsLabelVo{" +
                "storeId=" + storeId +
                ", id=" + id +
                ", goodsId='" + goodsId + '\'' +
                ", labelId=" + labelId +
                '}';
    }
}