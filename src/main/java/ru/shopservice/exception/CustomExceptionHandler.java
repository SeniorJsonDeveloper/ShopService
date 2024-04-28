package ru.shopservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ExceptionBody> handleNotFound(NotFoundException e,WebRequest request) {
        return handleException(HttpStatus.NOT_FOUND,e,request);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ExceptionBody> handleBadRequest(BadRequestException e,WebRequest request) {
        return handleException(HttpStatus.BAD_REQUEST,e,request);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionBody> handleException(Exception e,WebRequest request) {
        return handleException(HttpStatus.INTERNAL_SERVER_ERROR,e,request);
    }


    private static ResponseEntity<ExceptionBody> handleException(HttpStatus httpStatus, Exception e, WebRequest webRequest) {
        return ResponseEntity
                .status(httpStatus)
                .body(ExceptionBody
                        .builder()
                        .message(e.getMessage())
                        .description(webRequest.getDescription(false))
                        .build());
    }
}
