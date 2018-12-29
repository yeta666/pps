package com.yeta.pps.vo;

import com.yeta.pps.util.CommonResponse;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class IncomeExpensesVo {

    /**
     * 店铺id
     */
    @NotNull(message = CommonResponse.PARAMETER_ERROR)
    private Integer storeId;

    /**
     * 科目编号
     */
    @NotBlank(message = CommonResponse.PARAMETER_ERROR)
    private String id;

    /**
     * 科目名称
     */
    @NotBlank(message = CommonResponse.PARAMETER_ERROR)
    private String name;

    /**
     * 核算项，1：供应商，2：客户，3：往来单位，4：职员，5：部门
     */
    @Column(name = "check_item")
    private Byte checkItem;

    /**
     * 借贷，1：贷，2：借
     */
    @Column(name = "debit_credit")
    @NotNull(message = CommonResponse.PARAMETER_ERROR)
    private Byte debitCredit;

    /**
     * 收支，1：收入，2：支出
     */
    @NotNull(message = CommonResponse.PARAMETER_ERROR)
    private Byte type;

    public IncomeExpensesVo() {
    }

    public IncomeExpensesVo(@NotNull(message = CommonResponse.PARAMETER_ERROR) Integer storeId, @NotNull(message = CommonResponse.PARAMETER_ERROR) Byte type) {
        this.storeId = storeId;
        this.type = type;
    }

    public IncomeExpensesVo(@NotNull(message = CommonResponse.PARAMETER_ERROR) Integer storeId, @NotBlank(message = CommonResponse.PARAMETER_ERROR) String id) {
        this.storeId = storeId;
        this.id = id;
    }

    public Integer getStoreId() {
        return storeId;
    }

    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
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

    public Byte getCheckItem() {
        return checkItem;
    }

    public void setCheckItem(Byte checkItem) {
        this.checkItem = checkItem;
    }

    public Byte getDebitCredit() {
        return debitCredit;
    }

    public void setDebitCredit(Byte debitCredit) {
        this.debitCredit = debitCredit;
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "IncomeExpensesVo{" +
                "storeId=" + storeId +
                ", id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", checkItem=" + checkItem +
                ", debitCredit=" + debitCredit +
                ", type=" + type +
                '}';
    }
}