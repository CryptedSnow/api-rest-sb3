package com.sb3.apirestsb3.DAO;

import com.sb3.apirestsb3.Entity.*;

import java.util.List;

public interface CharacterDAO {

    List<CharacterEntity> index();
    CharacterEntity create(CharacterEntity theCharacterEntity);
    CharacterEntity read(int id);
    List<CharacterEntity> search(String name);
    CharacterEntity update(CharacterEntity theCharacterEntity);
    void trash(int id);
    List<CharacterEntity> indexTrash();
    List<CharacterEntity> searchTrash(String name);
    CharacterEntity restore(int id);
    void delete(int id);

}
