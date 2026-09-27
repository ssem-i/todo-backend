package com.back.global.initData;

import com.back.entity.TodoItem;
import com.back.repository.TodoRepository;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Configuration
public class DataInit {
    private final DataInit self;
    private final TodoRepository todoRepository;

    public DataInit(@Lazy DataInit self, TodoRepository todoRepository) {
        this.self = self;
        this.todoRepository = todoRepository;
    }
    @Bean
    public ApplicationRunner baseInitDataRunner() {
        return args -> {
            // self.makeTodoItems();
        };
    }

    @Transactional
    public void makeTodoItems() {
        if(todoRepository.count() > 0) return;

        todoRepository.saveAll(List.of(
                new TodoItem("todo 1"),
                new TodoItem("todo 2"),
                new TodoItem("todo 3")
        ));
    }
}
