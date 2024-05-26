package ru.job4j.socialmedia.controller;

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
    public ResponseEntity<Friend> get(@PathVariable("friendId")
                                       Long friendId) {
        return friendService.findById(friendId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Friend> save(@RequestBody Friend friend) {
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
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Friend> update(@RequestBody Friend friend) {
        Friend updateFriend = friendService.update(friend);
        return ResponseEntity.ok(updateFriend);
    }

    @PatchMapping
    @ResponseStatus(HttpStatus.OK)
    public void change(@RequestBody Friend friend) {
        friendService.update(friend);
    }

    @DeleteMapping("/{friendId}")
    public ResponseEntity<Void> removeById(@PathVariable long friendId) {
        friendService.deleteById(friendId);
        return ResponseEntity.ok().build();
    }
}
