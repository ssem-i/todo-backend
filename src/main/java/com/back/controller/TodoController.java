package com.back.controller;

import com.back.dto.TodoItemResponse;
import com.back.dto.TodoRequest;
import com.back.service.TodoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/todos")
public class TodoController {
    private final TodoService todoService;

    @PostMapping
    public long addtodo(@Valid @RequestBody TodoRequest request) {
        return todoService.addTodo(request);
    }

    @GetMapping
    public List<TodoItemResponse> getTodoList() {
        return todoService.getTodoList();
    } // opntional

    @GetMapping("/{id}")
    public TodoItemResponse getTodo(@PathVariable long id) {
        return todoService.getTodo(id);
    }

    @PutMapping("/{id}")
    public TodoItemResponse editTodo(@PathVariable long id, @Valid @RequestBody TodoRequest request){
        return todoService.editTodo(id, request.getTitle());
    }

    @DeleteMapping("/{id}")
    public void deletetodo(@PathVariable long id){
        todoService.deleteTodo(id);
    }

    @PatchMapping("/{id}/completion")
    public TodoItemResponse changeCompletion(@PathVariable long id, @RequestParam boolean completed){
        return todoService.changeCompletion(id, completed);
    }
}
