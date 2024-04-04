package com.sb3.apirestsb3.Service;

import com.sb3.apirestsb3.DAO.CharacterDAO;
import com.sb3.apirestsb3.Model.CharacterModel;
import jakarta.persistence.*;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

import java.util.List;

@Service
public class CharacterService implements CharacterDAO {

    @PersistenceContext
    private EntityManager entityManager;

    public CharacterService(EntityManager theEntityManager) {
        this.entityManager = theEntityManager;
    }

    @Override
    public List<CharacterModel> index() {
        TypedQuery<CharacterModel> query = entityManager.createQuery("from CharacterModel", CharacterModel.class);
        return query.getResultList();
    }

    @Override
    @Transactional
    public CharacterModel create(CharacterModel theCharacterModel) {
        entityManager.persist(theCharacterModel);
        return theCharacterModel;
    }

    @Override
    public CharacterModel read(int id) {
        return entityManager.find(CharacterModel.class, id);
    }

    @Override
    public List<CharacterModel> search(String name) {
        String query_search = "SELECT c FROM CharacterModel c WHERE c.name LIKE :name";
        TypedQuery<CharacterModel> query = entityManager.createQuery(query_search, CharacterModel.class);
        query.setParameter("name", "%" + name + "%");
        return query.getResultList();
    }

    @Override
    @Transactional
    public CharacterModel update(CharacterModel theCharacterModel) {
        CharacterModel characterModel = entityManager.merge(theCharacterModel);
        return characterModel;
    }

    @Override
    @Transactional
    public void delete(int id) {
        CharacterModel characterModel = entityManager.find(CharacterModel.class,id);
        entityManager.remove(characterModel);
    }

}