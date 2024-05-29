package ru.job4j.socialmedia.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Data
@Table(name = "users")
@Schema(description = "User Model Information")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private long id;

    @Schema(description = "UserEmail title", example = "example@test.com")
    @Column(unique = true, nullable = false)
    private String email;

    @Schema(description = "UserName title", example = "Mediator")
    private String name;

    @Schema(description = "UserPas title", example = "kjnb78SDFY")
    private String password;
}
