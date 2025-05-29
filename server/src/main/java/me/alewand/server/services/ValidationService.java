package me.alewand.server.services;

import java.util.Set;

import jakarta.validation.Validator;

import org.springframework.stereotype.Service;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;

@Service
public class ValidationService {

    private final Validator validator;

    public ValidationService(Validator validator) {
        this.validator = validator;
    }

    /**
     * Validates the given object using the configured Validator.
     * Throws a ConstraintViolationException if there are validation errors.
     *
     * @param object the object to validate
     * @param <T>    the type of the object
     * @throws ConstraintViolationException if validation fails
     */
    public <T> void validate(T object) {
        Set<ConstraintViolation<T>> violations = validator.validate(object);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
    }

}
