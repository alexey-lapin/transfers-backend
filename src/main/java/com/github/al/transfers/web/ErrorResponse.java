package com.github.al.transfers.web;

public class ErrorResponse {

    private Error error;

    ErrorResponse() {
    }

    public ErrorResponse(String message) {
        error = new Error(message);
    }

    public Error getError() {
        return error;
    }

    public static class Error {

        Error() {
        }

        public Error(String message) {
            this.message = message;
        }

        private String message;

        public String getMessage() {
            return message;
        }
    }
}
