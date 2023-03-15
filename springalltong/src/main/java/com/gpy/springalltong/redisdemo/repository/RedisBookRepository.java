package com.gpy.springalltong.redisdemo.repository;

import com.gpy.springalltong.testmodel.Book;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface RedisBookRepository extends MongoRepository<Book, String> {
    List<Book> findByName(String name);
}
