package com.example.demo.domain;

import java.io.Serializable;

/**
 * @author youliangzhang
 * @date 2018/5/25  上午12:19
 **/
public class User implements Serializable {

    private int id;

    private String name;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
