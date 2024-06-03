package com.sb3.apirestsb3.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.*;

@Entity @Table(name="characters") @Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class CharacterEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    @Column(name="id")
    private int id;

    @NotBlank(message = "Name is required.")
    @Getter(AccessLevel.NONE)
    @Column(name="name")
    private String name;

    @NotBlank(message = "Race is required.")
    @Size(max = 15, message = "Race must be deve in max 15 characters.")
    @Column(name="race")
    private String race;

    @NotBlank(message = "Gender is required.")
    @Size(max = 15, message = "Gender must be max 15 characters.")
    @Column(name="gender")
    private String gender;

    @NotBlank(message = "Class is required.")
    @Column(name="type_class")
    private String type_class;

    @NotNull(message = "Age is required.")
    @Min(value = 14, message = "Age must has min 14 years old.")
    @Column(name="age")
    private Integer age;

    @NotNull(message = "Height is required.")
    @DecimalMin(value = "1.50", message = "Height must has min 1.50m.")
    @DecimalMax(value = "2.50", message = "Height must has max 2.50m.")
    @Column(name="height")
    private Float height;

    @NotBlank(message = "Element is required.")
    @Size(max = 15, message = "Element must be max 15 characters.")
    @Column(name="element")
    private String element;

    @NotBlank(message = "Origin is required.")
    @Size(max = 15, message = "Origin must be max 15 characters.")
    @Column(name="origin")
    private String origin;

    @NotBlank(message = "Weapon is required.")
    @Size(max = 15, message = "Weapon must be max 15 characters.")
    @Column(name="weapon")
    private String weapon;

    @NotBlank(message = "Alignment is required.")
    @Size(max = 15, message = "Aligment must be max 15 characters.")
    @Column(name="alignment")
    private String alignment;

    @NotNull(message = "Alive status (boolean value) is required.")
    @Column(name="alive")
    private Boolean alive;

    @Setter(AccessLevel.NONE)
    @Column(name = "deleted_at")
    private LocalDateTime deleted_at;

    public String getName() { return name; }

    public void setId(int id) { this.id = id; }

    public void setDeletedAt(LocalDateTime deleted_at) { this.deleted_at = deleted_at; }
    
}