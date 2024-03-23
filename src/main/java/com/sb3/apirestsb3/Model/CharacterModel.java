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

    @NotBlank(message = "Class is required.")
    @Column(name="class_situation")
    private String class_situation;

    @NotNull(message = "Age is required.")
    @Min(value = 0, message = "The age must be a positive number.")
    @Column(name="age")
    private Integer age;

    @NotBlank(message = "Element is required.")
    @Column(name="element")
    private String element;

//    @NotNull(message = "Height is required.")
//    @DecimalMin(value = "0.0", inclusive = false, message = "A altura deve ser um número positivo.")
//    @Column(name="height")
//    private float height;

    public CharacterModel() {  // No-arg constructor for JPA

    }

    public CharacterModel(int id, String name, String race, String class_situation, Integer age, String element) {
        this.id = id;
        this.name = name;
        this.race = race;
        this.class_situation = class_situation;
        this.age = age;
        this.element = element;
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

    public String getClass_situation() {
        return class_situation;
    }

    public void setClass_situation(String class_situation) {
        this.class_situation = class_situation;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getElement() {
        return element;
    }

    public void setElement(String element) {
        this.element = element;
    }
}
