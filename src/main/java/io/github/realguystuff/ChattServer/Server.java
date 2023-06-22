package io.github.realguystuff.ChattServer;

import java.io.IOException; // libraries
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    // create serverSocket class
    private final ServerSocket serverSocket;

    // constructor of ServerSocket class
    public Server(ServerSocket serverSocket){
        this.serverSocket = serverSocket;
    }

    public void serverStart(){

        try{
            // check and loop the serverSocket
            while(!serverSocket.isClosed()){
                Socket socket = serverSocket.accept();
                System.out.println("New Friend Connected");
                // implemented an object which handle runnable class
                ClientHandler clientHandler = new ClientHandler(socket);

                Thread thread = new Thread(clientHandler);
                thread.start();
            }
        } catch (IOException e) {
            System.out.println("Error S1:");
            e.printStackTrace();
            System.out.println("Closing server...");
            closeServer();
        }
    }
    // this will close the server
    public void closeServer(){

        try{
            if(serverSocket != null){
                serverSocket.close();
            }
        } catch(IOException e){
            System.out.println("Error S2:");
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) throws Exception {
        System.out.println("Hosting ChattServer on localhost:5000...");
        ServerSocket serverSocket = new ServerSocket(5000);
        System.out.println("Creating server...");
        Server server = new Server(serverSocket);
        System.out.println("Starting server...");
        server.serverStart();
        System.out.println("Done!");
    }
}