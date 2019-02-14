package com.yeta.pps.util;

import java.util.Set;

/**
 * @author YETA
 * @date 2018/12/09/2:00
 */
public class Skus {

    private String key;

    private Set<String> value;

    public Skus() {
    }

    public Skus(String key, Set<String> value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Set<String> getValue() {
        return value;
    }

    public void setValue(Set<String> value) {
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
