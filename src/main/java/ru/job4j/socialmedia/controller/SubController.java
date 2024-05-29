package ru.job4j.socialmedia.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.job4j.socialmedia.model.Sub;
import ru.job4j.socialmedia.service.SubService;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/subs")
public class SubController {
    private final SubService subService;

    @GetMapping
    public ResponseEntity<List<Sub>> getAllSubs() {
        List<Sub> subs = subService.findAll();
        return ResponseEntity.ok(subs);
    }

    @GetMapping("/{subId}")
    public ResponseEntity<Sub> get(@PathVariable("subId") Long subId) {
        return subService.findById(subId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PostMapping
    public ResponseEntity<Sub> save(@Valid @RequestBody Sub sub) {
        subService.save(sub);
        var uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(sub.getId())
                .toUri();
        return ResponseEntity.status(HttpStatus.CREATED)
                .location(uri)
                .body(sub);
    }

    @PutMapping
    public ResponseEntity<Sub> update(@Valid @RequestBody Sub sub) {
        Sub updatedSub = subService.update(sub);
        return ResponseEntity.ok(updatedSub);
    }

    @PatchMapping
    public ResponseEntity<Void> change(@Valid @RequestBody Sub sub) {
        subService.update(sub);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{subId}")
    public ResponseEntity<Void> removeById(@PathVariable long subId) {
        subService.deleteById(subId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
