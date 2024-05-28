package ru.job4j.socialmedia.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PostDTO {
    private long id;
    private String title;
    private String context;
    private String imageUrl;
    private LocalDateTime createdAt;
}
