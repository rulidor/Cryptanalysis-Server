package server;
import java.net.*;
import java.io.*;

public class Server {
    public static void main(String[] args) throws IOException, InterruptedException {
        ServerSocket serverSocket = null;

        try {
            //server is listening on port 3000
            serverSocket = new ServerSocket(3000);
            serverSocket.setReuseAddress(true);

            //running infinite loop for getting client requests
            while(true){

                //socket object to receive incoming client requests
                Socket client = serverSocket.accept();

                //displaying that a new client is connected to server
                System.out.println("New client has connected: " + client.getInetAddress().getHostAddress());

                //create a new thread object
                ClientHandler clientHandler = new ClientHandler(client);

                //this thread will handle the client separately
                new Thread(clientHandler).start();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        finally {
            if(serverSocket != null){
                try{
                    serverSocket.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
    }

    private static class ClientHandler implements Runnable{
        private Socket clientSocket;

        public ClientHandler(Socket socket){
            this.clientSocket = socket;
        }

        public void run(){
            PrintWriter out = null;
            BufferedReader in = null;
            try{

                //get the output stream of client
                out = new PrintWriter(clientSocket.getOutputStream(), true);

                //get input stream of client
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

                String line;
                while( (line = in.readLine()) != null ){

                    //writing the received messagefrom client
                    System.out.println("Sent from client: " + line);
                    out.println(line);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
            finally {
                try{
                    if (out != null)
                        out.close();
                    if (in != null) {
                        in.close();
                        clientSocket.close();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }
}
