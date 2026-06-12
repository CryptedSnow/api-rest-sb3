package com.sb3.apirestsb3.Repository;

import com.sb3.apirestsb3.Entity.Character;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CharacterRepository extends JpaRepository<Character, Integer> {
    Page<Character> findByNameContainingIgnoreCase(String name, Pageable pageable);
}