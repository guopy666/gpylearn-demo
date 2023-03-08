package com.gpy.springalltong.springbucks.entity;

import com.gpy.springalltong.springbucks.constant.OrderState;
import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.List;

/**
 * @ClassName CoffeeOrder
 * @Description
 * @Author guopy
 * @Date 2022/12/29 11:02
 */
@Data
@Entity
@Table(name = "T_ORDER")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Accessors(chain = true)
@ToString(callSuper = true)
public class CoffeeOrder extends BaseEntity{
    private String customer;
    @ManyToMany
    @JoinTable(name = "T_ORDER_COFFEE")
    private List<Coffee> items;
    @Column(nullable = false)
    private OrderState state;

}
