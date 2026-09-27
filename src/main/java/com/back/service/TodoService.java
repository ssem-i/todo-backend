package com.back.service;

import com.back.dto.TodoItemResponse;
import com.back.entity.TodoItem;
import com.back.repository.TodoRepository;
import com.back.dto.TodoRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TodoService {
    private TodoRepository todoRepository;

    public long addTodo(TodoRequest todoRequest){
        TodoItem todoItem = new TodoItem(todoRequest.getTitle());

        return todoRepository.save(todoItem).getId();
    }

    public List<TodoItemResponse> getTodoList(){
        return todoRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public TodoItemResponse getTodo(long id) {
        TodoItem todoItem = todoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않음"));

        return toResponse(todoItem);
    }

    public TodoItemResponse editTodo(long id, String title){
        TodoItem todoItem = todoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않음"));
        todoItem.setTitle(title);

        return toResponse(todoItem);
    }

    public void deleteTodo(long id){
        todoRepository.deleteById(id);
    }

    public TodoItemResponse changeCompletion(long id, boolean completed){
        TodoItem todoItem = todoRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException("존재하지 않음")
                );
        todoItem.changeCompletion(completed);
        return toResponse(todoItem);
    }

    private TodoItemResponse toResponse(TodoItem todoItem){
        return new TodoItemResponse(
                todoItem.getId(),
                todoItem.getTitle(),
                todoItem.isCompleted(),
                todoItem.getCreatedTime()
        );
    }
}
