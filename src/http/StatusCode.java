package http;

import java.util.HashMap;
import java.util.Map;

/**
 http://www.iana.org/assignments/http-status-codes/http-status-codes.xhtml
 */


public class StatusCode {

    public static final int NOT_FOUND = 404;
    public static final int OK = 200;

    private static Map<Integer, String> statusCodes;

    public static void init() {
        statusCodes = new HashMap<>();
        statusCodes.put(NOT_FOUND, "Not Found");
        statusCodes.put(OK, "OK");

    }

    public static String getDescription(int code) {
        return statusCodes.get(code);
    }

}
