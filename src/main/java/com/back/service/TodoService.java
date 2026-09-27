package com.back.service;

import com.back.dto.TodoItemResponse;
import com.back.entity.TodoItem;
import com.back.repository.TodoRepository;
import com.back.dto.TodoRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RequiredArgsConstructor
@Service
public class TodoService {
    private final TodoRepository todoRepository;

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
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "존재하지 않는 할 일입니다."));

        return toResponse(todoItem);
    }

    @Transactional
    public TodoItemResponse editTodo(long id, String title){
        TodoItem todoItem = todoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "존재하지 않는 할 일입니다."));
        todoItem.setTitle(title);

        return toResponse(todoItem);
    }

    public void deleteTodo(long id){
        TodoItem todoItem = todoRepository.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "존재하지 않는 할 일입니다."
                        )
                );
        todoRepository.delete(todoItem);
    }

    @Transactional
    public TodoItemResponse changeCompletion(long id, boolean completed){
        TodoItem todoItem = todoRepository.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "존재하지 않는 할 일입니다.")
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
