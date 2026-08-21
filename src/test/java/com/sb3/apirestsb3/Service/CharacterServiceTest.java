package com.sb3.apirestsb3.Service;

import com.sb3.apirestsb3.Entity.Character;
import com.sb3.apirestsb3.Repository.CharacterRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CharacterServiceTest {

    @Mock
    private CharacterRepository characterRepository;

    @InjectMocks
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
    @DisplayName("List characters")
    void indexCharacters() {
        Page<Character> page = new PageImpl<>(List.of(createSampleCharacter()));
        when(characterRepository.findAll(any(PageRequest.class))).thenReturn(page);
        Page<Character> result = characterService.indexCharacters(PageRequest.of(0, 10));
        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getContent().get(0).getName()).contains("Mardek");
    }

    @Test
    @DisplayName("Find character by existing ID")
    void findCharacterIdWhenExistsReturnsCharacter() {
        when(characterRepository.findById(1)).thenReturn(Optional.of(createSampleCharacter()));
        Character result = characterService.findCharacterId(1);
        assertThat(result.getName()).isEqualTo("Mardek Innanu El-Enkidu");
        assertThat(result.getAlive()).isTrue();
        verify(characterRepository).findById(1);
    }

    @Test
    @DisplayName("When don't find character ID")
    void findCharacterIdWhenNotExistsThrowsException() {
        when(characterRepository.findById(999)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> characterService.findCharacterId(999))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("not found");
    }

    @Test
    @DisplayName("Create a character")
    void createCharacter() {
        Character toSave = createSampleCharacter();
        toSave.setId(0);
        when(characterRepository.save(any(Character.class))).thenReturn(createSampleCharacter());
        Character result = characterService.createCharacter(toSave);
        assertThat(result.getId()).isEqualTo(1);
        assertThat(result.getName()).isEqualTo("Mardek Innanu El-Enkidu");
        verify(characterRepository).save(any(Character.class));
    }

    @Test
    @DisplayName("Update partially the character")
    void updateCharacterPartially() {
        Character existing = createSampleCharacter();
        when(characterRepository.findById(1)).thenReturn(Optional.of(existing));
        when(characterRepository.save(any(Character.class))).thenAnswer(inv -> inv.getArgument(0));
        Character result = characterService.updateCharacterPartially(1, Map.of("height", 1.85));
        assertThat(result.getHeight()).isEqualTo(1.85f);
        verify(characterRepository).save(any(Character.class));
    }

    @Test
    @DisplayName("Delete character")
    void deleteCharacter() {
        doNothing().when(characterRepository).deleteById(1);
        characterService.deleteCharacter(1);
        verify(characterRepository).deleteById(1);
    }

}
