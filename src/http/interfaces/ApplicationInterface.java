package http.interfaces;

/**
 * Created by Khalil on 14/02/2017.
 */
public interface ApplicationInterface {

    Object doGet(RequestInterface requestInterface);
    Object doPost(RequestInterface requestInterface);
    Object doPut(RequestInterface requestInterface);
    Object doDelete(RequestInterface requestInterface);

}
