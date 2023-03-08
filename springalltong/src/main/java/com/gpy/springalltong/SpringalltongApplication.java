package com.gpy.springalltong;

import com.gpy.springalltong.springbucks.constant.OrderState;
import com.gpy.springalltong.springbucks.entity.Coffee;
import com.gpy.springalltong.springbucks.entity.CoffeeOrder;
import com.gpy.springalltong.springbucks.respository.CoffeeOrderRespository;
import com.gpy.springalltong.springbucks.respository.CoffeeRespository;
import lombok.extern.slf4j.Slf4j;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.annotation.Order;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Collections;
import java.util.Currency;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootApplication
@EnableJpaRepositories
@Slf4j
@Order(value = 1000)
public class SpringalltongApplication implements ApplicationRunner {

    @Autowired
    private CoffeeOrderRespository orderRespository;
    @Autowired
    private CoffeeRespository coffeeRespository;

    public static void main(String[] args) {
        SpringApplication.run(SpringalltongApplication.class, args);
    }

    @Override
    @Transactional(value = "transactionManager")
    public void run(ApplicationArguments args) throws Exception {
        initOrders();
        findOrder();
    }

    private void initOrders(){
        Coffee latte = Coffee.builder().name("latte")
                .price(Money.of(CurrencyUnit.of("CNY"), 30.0))
                .build();
        coffeeRespository.save(latte);
        log.info("Coffee Latte:{}", latte);

        Coffee espresso = Coffee.builder().name("espresso")
                .price(Money.of(CurrencyUnit.of("CNY"), 20.0))
                .build();
        coffeeRespository.save(espresso);
        log.info("Coffee Espresso:{}", espresso);

        CoffeeOrder order = CoffeeOrder.builder().customer("Li Lei")
                .items(Collections.singletonList(espresso))
                .state(OrderState.INIT)
                .build();
        orderRespository.save(order);
        log.info("order info 1 :{}", order);


        order = CoffeeOrder.builder().customer("Li Lei")
                .items(Arrays.asList(espresso, latte))
                .state(OrderState.INIT)
                .build();
        orderRespository.save(order);
        log.info("order info 2 :{}", order);
    }

    private void findOrder(){
        coffeeRespository.findAll(Sort.by(Sort.Direction.DESC, "id"))
                .forEach(o -> log.info("Loading :{}", o));

        List<CoffeeOrder> list = orderRespository.findTop3ByOrderByUpdateTimeDescIdAsc();
        log.info("find top 3 :{}", getJoinedOrderId(list));

        List<CoffeeOrder> list2 = orderRespository.findByCustomerOrderById("Li Lei");

        list2.forEach(o -> {
            log.info("order2 info :{}", o);
            o.getItems().forEach(i -> log.info("item :{}", i));
        });

        List<CoffeeOrder> latte = orderRespository.findByItems_Name("latte");
        log.info("find By items Name :{}", getJoinedOrderId(latte));
    }


    private String getJoinedOrderId(List<CoffeeOrder> list){
        return list.stream().map(o -> o.getId().toString()).collect(Collectors.joining(","));
    }


}
