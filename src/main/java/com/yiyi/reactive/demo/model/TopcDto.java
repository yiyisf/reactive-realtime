package com.yiyi.reactive.demo.model;

import java.io.Serializable;

public class TopcDto implements Serializable {
    String name;
    String msg;

    public TopcDto() {
    }

    public TopcDto(String name, String msg) {
        this.name = name;
        this.msg = msg;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
