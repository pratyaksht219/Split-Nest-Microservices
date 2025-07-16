package com.app.userservice.exceptions;

public class DuplicateResourcesException extends RuntimeException{
    String resourceName;
    String field;
    String fieldName;
    Long fieldId;

    public DuplicateResourcesException(String resourceName, String field, String fieldName ) {
        super(String.format("%s already exists with %s : '%s'", resourceName, field, fieldName));
        this.resourceName = resourceName;
        this.field = field;
        this.fieldName = fieldName;
    }

    public DuplicateResourcesException(String resourceName, String field, Long fieldId) {
        super(String.format("%s already exists with %s : '%d'", resourceName, field, fieldId));
        this.resourceName = resourceName;
        this.field = field;
        this.fieldId = fieldId;
    }

    public DuplicateResourcesException() {

    }
}
