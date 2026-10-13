package ru.example.lab1.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.example.lab1.exception.UnsupportedCodeException;
import ru.example.lab1.exception.ValidationFailedException;
import ru.example.lab1.model.Request;
import ru.example.lab1.model.Response;
import ru.example.lab1.service.ValidationService;

import java.time.Instant;

@RestController
public class MyController {

    private static final Logger logger =
            LoggerFactory.getLogger(MyController.class);

    private final ValidationService validationService;

    public MyController(ValidationService validationService) {
        this.validationService = validationService;
    }

    @PostMapping("/feedback")
    public ResponseEntity<Response> feedback(
            @RequestBody Request request) {

        Response response = Response.builder()
                .uid(request.getUid())
                .operationUid(request.getOperationUid())
                .systemTime(Instant.now().toString())
                .code("success")
                .errorCode("")
                .errorMessage("")
                .build();

        try {
            validationService.validate(request);
            return ResponseEntity.ok(response);

        } catch (ValidationFailedException exception) {
            response.setCode("failed");
            response.setErrorCode("ValidationException");
            response.setErrorMessage(
                    "Ошибка валидации: " + exception.getMessage()
            );

            return ResponseEntity.badRequest().body(response);

        } catch (UnsupportedCodeException exception) {
            response.setCode("failed");
            response.setErrorCode("UnsupportedCodeException");
            response.setErrorMessage(exception.getMessage());

            return ResponseEntity.badRequest().body(response);

        } catch (Exception exception) {
            logger.error("Непредвиденная ошибка сервиса", exception);

            response.setCode("failed");
            response.setErrorCode("UnknownException");
            response.setErrorMessage(
                    "Произошла непредвиденная ошибка"
            );

            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(response);
        }
    }
}