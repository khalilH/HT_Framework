package http;

import java.util.HashMap;
import java.util.Map;

/**
 http://www.iana.org/assignments/http-status-codes/http-status-codes.xhtml
 */


public class StatusCode {

    public static final int NOT_FOUND = 404;
    public static final int OK = 200;
    public static final int METHOD_NOT_ALLOWED = 405;
    public static final int INTERNAL_SERVER_ERROR = 500;


    private static Map<Integer, String> statusCodes;

    public static void init() {
        statusCodes = new HashMap<>();
        statusCodes.put(NOT_FOUND, "Not Found");
        statusCodes.put(OK, "OK");
        statusCodes.put(METHOD_NOT_ALLOWED, "Method Not Allowed");
        statusCodes.put(INTERNAL_SERVER_ERROR, "Internal Server Error");

    }

    public static String getDescription(int code) {
        return statusCodes.get(code);
    }

}
