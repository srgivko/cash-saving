package by.sivko.cashsaving.controllers;

import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
class ErrorHandlingControllerAdvice {

/*    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ValidationErrorResponse onConstraintValidationException(
            ConstraintViolationException e) {
        ValidationErrorResponse validationErrorResponse = new ValidationErrorResponse();
        for (ConstraintViolation violation : e.getConstraintViolations()) {
            validationErrorResponse.addViolation(violation.getPropertyPath().toString(), violation.getMessage());
        }
        return validationErrorResponse;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ValidationErrorResponse onMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        ValidationErrorResponse validationErrorResponse = new ValidationErrorResponse();
        for (FieldError fieldError : e.getBindingResult().getFieldErrors()) {
            validationErrorResponse.addViolation(fieldError.getField(), fieldError.getDefaultMessage());
        }
        return validationErrorResponse;
    }*/

}
