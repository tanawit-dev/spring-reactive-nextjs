package com.tanawit.reactivecourseservice.service;

import com.tanawit.reactivecourseservice.event.CourseCreatedEvent;
import com.tanawit.reactivecourseservice.model.Course;
import com.tanawit.reactivecourseservice.repository.ICourseRepository;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Slf4j
public class CourseService {

    private final ICourseRepository repository;
    private final ApplicationEventPublisher publisher;

    public Mono<Course> create(Course course) {
        log.info("Try to create new course {}", course.getTitle());
        return repository.save(course).doOnNext(c -> {
            log.info("Course  {} saved to database.", c.getTitle());
            publisher.publishEvent(new CourseCreatedEvent(c));
        });
    }

    public Flux<Course> findAll() {
        return repository.findAll();
    }
}
