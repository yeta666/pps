package com.yeta.pps.util;

import java.util.List;

/**
 * @author YETA
 * @date 2018/12/09/2:00
 */
public class Skus {

    private String key;

    private List<String> value;

    public Skus() {
    }

    public Skus(String key, List<String> value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public List<String> getValue() {
        return value;
    }

    public void setValue(List<String> value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Skus{" +
                "key='" + key + '\'' +
                ", value=" + value +
                '}';
    }
}
