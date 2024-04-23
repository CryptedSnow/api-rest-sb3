package com.sb3.apirestsb3.Service;

import com.sb3.apirestsb3.DAO.*;
import com.sb3.apirestsb3.Entity.*;
import jakarta.persistence.*;
import org.springframework.stereotype.*;
import jakarta.transaction.*;

import java.util.*;
import java.time.*;

@Service
public class CharacterService implements CharacterDAO {

    @PersistenceContext
    private EntityManager entityManager;

    public CharacterService(EntityManager theEntityManager) {
        this.entityManager = theEntityManager;
    }

    @Override
    public List<CharacterEntity> index() {
        TypedQuery<CharacterEntity> query = entityManager.createQuery("SELECT c FROM CharacterEntity c WHERE c.deleted_at IS NULL", CharacterEntity.class);
        return query.getResultList();
    }

    @Override
    @Transactional
    public CharacterEntity create(CharacterEntity theCharacterEntity) {
        entityManager.persist(theCharacterEntity);
        return theCharacterEntity;
    }

    @Override
    public CharacterEntity read(int id) {
        return entityManager.find(CharacterEntity.class, id);
    }

    @Override
    public List<CharacterEntity> search(String name) {
        String query_search = "SELECT c FROM CharacterEntity c WHERE c.name LIKE :name AND c.deleted_at IS NULL";
        TypedQuery<CharacterEntity> query = entityManager.createQuery(query_search, CharacterEntity.class);
        query.setParameter("name", "%" + name + "%");
        return query.getResultList();
    }

    @Override
    @Transactional
    public CharacterEntity update(CharacterEntity theCharacterEntity) {
        CharacterEntity characterEntity = entityManager.merge(theCharacterEntity);
        return characterEntity;
    }

    @Override
    @Transactional
    public void trash(int id) {
        CharacterEntity characterEntity = entityManager.find(CharacterEntity.class, id);
        if (characterEntity != null) {
            characterEntity.setDeleted_at(LocalDateTime.now());
            entityManager.merge(characterEntity);
        }
    }

    @Override
    public List<CharacterEntity> indexTrash() {
        TypedQuery<CharacterEntity> query = entityManager.createQuery("SELECT c FROM CharacterEntity c WHERE c.deleted_at IS NOT NULL", CharacterEntity.class);
        return query.getResultList();
    }

    @Override
    public List<CharacterEntity> searchTrash(String name) {
        String query_search = "SELECT c FROM CharacterEntity c WHERE c.name LIKE :name AND c.deleted_at IS NOT NULL";
        TypedQuery<CharacterEntity> query = entityManager.createQuery(query_search, CharacterEntity.class);
        query.setParameter("name", "%" + name + "%");
        return query.getResultList();
    }

    @Override
    @Transactional
    public CharacterEntity restore(int id) {
        CharacterEntity characterEntity = entityManager.find(CharacterEntity.class, id);
        if (characterEntity != null) {
            characterEntity.setDeleted_at(null);
            entityManager.persist(characterEntity);
        } else {
            throw new IllegalArgumentException("Register ID " + id + " not found.");
        }
        return characterEntity;
    }

    @Override
    @Transactional
    public void delete(int id) {
        CharacterEntity characterEntity = entityManager.find(CharacterEntity.class,id);
        entityManager.remove(characterEntity);
    }

}