package com.sb3.apirestsb3.Service;

import com.sb3.apirestsb3.Repository.CharacterRepository;
import com.sb3.apirestsb3.Entity.Character;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.Map;

@Service
public class CharacterService {

    private final CharacterRepository characterRepository;

    public CharacterService(CharacterRepository characterRepository) {
        this.characterRepository = characterRepository;
    }

    public Page<Character> indexCharacters(Pageable pageable) {
        return characterRepository.findAll(pageable);
    }

    public Character createCharacter(Character character) {
        return characterRepository.save(character);
    }

    public Character findCharacterId(int id) {
        return characterRepository.findById(id).orElseThrow(() -> new RuntimeException("Character ID " + id + " not found."));
    }

    public Page<Character> searchCharacter(String name, Pageable pageable) {
        return characterRepository.findByNameContainingIgnoreCase(name, pageable);
    }

    public Character updateCharacter(Character character) {
        return characterRepository.save(character);
    }

    public Character updateCharacterPartially(int id, Map<String, Object> patchUpdate) {
        Character character = findCharacterId(id);
        patchUpdate.forEach((fieldName, value) -> {
            try {
                Field field = Character.class.getDeclaredField(fieldName);
                field.setAccessible(true);
                field.set(character, convertValue(value, field.getType()));
            } catch (NoSuchFieldException | IllegalAccessException e) {
                throw new RuntimeException("Error updating field: " + fieldName, e);
            }
        });
        return characterRepository.save(character);
    }

    public void deleteCharacter(int id) {
        characterRepository.deleteById(id);
    }

    private Object convertValue(Object value, Class<?> targetType) {
        if (targetType == Integer.class || targetType == int.class)
            return Integer.parseInt(value.toString());
        if (targetType == Float.class || targetType == float.class)
            return Float.parseFloat(value.toString());
        if (targetType == Boolean.class || targetType == boolean.class)
            return Boolean.parseBoolean(value.toString());
        return value;
    }

}