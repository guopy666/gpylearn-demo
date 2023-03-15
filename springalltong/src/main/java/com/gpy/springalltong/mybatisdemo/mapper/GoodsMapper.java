package com.gpy.springalltong.mybatisdemo.mapper;

import com.gpy.springalltong.testmodel.Goods;
import org.apache.ibatis.annotations.*;

@Mapper
public interface GoodsMapper {

    @Insert("insert into t_goods (name, price, addTime, updateTime) values (#{name}, #{price}, now(), now())")
    // 增加options注解，才能在高版本的mybatis中自动填充id
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int save(Goods goods);

    @Select("select * from t_goods where id = #{id}")
    @Results({
            @Result(id = true, column = "id", property = "id"),
            @Result(column = "addTime", property = "addTime")
    })
    Goods findById(Long id);
}
