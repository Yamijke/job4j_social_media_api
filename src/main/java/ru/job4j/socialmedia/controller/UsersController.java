package ru.job4j.socialmedia.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.job4j.socialmedia.model.User;
import ru.job4j.socialmedia.service.UserService;

import java.util.List;

@Tag(name = "UsersController", description = "UsersController management APIs")
@AllArgsConstructor
@RestController
@RequestMapping("/api/users")
public class UsersController {
    private final UserService userService;

    @Operation(
            summary = "Retrieve all users",
            description = "Get a list of all users. The response is a list of User objects.",
            tags = { "User", "get" })
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK", content = { @Content(schema = @Schema(implementation = User.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "204", description = "No Content", content = @Content(schema = @Schema(hidden = true)))
    })
    @GetMapping
    public ResponseEntity<List<User>> getAll() {
        List<User> users = userService.findAll();
        return ResponseEntity.ok(users);
    }
}
