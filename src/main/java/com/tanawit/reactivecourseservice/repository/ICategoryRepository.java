package com.tanawit.reactivecourseservice.repository;

import com.tanawit.reactivecourseservice.model.Category;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface ICategoryRepository extends ReactiveCrudRepository<Category, Long> {

}
