package ite.product.gearheadproduct.exception;

import org.springframework.http.HttpStatus;

public class ResourceNotFound extends ApiException{
    public ResourceNotFound(Long id , String resource) {
        super(HttpStatus.BAD_REQUEST , resource + id + "Not Found");
    }
}
