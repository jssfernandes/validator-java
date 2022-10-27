package br.com.jssfernandes.validator.validators.exceptions;

public class BusinessException extends Exception {
    /**
     *
     */
    private static final long serialVersionUID = -7636294716193086181L;

    public BusinessException() {
        super();
    }

    public BusinessException(String arg0) {
        super(arg0);
    }

    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }
}
