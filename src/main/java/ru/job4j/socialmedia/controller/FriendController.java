package ru.job4j.socialmedia.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.job4j.socialmedia.model.Friend;
import ru.job4j.socialmedia.service.FriendService;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/friends")
public class FriendController {
    private final FriendService friendService;

    @GetMapping
    public ResponseEntity<List<Friend>> getAllFriends() {
        List<Friend> friends = friendService.findAll();
        return ResponseEntity.ok(friends);
    }

    @GetMapping("/{friendId}")
    public ResponseEntity<Friend> get(@PathVariable("friendId") Long friendId) {
        return friendService.findById(friendId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

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

    @PutMapping
    public ResponseEntity<Friend> update(@Valid @RequestBody Friend friend) {
        Friend updatedFriend = friendService.update(friend);
        return ResponseEntity.ok(updatedFriend);
    }

    @PatchMapping
    public ResponseEntity<Void> change(@Valid @RequestBody Friend friend) {
        friendService.update(friend);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{friendId}")
    public ResponseEntity<Void> removeById(@PathVariable long friendId) {
        friendService.deleteById(friendId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
