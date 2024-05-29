package ru.job4j.socialmedia.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PostDTO {
    private long id;

    @NotBlank(message = "The title should not be empty")
    private String title;

    @NotBlank(message = "The context should not be empty")
    private String context;

    private String imageUrl;

    @NotNull(message = "The creation date should not be null")
    private LocalDateTime createdAt;
}
