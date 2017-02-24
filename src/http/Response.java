package http;

import http.interfaces.ResponseInterface;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.*;

public class Response implements ResponseInterface{

    public Response() {
        headers = new HashMap<>();
        cookies = new ArrayList<>();
    }

    private int statusCode;
    private Map<String, String> headers;
    private List<Cookie> cookies;
    private Object body;

    @Override
    public void addHeader(String fieldName, String value) {
        headers.put(fieldName, value);
    }

    @Override
    public void setStatusCode(int statusCode) {
        this.statusCode = StatusCode.OK;
    }

    @Override
    public int getStatusCode(){ return this.statusCode; }

    @Override
    public void addCookie(Cookie cookie) {
        cookies.add(cookie);
    }

    public void setHeaders(Map<String, String> headers) { this.headers = headers; }




    public void setBody(Object body) {
        this.body = body;
        addHeader(Headers.CONTENT_LENGTH, getBody().toString().length()+"");
    }

    @Override
    public Object getBody(){ return this.body; }

    @Override
    public String toString() {
        String response = "HTTP/1.1 "+getStatusCode()+" "+StatusCode.getDescription(getStatusCode())+System.lineSeparator();
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss z");
        addHeader(Headers.DATE, dateFormat.format((new Date()).getTime()).toString());
        for (Map.Entry<String, String> entry : headers.entrySet()) {
            response += entry.getKey() + ": "+entry.getValue()+System.lineSeparator();
        }
        for (Cookie c : cookies) {
            response += c.toString()+System.lineSeparator();
        }
        response += System.lineSeparator();
        response += getBody()+System.lineSeparator();
        return response;
    }



}
