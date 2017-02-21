package server;

import apps.pointApp.List;
import exception.MapperFileException;
import exception.MethodNotAllowedException;
import http.*;
import http.interfaces.RequestInterface;
import http.interfaces.ResponseInterface;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.regex.Pattern;

/**
 * Vrai serveur http qui fait le router puis exeucte la methode a appeler
 */
public class HttpServer extends AbstractServer {

    private Router router;

    public HttpServer(int port) throws MapperFileException, IOException, SAXException, ParserConfigurationException {
        super(port);
        router = new Router();
        router.parseXML();
        router.route();
    }

    @Override
    public ResponseInterface handleRequest(RequestInterface request) {
        // Getting the class to instantiate depending on url path
        Url url = request.getUrl();
        String path = url.getPath();
        String className = "";
        ResponseInterface response = null;
        for(String pattern : router.getPatterns()){
            if(Pattern.matches(pattern, path))
                className = router.getMapping(pattern);
        }


        // Gestion favicon.ico
        if (path.equals("/favicon.ico")) {
            response = new Response();
            response.setStatusCode(StatusCode.OK);
        } else {
            // Instantiating
            try {
                Class methodClass = Class.forName(className);
                Object classInstance = methodClass.newInstance();
                String methodName = "";
                switch (request.getMethod()) {
                    case GET:
                        methodName = "doGet";
                        break;
                    case PUT:
                        methodName = "doPut";
                        break;
                    case POST:
                        methodName = "doPost";
                        break;
                    case DELETE:
                        methodName = "doDelete";
                        break;
                }

                Method method = methodClass.getMethod(methodName, RequestInterface.class);

                Object body = method.invoke(classInstance, request);
                response = new Response();
                response.setStatusCode(StatusCode.OK);
                response.setBody(body);
                response.addHeader(Headers.CONTENT_TYPE, request.getHeader(Headers.CONTENT_TYPE));
            } catch (ClassNotFoundException e) {
                response = new Response();
                response.setStatusCode(StatusCode.NOT_FOUND);
                //TODO faire avec la template
                response.setBody("<!DOCTYPE html><html><head><title>Halitran Framework v1.0- Rapport d''erreur</title><style type=\"text/css\">H1 {font-family:Tahoma,Arial,sans-serif;color:white;background-color:#525D76;font-size:22px;} H2 {font-family:Tahoma,Arial,sans-serif;color:white;background-color:#525D76;font-size:16px;} H3 {font-family:Tahoma,Arial,sans-serif;color:white;background-color:#525D76;font-size:14px;} BODY {font-family:Tahoma,Arial,sans-serif;color:black;background-color:white;} B {font-family:Tahoma,Arial,sans-serif;color:white;background-color:#525D76;} P {font-family:Tahoma,Arial,sans-serif;background:white;color:black;font-size:12px;}A {color : black;}A.name {color : black;}.line {height: 1px; background-color: #525D76; border: none;}</style> </head><body><h1>Etat HTTP 404 - "+request.getUrl().getPath()+"</h1><div class=\"line\"></div><p><b>type</b> Rapport d''état</p><p><b>message</b> <u>"+request.getUrl().getPath()+"</u></p><p><b>description</b> <u>La ressource demandée n''est pas disponible.</u></p><hr class=\"line\"><h3>Halitran Framework v1.0</h3></body></html>");
                response.addHeader(Headers.CONTENT_TYPE, Headers.TEXT_HTML);
                response.addHeader(Headers.CONTENT_LENGTH, response.getBody().toString().length()+"");
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                response = ResponseBuilder.serverResponse(StatusCode.INTERNAL_SERVER_ERROR);
            } catch (InstantiationException e) {
                e.printStackTrace();
                response = ResponseBuilder.serverResponse(StatusCode.INTERNAL_SERVER_ERROR);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace(); // TODO logs
                if (e.getCause() instanceof MethodNotAllowedException) {
                    response = ResponseBuilder.serverResponse(StatusCode.METHOD_NOT_ALLOWED);
                } else {
                    System.out.println(e.getCause()); //TODO
                    e.printStackTrace();
                }
            } finally {
                if (response == null) {
                    response = ResponseBuilder.serverResponse(StatusCode.INTERNAL_SERVER_ERROR);
                }
            }
        }

        return response;
    }


    public static void main(String[] args) {
        try {
            HttpServer httpServer = new HttpServer(80);
            httpServer.start();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}
