package com.tanawit.reactivecourseservice.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.tanawit.reactivecourseservice.model.Category;
import com.tanawit.reactivecourseservice.model.Course;
import com.tanawit.reactivecourseservice.repository.ICategoryRepository;
import com.tanawit.reactivecourseservice.repository.ICourseRepository;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class DataInitializer implements CommandLineRunner {

    private final ICategoryRepository categoryRepository;
    private final ICourseRepository courseRepository;

    public DataInitializer(ICategoryRepository categoryRepository, ICourseRepository courseRepository) {
        log.info("Run CategoryInitializer...");
        this.categoryRepository = categoryRepository;
        this.courseRepository = courseRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        Mono<Long> count = categoryRepository.count();
        count.subscribe(c -> {
            if (c > 0) {
                log.info("Category items already created.");
            } else {
                List<Category> categories = new ArrayList<>(
                        Arrays.asList(new Category("Bootcamp"), new Category("Circuit Training"),
                                new Category("Gymnastics"), new Category("Outdoor"), new Category("Weight Training")));

                categoryRepository.saveAll(categories).log().subscribe(createdCategory -> {
                    createExampleCourse(createdCategory);
                });
            }
        });
    }

    private void createExampleCourse(Category category) {
        Course course = new Course(null, "Course of " + category.getTitle(), category.getId(), "", "", 60L);
        courseRepository.save(course).subscribe(createdCourse -> {
            log.info("Couse " + createdCourse.getTitle() + " was created");
        });
    }

}
