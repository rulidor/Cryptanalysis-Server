package client;
import java.net.*;
import java.io.*;
import java.util.Scanner;

public class Client {

    public static void main(String[] args) throws IOException{
        PrintWriter out = null;

        //start connection by providing host and port
        try(Socket socket = new Socket("localhost", 3000)){

//            //writing to server
            out = new PrintWriter(socket.getOutputStream(), true);

            //reading from server
            InputStreamReader in = new InputStreamReader(socket.getInputStream());
            BufferedReader bf = new BufferedReader(in);
            String line = null;

            while( (line = bf.readLine()) != null ){
                //writing the received message from server
                System.out.println("Server replied: " + line);
                if(line.equals("The format should be as: 'a:._', and a separate line for each letter"))
                    break;
            }
            //object of scanner class
            Scanner scanner = new Scanner(System.in);

            while(!"exit".equalsIgnoreCase(line)){

                //reading from user
                line = scanner.nextLine();

                out.println(line);
                out.flush();

                //Reading from file
                //
                // Create an instance of File for data.txt file.
                //
                File file = new File(line);
                String dictionary = "";

                try {
                    //
                    // Create a new Scanner object which will read the data from the
                    // file passed in. To check if there are more line to read from it
                    // we check by calling the scanner.hasNextLine() method. We then
                    // read line one by one till all line is read.
                    //

                    System.out.println("received path is: "+line);
                    Scanner fileScanner = new Scanner(file);
                    while (fileScanner.hasNextLine()) {
                        String fileLine = fileScanner.nextLine();
                        System.out.println("debug: fileLine is: "+ fileLine);
                        String[] lineTokens = fileLine.split(":");
                        dictionary += lineTokens[0] + ":" + lineTokens[1] + ",";
                    }

                    System.out.println("dictionary: " + dictionary);

                    //writing to server
                    out = new PrintWriter(socket.getOutputStream());
                    out.println(dictionary);
                    out.flush();

                    in = new InputStreamReader(socket.getInputStream());
                    bf = new BufferedReader(in);
                    line = bf.readLine();
                    System.out.println("Server replied: " + line);

                    //receiving path for text file and parsing it
                    String text = getPathForTxtFileAndParseIt();

                    out.print(text);
                    out.flush();
                    out.close();



                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }


            }

            //closing scanner object
            scanner.close();

        }catch (Exception e){
            e.printStackTrace();
        }


    }

    public static String getPathForTxtFileAndParseIt(){
        Scanner scanner = new Scanner(System.in);
        String path = scanner.nextLine();
        String res = "";

        File file = new File(path);

        try{
            Scanner fileScanner = new Scanner(file);

            while (fileScanner.hasNextLine()) {
                String fileLine = fileScanner.nextLine();
                res += fileLine + "\n";
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        System.out.println("debug: res is:\n");
        System.out.println(res);

        return res;

    }
}
