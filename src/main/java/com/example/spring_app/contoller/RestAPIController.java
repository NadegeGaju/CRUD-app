package com.example.spring_app.contoller;

import com.example.spring_app.Entity.Book;
import com.example.spring_app.repository.BookRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class RestAPIController {

    private final BookRepository bookRepository;

    @Secured("ROLE_ADMIN") // Ensures that only admins can create books
    @PostMapping("/create-book")
    public ResponseEntity<Book> createBook(@Valid @RequestBody Book book) {
        Book savedBook = bookRepository.save(book);
        return new ResponseEntity<>(savedBook, HttpStatus.CREATED);
    }

    @Secured({"ROLE_USER", "ROLE_ADMIN"}) // Allows both users and admins to view all books
    @GetMapping("/get-all-books")
    public ResponseEntity<List<Book>> getAllBooks() {
        List<Book> books = bookRepository.findAll();
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    @Secured({"ROLE_USER", "ROLE_ADMIN"}) // Allows both users and admins to search by title
    @GetMapping("/{title}")
    public ResponseEntity<EntityModel<Book>> findBookByTitle(@PathVariable String title) {
        Book book = bookRepository.findByTitle(title)
                .orElseThrow(() -> new RuntimeException("Book not found with title: " + title));
        EntityModel<Book> resource = EntityModel.of(book);

        Link selfLink = WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(this.getClass())
                        .findBookByTitle(title)
        ).withSelfRel();
        Link allBooksLink = WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(this.getClass()).getAllBooks()
        ).withRel("all-books");

        resource.add(selfLink, allBooksLink);
        return new ResponseEntity<>(resource, HttpStatus.OK);
    }

    @Secured("ROLE_ADMIN") // Ensures that only admins can update books
    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable Long id, @Valid @RequestBody Book bookDetails) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found with id " + id));

        book.setTitle(bookDetails.getTitle());
        book.setAuthor(bookDetails.getAuthor());
        Book updatedBook = bookRepository.save(book);

        return new ResponseEntity<>(updatedBook, HttpStatus.OK);
    }

    @Secured("ROLE_ADMIN") // Ensures that only admins can delete books
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found with id " + id));

        bookRepository.delete(book);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
