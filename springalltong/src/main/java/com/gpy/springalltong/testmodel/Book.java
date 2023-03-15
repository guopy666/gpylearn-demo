package com.gpy.springalltong.testmodel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.joda.money.Money;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 * @ClassName Book
 * @Description
 * @Author guopy
 * @Date 2023/3/9 17:45
 */
@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Book {
    @Id
    private String id;
    private String name;
    private Money price;
    private Date addTime;
    private Date updateTime;
}
