package com.yeta.pps.po;

public class Function {

    /**
     * 功能id
     */
    private Integer id;

    /**
     * 功能名
     */
    private String name;

    /**
     * 功能级别
     */
    private Byte level;

    /**
     * 父功能id
     */
    private Integer parnetId;

    public Function() {
    }

    public Function(Integer id, String name, Byte level, Integer parnetId) {
        this.id = id;
        this.name = name;
        this.level = level;
        this.parnetId = parnetId;
    }

    /**
     * 获取功能id
     *
     * @return id - 功能id
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置功能id
     *
     * @param id 功能id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取功能名
     *
     * @return name - 功能名
     */
    public String getName() {
        return name;
    }

    /**
     * 设置功能名
     *
     * @param name 功能名
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取功能级别
     *
     * @return level - 功能级别
     */
    public Byte getLevel() {
        return level;
    }

    /**
     * 设置功能级别
     *
     * @param level 功能级别
     */
    public void setLevel(Byte level) {
        this.level = level;
    }

    /**
     * 获取父功能id
     *
     * @return parnet_id - 父功能id
     */
    public Integer getParnetId() {
        return parnetId;
    }

    /**
     * 设置父功能id
     *
     * @param parnetId 父功能id
     */
    public void setParnetId(Integer parnetId) {
        this.parnetId = parnetId;
    }

    @Override
    public String toString() {
        return "Function{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", level=" + level +
                ", parnetId=" + parnetId +
                '}';
    }
}