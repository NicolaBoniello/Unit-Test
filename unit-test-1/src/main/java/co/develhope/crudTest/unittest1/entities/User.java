package co.develhope.crudTest.unittest1.entities;

import jakarta.persistence.*;

@Entity
@Table
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String surname;
    private Integer age;
    private boolean isActive;

    public User() {
    }

    public User(Long id, String name, String surname, Integer age, boolean isActive) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.age = age;
        this.isActive = isActive;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
