package client;

public class ExceptionResponse extends Exception {
    private final int StatusCode statusCode;

    public int getStatusCode(int statusCode, String message) {
        statusCode = statusCode.(message);


    }
}
