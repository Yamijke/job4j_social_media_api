package ru.job4j.socialmedia.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.util.List;

@Data
public class UserDTO {
    private long userId;

    @NotBlank(message = "the username should not be empty")
    @Length(min = 4,
            max = 15,
            message = "the user name must be 4-15 characters long"
    )
    private String username;
    private List<PostDTO> posts;
}
