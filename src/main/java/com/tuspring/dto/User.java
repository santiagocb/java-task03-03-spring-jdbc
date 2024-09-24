package com.tuspring.dto;

public class User {
    private long id;        // Auto-incremented in the database
    private String name;
    private String surname;
    private String birthdate;  // Store as a string in "YYYY-MM-DD" format

    public User() {}

    public User(String name, String surname, String birthdate) {
        this.name = name;
        this.surname = surname;
        this.birthdate = birthdate;
    }

    public User(long id, String name, String surname, String birthdate) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.birthdate = birthdate;
    }

    // Getters and Setters
    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    @Override
    public String toString() {
        return "dto.User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", birthdate='" + birthdate + '\'' +
                '}';
    }
}
