package com.sb3.apirestsb3.Repository;

import com.sb3.apirestsb3.Entity.Character;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CharacterRepository extends JpaRepository<Character, Integer> {
    List<Character> findByNameContainingIgnoreCase(String name);
}