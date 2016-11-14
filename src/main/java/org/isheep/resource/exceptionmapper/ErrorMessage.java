package org.isheep.resource.exceptionmapper;

import com.fasterxml.jackson.annotation.JsonCreator;
import org.springframework.http.HttpStatus;

import javax.servlet.http.HttpServletRequest;
import java.util.Calendar;

/**
 * Created by raymo on 09/11/2016.
 */
public class ErrorMessage {

    private Long timestamp;
    private Integer status = -1;
    private String error;
    private String message;
    private String path;

    @JsonCreator
    public ErrorMessage() {}

    public Long getTimestamp() {
        return timestamp;
    }

    public Integer getStatus() {
        return status;
    }

    public String getError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    public String getPath() {
        return path;
    }

    public static ErrorMessageBuilder builder() {
        return new ErrorMessageBuilder();
    }


    public static class ErrorMessageBuilder {
        private ErrorMessage errorMessage;

        private ErrorMessageBuilder() {
            this.errorMessage = new ErrorMessage();
        }

        public ErrorMessage build() {
            if (new Integer(-1).equals(errorMessage.status)) {
                throw new IllegalArgumentException("Cannot build an " + ErrorMessage.class.getName() + " without a status");
            }
            if (errorMessage.error == null || errorMessage.path.trim().length() == 0) {
                throw new IllegalArgumentException("Cannot build an " + ErrorMessage.class.getName() + " without an error");
            }
            if (errorMessage.path == null || errorMessage.path.trim().length() == 0) {
                throw new IllegalArgumentException("Cannot build an " + ErrorMessage.class.getName() + " without a path");
            }

            errorMessage.timestamp = Calendar.getInstance().getTimeInMillis();
            return errorMessage;
        }

        public ErrorMessageBuilder exception(Throwable e) {
            errorMessage.error = e.getClass().getName();
            errorMessage.message = e.getMessage();
            return this;
        }
        public ErrorMessageBuilder status(HttpStatus status) {
            errorMessage.status = status.value();
            return this;
        }
        public ErrorMessageBuilder request(HttpServletRequest request) {
            errorMessage.path = request.getRequestURI();
            return this;
        }

    }
}
