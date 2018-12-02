package com.yeta.pps.util;

/**
 * @author YETA
 * @date 2018/12/02/19:04
 */
public class Title {

    private String name;

    private String key;

    public Title() {
    }

    public Title(String name, String key) {
        this.name = name;
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public String toString() {
        return "Title{" +
                "name='" + name + '\'' +
                ", key='" + key + '\'' +
                '}';
    }
}
