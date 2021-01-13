package com.tanawit.reactivecourseservice.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table("courses")
public class Course {
    @Id
    private Long id;
    private String title;
    @Column("category_id")
    private Long categoryId;
    private String teaser;
    private String description;
    private Long duration;
}
