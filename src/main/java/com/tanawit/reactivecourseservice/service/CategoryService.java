package com.tanawit.reactivecourseservice.service;

import com.tanawit.reactivecourseservice.model.Category;
import com.tanawit.reactivecourseservice.repository.ICategoryRepository;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final ICategoryRepository repository;

    public Mono<Category> findById(Long id) {
        return repository.findById(id);
    }

    public Flux<Category> findAll() {
        return repository.findAll();
    }
}
