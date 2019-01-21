package com.yeta.pps.po;

public class IncomeExpenses {

    /**
     * 科目编号
     */
    private String id;

    /**
     * 科目名称
     */
    private String name;

    /**
     * 核算项
     */
    private Byte checkItem;

    /**
     * 借贷，1：贷，2：借
     */
    private Byte debitCredit;

    /**
     * 收支，1：收入，2：支出
     */
    private Byte type;

    public IncomeExpenses() {
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
        return "IncomeExpenses{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", checkItem=" + checkItem +
                ", debitCredit=" + debitCredit +
                ", type=" + type +
                '}';
    }
}