package com.sb3.apirestsb3.Api.Controller;

import com.sb3.apirestsb3.Entity.Character;
import com.sb3.apirestsb3.Service.CharacterService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Tag(name = "Character", description = "Endpoints to manage characters")
@RestController
@RequestMapping("/api")
public class CharacterController {

    private final CharacterService characterService;

    @Autowired
    public CharacterController(CharacterService theCharacterService) {
        this.characterService = theCharacterService;
    }

    @Operation(summary = "List all characters")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Listed characters"),
            @ApiResponse(responseCode = "404", description = "No characters found")
    })
    @GetMapping("/characters")
    public ResponseEntity<?> index(@ParameterObject @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        Page<Character> characters = characterService.indexCharacters(pageable);
        if (characters.isEmpty()) {
            return new ResponseEntity<>("No characters found.", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(characters);
    }

    @Operation(summary = "Create character")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Created character"),
            @ApiResponse(responseCode = "400", description = "Invalid data to request")
    })
    @PostMapping("/character")
    public ResponseEntity<?> create(@Valid @RequestBody Character character, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return handleValidationErrors(bindingResult);
        }
        Character createdCharacter = characterService.createCharacter(character);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCharacter);
    }

    @Operation(summary = "Search character by name")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Character(s) found"),
            @ApiResponse(responseCode = "404", description = "Character not found")
    })
    @GetMapping("/search-character")
    public ResponseEntity<?> searchCharacterName(@RequestParam String name, @ParameterObject @PageableDefault(page = 0, size = 10, sort = "name", direction = Sort.Direction.ASC) Pageable pageable) {
        Page<Character> characters = characterService.searchCharacter(name, pageable);
        if (characters.isEmpty()) {
            return new ResponseEntity<>("Character " + name + " not found.", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(characters);
    }

    @Operation(summary = "Show character by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Character found"),
            @ApiResponse(responseCode = "404", description = "Character not found")
    })
    @GetMapping("/character/{id}")
    public ResponseEntity<?> show(@PathVariable int id) {
        try {
            Character character = characterService.findCharacterId(id);
            return ResponseEntity.ok(character);
        } catch (RuntimeException e) {
            return new ResponseEntity<>("Character ID " + id + " not found.", HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Update character by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "202", description = "Character updated"),
            @ApiResponse(responseCode = "400", description = "Invalid data to request"),
            @ApiResponse(responseCode = "404", description = "Character not found")
    })
    @PutMapping("/character/{id}")
    public ResponseEntity<?> update(@PathVariable int id, @Valid @RequestBody Character character, BindingResult bindingResult) {
        try {
            characterService.findCharacterId(id);
        } catch (RuntimeException e) {
            return new ResponseEntity<>("Character ID " + id + " not found.", HttpStatus.NOT_FOUND);
        }
        if (bindingResult.hasErrors()) {
            return handleValidationErrors(bindingResult);
        }
        character.setId(id);
        Character updatedCharacter = characterService.updateCharacter(character);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(updatedCharacter);
    }

    @Operation(summary = "Update partially character by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "202", description = "Character was partially updated"),
            @ApiResponse(responseCode = "400", description = "Invalid request"),
            @ApiResponse(responseCode = "404", description = "Character not found")
    })
    @PatchMapping("/character/{id}")
    public ResponseEntity<?> partialUpdate(@PathVariable int id, @RequestBody Map<String, Object> patchUpdate) {
        try {
            Character partiallyupdatedCharacter = characterService.updateCharacterPartially(id, patchUpdate);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(partiallyupdatedCharacter);
        } catch (RuntimeException e) {
            String message = e.getMessage();
            if (message.contains("not found")) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid request: " + message);
        }
    }

    @Operation(summary = "Delete character by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Character deleted"),
            @ApiResponse(responseCode = "404", description = "Character not found")
    })
    @DeleteMapping("/character/{id}")
    public ResponseEntity<?> delete(@PathVariable int id) {
        try {
            Character character = characterService.findCharacterId(id);
            characterService.deleteCharacter(id);
            return new ResponseEntity<>(character.getName() + " was deleted.", HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>("Character ID " + id + " not found.", HttpStatus.NOT_FOUND);
        }
    }

    private ResponseEntity<Object> handleValidationErrors(BindingResult bindingResult) {
        Map<String, String> errors = new HashMap<>();
        bindingResult.getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
        return ResponseEntity.badRequest().body(errors);
    }

}