package com.gpy.springalltong.springbucks.entity;

import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.annotations.Type;
import org.joda.money.Money;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @ClassName Coffee
 * @Description
 * @Author guopy
 * @Date 2022/12/28 17:18
 */
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString(callSuper = true)
@Accessors(chain = true)
@Table(name = "T_COFFEE")
public class Coffee extends BaseEntity {
    private String name;
    @Column
    @Type(type = "org.jadira.usertype.moneyandcurrency.joda.PersistentMoneyAmount",
    parameters = {@org.hibernate.annotations.Parameter(name = "currencyCode", value = "CNY")})
    private Money price;
}
