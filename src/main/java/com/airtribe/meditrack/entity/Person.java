package com.airtribe.meditrack.entity;

import com.airtribe.meditrack.exception.InvalidDataException;
import com.airtribe.meditrack.interfaces.Identifiable;
import com.airtribe.meditrack.util.Validator;

/**
 * Abstract base class representing a Person in the MediTrack system.
 * Demonstrates encapsulation, abstraction, and inheritance.
 */
public abstract class Person implements Identifiable {

    private final String id;
    private String firstName;
    private String lastName;
    private String address;
    private int age;
    private Sex sex;

    // Static block for demonstration
    static {
        System.out.println("Person class loaded into JVM.");
    }

    public Person(String id, String firstName, String lastName, String address, int age, Sex sex) throws InvalidDataException {
        Validator.validateName(firstName, "First name");
        Validator.validateName(lastName, "Last name");
        Validator.validateAddress(address);
        Validator.validateAge(age);
        Validator.validateNotNull(sex, "Sex");

        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.age = age;
        this.sex = sex;
    }

    // Getters
    @Override
    public String getId() { return id; }

    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getFullName() { return firstName + " " + lastName; }
    public String getAddress() { return address; }
    public int getAge() { return age; }
    public Sex getSex() { return sex; }

    // Setters (id is immutable — no setter)
    public void setFirstName(String firstName) throws InvalidDataException {
        Validator.validateName(firstName, "First name");
        this.firstName = firstName;
    }

    public void setLastName(String lastName) throws InvalidDataException {
        Validator.validateName(lastName, "Last name");
        this.lastName = lastName;
    }

    public void setAddress(String address) throws InvalidDataException {
        Validator.validateAddress(address);
        this.address = address;
    }

    public void setAge(int age) throws InvalidDataException {
        Validator.validateAge(age);
        this.age = age;
    }

    public void setSex(Sex sex) throws InvalidDataException {
        Validator.validateNotNull(sex, "Sex");
        this.sex = sex;
    }

    @Override
    public String toString() {
        return String.format("ID: %s | Name: %s | Age: %d | Sex: %s | Address: %s",
                id, getFullName(), age, sex, address);
    }
}
