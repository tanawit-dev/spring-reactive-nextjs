package com.tanawit.reactivecourseservice.repository;

import com.tanawit.reactivecourseservice.model.Course;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import reactor.core.publisher.Flux;

public interface ICourseRepository extends ReactiveCrudRepository<Course, Long> {
    @Query("select * from courses where category_id = $1")
    Flux<Course> findByCategoryId(Long categoryId);
}
