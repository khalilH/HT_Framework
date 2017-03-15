package apps.todoList.setvlet;

import apps.todoList.setvlet.services.UserServices;
import http.ApplicationResponse;
import http.Headers;
import http.ResponseBuilder;
import http.StatusCode;
import http.interfaces.ApplicationInterface;
import http.interfaces.ApplicationResponseInterface;
import http.interfaces.RequestInterface;
import http.interfaces.SessionInterface;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by ladislas on 15/03/2017.
 */
public class GetUserInfo implements ApplicationInterface {
    @Override
    public ApplicationResponseInterface doGet(RequestInterface request, SessionInterface session) {
        ApplicationResponseInterface response = new ApplicationResponse();
        String HTMLResponse = UserServices.getUserInfoTemplate(request.getParameter("username"));
        response.setBody(HTMLResponse);
        response.setContentType(Headers.TEXT_HTML);
        return response;
    }
}
