package server;

import apps.pointApp.List;
import exception.MapperFileException;
import exception.MethodNotAllowedException;
import http.Headers;
import http.Response;
import http.StatusCode;
import http.Url;
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

        // Instantiating
        try {
            Class methodClass = Class.forName(className);
            Object classInstance = methodClass.newInstance();
            String methodName = "";
            switch (request.getMethod()){
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
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            if (e.getCause() instanceof MethodNotAllowedException) {
                response = new Response();
                response.setStatusCode(StatusCode.METHOD_NOT_ALLOWED);
                response.setBody(e.getCause().getMessage()+" not allowed");
            } else {
                System.out.println(e.getCause());
                e.printStackTrace();
            }
        } finally {
            if (response == null) {
                response = new Response();
                response.setStatusCode(StatusCode.INTERNAL_SERVER_ERROR);
                response.setBody("internal server error");
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
