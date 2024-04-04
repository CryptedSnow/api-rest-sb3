package com.sb3.apirestsb3.REST;

import com.sb3.apirestsb3.Model.CharacterModel;
import com.sb3.apirestsb3.Service.CharacterService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.validation.BindingResult;
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

    @GetMapping("/character") // GET: localhost:8080/api/character
    public ResponseEntity<Object> index() {
        List<CharacterModel> characters = characterService.index();
        if (characters.isEmpty()) {
            String message = "No characters found.";
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        } else {
            return ResponseEntity.ok(characters);
        }
    }

    @PostMapping("/character") // POST: localhost:8080/api/character
    public ResponseEntity<Object> create(@Valid @RequestBody CharacterModel characterModel, BindingResult bindingResult) {
        characterModel.setName(characterModel.getName().trim());
        characterModel.setRace(characterModel.getRace().trim());
        characterModel.setGender(characterModel.getGender().trim());
        characterModel.setType_class(characterModel.getType_class().trim());
        characterModel.setElement(characterModel.getElement().trim());
        characterModel.setOrigin(characterModel.getOrigin().trim());
        characterModel.setWeapon(characterModel.getWeapon().trim());
        characterModel.setAlignment(characterModel.getAlignment().trim());
        if (bindingResult.hasErrors()) {
            return handleValidationErrors(bindingResult);
        }
        CharacterModel createdCharacter = characterService.create(characterModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCharacter);
    }

    @GetMapping("/character/{id}") // GET: localhost:8080/api/character/id
    public ResponseEntity<Object> read(@PathVariable int id) {
        CharacterModel character = characterService.read(id);
        if (character != null) {
            return ResponseEntity.ok(character);
        } else {
            String message = "ID " + id + " not found.";
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/character/search") // GET: localhost:8080/api/character/search?name=NameCharacter
    public ResponseEntity<Object> searchByName(@RequestParam String name) {
        List<CharacterModel> characters = characterService.search(name);
        if (characters.isEmpty()) {
            String message = "Character " + name + " not found.";
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        } else {
            return ResponseEntity.ok(characters);
        }
    }

    @PutMapping("/character/{id}") // PUT: localhost:8080/api/character/id
    public ResponseEntity<Object> update(@PathVariable int id, @Valid @RequestBody CharacterModel characterModel, BindingResult bindingResult) {
        characterModel.setName(characterModel.getName().trim());
        characterModel.setRace(characterModel.getRace().trim());
        characterModel.setGender(characterModel.getGender().trim());
        characterModel.setType_class(characterModel.getType_class().trim());
        characterModel.setElement(characterModel.getElement().trim());
        characterModel.setOrigin(characterModel.getOrigin().trim());
        characterModel.setWeapon(characterModel.getWeapon().trim());
        characterModel.setAlignment(characterModel.getAlignment().trim());
        if (bindingResult.hasErrors()) {
            return handleValidationErrors(bindingResult);
        }
        CharacterModel existingCharacter = characterService.read(id);
        if (existingCharacter == null) {
            String message = "ID " + id + " not found.";
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        }
        characterModel.setId(id);
        CharacterModel updatedCharacter = characterService.update(characterModel);
        return ResponseEntity.ok(updatedCharacter);
    }

    @DeleteMapping("/character/{id}") // DELETE: localhost:8080/api/character/id
    public ResponseEntity<Object> delete(@PathVariable int id) {
        CharacterModel character = characterService.read(id);
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