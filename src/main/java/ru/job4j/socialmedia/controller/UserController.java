package ru.job4j.socialmedia.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.job4j.socialmedia.model.User;
import ru.job4j.socialmedia.service.UserService;

import java.util.List;

@Tag(name = "UserController", description = "UserController management APIs")
@AllArgsConstructor
@RestController
@RequestMapping("/api/user")
public class UserController {
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
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.findAll();
        return ResponseEntity.ok(users);
    }

    @Operation(
            summary = "Retrieve a User by userId",
            description = "Get a User object by specifying its userId. The response is User object with userId, username and date of created.",
            tags = { "User", "get" })
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK", content = { @Content(schema = @Schema(implementation = User.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(schema = @Schema(hidden = true))) })
    @GetMapping("/{userId}")
    public ResponseEntity<User> get(@PathVariable("userId") Long userId) {
        return userService.findById(userId)
                .map(user -> ResponseEntity.status(HttpStatus.OK).body(user))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @Operation(
            summary = "Create a new User",
            description = "Save a new User object. The response is the created User object with a generated ID.",
            tags = { "User", "post" })
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Created", content = { @Content(schema = @Schema(implementation = User.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(schema = @Schema(hidden = true))) })
    @PostMapping
    public ResponseEntity<User> save(@Valid @RequestBody User user) {
        userService.save(user);
        var uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(user.getId())
                .toUri();
        return ResponseEntity.status(HttpStatus.CREATED)
                .location(uri)
                .body(user);
    }

    @Operation(
            summary = "Update an existing User",
            description = "Update a User object. The response is the updated User object.",
            tags = { "User", "put" })
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK", content = { @Content(schema = @Schema(implementation = User.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(schema = @Schema(hidden = true))) })
    @PutMapping
    public ResponseEntity<User> update(@Valid @RequestBody User user) {
        User updateUser = userService.update(user);
        return ResponseEntity.status(HttpStatus.OK).body(updateUser);
    }

    @Operation(
            summary = "Partially update an existing User",
            description = "Partially update a User object. The response indicates success with no content.",
            tags = { "User", "patch" })
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "No Content", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(schema = @Schema(hidden = true))) })
    @PatchMapping
    public ResponseEntity<Void> change(@Valid @RequestBody User user) {
        userService.update(user);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @Operation(
            summary = "Delete a User by userId",
            description = "Delete a User object by specifying its userId. The response indicates success with no content.",
            tags = { "User", "delete" })
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "No Content", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(schema = @Schema(hidden = true))) })
    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> removeById(@PathVariable long userId) {
        userService.deleteById(userId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
