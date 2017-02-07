package server;

import http.interfaces.RequestInterface;
import http.interfaces.ResponseInterface;

import java.io.IOException;

/**
 * Created by patrick.tran on 07/02/2017.
 */
public interface ServerInterface {
    void start() throws IOException;
    void shutdown() throws IOException;
    ResponseInterface handleRequest(RequestInterface requestInterface);
}
