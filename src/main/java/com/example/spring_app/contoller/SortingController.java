package com.example.spring_app.contoller;
import com.example.spring_app.Entity.Book;
import com.example.spring_app.repository.BookRepository;
import com.example.spring_app.service.SortingService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/sort")
@RequiredArgsConstructor
public class SortingController {


    private final BookRepository bookRepository;


    private final SortingService sortingService;

    @GetMapping("/quick")
    public ResponseEntity<List<Book>> sortBooksQuickSort() {
        List<Book> books = bookRepository.findAll();
        sortingService.quickSort(books);
        return new ResponseEntity<>(books, HttpStatus.OK);
    }
}

