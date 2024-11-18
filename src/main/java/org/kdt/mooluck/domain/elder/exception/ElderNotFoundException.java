package org.kdt.mooluck.domain.elder.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ElderNotFoundException extends RuntimeException {
    public ElderNotFoundException(String message) {
        super(message);
    }
}
