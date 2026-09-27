package com.back.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class TodoItemResponse {
    private Long id;
    private String title;
    private boolean completed;
    private LocalDateTime createdTime;
}
