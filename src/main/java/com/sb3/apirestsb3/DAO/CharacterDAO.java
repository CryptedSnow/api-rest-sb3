package com.sb3.apirestsb3.DAO;

import com.sb3.apirestsb3.Model.CharacterModel;

import java.util.List;

public interface CharacterDAO {

    List<CharacterModel> index();
    CharacterModel create(CharacterModel theCharacterModel);
    CharacterModel read(int id);
    CharacterModel update(CharacterModel theCharacterModel);
    void delete(int id);

}
