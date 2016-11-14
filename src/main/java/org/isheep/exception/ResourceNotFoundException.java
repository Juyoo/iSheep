package org.isheep.exception;

/**
 * Created by anthony on 14/11/16.
 */
public class ResourceNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 7630602714135149912L;

    public ResourceNotFoundException(String s) {
        super(s);
    }

    public ResourceNotFoundException(String s, Throwable throwable) {
        super(s, throwable);
    }
}
