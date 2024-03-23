package com.sb3.apirestsb3.REST;

import com.sb3.apirestsb3.Model.CharacterModel;
import com.sb3.apirestsb3.Service.CharacterService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RequestMapping("/api")
@RestController
public class CharacterREST {

    private final CharacterService characterServiceImplementation;

    @Autowired
    public CharacterREST(CharacterService characterServiceImplementation) {
        this.characterServiceImplementation = characterServiceImplementation;
    }

    @GetMapping("/character") // GET: localhost:8080/api/character
    public List<CharacterModel> index() {
        return characterServiceImplementation.index();
    }

    @PostMapping("/character") // POST: localhost:8080/api/character
    public ResponseEntity<Object> create(@Valid @RequestBody CharacterModel characterModel, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return handleValidationErrors(bindingResult);
        }
        CharacterModel createdCharacter = characterServiceImplementation.create(characterModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCharacter);
    }

    @GetMapping("/character/{id}") // GET: localhost:8080/api/character/{id}
    public CharacterModel read(@PathVariable int id) {
        return characterServiceImplementation.read(id);
    }

    @PutMapping("/character/{id}") // PUT: localhost:8080/api/character/{id}
    public ResponseEntity<Object> update(@PathVariable int id, @Valid @RequestBody CharacterModel characterModel, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return handleValidationErrors(bindingResult);
        }
        characterModel.setId(id);
        CharacterModel updatedCharacter = characterServiceImplementation.update(characterModel);
        return ResponseEntity.ok(updatedCharacter);
    }

    @DeleteMapping("/character/{id}") // DELETE: localhost:8080/api/character/{id}
    public void delete(@PathVariable int id) {
        characterServiceImplementation.delete(id);
    }

    private ResponseEntity<Object> handleValidationErrors(BindingResult bindingResult) {
        Map<String, String> errors = new HashMap<>();
        bindingResult.getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
        return ResponseEntity.badRequest().body(errors);
    }

}
