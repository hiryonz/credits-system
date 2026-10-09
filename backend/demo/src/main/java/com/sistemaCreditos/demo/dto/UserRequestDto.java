package com.sistemaCreditos.demo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequestDto {

    @Schema(example = "usuario1")
    private String username;

    @Schema(example = "secreto123")
    private String password;

}
