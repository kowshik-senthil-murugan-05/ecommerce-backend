package com.ecommerce.app.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class Resp<T>
{

    public ResponseEntity<T> success(T message)
    {
        return new ResponseEntity<>(
                message,
                HttpStatus.OK
        );
    }

}
