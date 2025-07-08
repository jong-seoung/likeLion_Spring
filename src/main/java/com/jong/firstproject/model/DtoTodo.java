package com.jong.firstproject.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DtoTodo {
    private Integer id;
    private String title;
    private boolean completed;
    private Integer userId;
}
