package com.sb3.apirestsb3.Api.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sb3.apirestsb3.Entity.Character;
import com.sb3.apirestsb3.Service.CharacterService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CharacterController.class)
class CharacterControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private CharacterService characterService;

    private Character createSampleCharacter() {
        Character character = new Character();
        character.setId(1);
        character.setName("Mardek Innanu El-Enkidu");
        character.setRace("Human");
        character.setGender("Male");
        character.setTypeClass("Recruit");
        character.setAge(18);
        character.setHeight(BigDecimal.valueOf(1.78f));
        character.setElement("Light");
        character.setOrigin("Goznor");
        character.setWeapon("Sword");
        character.setAlignment("Lawful Good");
        character.setAlive(true);
        return character;
    }

    @Test
    @DisplayName("GET: /api/characters → 200")
    void indexCharacters() throws Exception {
        when(characterService.indexCharacters(any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(createSampleCharacter())));
        mockMvc.perform(get("/api/characters"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].name").value("Mardek Innanu El-Enkidu"));
    }

    @Test
    @DisplayName("GET: /api/character/{id} → 200")
    void showCharacterId() throws Exception {
        when(characterService.findCharacterId(1)).thenReturn(createSampleCharacter());
        mockMvc.perform(get("/api/character/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Mardek Innanu El-Enkidu"))
                .andExpect(jsonPath("$.alive").value(true));
    }

    @Test
    @DisplayName("GET: /api/character/{id} → 404")
    void showCharacterDoesntExists() throws Exception {
        when(characterService.findCharacterId(999))
                .thenThrow(new RuntimeException("Character ID 999 not found."));
        mockMvc.perform(get("/api/character/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("POST: /api/character → 201")
    void createCharacter() throws Exception {
        Character character = createSampleCharacter();
        when(characterService.createCharacter(any(Character.class))).thenReturn(character);
        mockMvc.perform(post("/api/character")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(character)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Mardek Innanu El-Enkidu"));
    }

    @Test
    @DisplayName("POST: /api/character → 400")
    void createCharacterInvalidData() throws Exception {
        Character invalid = new Character();
        mockMvc.perform(post("/api/character")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalid)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("PUT: /api/character/{id} → 202")
    void updateCharacter() throws Exception {
        Character character = createSampleCharacter();
        when(characterService.findCharacterId(1)).thenReturn(character);
        when(characterService.updateCharacter(any(Character.class))).thenReturn(character);
        mockMvc.perform(put("/api/character/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(character)))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.name").value("Mardek Innanu El-Enkidu"));
    }

    @Test
    @DisplayName("PATCH: /api/character/{id} → 202")
    void partiallyUpdateCharacter() throws Exception {
        Character updated = createSampleCharacter();
        updated.setHeight(BigDecimal.valueOf(1.85f));
        when(characterService.updateCharacterPartially(eq(1), any(Map.class))).thenReturn(updated);
        mockMvc.perform(patch("/api/character/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"height\": 1.85}"))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.height").value(1.85));
    }

    @Test
    @DisplayName("DELETE: /api/character/{id} → 200")
    void deleteCharacter() throws Exception {
        when(characterService.findCharacterId(1)).thenReturn(createSampleCharacter());
        mockMvc.perform(delete("/api/character/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Mardek Innanu El-Enkidu was deleted."));
    }
}
