package com.sb3.apirestsb3.Api.Controller;

import com.sb3.apirestsb3.Entity.Character;
import com.sb3.apirestsb3.Service.CharacterService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class CharacterController {

    private final CharacterService characterService;

    @Autowired
    public CharacterController(CharacterService theCharacterService) {
        this.characterService = theCharacterService;
    }

    @GetMapping("/characters")
    public ResponseEntity<Object> index() {
        List<Character> characters = characterService.indexCharacters();
        if (characters.isEmpty()) {
            String message = "No characters found.";
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        } else {
            return ResponseEntity.ok(characters);
        }
    }

    @PostMapping("/character")
    public ResponseEntity<Object> create(@Valid @RequestBody Character character, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return handleValidationErrors(bindingResult);
        }
        Character createdCharacter = characterService.createCharacter(character);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCharacter);
    }

    @GetMapping("/character/search-character")
    public ResponseEntity<Object> searchCharacter(@RequestParam String name) {
        List<Character> characters = characterService.searchCharacter(name);
        if (characters.isEmpty()) {
            String message = "Character " + name + " not found.";
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        } else {
            return ResponseEntity.ok(characters);
        }
    }

    @GetMapping("/character/{id}")
    public ResponseEntity<Object> findId(@PathVariable int id) {
        Character character = characterService.findCharacterId(id);
        if (character != null) {
            return ResponseEntity.ok(character);
        } else {
            String message = "Character ID " + id + " not found.";
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/character/{id}")
    public ResponseEntity<Object> update(@PathVariable int id, @Valid @RequestBody Character character, BindingResult bindingResult) {
        Character existingCharacter = characterService.findCharacterId(id);
        if (existingCharacter == null) {
            String message = "Character ID " + id + " not found.";
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        }
        if (bindingResult.hasErrors()) {
            return handleValidationErrors(bindingResult);
        }
        character.setId(id);
        Character updatedCharacter = characterService.updateCharacter(character);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(updatedCharacter);
    }

    @PatchMapping("/character/{id}")
    public ResponseEntity<Object> partialUpdate(@PathVariable int id, @Valid @RequestBody Map<String, Object> patchUpdate) {
        try {
            Character updatedCharacter = characterService.updateCharacterPartially(id, patchUpdate);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(updatedCharacter);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid request.");
        }
    }

    @DeleteMapping("/character/{id}")
    public ResponseEntity<Object> delete(@PathVariable int id) {
        Character character = characterService.findCharacterId(id);
        if (character != null) {
            String name = character.getName();
            characterService.deleteCharacter(id);
            String message = name + " is deleted.";
            return new ResponseEntity<>(message, HttpStatus.OK);
        } else {
            String message = "Character ID " + id + " not found.";
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        }
    }

    private ResponseEntity<Object> handleValidationErrors(BindingResult bindingResult) {
        Map<String, String> errors = new HashMap<>();
        bindingResult.getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
        return ResponseEntity.badRequest().body(errors);
    }

}