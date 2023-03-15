package com.gpy.springalltong.mybatisdemo;

import com.gpy.springalltong.mybatisdemo.mapper.GoodsMapper;
import com.gpy.springalltong.testmodel.Goods;
import lombok.extern.slf4j.Slf4j;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @ClassName MybatisDemoApplication
 * @Description
 * @Author guopy
 * @Date 2023/3/8 16:07
 */
@SpringBootApplication
@Slf4j
@MapperScan("com.gpy.springalltong.mybatisdemo.mapper")
public class MybatisDemoApplication implements CommandLineRunner {

    @Autowired
    private GoodsMapper goodsMapper;

    public static void main(String[] args) {
        SpringApplication.run(MybatisDemoApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        testMybatis();
    }

    private void testMybatis() throws InterruptedException {
        Goods goods = Goods.builder().name("集成灶").price(Money.of(CurrencyUnit.of("CNY"), 6000.0)).build();
        int save = goodsMapper.save(goods);
        log.info("Save {} goodsinfo:{}", save, goods);
        goods = Goods.builder().name("热水器").price(Money.of(CurrencyUnit.of("CNY"), 1000.0)).build();
        int c = goodsMapper.save(goods);
        log.info("Save second goods{}, goods:{}", c, goods);
        Goods goods1 = goodsMapper.findById(goods.getId());
        log.info("Goods Info-->{}", goods1);

    }
}
