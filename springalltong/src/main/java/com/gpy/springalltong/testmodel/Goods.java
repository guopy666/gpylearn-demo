package com.gpy.springalltong.testmodel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.joda.money.Money;

import java.util.Date;

/**
 * @ClassName Goods
 * @Description
 * @Author guopy
 * @Date 2023/3/8 15:59
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Goods {
    private Long id;
    private String name;
    private Money price;
    private Date addTime;
    private Date updateTime;
}
