package ru.job4j.socialmedia.dto;

import lombok.Data;

import java.util.List;

@Data
public class UserDTO {
    private long userId;
    private String username;
    private List<PostDTO> posts;
}
