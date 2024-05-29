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
import ru.job4j.socialmedia.model.Friend;
import ru.job4j.socialmedia.service.FriendService;

import java.util.List;

@Tag(name = "FriendController", description = "FriendController management APIs")
@AllArgsConstructor
@RestController
@RequestMapping("/api/friends")
public class FriendController {
    private final FriendService friendService;

    @Operation(
            summary = "Retrieve all friends",
            description = "Get a list of all friends. The response is a list of Friend objects.",
            tags = { "Friend", "get" })
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK", content = { @Content(schema = @Schema(implementation = Friend.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "204", description = "No Content", content = @Content(schema = @Schema(hidden = true)))
    })
    @GetMapping
    public ResponseEntity<List<Friend>> getAllFriends() {
        List<Friend> friends = friendService.findAll();
        return ResponseEntity.ok(friends);
    }

    @Operation(
            summary = "Retrieve a Friend by friendId",
            description = "Get a Friend object by specifying its friendId. The response is a Friend object.",
            tags = { "Friend", "get" })
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK", content = { @Content(schema = @Schema(implementation = Friend.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(schema = @Schema(hidden = true))) })
    @GetMapping("/{friendId}")
    public ResponseEntity<Friend> get(@PathVariable("friendId") Long friendId) {
        return friendService.findById(friendId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @Operation(
            summary = "Create a new Friend",
            description = "Save a new Friend object. The response is the created Friend object with a generated ID.",
            tags = { "Friend", "post" })
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Created", content = { @Content(schema = @Schema(implementation = Friend.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(schema = @Schema(hidden = true))) })
    @PostMapping
    public ResponseEntity<Friend> save(@Valid @RequestBody Friend friend) {
        friendService.save(friend);
        var uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(friend.getId())
                .toUri();
        return ResponseEntity.status(HttpStatus.CREATED)
                .location(uri)
                .body(friend);
    }

    @Operation(
            summary = "Update an existing Friend",
            description = "Update a Friend object. The response is the updated Friend object.",
            tags = { "Friend", "put" })
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK", content = { @Content(schema = @Schema(implementation = Friend.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(schema = @Schema(hidden = true))) })
    @PutMapping
    public ResponseEntity<Friend> update(@Valid @RequestBody Friend friend) {
        Friend updatedFriend = friendService.update(friend);
        return ResponseEntity.ok(updatedFriend);
    }

    @Operation(
            summary = "Partially update an existing Friend",
            description = "Partially update a Friend object. The response indicates success with no content.",
            tags = { "Friend", "patch" })
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "No Content", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(schema = @Schema(hidden = true))) })
    @PatchMapping
    public ResponseEntity<Void> change(@Valid @RequestBody Friend friend) {
        friendService.update(friend);
        return ResponseEntity.ok().build();
    }

    @Operation(
            summary = "Delete a Friend by friendId",
            description = "Delete a Friend object by specifying its friendId. The response indicates success with no content.",
            tags = { "Friend", "delete" })
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "No Content", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(schema = @Schema(hidden = true))) })
    @DeleteMapping("/{friendId}")
    public ResponseEntity<Void> removeById(@PathVariable long friendId) {
        friendService.deleteById(friendId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
