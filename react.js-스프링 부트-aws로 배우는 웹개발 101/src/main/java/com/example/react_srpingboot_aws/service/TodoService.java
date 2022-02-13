package com.example.react_srpingboot_aws.service;

import com.example.react_srpingboot_aws.domain.Todo;
import com.example.react_srpingboot_aws.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class TodoService {

    private final TodoRepository todoRepository;

    public List<Todo> create(final Todo entity) {
        validate(entity);
        todoRepository.save(entity);
        return todoRepository.findByUserId(entity.getUserId());
    }

    public List<Todo> retrieve(final String userId) {
        return todoRepository.findByUserId(userId);
    }

    public List<Todo> update(final Todo entity) {
        validate(entity);

        final Optional<Todo> original = todoRepository.findById(entity.getId());

        original.ifPresent(todo -> {
            todo.setTitle(entity.getTitle());
            todo.setDone(entity.isDone());
            todoRepository.save(todo);
        });

        return retrieve(entity.getUserId());
    }
    
    private void validate(final Todo entity) {
        if (entity == null) {
            log.warn("Entity cannot be null");
            throw new RuntimeException("Entity cannot be null");
        }

        if (entity.getUserId() == null) {
            log.warn("Unknown user.");
            throw new RuntimeException("Unknown user.");
        }
    }
}
