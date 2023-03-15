package com.gpy.springalltong.mongodemo.repository;

import com.gpy.springalltong.testmodel.Book;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface BookRepository extends MongoRepository<Book, String> {
    List<Book> findByName(String name);
}
