package com.yeta.pps.po;

public class WarehouseUser {

    /**
     * 仓库用户id
     */
    private Integer id;

    /**
     * 仓库id
     */
    private Integer warehouseId;

    /**
     * 用户id
     */
    private String userId;

    public WarehouseUser() {
    }

    /**
     * 获取仓库用户id
     *
     * @return id - 仓库用户id
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置仓库用户id
     *
     * @param id 仓库用户id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取仓库id
     *
     * @return warehouse_id - 仓库id
     */
    public Integer getWarehouseId() {
        return warehouseId;
    }

    /**
     * 设置仓库id
     *
     * @param warehouseId 仓库id
     */
    public void setWarehouseId(Integer warehouseId) {
        this.warehouseId = warehouseId;
    }

    /**
     * 获取用户id
     *
     * @return user_id - 用户id
     */
    public String getUserId() {
        return userId;
    }

    /**
     * 设置用户id
     *
     * @param userId 用户id
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "WarehouseUser{" +
                "id=" + id +
                ", warehouseId=" + warehouseId +
                ", userId='" + userId + '\'' +
                '}';
    }
}