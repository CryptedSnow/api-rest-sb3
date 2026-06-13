package com.sb3.apirestsb3.Entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name="characters")
public class Character {

    @Schema(accessMode = Schema.AccessMode.READ_ONLY, description = "Character ID")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @Schema(description = "Character name", example = "Elwyen Sirene Wu-Nympha")
    @NotBlank(message = "Name is required.")
    @Column(name="name")
    private String name;

    @Schema(description = "Character race", example = "Human")
    @NotBlank(message = "Race is required.")
    @Size(max = 15, message = "Race must be deve in max 15 characters.")
    @Column(name="race")
    private String race;

    @Schema(description = "Character gender", example = "Female")
    @NotBlank(message = "Gender is required.")
    @Size(max = 15, message = "Gender must be max 15 characters.")
    @Column(name="gender")
    private String gender;

    @Schema(description = "Character class", example = "Youngling")
    @NotBlank(message = "Class type is required.")
    @Column(name="type_class")
    private String typeClass;

    @Schema(description = "Character age (minimum 14)", example = "14")
    @NotNull(message = "Age is required.")
    @Min(value = 14, message = "Age must has min 14 years old.")
    @Column(name="age")
    private Integer age;

    @Schema(description = "Character height in meters (between 1.50 and 2.50)", example = "1.61")
    @NotNull(message = "Height is required.")
    @DecimalMin(value = "1.50", message = "Height must has min 1.50m.")
    @DecimalMax(value = "2.50", message = "Height must has max 2.50m.")
    @Column(name="height")
    private Float height;

    @Schema(description = "Character element", example = "Water")
    @NotBlank(message = "Element is required.")
    @Size(max = 15, message = "Element must be max 15 characters.")
    @Column(name="element")
    private String element;

    @Schema(description = "Character origin", example = "Canonia")
    @NotBlank(message = "Origin is required.")
    @Size(max = 15, message = "Origin must be max 15 characters.")
    @Column(name="origin")
    private String origin;

    @Schema(description = "Character weapon", example = "Harp")
    @NotBlank(message = "Weapon is required.")
    @Size(max = 15, message = "Weapon must be max 15 characters.")
    @Column(name="weapon")
    private String weapon;

    @Schema(description = "Character alignment", example = "Chaotic Neutral")
    @NotBlank(message = "Alignment is required.")
    @Size(max = 15, message = "Aligment must be max 15 characters.")
    @Column(name="alignment")
    private String alignment;

    @Schema(description = "Whether the character is alive", example = "true")
    @NotNull(message = "Alive status (boolean value) is required.")
    @Column(name="alive")
    private Boolean alive;

    public Character() {

    }

    public Character(int id, String name, String race, String gender, String typeClass, Integer age, Float height, String element, String origin, String weapon, String alignment, Boolean alive) {
        this.id = id;
        this.name = name;
        this.race = race;
        this.gender = gender;
        this.typeClass = typeClass;
        this.age = age;
        this.height = height;
        this.element = element;
        this.origin = origin;
        this.weapon = weapon;
        this.alignment = alignment;
        this.alive = alive;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRace() {
        return race;
    }

    public void setRace(String race) {
        this.race = race;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getTypeClass() {
        return typeClass;
    }

    public void setTypeClass(String typeClass) {
        this.typeClass = typeClass;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Float getHeight() {
        return height;
    }

    public void setHeight(Float height) {
        this.height = height;
    }

    public String getElement() {
        return element;
    }

    public void setElement(String element) {
        this.element = element;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getWeapon() {
        return weapon;
    }

    public void setWeapon(String weapon) {
        this.weapon = weapon;
    }

    public String getAlignment() {
        return alignment;
    }

    public void setAlignment(String alignment) {
        this.alignment = alignment;
    }

    public Boolean getAlive() {
        return alive;
    }

    public void setAlive(Boolean alive) {
        this.alive = alive;
    }
}