package com.example.spring_app.service;


import com.example.spring_app.Entity.Book;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SortingService {

    public List<Book> quickSort(List<Book> books) {
        if (books == null || books.size() <= 1) {
            return books;
        }
        quickSort(books, 0, books.size() - 1);
        return books;
    }

    private void quickSort(List<Book> books, int low, int high) {
        if (low < high) {
            int pi = partition(books, low, high);
            quickSort(books, low, pi - 1);
            quickSort(books, pi + 1, high);
        }
    }

    private int partition(List<Book> books, int low, int high) {
        Book pivot = books.get(high);
        int i = low - 1;
        for (int j = low; j < high; j++) {
            if (books.get(j).getTitle().compareTo(pivot.getTitle()) <= 0) {
                i++;
                Book temp = books.get(i);
                books.set(i, books.get(j));
                books.set(j, temp);
            }
        }
        Book temp = books.get(i + 1);
        books.set(i + 1, books.get(high));
        books.set(high, temp);
        return i + 1;
    }
}

