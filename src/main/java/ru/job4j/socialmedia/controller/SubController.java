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
import ru.job4j.socialmedia.model.Sub;
import ru.job4j.socialmedia.service.SubService;

import java.util.List;

@Tag(name = "SubController", description = "SubController management APIs")
@AllArgsConstructor
@RestController
@RequestMapping("/api/subs")
public class SubController {
    private final SubService subService;

    @Operation(
            summary = "Retrieve all subs",
            description = "Get a list of all subs. The response is a list of Sub objects.",
            tags = { "Sub", "get" })
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK", content = { @Content(schema = @Schema(implementation = Sub.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "204", description = "No Content", content = @Content(schema = @Schema(hidden = true)))
    })
    @GetMapping
    public ResponseEntity<List<Sub>> getAllSubs() {
        List<Sub> subs = subService.findAll();
        return ResponseEntity.ok(subs);
    }

    @Operation(
            summary = "Retrieve a Sub by subId",
            description = "Get a Sub object by specifying its subId. The response is a Sub object.",
            tags = { "Sub", "get" })
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = Sub.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(schema = @Schema(hidden = true))) })
    @GetMapping("/{subId}")
    public ResponseEntity<Sub> get(@PathVariable("subId") Long subId) {
        return subService.findById(subId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @Operation(
            summary = "Create a new Sub",
            description = "Save a new Sub object. The response is the created Sub object with a generated ID.",
            tags = { "Sub", "post" })
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Created", content = { @Content(schema = @Schema(implementation = Sub.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(schema = @Schema(hidden = true))) })
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

    @Operation(
            summary = "Update an existing Sub",
            description = "Update a Sub object. The response is the updated Sub object.",
            tags = { "Sub", "put" })
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = Sub.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(schema = @Schema(hidden = true))) })
    @PutMapping
    public ResponseEntity<Sub> update(@Valid @RequestBody Sub sub) {
        Sub updatedSub = subService.update(sub);
        return ResponseEntity.ok(updatedSub);
    }

    @Operation(
            summary = "Partially update an existing Sub",
            description = "Partially update a Sub object. The response indicates success with no content.",
            tags = { "Sub", "patch" })
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "No Content"),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(schema = @Schema(hidden = true))) })
    @PatchMapping
    public ResponseEntity<Void> change(@Valid @RequestBody Sub sub) {
        subService.update(sub);
        return ResponseEntity.ok().build();
    }

    @Operation(
            summary = "Delete a Sub by subId",
            description = "Delete a Sub object by specifying its subId. The response indicates success with no content.",
            tags = { "Sub", "delete" })
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "No Content"),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(schema = @Schema(hidden = true))) })
    @DeleteMapping("/{subId}")
    public ResponseEntity<Void> removeById(@PathVariable long subId) {
        subService.deleteById(subId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
