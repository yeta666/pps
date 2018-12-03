package com.yeta.pps.po;

import javax.persistence.*;

public class IncomeExpenses {
    /**
     * 科目编号
     */
    @Id
    private String id;

    /**
     * 科目名称
     */
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
    private Byte debitCredit;

    /**
     * 收支，1：收入，2：支出
     */
    private Byte type;

    /**
     * 获取科目编号
     *
     * @return id - 科目编号
     */
    public String getId() {
        return id;
    }

    /**
     * 设置科目编号
     *
     * @param id 科目编号
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 获取科目名称
     *
     * @return name - 科目名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置科目名称
     *
     * @param name 科目名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取核算项，1：供应商，2：客户，3：往来单位，4：职员，5：部门
     *
     * @return check_item - 核算项，1：供应商，2：客户，3：往来单位，4：职员，5：部门
     */
    public Byte getCheckItem() {
        return checkItem;
    }

    /**
     * 设置核算项，1：供应商，2：客户，3：往来单位，4：职员，5：部门
     *
     * @param checkItem 核算项，1：供应商，2：客户，3：往来单位，4：职员，5：部门
     */
    public void setCheckItem(Byte checkItem) {
        this.checkItem = checkItem;
    }

    /**
     * 获取借贷，1：贷，2：借
     *
     * @return debit_credit - 借贷，1：贷，2：借
     */
    public Byte getDebitCredit() {
        return debitCredit;
    }

    /**
     * 设置借贷，1：贷，2：借
     *
     * @param debitCredit 借贷，1：贷，2：借
     */
    public void setDebitCredit(Byte debitCredit) {
        this.debitCredit = debitCredit;
    }

    /**
     * 获取收支，1：收入，2：支出
     *
     * @return type - 收支，1：收入，2：支出
     */
    public Byte getType() {
        return type;
    }

    /**
     * 设置收支，1：收入，2：支出
     *
     * @param type 收支，1：收入，2：支出
     */
    public void setType(Byte type) {
        this.type = type;
    }
}