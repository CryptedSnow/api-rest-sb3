package com.sb3.apirestsb3.DAO;

import com.sb3.apirestsb3.Entity.Character;

import java.util.List;
import java.util.Map;

public interface CharacterDAO {

    List<Character> indexCharacters();
    Character createCharacter(Character character);
    List<Character> searchCharacter(String name);
    Character findCharacterId(int id);
    Character updateCharacter(Character character);
    Character updateCharacterPartially(int id, Map<String, Object> patchUpdate);
    void deleteCharacter(int id);

}