package com.spring.springfamily.bean;

import lombok.Data;

/**
 * @ClassName User
 * @Description
 * @Author guopy
 * @Date 2021/9/27 17:26
 */
@Data
public class User {

    private Integer id;
    private String name;
    private String address;
    private Integer age;
    private Integer sex;

    public User() {

    }

    public User(String name, Integer age) {
        this.name = name;
        this.age = age;
    }
}
