package com.yeta.pps.po;

import com.yeta.pps.util.CommonResponse;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author YETA
 * @date 2019/01/21/13:16
 */
public class AccountingSubject {

    /**
     * 店铺编号
     */
    @NotNull(message = CommonResponse.PARAMETER_ERROR)
    private Integer storeId;

    /**
     * 会计科目编号
     */
    @NotBlank(message = CommonResponse.PARAMETER_ERROR)
    private String id;

    /**
     * 会计科目名称
     */
    @NotBlank(message = CommonResponse.PARAMETER_ERROR)
    private String name;

    /**
     * 核算项
     */
    private Byte checkItem;

    /**
     * 借贷
     */
    private Byte debitCredit;

    /**
     * 固定资产期初
     */
    private Double fixedAssetsOpening;

    /**
     * 资产负债期初
     */
    private Double assetsLiabilitiesOpening;

    public AccountingSubject() {
    }

    public AccountingSubject(Integer storeId) {
        this.storeId = storeId;
    }

    public AccountingSubject(Integer storeId, String id) {
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

    public Double getFixedAssetsOpening() {
        return fixedAssetsOpening;
    }

    public void setFixedAssetsOpening(Double fixedAssetsOpening) {
        this.fixedAssetsOpening = fixedAssetsOpening;
    }

    public Double getAssetsLiabilitiesOpening() {
        return assetsLiabilitiesOpening;
    }

    public void setAssetsLiabilitiesOpening(Double assetsLiabilitiesOpening) {
        this.assetsLiabilitiesOpening = assetsLiabilitiesOpening;
    }

    @Override
    public String toString() {
        return "AccountingSubject{" +
                "storeId=" + storeId +
                ", id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", checkItem=" + checkItem +
                ", debitCredit=" + debitCredit +
                ", fixedAssetsOpening=" + fixedAssetsOpening +
                ", assetsLiabilitiesOpening=" + assetsLiabilitiesOpening +
                '}';
    }
}
