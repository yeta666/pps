package com.yeta.pps.vo;

import com.yeta.pps.util.CommonResponse;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;

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
    private List<Integer> roleIds;

    /**
     * 功能id
     */
    private Set<Integer> ids;

    public FunctionMapVo() {
    }

    public FunctionMapVo(@NotNull(message = CommonResponse.MESSAGE3) Integer storeId, @NotNull(message = CommonResponse.MESSAGE3) List<Integer> roleIds) {
        this.storeId = storeId;
        this.roleIds = roleIds;
    }

    public Integer getStoreId() {
        return storeId;
    }

    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }

    public List<Integer> getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(List<Integer> roleIds) {
        this.roleIds = roleIds;
    }

    public Set<Integer> getIds() {
        return ids;
    }

    public void setIds(Set<Integer> ids) {
        this.ids = ids;
    }

    @Override
    public String toString() {
        return "FunctionMapVo{" +
                "storeId=" + storeId +
                ", roleIds=" + roleIds +
                ", ids=" + ids +
                '}';
    }
}
