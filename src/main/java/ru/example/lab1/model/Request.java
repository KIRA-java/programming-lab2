package ru.example.lab1.model;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Request {

    @NotBlank(message = "uid обязателен")
    @Size(max = 32, message = "uid должен содержать не более 32 символов")
    private String uid;

    @NotBlank(message = "operationUid обязателен")
    @Size(max = 32, message = "operationUid должен содержать не более 32 символов")
    private String operationUid;

    private String systemName;

    @NotBlank(message = "systemTime обязателен")
    private String systemTime;

    private String source;

    @Min(value = 1, message = "communicationId должен быть не меньше 1")
    @Max(value = 100000, message = "communicationId должен быть не больше 100000")
    private int communicationId;

    private int templateId;

    private int productCode;

    private int smsCode;
}