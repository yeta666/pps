package com.yeta.pps.util;

/**
 * @author YETA
 * @date 2019/01/25/16:51
 */
public class TemplateParam {

    private Integer code;

    private String name;

    public TemplateParam() {
    }

    public TemplateParam(Integer code) {
        this.code = code;
    }

    public TemplateParam(String name) {
        this.name = name;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "TemplateParam{" +
                "code=" + code +
                ", name='" + name + '\'' +
                '}';
    }
}
