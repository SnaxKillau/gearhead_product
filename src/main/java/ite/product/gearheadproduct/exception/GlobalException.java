package ite.product.gearheadproduct.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalException {

    @ExceptionHandler(value = ApiException.class)
    public ResponseEntity<?> handleApiException (ApiException e){
    ErrorRespone errorRespone = new ErrorRespone(e.getStatus() , e.getMessage());
    return ResponseEntity.status(errorRespone.getStatus())
            .body(errorRespone.getMessage());
    }

}
