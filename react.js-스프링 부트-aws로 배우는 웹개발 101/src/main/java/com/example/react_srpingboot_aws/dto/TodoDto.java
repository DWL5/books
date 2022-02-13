package com.example.react_srpingboot_aws.dto;

import com.example.react_srpingboot_aws.domain.Todo;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class TodoDto {
    private String id;
    private String title;
    private boolean done;

    public TodoDto(final Todo entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.done = entity.isDone();
    }

    public static Todo toEntity(final TodoDto dto) {
        return Todo.builder()
                .id(dto.getId())
                .title(dto.getTitle())
                .done(dto.isDone())
                .build();
    }
}
