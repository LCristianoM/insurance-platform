package com.leaocrist.insurance.presentation.common;

import com.leaocrist.insurance.application.customer.CustomerNotFoundException;
import com.leaocrist.insurance.application.policy.PolicyNotFoundException;
import com.leaocrist.insurance.application.policy.PolicyNumberAlreadyExistsException;
import com.leaocrist.insurance.application.risk.RiskNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,String>> handleValidationErrors(MethodArgumentNotValidException exception){
        Map<String, String> errors = new HashMap<>();

        exception.getBindingResult()
                .getFieldErrors()
                .forEach(error ->
                        errors.put(error.getField(), error.getDefaultMessage()));

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

    @ExceptionHandler(CustomerNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleCustomerNotFound(CustomerNotFoundException exception){

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse(exception.getMessage()));
    }

    /*@ExceptionHandler(RiskNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleRiskNotFound (RiskNotFoundException exception){
        Map<String, String> error = new HashMap<>();
        error.put("error", exception.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }*/
    @ExceptionHandler(RiskNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleRiskNotFound(
        RiskNotFoundException exception) {

    return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(new ErrorResponse(exception.getMessage()));
    }

    @ExceptionHandler(PolicyNotFoundException.class)
    public ResponseEntity<ErrorResponse> handlePolicyNotFound(PolicyNotFoundException exception){
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse(exception.getMessage()));
    }

    @ExceptionHandler({IllegalArgumentException.class, IllegalStateException.class})
    public ResponseEntity<Map<String, String>> handleBussinessRules(RuntimeException exception){
        Map<String, String> error = new HashMap<>();
        error.put("error", exception.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(PolicyNumberAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handlePolicyNumberAlreadyExists(PolicyNumberAlreadyExistsException exception){

        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new ErrorResponse(exception.getMessage()));
    }

}
