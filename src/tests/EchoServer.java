package tests;

import http.Response;
import http.StatusCode;
import http.interfaces.RequestInterface;
import http.interfaces.ResponseInterface;
import jdk.nashorn.internal.ir.debug.JSONWriter;
import org.json.JSONException;
import org.json.JSONObject;
import server.AbstractServer;

import java.io.IOException;
import java.util.Map;

/**
 * Created by Patrick on 07/02/2017.
 */
public class EchoServer extends AbstractServer {


    public EchoServer(int port) {
        super(port);
    }

    @Override
    public ResponseInterface handleRequest(RequestInterface request) {
        String contentType = request.getHeaders().get("Content-Type");
        String ResponseBody = "";
        Map<String, String> headers = request.getHeaders();
        Map<String, String> cookies = request.getCookies();

        Response res = new Response();
        res.setStatusCode(StatusCode.OK);

        if(contentType != null) {
            switch (contentType) {
                case "text/html":
                    ResponseBody = "<table>\n" +
                            "  <tr>\n" +
                            "    <th>Clé</th>\n" +
                            "    <th>Valeur</th>\n" +
                            "    <th>Explication</th>\n" +
                            "  </tr>\n" +
                            "  <tr>\n" +
                            "    <td>URL</td>\n" +
                            "    <td>" + request.getUrl().toString() + "</td>\n" +
                            "    <td>URL à partir de laquelle la requête a été faite</td>\n" +
                            "  </tr>\n" +
                            "  <tr>\n" +
                            "    <td>Méthode</td>\n" +
                            "    <td>" + request.getMethod() + "</td>\n" +
                            "    <td>Type de requête HTTP</td>\n" +
                            "  </tr>\n";
                    for (String key : headers.keySet()) {
                        ResponseBody += "<tr>\n" +
                                "    <td>" + key + "</td>\n" +
                                "    <td>" + headers.get(key) + "</td>\n" +
                                "    <td>Header de la requête</td>\n" +
                                "  </tr>";
                    }
                    if (cookies != null) {
                        for (String key : cookies.keySet()) {
                            ResponseBody += "<tr>\n" +
                                    "    <td>" + key + "</td>\n" +
                                    "    <td>" + cookies.get(key) + "</td>\n" +
                                    "    <td>Un cookie</td>\n" +
                                    "  </tr>";
                        }
                    }
                    if (request.getBody() != null) {
                        ResponseBody += "<tr>\n" +
                                "    <td>Corps de la requete</td>\n" +
                                "    <td>" + request.getBody() + "</td>\n" +
                                "    <td>Le corps de la requete</td>\n" +
                                "  </tr>";
                    }
                    ResponseBody += "</table>";
                    break;
                case "application/json":
                    try {
                        JSONObject json = new JSONObject();
                        json.put("URL", request.getUrl().toString());
                        json.put("Method", request.getMethod());
                        json.put("Headers", request.getHeaders());
                        json.put("Cookies", request.getCookies());
                        if (request.getBody() != null)
                            json.put("Request Body", request.getBody());
                        ResponseBody = json.toString();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                default:
                    // text/plain & default
                    String route = request.getUrl().getEntirePath();
                    ResponseBody = request.getMethod() + " " + route + " HTTP/1.1\n";
                    for (String key : headers.keySet()) {
                        ResponseBody += key + ": " + headers.get(key) + "\n";
                    }
                    if (request.getBody() != null)
                        ResponseBody += "\n"+request.getBody()+"\n";
                    break;
            }
        }else{
            // text/plain & default
            String route = request.getUrl().getEntirePath();
            ResponseBody = request.getMethod() + " " + route + " HTTP/1.1\n";
            for (String key : headers.keySet()) {
                ResponseBody += key + ": " + headers.get(key) + "\n";
            }
            if (request.getBody() != null)
                ResponseBody += "\n"+request.getBody()+"\n";
        }

        res.setBody(ResponseBody);
        return res;
    }

    public static void main(String [] args){
        try {
            EchoServer echoServer = new EchoServer(80);
            echoServer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
