package com.gpy.springalltong.springbucks.respository;

import com.gpy.springalltong.springbucks.entity.CoffeeOrder;

import java.util.List;

/**
 * @ClassName CoffeeOrderRespository
 * @Description
 * @Author guopy
 * @Date 2022/12/29 15:10
 */
public interface CoffeeOrderRespository extends BaseRepository<CoffeeOrder, Long> {
    List<CoffeeOrder> findByCustomerOrderById(String customer);
    List<CoffeeOrder> findByItems_Name(String name);
}
