package com.example.react_srpingboot_aws.controller;

import com.example.react_srpingboot_aws.domain.Todo;
import com.example.react_srpingboot_aws.dto.ResponseDto;
import com.example.react_srpingboot_aws.dto.TodoDto;
import com.example.react_srpingboot_aws.service.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("todo")
@RequiredArgsConstructor
public class TodoController {

    private final TodoService todoService;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody TodoDto dto) {
        try {
            Todo entity = TodoDto.toEntity(dto);
            entity.setUserId("temp");

            List<Todo> entities = todoService.create(entity);

            List<TodoDto> dtos = entities.stream()
                    .map(e -> new TodoDto(e))
                    .collect(Collectors.toList());

            ResponseDto response = ResponseDto.<TodoDto>builder().data(dtos).build();
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            ResponseDto response = ResponseDto.<TodoDto>builder().error(e.getMessage()).build();
            return ResponseEntity.badRequest().body(response);
        }
    }

    @GetMapping
    public ResponseEntity<?> retrieveTodoList() {
        String tempUserId = "temp";

        List<Todo> entities = todoService.retrieve(tempUserId);
        List<TodoDto> dtos = entities.stream()
                .map(TodoDto::new)
                .collect(Collectors.toList());

        ResponseDto<TodoDto> response = ResponseDto.<TodoDto>builder()
                .data(dtos)
                .build();

        return ResponseEntity.ok().body(response);
    }
}
