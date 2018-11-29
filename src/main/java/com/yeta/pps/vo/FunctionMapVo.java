package com.yeta.pps.vo;

import com.yeta.pps.po.Function;
import com.yeta.pps.util.CommonResponse;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author YETA
 * @date 2018/11/29/21:02
 */
public class FunctionMapVo {

    /**
     * 店铺id
     */
    @NotNull(message = CommonResponse.MESSAGE3)
    private Integer storeId;

    /**
     * 角色id
     */
    @NotNull(message = CommonResponse.MESSAGE3)
    private Integer roleId;

    /**
     * 一级功能
     */
    private List<Function> level1;

    /**
     * 二级功能
     */
    private List<Function> level2;

    /**
     * 三级功能
     */
    private List<Function> level3;

    public FunctionMapVo() {
    }

    public FunctionMapVo(@NotNull(message = CommonResponse.MESSAGE3) Integer storeId, @NotNull(message = CommonResponse.MESSAGE3) Integer roleId) {
        this.storeId = storeId;
        this.roleId = roleId;
    }

    public FunctionMapVo(List<Function> level1, List<Function> level2, List<Function> level3) {
        this.level1 = level1;
        this.level2 = level2;
        this.level3 = level3;
    }

    public Integer getStoreId() {
        return storeId;
    }

    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public List<Function> getLevel1() {
        return level1;
    }

    public void setLevel1(List<Function> level1) {
        this.level1 = level1;
    }

    public List<Function> getLevel2() {
        return level2;
    }

    public void setLevel2(List<Function> level2) {
        this.level2 = level2;
    }

    public List<Function> getLevel3() {
        return level3;
    }

    public void setLevel3(List<Function> level3) {
        this.level3 = level3;
    }

    @Override
    public String toString() {
        return "FunctionMapVo{" +
                "storeId=" + storeId +
                ", roleId='" + roleId + '\'' +
                ", level1=" + level1 +
                ", level2=" + level2 +
                ", level3=" + level3 +
                '}';
    }
}
