package com.tanawit.reactivecourseservice.event;

import com.tanawit.reactivecourseservice.model.Course;

import org.springframework.context.ApplicationEvent;

public class CourseCreatedEvent extends ApplicationEvent {

    private static final long serialVersionUID = 1L;

    public CourseCreatedEvent(Course course) {
        super(course);
    }

}
