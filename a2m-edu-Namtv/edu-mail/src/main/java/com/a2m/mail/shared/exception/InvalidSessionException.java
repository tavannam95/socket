package com.a2m.mail.shared.exception;

public class InvalidSessionException extends HupaException{

    private static final long serialVersionUID = 995112620968798947L;

    public InvalidSessionException(String message) {
        super(message);
    }
}
