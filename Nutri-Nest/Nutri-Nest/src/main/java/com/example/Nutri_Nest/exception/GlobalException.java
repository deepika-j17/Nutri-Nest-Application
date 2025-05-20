package com.example.Nutri_Nest.exception;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalException {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<String> handleUserNotFoundException(UserNotFoundException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DietPlanNotFoundException.class)
    public ResponseEntity<String> handleDietPlanNotFoundException(DietPlanNotFoundException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(FitnessActivityNotFoundException.class)
    public ResponseEntity<String> handleFitnessNotFoundException(FitnessActivityNotFoundException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserProgressNotFoundException.class)
    public ResponseEntity<String> handleUserProgressNotFound(UserProgressNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<String> handleNullPointerException(NullPointerException ex) {
        return new ResponseEntity<>("Something went wrong. Possibly a null field: " + ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGeneralException(Exception exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleValidationErrors(MethodArgumentNotValidException ex) {
        String errorMsg = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .findFirst()
                .orElse("Validation error");

        return ResponseEntity.badRequest().body(errorMsg);
    }

//    @ExceptionHandler(HttpMessageNotReadableException.class)
//    public ResponseEntity<Map<String, String>> handleJsonParseError(HttpMessageNotReadableException ex) {
//        Map<String, String> response = new HashMap<>();
//        response.put("error", "Invalid input. Please check the field values. Example: goal should be one of [LOSS_WEIGHT, GAIN_WEIGHT, MAINTENANCE]");
//        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
//    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, String>> handleEnumCoercionError(HttpMessageNotReadableException ex) {
        Throwable cause = ex.getCause();
        Map<String, String> response = new HashMap<>();

        if (cause instanceof InvalidFormatException formatEx) {
            Class<?> targetType = formatEx.getTargetType();

            if (targetType.isEnum()) {
                String fieldName = extractFieldName(formatEx.getPathReference());
                String validValues = Arrays.toString(targetType.getEnumConstants());
                response.put("error", String.format(
                        "Invalid value for '%s'. Allowed values are: %s",
                        fieldName, validValues
                ));
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }
        }

        response.put("error", "Invalid input. Please check your request body.");
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    private String extractFieldName(String pathReference) {
        if (pathReference != null && pathReference.contains("\"")) {
            return pathReference.substring(pathReference.indexOf("\"") + 1, pathReference.lastIndexOf("\""));
        }
        return "field";
    }

    @ExceptionHandler(GoalReportNotFoundException.class)
    public ResponseEntity<String> handleGoalReportNotFound(GoalReportNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }
}



