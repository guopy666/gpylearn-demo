package com.gpy.springalltong.mongodemo;

import com.gpy.springalltong.mongodemo.converter.MoneyReadConverter;
import com.gpy.springalltong.testmodel.Book;
import com.gpy.springalltong.mongodemo.repository.BookRepository;
import com.mongodb.client.result.UpdateResult;
import lombok.extern.slf4j.Slf4j;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @ClassName MongoDemoApplication
 * @Description
 * @Author guopy
 * @Date 2023/3/9 17:55
 */
@SpringBootApplication
@Slf4j
public class MongoDemoApplication implements ApplicationRunner {

    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private BookRepository bookRepository;

    public static void main(String[] args) {
        SpringApplication.run(MongoDemoApplication.class);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        // 使用 mongoTemplate
        testTemplate();
        log.info("=========开始rep操作=========");
        // 使用 mongoRepository
        testRepository();
    }

    private void testRepository() throws InterruptedException {
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

        bookRepository.findAll(Sort.by("price")).forEach(b -> log.info("sort book by price -->{}", b));

        log.info("---------------------");
        Thread.sleep(2000);

        book1.setPrice(Money.ofMajor(CurrencyUnit.of("CNY"), 999));
        book1.setUpdateTime(new Date());
        Book save = bookRepository.save(book1);
        bookRepository.findAll(Sort.by("price")).forEach(b -> log.info("sort book by price -->{}", b));

        bookRepository.deleteAll();

    }

    public void testTemplate() throws Exception{
        Book book = Book.builder().name("Java 从入门到放弃")
                .price(Money.of(CurrencyUnit.of("CNY"), 59.0))
                .addTime(new Date())
                .updateTime(new Date()).build();
        Book save = mongoTemplate.save(book);
        log.info("Book--> {}", save);

        List<Book> books = mongoTemplate.find(Query.query(Criteria.where("name").is("Java 从入门到放弃")), Book.class);
        log.info("find book info -->{},,, size:{}", books, books.size());
        books.forEach(b -> log.info("book -->{}", b));

        // 为了查看更新时间
        Thread.sleep(1000);

        UpdateResult result = mongoTemplate.updateFirst(Query.query(Criteria.where("name").is("Java 从入门到放弃")), new Update().
                set("price", Money.ofMajor(CurrencyUnit.of("CNY"), 108)).currentDate("updateTime"), Book.class);
        log.info("update result -->{}", result.getModifiedCount());
        Book byId = mongoTemplate.findById(save.getId(), Book.class);
        log.info("update result :{}", byId);
        mongoTemplate.remove(byId);
    }



    @Bean
    public MongoCustomConversions mongoCustomConversions(){
        return new MongoCustomConversions(Arrays.asList(new MoneyReadConverter()));
    }
}
