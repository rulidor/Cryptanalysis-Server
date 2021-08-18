package client;
import java.net.*;
import java.io.*;
import java.util.Scanner;

public class Client {

    public static void main(String[] args) throws IOException{

        //start connection by providing host and port
        try(Socket socket = new Socket("localhost", 3000)){

            //writing to server
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            //reading from server
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            //object of scanner class
            Scanner scanner = new Scanner(System.in);
            String line = null;

            while(!"exit".equalsIgnoreCase(line)){

                //reading from user
                line = scanner.nextLine();

                //sending the user input to the server
                out.println(line);
                out.flush();

                //displaying server reply
                System.out.println("Server replied: " + in.readLine());
            }

            //closing scanner object
            scanner.close();

        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
