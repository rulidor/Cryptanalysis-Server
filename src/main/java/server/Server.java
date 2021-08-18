package server;
import java.net.*;
import java.io.*;

public class Server {
    public static void main(String[] args) throws IOException, InterruptedException {
        ServerSocket serverSocket = new ServerSocket(3000);

        Socket socket = serverSocket.accept();

        System.out.println("client connn");
    }
}
