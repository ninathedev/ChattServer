/*
 * ChattClient: A privacy-focused chat client.
 * Copyright (C) 2023  realguystuff
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 *
 * Email: realguybackup@gmail.com
 */

package io.github.realguystuff.ChattServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {
    // create serverSocket class
    private final ServerSocket serverSocket;

    // constructor of ServerSocket class
    public Server(ServerSocket serverSocket){
        this.serverSocket = serverSocket;
    }

    public void serverStart() {
        System.out.println("Server is up and running.");
        try {
            // check and loop the serverSocket
            while (!serverSocket.isClosed()) {
                Socket socket = serverSocket.accept();
                System.out.println("New Friend Connected");
                // implemented an object which handle runnable class
                ClientHandler clientHandler = new ClientHandler(socket);

                Thread thread = new Thread(clientHandler);
                thread.start();
            }

            Scanner scanner = new Scanner(System.in);
            String input;
            while (true) {
                input = scanner.nextLine();
                if (input.equalsIgnoreCase("stop")) {
                    closeServer();
                    break;
                }
            }
            scanner.close();
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
                System.out.println("Closing server...");
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
    }
}