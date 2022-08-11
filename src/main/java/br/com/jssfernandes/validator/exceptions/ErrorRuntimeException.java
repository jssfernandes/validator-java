package br.com.jssfernandes.validator.exceptions;

public class ErrorRuntimeException extends RuntimeException {

    public ErrorRuntimeException(String message) {
        super(message);
    }

    public ErrorRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public ErrorRuntimeException(Throwable cause) {
        super(cause);
    }
}