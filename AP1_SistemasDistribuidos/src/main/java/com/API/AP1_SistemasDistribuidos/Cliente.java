package com.API.AP1_SistemasDistribuidos;

import java.io.*;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Cliente {
    public static void main(String[] args) {
        String[] fileNames = {"dados.json" ,"dados.csv", "dados.xml", "dados.yml", "dados.toml"};
        String serverHost = "localhost";
        int serverPort = 8080;

        try (Socket socket = new Socket(serverHost, serverPort);
             OutputStream outputStream = socket.getOutputStream();
             BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream))) {

            for (String fileName : fileNames) {
                // Lê o conteúdo do arquivo
                String fileContent = new String(Files.readAllBytes(Paths.get("src/main/java/com/API/AP1_SistemasDistribuidos/files/" + fileName)));

                // Envia o nome do arquivo para identificar o formato
                writer.write(fileName + "\n");
                writer.flush();

                // Envia o conteúdo do arquivo
                writer.write(fileContent);
                writer.write("\nEND\n"); // Marca o final do arquivo
                writer.flush();

                // Ler toda a resposta do servidor até a próxima mensagem "END"
                String serverResponse;
                while ((serverResponse = reader.readLine()) != null && !serverResponse.equals("END")) {
                    System.out.println(serverResponse);
                }

            }


            socket.getKeepAlive();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
