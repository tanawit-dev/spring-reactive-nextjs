package com.tanawit.reactivecourseservice.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Table("categories")
public class Category {
    @Id
    private Long id;
    private String title;

    public Category(String title) {
        this.title = title;
    }
}
