package com.sb3.apirestsb3.REST;

import com.sb3.apirestsb3.Entity.*;
import com.sb3.apirestsb3.Service.*;
import jakarta.validation.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.validation.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RequestMapping("/api")
@RestController
public class CharacterREST {

    private final CharacterService characterService;

    @Autowired
    public CharacterREST(CharacterService theCharacterService) {
        this.characterService = theCharacterService;
    }

    @GetMapping("/character")
    public ResponseEntity<Object> index() {
        List<CharacterEntity> characters = characterService.index();
        if (characters.isEmpty()) {
            String message = "No characters found.";
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        } else {
            return ResponseEntity.ok(characters);
        }
    }

    @PostMapping("/character")
    public ResponseEntity<Object> create(@Valid @RequestBody CharacterEntity characterEntity, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return handleValidationErrors(bindingResult);
        }
        CharacterEntity createdCharacter = characterService.create(characterEntity);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCharacter);
    }

    @GetMapping("/character/{id}")
    public ResponseEntity<Object> read(@PathVariable int id) {
        CharacterEntity character = characterService.read(id);
        if (character != null) {
            return ResponseEntity.ok(character);
        } else {
            String message = "ID " + id + " not found.";
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/character/search")
    public ResponseEntity<Object> searchByName(@RequestParam String name) {
        List<CharacterEntity> characters = characterService.search(name);
        if (characters.isEmpty()) {
            String message = "Character " + name + " not found.";
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        } else {
            return ResponseEntity.ok(characters);
        }
    }

    @PutMapping("/character/{id}")
    public ResponseEntity<Object> update(@PathVariable int id, @Valid @RequestBody CharacterEntity characterEntity, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return handleValidationErrors(bindingResult);
        }
        CharacterEntity existingCharacter = characterService.read(id);
        if (existingCharacter == null) {
            String message = "ID " + id + " not found.";
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        }
        characterEntity.setId(id);
        CharacterEntity updatedCharacter = characterService.update(characterEntity);
        return ResponseEntity.ok(updatedCharacter);
    }

    @GetMapping("/trash-character/{id}")
    public ResponseEntity<Object> trash(@PathVariable int id) {
        CharacterEntity character = characterService.read(id);
        if (character != null) {
            String name = character.getName();
            characterService.trash(id);
            String message = name + " is at trash.";
            return new ResponseEntity<>(message, HttpStatus.OK);
        } else {
            String message = "ID " + id + " not found.";
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/character-trash")
    public ResponseEntity<Object> indexTrash() {
        List<CharacterEntity> characters = characterService.indexTrash();
        if (characters.isEmpty()) {
            String message = "No characters deleted found.";
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        } else {
            return ResponseEntity.ok(characters);
        }
    }

    @GetMapping("/character/search-trash")
    public ResponseEntity<Object> searchByNameTrash(@RequestParam String name) {
        List<CharacterEntity> characters = characterService.searchTrash(name);
        if (characters.isEmpty()) {
            String message = "Character " + name + " not found.";
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        } else {
            return ResponseEntity.ok(characters);
        }
    }

    @GetMapping("/restore-character-trash/{id}")
    public ResponseEntity<Object> restore(@PathVariable int id) {
        CharacterEntity character = characterService.read(id);
        if (character != null) {
            String name = character.getName();
            characterService.restore(id);
            String message = name + " was restored.";
            return new ResponseEntity<>(message, HttpStatus.OK);
        } else {
            String message = "ID " + id + " not found to restore.";
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/character-delete/{id}")
    public ResponseEntity<Object> delete(@PathVariable int id) {
        CharacterEntity character = characterService.read(id);
        if (character != null) {
            String name = character.getName();
            characterService.delete(id);
            String message = name + " was deleted successfully.";
            return new ResponseEntity<>(message, HttpStatus.OK);
        } else {
            String message = "ID " + id + " not found to exclusion.";
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        }
    }

    private ResponseEntity<Object> handleValidationErrors(BindingResult bindingResult) {
        Map<String, String> errors = new HashMap<>();
        bindingResult.getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
        return ResponseEntity.badRequest().body(errors);
    }

}