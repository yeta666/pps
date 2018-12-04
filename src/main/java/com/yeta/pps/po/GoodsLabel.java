package com.yeta.pps.po;

public class GoodsLabel {

    /**
     * 商品标签id
     */
    private Integer id;

    /**
     * 商品便签名
     */
    private String name;

    public GoodsLabel() {
    }

    /**
     * 获取商品标签id
     *
     * @return id - 商品标签id
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置商品标签id
     *
     * @param id 商品标签id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取商品便签名
     *
     * @return name - 商品便签名
     */
    public String getName() {
        return name;
    }

    /**
     * 设置商品便签名
     *
     * @param name 商品便签名
     */
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "GoodsLabel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}