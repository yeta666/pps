package com.yeta.pps.po;

public class ClientClientLevel {

    /**
     * 客户客户级别id
     */
    private Integer id;

    /**
     * 客户编号
     */
    private String clientId;

    /**
     * 客户级别id
     */
    private Integer levelId;

    public ClientClientLevel() {
    }

    public ClientClientLevel(String clientId) {
        this.clientId = clientId;
    }

    public ClientClientLevel(Integer levelId) {
        this.levelId = levelId;
    }

    public ClientClientLevel(String clientId, Integer levelId) {
        this.clientId = clientId;
        this.levelId = levelId;
    }

    /**
     * 获取客户客户级别id
     *
     * @return id - 客户客户级别id
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置客户客户级别id
     *
     * @param id 客户客户级别id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取客户编号
     *
     * @return client_id - 客户编号
     */
    public String getClientId() {
        return clientId;
    }

    /**
     * 设置客户编号
     *
     * @param clientId 客户编号
     */
    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public Integer getLevelId() {
        return levelId;
    }

    public void setLevelId(Integer levelId) {
        this.levelId = levelId;
    }

    @Override
    public String toString() {
        return "ClientClientLevel{" +
                "id=" + id +
                ", clientId='" + clientId + '\'' +
                ", levelId=" + levelId +
                '}';
    }
}