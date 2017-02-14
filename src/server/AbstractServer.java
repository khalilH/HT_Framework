package server;

import http.RequestAnalyser;
import http.StatusCode;
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
        StatusCode.init();
        this.port = port;
        clientSockets = new ArrayList<>();

    }

    public abstract ResponseInterface handleRequest(RequestInterface request);

    public void start() throws IOException {
        serverSocket = new ServerSocket(port);
        System.out.println("Listening on "+port+"...");
        while (true) {
            clientSockets.add(serverSocket.accept());
            System.out.println("Connection opened");
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Socket clientSocket = clientSockets.remove(0);
                        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                        String inputLine, httpRequestText = "";
                        while (!(inputLine = in.readLine()).equals("")) {
//                            System.out.println(inputLine);
                            httpRequestText += inputLine + "\n";
                        }
                        System.out.println("=============");
                        System.out.println(httpRequestText);
                        RequestInterface request = RequestAnalyser.analyse(httpRequestText);
                        ResponseInterface response = handleRequest(request);
                        out.println("HTTP/1.1 " + response.getStatusCode() + " OK");
                        out.println("\n\n" + response.getBody());
                        out.close();
                        in.close();
                        clientSocket.close();
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
