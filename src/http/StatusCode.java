package http;

import java.util.HashMap;
import java.util.Map;

/**
 http://www.iana.org/assignments/http-status-codes/http-status-codes.xhtml
 */


public class StatusCode {

    public static final int OK = 200;
    public static final int NOT_MODIFIED = 304;
    public static final int BAD_REQUEST = 400;
    public static final int UNAUTHORIZED = 401;
    public static final int FORBIDDEN = 403;
    public static final int NOT_FOUND = 404;
    public static final int METHOD_NOT_ALLOWED = 405;
    public static final int REQUEST_TIMEOUT = 408;
    public static final int LENGTH_REQUIRED = 411;
    public static final int INTERNAL_SERVER_ERROR = 500;
    public static final int NOT_IMPLEMENTED = 501;


    private static Map<Integer, String> statusCodes;

    public static void init() {
        statusCodes = new HashMap<>();
        statusCodes.put(OK, "OK");
        statusCodes.put(NOT_MODIFIED, "Not Modified");
        statusCodes.put(BAD_REQUEST, "Bad Request");
        statusCodes.put(UNAUTHORIZED, "Unauthorized");
        statusCodes.put(FORBIDDEN, "Forbidden");
        statusCodes.put(NOT_FOUND, "Not Found");
        statusCodes.put(METHOD_NOT_ALLOWED, "Method Not Allowed");
        statusCodes.put(REQUEST_TIMEOUT, "Request Timeout");
        statusCodes.put(LENGTH_REQUIRED, "Length-Required");
        statusCodes.put(INTERNAL_SERVER_ERROR, "Internal Server Error");
        statusCodes.put(NOT_IMPLEMENTED, "Not Implemented");

    }

    public static String getDescription(int code) {
        return statusCodes.get(code);
    }

}
