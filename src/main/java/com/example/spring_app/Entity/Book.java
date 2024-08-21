package com.example.spring_app.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @NotBlank(message = "Title is mandatory")
    @Size(max = 50, message = "Title should not exceed 50 characters")
    @Column(name = "title", nullable = false, length = 50)
    private String title;

    @NotBlank(message = "Author is mandatory")
    @Size(max = 50, message = "Author should not exceed 50 characters")
    @Column(name = "author", nullable = false, length = 50)
    private String author;

    @Min(value = 0, message = "Number of copies should be at least 0")
    @Column(name = "copies")
    private int copies;

    @Column(name = "available")
    private boolean available;
}
