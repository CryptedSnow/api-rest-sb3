package com.sb3.apirestsb3.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name="characters")
public class CharacterModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @NotBlank(message = "Name is required.")
    @Column(name="name")
    private String name;

    @NotBlank(message = "Race is required.")
    @Size(max = 10, message = "Race must be deve in max 10 characters.")
    @Column(name="race")
    private String race;

    @NotBlank(message = "Gender is required.")
    @Size(max = 10, message = "Gender must be max 10 characters.")
    @Column(name="gender")
    private String gender;

    @NotBlank(message = "Class is required.")
    @Column(name="type_class")
    private String type_class;

    @NotNull(message = "Age is required.")
    @Min(value = 10, message = "Age must has min 10 years old.")
    @Column(name="age")
    private Integer age;

    @NotNull(message = "Height is required.")
    @DecimalMin(value = "1.50", message = "Height must has min 1.50m.")
    @DecimalMax(value = "2.50", message = "Height must has max 2.50m.")
    @Column(name="height")
    private Float height;

    @NotBlank(message = "Element is required.")
    @Size(max = 10, message = "Element must be max 10 characters.")
    @Column(name="element")
    private String element;

    @NotBlank(message = "Origin is required.")
    @Size(max = 10, message = "Origin must be max 10 characters.")
    @Column(name="origin")
    private String origin;

    @NotBlank(message = "Weapon is required.")
    @Size(max = 10, message = "Weapon must be max 10 characters.")
    @Column(name="weapon")
    private String weapon;

    @NotBlank(message = "Alignment is required.")
    @Column(name="alignment")
    private String alignment;

    @NotNull(message = "Alive status (boolean value) is required.")
    @Column(name="alive")
    private Boolean alive;

    public CharacterModel() {  // No-arg constructor for JPA

    }

    public CharacterModel(int id, String name, String race, String gender, String type_class, Integer age, Float height, String element, String origin, String weapon, String alignment, Boolean alive) {
        this.id = id;
        this.name = name;
        this.race = race;
        this.gender = gender;
        this.type_class = type_class;
        this.age = age;
        this.height = height;
        this.element = element;
        this.origin = origin;
        this.weapon = weapon;
        this.alignment = alignment;
        this.alive = alive;
    }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getRace() { return race; }

    public void setRace(String race) { this.race = race; }

    public String getGender() { return gender; }

    public void setGender(String gender) { this.gender = gender; }

    public String getType_class() { return type_class; }

    public void setType_class(String type_class) { this.type_class = type_class; }

    public Integer getAge() { return age; }

    public void setAge(Integer age) { this.age = age; }

    public Float getHeight() { return height; }

    public void setHeight(Float height) { this.height = height; }

    public String getElement() { return element; }

    public void setElement(String element) { this.element = element; }

    public String getOrigin() { return origin; }

    public void setOrigin(String origin) { this.origin = origin; }

    public String getWeapon() { return weapon; }

    public void setWeapon(String weapon) { this.weapon = weapon; }

    public String getAlignment() { return alignment; }

    public void setAlignment(String alignment) { this.alignment = alignment; }

    public Boolean getAlive() { return alive; }

    public void setAlive(Boolean alive) { this.alive = alive; }
}