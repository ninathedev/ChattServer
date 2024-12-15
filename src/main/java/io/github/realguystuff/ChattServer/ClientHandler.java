package io.github.realguystuff.ChattServer;

import java.io.*;
import java.net.Socket;
import java.security.PublicKey;
import java.security.KeyFactory;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;

public class ClientHandler implements Runnable {
    public static ArrayList<ClientHandler> clientHandlers = new ArrayList<>();
    public static HashMap<String, PublicKey> publicKeyMap = new HashMap<>();
    public Socket socket;
    private BufferedReader buffReader;
    private BufferedWriter buffWriter;
    private String name;

    public ClientHandler(Socket socket) {
        try {
            this.socket = socket;
            this.buffWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.buffReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String[] clientInfo = buffReader.readLine().split(" ", 2);
            this.name = clientInfo[0];
            String publicKeyString = clientInfo[1];
            PublicKey publicKey = getPublicKeyFromString(publicKeyString);
            publicKeyMap.put(name, publicKey);
            clientHandlers.add(this);
            broadcastMessage("server: " + name + " has entered the room");
        } catch (IOException e) {
            System.err.println("Error C2:");
            e.printStackTrace();
            closeAll(socket, buffReader, buffWriter);
        }
    }

    @Override
    public void run() {
        String messageFromClient;

        while (socket.isConnected()) {
            try {
                messageFromClient = buffReader.readLine();
                PublicKey publicKey = publicKeyMap.get(name);
                broadcastMessage(messageFromClient + " [PUBLICKEY: " + Base64.getEncoder().encodeToString(publicKey.getEncoded()) + "]");
            } catch (IOException e) {
                System.err.println("Error C1 (DO NOT REPORT):");
                e.printStackTrace();
                closeAll(socket, buffReader, buffWriter);
                break;
            }
        }
    }

    public void broadcastMessage(String messageToSend) {
        for (ClientHandler clientHandler : clientHandlers) {
            try {
                if (!clientHandler.name.equals(name)) {
                    clientHandler.buffWriter.write(messageToSend);
                    clientHandler.buffWriter.newLine();
                    clientHandler.buffWriter.flush();
                }
            } catch (IOException e) {
                System.err.println("Error C3:");
                e.printStackTrace();
                closeAll(socket, buffReader, buffWriter);
            }
        }
    }

    public void removeClientHandler() {
        clientHandlers.remove(this);
        publicKeyMap.remove(name);
        broadcastMessage("server: " + name + " has left the room");
    }

    public void closeAll(Socket socket, BufferedReader buffReader, BufferedWriter buffWriter) {
        removeClientHandler();
        try {
            if (buffReader != null) {
                buffReader.close();
            }
            if (buffWriter != null) {
                buffWriter.close();
            }
            if (socket != null) {
                socket.close();
            }
        } catch (IOException e) {
            System.err.println("Error C4:");
            e.printStackTrace();
        }
    }

    private PublicKey getPublicKeyFromString(String key) {
        try {
            byte[] byteKey = Base64.getDecoder().decode(key.getBytes());
            X509EncodedKeySpec X509publicKey = new X509EncodedKeySpec(byteKey);
            KeyFactory kf = KeyFactory.getInstance("RSA");
            return kf.generatePublic(X509publicKey);
        } catch (Exception e) {
            throw new RuntimeException("Error while generating public key from string", e);
        }
    }
}