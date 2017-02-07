package server;

import http.RequestAnalyser;
import http.interfaces.RequestInterface;
import http.interfaces.ResponseInterface;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractServer implements ServerInterface{

    private int port;
    private ServerSocket serverSocket;
    private List<Socket> clientSockets;

    public AbstractServer(int port) {
        this.port = port;
        clientSockets = new ArrayList<>();

    }

    public abstract ResponseInterface handleRequest(RequestInterface request);

    public void start() throws IOException {
        serverSocket = new ServerSocket(port);

        while (true) {
            clientSockets.add(serverSocket.accept());
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Socket clientSocket = clientSockets.remove(0);
                        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                        String inputLine, httpRequestText = "";
                        while ((inputLine = in.readLine()) != null) {
                            httpRequestText += inputLine;
                        }
                        RequestInterface request = RequestAnalyser.analyse(httpRequestText);
                        ResponseInterface response = handleRequest(request);
                        out.println(response.toString());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            });
            t.start();
        }
    }

    public void shutdown() throws IOException {
        serverSocket.close();
    }




}
