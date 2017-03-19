package apps.todoList.setvlet;

import apps.todoList.setvlet.model.Session;
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
public class GetMemos implements ApplicationInterface{
    @Override
    public ApplicationResponseInterface doGet(RequestInterface request, SessionInterface session) {
        ApplicationResponseInterface response = new ApplicationResponse();
        try {
            Session s;
            int userId;
            JSONObject jsonResponse = new JSONObject();

            if (session != null) {
                if (session instanceof Session) {
                    s = (Session) session;
                    userId = s.getId();
                    jsonResponse = UserServices.getMemos(request.getParameter("userId"));
                } else {
                    jsonResponse.put("error", "Vous n'etes pas connecte");
                }
            } else {
                jsonResponse.put("error", "Vous n'etes pas connecte");
            }

            response.setBody(jsonResponse);
            response.setContentType(Headers.APPLICATION_JSON);
        } catch (JSONException e) {
            response.setBody(ResponseBuilder.serverResponse(StatusCode.INTERNAL_SERVER_ERROR, request.getUrl().getPath()));
            response.setContentType(Headers.TEXT_HTML);
            e.printStackTrace();
        }
        return response;
    }
}
