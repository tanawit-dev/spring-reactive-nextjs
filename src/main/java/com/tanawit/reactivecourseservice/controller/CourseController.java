package com.tanawit.reactivecourseservice.controller;

import com.tanawit.reactivecourseservice.event.CourseCreatedEvent;
import com.tanawit.reactivecourseservice.event.CourseCreatedEventListener;
import com.tanawit.reactivecourseservice.model.Course;
import com.tanawit.reactivecourseservice.service.CourseService;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("courses")
@CrossOrigin()
@Slf4j
public class CourseController {

    private final CourseService courseService;
    private final Flux<CourseCreatedEvent> events;

    public CourseController(CourseService courseService, CourseCreatedEventListener listener) {
        this.courseService = courseService;
        this.events = Flux.create(listener).share();
    }

    @GetMapping
    public Flux<Course> findAll() {
        return courseService.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Course> create(@RequestBody Course course) {
        return courseService.create(course);
    }

    @GetMapping(value = "/sse", produces = "text/event-stream;charset=UTF-8")
    public Flux<Course> stream() {
        log.info("Start listening to the course collection.");
        return events.map(event -> {
            log.info("stream: {}", event);
            return (Course) event.getSource();
        });
    }

}
