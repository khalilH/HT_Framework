package server;

import exception.MapperFileException;
import exception.MethodNotAllowedException;
import http.*;
import http.interfaces.RequestInterface;
import http.interfaces.ResponseInterface;
import http.interfaces.SessionInterface;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
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
        Cookie idCookie = null;

        // Saving the cookies
        String userHash = CookieTable.sha256(request.getIp()+request.getHeader(Headers.USER_AGENT));
        boolean hasUniqueIdCookie = false;
        Cookie tmpCookie = null;
        String uniqueId = null;
        for(Cookie cookie : request.getCookies()) {
            if(cookie.getKey() == CookieTable.UNIQUE_ID) {
                hasUniqueIdCookie = true;
                uniqueId = cookie.getValue();
                break;
            }
        }

        List<Cookie> userCookies;
        SessionInterface session = null;

        // Associating the cookies to the userUniqueId if needed
        if(hasUniqueIdCookie) {
            if(CookieTable.cookieMap.containsKey(tmpCookie.getValue())){

                Cookie privateCookie = CookieTable.getUserCookies(uniqueId).get(0);
                if(privateCookie.getValue().equals(userHash)) {
                    SessionInterface _session = SessionTable.getUserSession(uniqueId);
                    if (session.isAlive()) {
                        session = _session;
                    }
                }else{
                    System.out.println("Fucking hacker, emptying cookie list");
                    idCookie = new Cookie(CookieTable.UNIQUE_ID, UUID.randomUUID().toString());
                    Cookie hashCookie = new Cookie(CookieTable.USER_HASH, userHash);
                    List<Cookie> cookieList = new ArrayList<>();
                    cookieList.add(0,hashCookie);
                    CookieTable.addCookiesToUser(idCookie.getValue(), cookieList);
                }

            }else{
                System.out.println("Fucking noob hacker, emptying cookie list");
                idCookie = new Cookie(CookieTable.UNIQUE_ID, UUID.randomUUID().toString());
                Cookie hashCookie = new Cookie(CookieTable.USER_HASH, userHash);
                List<Cookie> cookieList = new ArrayList<>();
                cookieList.add(0,hashCookie);
                CookieTable.addCookiesToUser(idCookie.getValue(), cookieList);
            }
        } else {
            idCookie = new Cookie(CookieTable.UNIQUE_ID, UUID.randomUUID().toString());
            Cookie hashCookie = new Cookie(CookieTable.USER_HASH, userHash);
            List<Cookie> requestCookiesClone = new ArrayList<>(request.getCookies());
            requestCookiesClone.add(0,hashCookie);
            CookieTable.addCookiesToUser(idCookie.getValue(), requestCookiesClone);
        }


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

                Method method = methodClass.getMethod(methodName, RequestInterface.class, SessionInterface.class);

                Object body = method.invoke(classInstance, request, session);
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
                response = ResponseBuilder.serverResponse(StatusCode.INTERNAL_SERVER_ERROR);
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
        if (idCookie != null) {
            response.addCookie(idCookie);
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
