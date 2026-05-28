package com.sb3.apirestsb3.Service;

import com.sb3.apirestsb3.DAO.CharacterDAO;
import com.sb3.apirestsb3.Entity.Character;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

@Service
public class CharacterService implements CharacterDAO {

    @PersistenceContext
    private EntityManager entityManager;

    public CharacterService(EntityManager theEntityManager) {
        this.entityManager = theEntityManager;
    }

    @Override
    public List<Character> indexCharacters() {
        TypedQuery<Character> query = entityManager.createQuery("SELECT character FROM Character character", Character.class);
        return query.getResultList();
    }

    @Override
    @Transactional
    public Character createCharacter(Character character) {
        entityManager.persist(character);
        return character;
    }

    @Override
    public Character findCharacterId(int id) {
        return entityManager.find(Character.class, id);
    }

    @Override
    public List<Character> searchCharacter(String name) {
        String querySearch = "SELECT character FROM Character character WHERE character.name LIKE :name";
        TypedQuery<Character> query = entityManager.createQuery(querySearch, Character.class);
        query.setParameter("name", "%" + name + "%");
        return query.getResultList();
    }

    @Override
    @Transactional
    public Character updateCharacter(Character character) {
        Character characterEntity = entityManager.merge(character);
        return characterEntity;
    }

    @Override
    @Transactional
    public Character updateCharacterPartially(int id, Map<String, Object> patchUpdate) {
        Character character = entityManager.find(Character.class, id);
        if (character == null) {
            throw new IllegalArgumentException("Character ID " + id + " not found.");
        }
        patchUpdate.forEach((field, value) -> {
            try {
                Field declaredField = Character.class.getDeclaredField(field);
                declaredField.setAccessible(true);
                declaredField.set(character, convertValueToUpdateCharacterPartially(value, declaredField.getType()));
            } catch (NoSuchFieldException | IllegalAccessException e) {
                throw new RuntimeException("Error updating field: " + field, e);
            }
        });
        return entityManager.merge(character);
    }

    @Override
    @Transactional
    public void deleteCharacter(int id) {
        Character character = entityManager.find(Character.class, id);
        entityManager.remove(character);
    }

    private Object convertValueToUpdateCharacterPartially(Object value, Class<?> targetType) {
        if (targetType == Integer.class || targetType == int.class) {
            return Integer.parseInt(value.toString());
        } else if (targetType == Float.class || targetType == float.class) {
            return Float.parseFloat(value.toString());
        } else if (targetType == Boolean.class || targetType == boolean.class) {
            return Boolean.parseBoolean(value.toString());
        } else {
            return value;
        }
    }

}