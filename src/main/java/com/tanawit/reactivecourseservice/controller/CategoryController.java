package com.tanawit.reactivecourseservice.controller;

import com.tanawit.reactivecourseservice.model.Category;
import com.tanawit.reactivecourseservice.service.CategoryService;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("categories")
@CrossOrigin
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public Flux<Category> findAll() {
        return categoryService.findAll();
    }

    @GetMapping("/{id}")
    public Mono<Category> findById(@PathVariable Long id) {
        return categoryService.findById(id);
    }
}
