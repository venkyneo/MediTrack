package com.airtribe.meditrack.util;

import com.airtribe.meditrack.constants.Constants;
import com.airtribe.meditrack.exception.InvalidDataException;

/**
 * Centralized validation utility.
 * All validation rules live here — change in one place, applies everywhere.
 */
public class Validator {

    private Validator() {}

    public static void validateName(String name, String fieldName) throws InvalidDataException {
        if (name == null || name.trim().isEmpty()) {
            throw new InvalidDataException(fieldName + " cannot be null or empty.");
        }
        if (!name.matches("[a-zA-Z ]+")) {
            throw new InvalidDataException(fieldName + " must contain only alphabets.");
        }
    }

    public static void validateAge(int age) throws InvalidDataException {
        if (age < Constants.MIN_AGE || age > Constants.MAX_AGE) {
            throw new InvalidDataException("Age must be between " + Constants.MIN_AGE + " and " + Constants.MAX_AGE + ".");
        }
    }

    public static void validateExperience(int experience) throws InvalidDataException {
        if (experience < Constants.MIN_EXPERIENCE || experience > Constants.MAX_EXPERIENCE) {
            throw new InvalidDataException("Experience must be between " + Constants.MIN_EXPERIENCE + " and " + Constants.MAX_EXPERIENCE + " years.");
        }
    }

    public static void validateNotNull(Object obj, String fieldName) throws InvalidDataException {
        if (obj == null) {
            throw new InvalidDataException(fieldName + " cannot be null.");
        }
    }

    public static void validateAddress(String address) throws InvalidDataException {
        if (address == null || address.trim().isEmpty()) {
            throw new InvalidDataException("Address cannot be null or empty.");
        }
    }

    public static void validateFee(double fee) throws InvalidDataException {
        if (fee < 0) {
            throw new InvalidDataException("Fee cannot be negative.");
        }
    }
}
