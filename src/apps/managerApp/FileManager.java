package apps.managerApp;

import http.ApplicationResponse;
import http.Headers;
import http.ResponseBuilder;
import http.StatusCode;
import http.interfaces.*;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Classe permettant de retourner une page html d'une application au client
 */
public class FileManager implements ApplicationInterface {

    @Override
    public ApplicationResponseInterface doGet(RequestInterface request, SessionInterface session) {
        Charset charset = StandardCharsets.UTF_8;
        String path = request.getUrl().getPath();
        path = path.replace("/", File.separator);
        path = "src" + File.separator + "apps" + File.separator + path;
        Path fileName = Paths.get(path);
        String[] tab = fileName.toString().split("\\.");
        String type = tab[tab.length-1];
        String content = "";
        try {
            content = new String(Files.readAllBytes(fileName), charset);
        } catch (IOException e) {
            e.printStackTrace();
        }
        ApplicationResponseInterface response;
        response = new ApplicationResponse();
        if (content.length() > 0) {
            response.setBody(content);
            if (type.equals("js")){
                response.setContentType(Headers.APPLICATION_JS);
                response.setBody(response.getBody()+"\n\n\n\n\n");
            } else if (type.equals("css")) {
                response.setContentType(Headers.TEXT_CSS);
            } else {
                response.setContentType(Headers.TEXT_HTML);
            }
        } else {
            ResponseInterface tmp = ResponseBuilder.serverResponse(StatusCode.NOT_FOUND, request.getUrl().getPath());
            response.setBody(tmp.getBody());
            response.setContentType(Headers.TEXT_HTML);
        }
        return response;
    }

}
