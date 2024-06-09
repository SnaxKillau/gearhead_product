package ite.product.gearheadproduct.exception;

import org.springframework.http.HttpStatus;

public class ResourceNotFoundStringObject extends ApiException{
    public ResourceNotFoundStringObject(String message) {
        super(HttpStatus.NOT_FOUND, message);
    }
}
