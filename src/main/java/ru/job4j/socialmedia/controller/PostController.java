package ru.job4j.socialmedia.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.job4j.socialmedia.dto.UserDTO;
import ru.job4j.socialmedia.model.Post;
import ru.job4j.socialmedia.service.PostService;

import java.util.List;

@Tag(name = "PostController", description = "PostController management APIs")
@AllArgsConstructor
@RestController
@RequestMapping("/api/posts")
public class PostController {
    private final PostService postService;

    @Operation(
            summary = "Retrieve all posts",
            description = "Get a list of all posts. The response is a list of Post objects.",
            tags = { "Post", "get" })
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK", content = { @Content(schema = @Schema(implementation = Post.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "204", description = "No Content", content = @Content(schema = @Schema(hidden = true)))
    })
    @GetMapping
    public ResponseEntity<List<Post>> getAllPosts() {
        List<Post> posts = postService.findAll();
        return ResponseEntity.ok(posts);
    }

    @Operation(
            summary = "Retrieve a Post by postId",
            description = "Get a Post object by specifying its postId. The response is a Post object.",
            tags = { "Post", "get" })
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK", content = { @Content(schema = @Schema(implementation = Post.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(schema = @Schema(hidden = true))) })
    @GetMapping("/{postId}")
    public ResponseEntity<Post> get(@PathVariable("postId") Long postId) {
        return postService.findById(postId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @Operation(
            summary = "Create a new Post",
            description = "Save a new Post object. The response is the created Post object with a generated ID.",
            tags = { "Post", "post" })
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Created", content = { @Content(schema = @Schema(implementation = Post.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(schema = @Schema(hidden = true))) })
    @PostMapping
    public ResponseEntity<Post> save(@Valid @RequestBody Post post) {
        postService.save(post);
        var uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(post.getId())
                .toUri();
        return ResponseEntity.status(HttpStatus.CREATED)
                .location(uri)
                .body(post);
    }

    @Operation(
            summary = "Update an existing Post",
            description = "Update a Post object. The response is the updated Post object.",
            tags = { "Post", "put" })
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK", content = { @Content(schema = @Schema(implementation = Post.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(schema = @Schema(hidden = true))) })
    @PutMapping
    public ResponseEntity<Post> update(@Valid @RequestBody Post post) {
        Post updatedPost = postService.update(post);
        return ResponseEntity.ok(updatedPost);
    }

    @Operation(
            summary = "Partially update an existing Post",
            description = "Partially update a Post object. The response indicates success with no content.",
            tags = { "Post", "patch" })
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "No Content", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(schema = @Schema(hidden = true))) })
    @PatchMapping
    public ResponseEntity<Void> change(@Valid @RequestBody Post post) {
        postService.update(post);
        return ResponseEntity.ok().build();
    }

    @Operation(
            summary = "Delete a Post by postId",
            description = "Delete a Post object by specifying its postId. The response indicates success with no content.",
            tags = { "Post", "delete" })
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "No Content", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(schema = @Schema(hidden = true))) })
    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> removeById(@PathVariable long postId) {
        postService.deleteById(postId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Operation(
            summary = "Retrieve users with posts by user IDs",
            description = "Get a list of users with their posts by specifying a list of user IDs. The response is a list of UserDTO objects.",
            tags = { "User", "post" })
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK", content = { @Content(schema = @Schema(implementation = UserDTO.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(schema = @Schema(hidden = true))) })
    @PostMapping("/users")
    public List<UserDTO> getUsersWithPosts(@RequestBody List<Long> userIds) {
        return postService.getUsersWithPosts(userIds);
    }
}
