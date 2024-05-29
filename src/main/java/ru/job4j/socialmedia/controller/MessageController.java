package ru.job4j.socialmedia.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.job4j.socialmedia.model.Message;
import ru.job4j.socialmedia.service.MessageService;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/messages")
public class MessageController {
    private final MessageService messageService;

    @GetMapping
    public ResponseEntity<List<Message>> getAllMessages() {
        List<Message> messages = messageService.findAll();
        return ResponseEntity.ok(messages);
    }

    @GetMapping("/{messageId}")
    public ResponseEntity<Message> get(@PathVariable("messageId") Long messageId) {
        return messageService.findById(messageId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

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

    @PutMapping
    public ResponseEntity<Message> update(@Valid @RequestBody Message message) {
        Message updatedMessage = messageService.update(message);
        return ResponseEntity.ok(updatedMessage);
    }

    @PatchMapping
    public ResponseEntity<Void> change(@Valid @RequestBody Message message) {
        messageService.update(message);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{messageId}")
    public ResponseEntity<Void> removeById(@PathVariable long messageId) {
        messageService.deleteById(messageId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
