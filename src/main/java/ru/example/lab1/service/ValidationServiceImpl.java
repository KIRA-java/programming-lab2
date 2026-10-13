package ru.example.lab1.service;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.springframework.stereotype.Service;
import ru.example.lab1.exception.UnsupportedCodeException;
import ru.example.lab1.exception.ValidationFailedException;
import ru.example.lab1.model.Request;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ValidationServiceImpl implements ValidationService {

    private final Validator validator;

    public ValidationServiceImpl(Validator validator) {
        this.validator = validator;
    }

    @Override
    public void validate(Request request) {
        Set<ConstraintViolation<Request>> errors =
                validator.validate(request);

        if (!errors.isEmpty()) {
            String message = errors.stream()
                    .map(error -> error.getPropertyPath()
                            + ": " + error.getMessage())
                    .sorted()
                    .collect(Collectors.joining("; "));

            throw new ValidationFailedException(message);
        }

        if ("123".equals(request.getUid())) {
            throw new UnsupportedCodeException(
                    "Значение uid = 123 не поддерживается"
            );
        }
    }
}