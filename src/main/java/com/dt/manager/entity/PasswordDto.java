package com.dt.manager.entity;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Value;

import java.time.LocalDateTime;

/**
 * DTO for {@link Password}
 */
@Value
public class PasswordDto {
    String passwordValue;
    @NotEmpty
    @NotBlank
    String serviceName;
    @NotNull
    LocalDateTime createdAt;
}