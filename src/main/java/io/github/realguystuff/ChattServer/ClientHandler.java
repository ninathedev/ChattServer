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

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.net.Socket;

public class ClientHandler implements Runnable{
    public static ArrayList<ClientHandler>clientHandlers = new ArrayList<>();
    public Socket socket;
    private BufferedReader buffReader;
    private BufferedWriter buffWriter;
    private String name;

    public ClientHandler(Socket socket){
        // Constructors of all the private classes
        try{
            this.socket = socket;
            this.buffWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.buffReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.name = buffReader.readLine();
            clientHandlers.add(this);
            broadcastMessage("server: " + name + " has entered in the room");

        } catch(IOException e){
            System.err.println("Error C2:");
            e.printStackTrace();
            closeAll(socket, buffReader, buffWriter);
        }
    }
    // run method override
    @Override
    public void run() {

        String messageFromClient;

        while(socket.isConnected()){
            try{
                messageFromClient = buffReader.readLine();
                broadcastMessage(messageFromClient);
            } catch(IOException e){
                System.err.println("Error C1 (DO NOT REPORT):");
                e.printStackTrace();
                closeAll(socket, buffReader,  buffWriter);
                break;
            }
        }
    }
    public void broadcastMessage(String messageToSend){
        for(ClientHandler clientHandler: clientHandlers){
            try{
                if(!clientHandler.name.equals(name)){
                    clientHandler.buffWriter.write(messageToSend);
                    clientHandler.buffWriter.newLine();
                    clientHandler.buffWriter.flush();
                }
            } catch(IOException e){
                System.err.println("Error C3:");
                e.printStackTrace();
                closeAll(socket,buffReader, buffWriter);
            }
        }
    }
    // notify if the user left the chat
    public void removeClientHandler(){
        clientHandlers.remove(this);
        broadcastMessage("server: " + name + " has left the room");
    }

    public void closeAll(Socket socket, BufferedReader buffReader, BufferedWriter buffWriter){

        // handle the removeClient function
        removeClientHandler();
        try{
            if(buffReader!= null){
                buffReader.close();
            }
            if(buffWriter != null){
                buffWriter.close();
            }
            if(socket != null){
                socket.close();
            }
        } catch (IOException e){
            System.err.println("Error C4:");
            e.getStackTrace();
        }

    }

}
