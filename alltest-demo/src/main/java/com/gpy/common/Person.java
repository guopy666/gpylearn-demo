package com.gpy.common;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @ClassName Person
 * @Description
 * @Author guopy
 * @Date 2021/6/10 9:51
 */
@Data
@Accessors(chain = true)
public class Person implements Serializable {
    private Integer id;
    private String name;
    private Integer sex;
    private Integer age;
}
