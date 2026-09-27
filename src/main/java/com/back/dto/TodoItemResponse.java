package com.back.dto;

import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
public class TodoItemResponse {
    private Long id;
    private String title;
    private boolean completed;
    private LocalDateTime createdTime;
}
