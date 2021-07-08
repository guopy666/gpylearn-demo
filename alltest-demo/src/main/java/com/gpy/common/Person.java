package com.gpy.common;

import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName Person
 * @Description
 * @Author guopy
 * @Date 2021/6/10 9:51
 */
@Data
public class Person implements Serializable {
    private Integer id;
    private String name;
    private Integer sex;
    private Integer age;
}
