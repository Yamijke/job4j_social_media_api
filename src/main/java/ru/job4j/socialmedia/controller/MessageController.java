package ru.job4j.socialmedia.controller;

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
        List<Message> message = messageService.findAll();
        return ResponseEntity.ok(message);
    }

    @GetMapping("/{messageId}")
    public ResponseEntity<Message> get(@PathVariable("messageId")
                                    Long messageId) {
        return messageService.findById(messageId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Message> save(@RequestBody Message message) {
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
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Message> update(@RequestBody Message message) {
        Message updateMessage = messageService.update(message);
        return ResponseEntity.ok(updateMessage);
    }

    @PatchMapping
    @ResponseStatus(HttpStatus.OK)
    public void change(@RequestBody Message message) {
        messageService.update(message);
    }

    @DeleteMapping("/{messageId}")
    public ResponseEntity<Void> removeById(@PathVariable long messageId) {
        messageService.deleteById(messageId);
        return ResponseEntity.ok().build();
    }
}
