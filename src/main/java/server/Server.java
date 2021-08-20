package server;
import java.net.*;
import java.io.*;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

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

        private Map<String, String> codeMap;


        public ClientHandler(Socket socket){
            this.clientSocket = socket;
        }

        public void run(){
            PrintWriter out = null;
            BufferedReader bf = null;
            try{

                //writing to client
                out = new PrintWriter(clientSocket.getOutputStream());
                out.println("Connected to server.");
                out.println("Please specify absolut path for the code dictionary text file.");
                out.println("The format should be as: 'a:._', and a separate line for each letter");
                out.flush();


                //reading path from client
                InputStreamReader in = new InputStreamReader(clientSocket.getInputStream());
                bf = new BufferedReader(in);
                String line = null;
                while( (line = bf.readLine()) == null ){

                }
                //writing the received message from client
                System.out.println("Client sent: " + line);


                in = new InputStreamReader(clientSocket.getInputStream());
                bf = new BufferedReader(in);
                line = bf.readLine();
                String[] dictionary = line.split(",");
                codeMap = new HashMap<>();
                for(int i = 0; i< 26; i++){
                    String[] letterTokens = dictionary[i].split(":");
                    codeMap.put(letterTokens[0], letterTokens[1]);
                }

                System.out.println("Received dictionary:");
                for (String letter : codeMap.keySet()) {
                    System.out.println("letter: "+ letter + ", code: " + codeMap.get(letter));
                }

                out.println("Enter a path for text file to be encoded: ");
                out.flush();


                in = new InputStreamReader(clientSocket.getInputStream());
                bf = new BufferedReader(in);
                line = null;
                while( (line = bf.readLine()) == null ){

                }
                String res = "";
                while( (line = bf.readLine()) != null ){
                    res+= line;
                }
                System.out.println(res);

                res = encodeText(res);

                System.out.println(res);


            }catch (Exception e){
                e.printStackTrace();
            }
            finally {
                try{
                    if (out != null)
                        out.close();
                    if (bf != null) {
                        bf.close();
                        clientSocket.close();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }

        private String encodeText(String text) {
            text = text.toUpperCase();
            String res = "\n";
            String[] textTokens = text.split("\\s+");
            for (int i=0; i< textTokens.length; i++){

                for(int j = 0; j<textTokens[i].length(); j++){
                    if( !Character.isLetter(textTokens[i].charAt(j)))
                        continue;
                    String key = String.valueOf(textTokens[i].charAt(j)).toUpperCase();
                    res += codeMap.get(key) + " ";
                }

                res += "   ";
            }
            return res;
        }
    }
}
