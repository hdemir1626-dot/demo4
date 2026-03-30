package com.hasan.demo4;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.hasan.demo4.entities.Book;
import com.hasan.demo4.exception.ResourceNotFoundException;
import com.hasan.demo4.repositories.BookRepository;
import com.hasan.demo4.services.BookService;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @Mock
    private BookRepository repository;

    @InjectMocks
    private BookService bookService;

    private Book book;

    @BeforeEach
    void setUp() {
        book = new Book(1L, "Suç ve Ceza", "Dostoyevski");
    }

    @Test
    void getAll_shouldReturnAllBooks() {
        when(repository.findAll()).thenReturn(List.of(book));

        List<Book> result = bookService.getAll();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getTitle()).isEqualTo("Suç ve Ceza");
        verify(repository, times(1)).findAll();
    }

    @Test
    void getById_shouldReturnBook_whenExists() {
        when(repository.findById(1L)).thenReturn(Optional.of(book));

        Book result = bookService.getById(1L);

        assertThat(result.getAuthor()).isEqualTo("Dostoyevski");
    }

    @Test
    void getById_shouldThrowException_whenNotFound() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> bookService.getById(99L))
            .isInstanceOf(ResourceNotFoundException.class)
            .hasMessageContaining("99");
    }

    @Test
    void create_shouldSaveAndReturnBook() {
        when(repository.save(any(Book.class))).thenReturn(book);

        Book result = bookService.create(book);

        assertThat(result.getTitle()).isEqualTo("Suç ve Ceza");
        verify(repository, times(1)).save(book);
    }

    @Test
    void delete_shouldCallDeleteById_whenExists() {
        when(repository.existsById(1L)).thenReturn(true);

        bookService.delete(1L);

        verify(repository, times(1)).deleteById(1L);
    }

    @Test
    void delete_shouldThrowException_whenNotFound() {
        when(repository.existsById(99L)).thenReturn(false);

        assertThatThrownBy(() -> bookService.delete(99L))
            .isInstanceOf(ResourceNotFoundException.class);
    }
}