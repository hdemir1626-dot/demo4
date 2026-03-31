package com.hasan.demo4.services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import com.hasan.demo4.entities.Book;
import com.hasan.demo4.exception.ResourceNotFoundException;
import com.hasan.demo4.repositories.BookRepository;

@Service
public class BookService {

    /* Kötü pratik , doğru olan @sevice annotasyonu kullanıp constructor ile injection yapmaktır
     @Autowired
    private  BookRepository repository;
    */
    private final BookRepository repository;

    public BookService(BookRepository repository) {
        this.repository = repository;
    }

    public List<Book> getAll() {
        return repository.findAll();
    }

    public Book getById(Long id) {
        return repository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Kitap bulunamadı: " + id));
    }

    public Book create(Book book) {
        book.setId(null);
        return repository.save(book);
    }

    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Kitap bulunamadı: " + id);
        }
        repository.deleteById(id);
    }

    public List<Book> getByAuthor(@RequestParam String name) {
        return repository.findByAuthorIgnoreCase(name); 
    }
}