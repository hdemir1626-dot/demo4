package com.hasan.demo4.repositories;

import com.hasan.demo4.entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findByAuthorIgnoreCase(String author);
    List<Book> findByTitleContaining(String keyword);
}