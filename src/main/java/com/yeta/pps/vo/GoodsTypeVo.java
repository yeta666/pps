package com.yeta.pps.vo;

import com.yeta.pps.util.CommonResponse;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

public class GoodsTypeVo {

    /**
     * 店铺id
     */
    @NotNull(message = CommonResponse.MESSAGE3)
    private Integer storeId;

    private List<GoodsPropertyKeyVo> goodsPropertyKeyVos;

    private List<GoodsVo> goodsVos;

    /**
     * 商品分类id
     */
    private Integer id;

    /**
     * 商品分类名
     */
    @NotBlank(message = CommonResponse.MESSAGE3)
    private String name;

    public GoodsTypeVo() {
    }

    public GoodsTypeVo(@NotNull(message = CommonResponse.MESSAGE3) Integer storeId) {
        this.storeId = storeId;
    }

    public GoodsTypeVo(@NotNull(message = CommonResponse.MESSAGE3) Integer storeId, Integer id) {
        this.storeId = storeId;
        this.id = id;
    }

    public GoodsTypeVo(@NotNull(message = CommonResponse.MESSAGE3) Integer storeId, @NotBlank(message = CommonResponse.MESSAGE3) String name) {
        this.storeId = storeId;
        this.name = name;
    }

    public Integer getStoreId() {
        return storeId;
    }

    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }

    public List<GoodsPropertyKeyVo> getGoodsPropertyKeyVos() {
        return goodsPropertyKeyVos;
    }

    public void setGoodsPropertyKeyVos(List<GoodsPropertyKeyVo> goodsPropertyKeyVos) {
        this.goodsPropertyKeyVos = goodsPropertyKeyVos;
    }

    public List<GoodsVo> getGoodsVos() {
        return goodsVos;
    }

    public void setGoodsVos(List<GoodsVo> goodsVos) {
        this.goodsVos = goodsVos;
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

    @Override
    public String toString() {
        return "GoodsTypeVo{" +
                "storeId=" + storeId +
                ", goodsPropertyKeyVos=" + goodsPropertyKeyVos +
                ", goodsVos=" + goodsVos +
                ", id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}