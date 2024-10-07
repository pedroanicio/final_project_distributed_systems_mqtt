package com.API.AP1_SistemasDistribuidos;

import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

@Component
public class Servidor {
    public final int PORT = 8000;

    public void ServidorSocket(){
        startServer();
    }

    public void startServer(){
        try (ServerSocket serverSocket = new ServerSocket(PORT)){
            System.out.printf("Servidor iniciado! ");

            while (true){
                Socket clientSocket = serverSocket.accept();
                System.out.println("Cliente conectado: "+ clientSocket.getInetAddress().getHostAddress());

                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                String line;
                StringBuilder dadosRecebidos = new StringBuilder();

                while ((line = in.readLine()) != null){
                    dadosRecebidos.append(line).append("\n");
                }

            }

        }catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }
}
