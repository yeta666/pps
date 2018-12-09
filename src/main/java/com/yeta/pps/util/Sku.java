package com.yeta.pps.util;

/**
 * @author YETA
 * @date 2018/12/09/2:00
 */
public class Sku {

    private String key;

    private String value;

    public Sku() {
    }

    public Sku(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Sku{" +
                "key='" + key + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
