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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.job4j.socialmedia.model.Message;
import ru.job4j.socialmedia.service.MessageService;

import java.util.List;

@Tag(name = "MessageController", description = "MessageController management APIs")
@AllArgsConstructor
@RestController
@RequestMapping("/api/messages")
public class MessageController {
    private final MessageService messageService;

    @Operation(
            summary = "Retrieve all messages",
            description = "Get a list of all messages. The response is a list of Message objects.",
            tags = {"Message", "get"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK", content = {@Content(schema = @Schema(implementation = Message.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "204", description = "No Content", content = @Content(schema = @Schema(hidden = true)))
    })
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<Message>> getAllMessages() {
        List<Message> messages = messageService.findAll();
        return ResponseEntity.ok(messages);
    }

    @Operation(
            summary = "Retrieve a Message by messageId",
            description = "Get a Message object by specifying its messageId. The response is a Message object.",
            tags = {"Message", "get"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK", content = {@Content(schema = @Schema(implementation = Message.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(schema = @Schema(hidden = true)))})
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    @GetMapping("/{messageId}")
    public ResponseEntity<Message> get(@PathVariable("messageId") Long messageId) {
        return messageService.findById(messageId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @Operation(
            summary = "Create a new Message",
            description = "Save a new Message object. The response is the created Message object with a generated ID.",
            tags = {"Message", "post"})
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Created", content = {@Content(schema = @Schema(implementation = Message.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(schema = @Schema(hidden = true)))})
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<Message> save(@Valid @RequestBody Message message) {
        messageService.save(message);
        var uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(message.getId())
                .toUri();
        return ResponseEntity.status(HttpStatus.CREATED)
                .location(uri)
                .body(message);
    }

    @Operation(
            summary = "Update an existing Message",
            description = "Update a Message object. The response is the updated Message object.",
            tags = {"Message", "put"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK", content = {@Content(schema = @Schema(implementation = Message.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(schema = @Schema(hidden = true)))})
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    @PutMapping
    public ResponseEntity<Message> update(@Valid @RequestBody Message message) {
        Message updatedMessage = messageService.update(message);
        return ResponseEntity.ok(updatedMessage);
    }

    @Operation(
            summary = "Partially update an existing Message",
            description = "Partially update a Message object. The response indicates success with no content.",
            tags = {"Message", "patch"})
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "No Content", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(schema = @Schema(hidden = true)))})
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    @PatchMapping
    public ResponseEntity<Void> change(@Valid @RequestBody Message message) {
        messageService.update(message);
        return ResponseEntity.ok().build();
    }

    @Operation(
            summary = "Delete a Message by messageId",
            description = "Delete a Message object by specifying its messageId. The response indicates success with no content.",
            tags = {"Message", "delete"})
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "No Content", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(schema = @Schema(hidden = true)))})
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    @DeleteMapping("/{messageId}")
    public ResponseEntity<Void> removeById(@PathVariable long messageId) {
        messageService.deleteById(messageId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
