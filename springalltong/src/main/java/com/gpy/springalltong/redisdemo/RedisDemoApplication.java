package com.gpy.springalltong.redisdemo;

import com.gpy.springalltong.mongodemo.converter.MoneyReadConverter;
import com.gpy.springalltong.mongodemo.repository.BookRepository;
import com.gpy.springalltong.redisdemo.config.JedisPoolCofig;
import com.gpy.springalltong.redisdemo.repository.RedisBookRepository;
import com.gpy.springalltong.testmodel.Book;
import lombok.extern.slf4j.Slf4j;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @ClassName RedisDemoApplication
 * @Description
 * @Author guopy
 * @Date 2023/3/10 10:14
 */
@SpringBootApplication(scanBasePackages = {"com.gpy.springalltong.mongodemo.repository"})
@Slf4j
public class RedisDemoApplication implements ApplicationRunner {

    @Autowired
    private JedisPoolCofig jedisPoolCofig;

    @Autowired
    private JedisPool jedisPool;

    @Resource
    private RedisBookRepository bookRepository;

    public static void main(String[] args) {
        SpringApplication.run(RedisDemoApplication.class);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        testRedis();
    }

    @Bean
    @ConfigurationProperties("redis")
    public JedisPoolCofig jedisPoolCofig(){
        return new JedisPoolCofig();
    }

    @Bean(destroyMethod = "close")
    public JedisPool jedisPool(@Value("${redis.host}") String host){
        return new JedisPool(jedisPoolCofig(), host);
    }

    private void testRedis(){
        Book book1 = Book.builder().name("Java 从入门到放弃")
                .price(Money.of(CurrencyUnit.of("CNY"), 59.0))
                .addTime(new Date())
                .updateTime(new Date()).build();

        Book book2 = Book.builder().name("Adobe PS 从入门到放弃")
                .price(Money.of(CurrencyUnit.of("CNY"), 69.0))
                .addTime(new Date())
                .updateTime(new Date()).build();

        Book book = Book.builder().name("Linux 从入门到入狱")
                .price(Money.of(CurrencyUnit.of("CNY"), 99.0))
                .addTime(new Date())
                .updateTime(new Date()).build();

        List<Book> books = bookRepository.insert(Arrays.asList(book, book1, book2));
        log.info("insert result -->{}", books);

        try (Jedis jedis = jedisPool.getResource()){
            log.info("jedis pool config info-->{}", jedisPoolCofig.toString());
            bookRepository.findAll().forEach(b ->
                    jedis.hset("bookByName",b.getName(), Long.toString(b.getPrice().getAmountMajorLong())));
            Map<String, String> bookByName = jedis.hgetAll("bookByName");
            log.info("book info from redis-->{}", bookByName);
            String price = jedis.hget("bookByName", "Java 从入门到放弃");
            log.info("get java price from redis -->{}", price);

        }
    }

    @Bean
    public MongoCustomConversions mongoCustomConversions(){
        return new MongoCustomConversions(Arrays.asList(new MoneyReadConverter()));
    }
}
