package server;

import exception.MapperFileException;
import http.RequestAnalyser;
import http.StatusCode;
import http.interfaces.RequestInterface;
import http.interfaces.ResponseInterface;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractServer implements ServerInterface{

    protected int port;
    protected ServerSocket serverSocket;
    protected List<Socket> clientSockets;

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
            System.out.print("Connection opened from ");
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Socket clientSocket = clientSockets.remove(0);

                        InetAddress ia = clientSocket.getInetAddress();
                        String _ip = clientSocket.getRemoteSocketAddress().toString();
                        String[] tab = _ip.split(":");
                        String ip = tab[0].substring(1);
                        System.out.println(ip);
                        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                        String inputLine, httpRequestText = "";
                        boolean hasBody = false, isReadingBody = false;
                        int contentLength = 0;
                        inputLine = in.readLine();
                        while (!inputLine.equals("") || (inputLine.equals("") && hasBody == true)) {
                            if(inputLine.contains("Content-Length:")) {
                                hasBody = true;
                                contentLength = Integer.parseInt(inputLine.split(": ")[1]);
                            }
                            if(inputLine.equals("")) {
                                hasBody = false;
                                isReadingBody = true;
                            }
                            if(isReadingBody){
                                char[] params = new char[contentLength];
                                isReadingBody = false;
                                in.read(params, 0, contentLength);
                                inputLine = new String(params);
                                httpRequestText += System.lineSeparator() + inputLine + System.lineSeparator();
                                break;
                            }else{
                                httpRequestText += inputLine + System.lineSeparator();
                                inputLine = in.readLine();
                            }
                        }
                        System.out.println("=============");
                        System.out.println(httpRequestText);
                        RequestInterface request = RequestAnalyser.analyse(httpRequestText);
                        request.setIp(ip);
                        ResponseInterface response = handleRequest(request);
                        out.println(response.toString());
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
