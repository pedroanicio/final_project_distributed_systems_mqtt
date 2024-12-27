package com.API.AP1_SistemasDistribuidos;


import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.net.ServerSocket;

import java.net.Socket;
public class ServidorNew {
    public static void main(String[] args) {
        int port = 8080;

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Servidor escutando na porta " + port);

            while (true) {
                // Aceita a conexão do cliente
                Socket socket = serverSocket.accept();

                try (BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                     BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()))) {

                    String line;
                    while ((line = reader.readLine()) != null) {
                        StringBuilder fileContent = new StringBuilder();
                        String fileName = line;  // nome do arquivo para identificar o formato
                        System.out.println("Arquivo recebido: " + fileName);

                        while ((line = reader.readLine()) != null && !line.equals("END")) {
                            fileContent.append(line).append("\n");
                        }

                        System.out.println("Conteúdo:\n" + fileContent.toString());

                        // Lógica para desserializar de acordo com o formato
                        if (fileName.endsWith(".json")) {
                            writer.write("Arquivo JSON recebido\n");

                            ObjectMapper objectMapper = new ObjectMapper();
                            Dados dados = objectMapper.readValue(fileContent.toString(), Dados.class);

                            //writer.write(dados.toString() + "\n");
                            writer.write("Nome: " + dados.getNome() + "\n");
                            writer.write("CPF: " + dados.getCpf() + "\n");
                            writer.write("Idade: " + dados.getIdade() + "\n");
                            writer.write("Mensagem: " + dados.getMensagem() + "\n");
                            writer.flush();

                        } else if (fileName.endsWith(".csv")) {
                            writer.write("Arquivo CSV recebido\n");

                            // Formata a saída do CSV
                            String csvContent = fileContent.toString().trim();
                            String[] lines = csvContent.split("\n");

                            if (lines.length > 0) {
                                // Processa a primeira linha (cabeçalho) para obter as chaves
                                String[] headers = lines[0].split(",");

                                // Processa a segunda linha (dados)
                                if (lines.length > 1) {
                                    String[] values = lines[1].split(",");
                                    for (int i = 0; i < headers.length; i++) {
                                        String key = headers[i].trim(); // A chave do cabeçalho
                                        String value = values[i].trim().replace("\"", ""); // O valor correspondente, removendo as aspas
                                        // Exibe com a primeira letra maiúscula
                                        String formattedKey = key.substring(0, 1).toUpperCase() + key.substring(1);
                                        writer.write(formattedKey + ": " + value + "\n");
                                    }
                                }
                            }

                            writer.write("\n");
                        } else if (fileName.endsWith(".xml")) {
                            writer.write("Arquivo XML recebido\n");

                            // Formata a saída do XML
                            String xmlContent = fileContent.toString().trim();
                            // Remove a tag <dados> e </dados> do início e fim
                            xmlContent = xmlContent.replaceFirst("<dados>", "").replaceFirst("</dados>", "").trim();
                            String[] lines = xmlContent.split("\n");
                            for (String xmlLine : lines) {
                                if (xmlLine.contains(">")) {
                                    String key = xmlLine.substring(xmlLine.indexOf("<") + 1, xmlLine.indexOf(">")).trim(); // A chave entre as tags
                                    String value = xmlLine.substring(xmlLine.indexOf(">") + 1, xmlLine.lastIndexOf("<")).trim(); // O valor entre as tags
                                    // Exibe com a primeira letra maiúscula
                                    String formattedKey = key.substring(0, 1).toUpperCase() + key.substring(1);
                                    writer.write(formattedKey + ": " + value + "\n");
                                }
                            }
                            writer.write("\n");
                        } else if (fileName.endsWith(".yaml") || fileName.endsWith(".yml")) {
                            writer.write("Arquivo YAML recebido\n");

                            // Formata a saída do YAML
                            String yamlContent = fileContent.toString().trim();
                            String[] lines = yamlContent.split("\n");
                            for (String yamlLine : lines) {
                                if (yamlLine.contains(":")) {
                                    String[] keyValue = yamlLine.split(":");
                                    String key = keyValue[0].trim(); // A chave antes dos dois pontos
                                    String value = keyValue[1].trim().replace("\"", ""); // O valor após os dois pontos
                                    // Exibe com a primeira letra maiúscula
                                    String formattedKey = key.substring(0, 1).toUpperCase() + key.substring(1);
                                    writer.write(formattedKey + ": " + value + "\n");
                                }
                            }
                            writer.write("\n");
                        } else if (fileName.endsWith(".toml")) {
                            writer.write("Arquivo TOML recebido\n");

                            // Formata a saída do TOML
                            String tomlContent = fileContent.toString().trim();
                            String[] lines = tomlContent.split("\n");
                            for (String tomlLine : lines) {
                                if (tomlLine.contains("=")) {
                                    String[] keyValue = tomlLine.split("=");
                                    String key = keyValue[0].trim(); // A chave antes do igual
                                    String value = keyValue[1].trim().replace("\"", ""); // O valor após o igual
                                    // Exibe com a primeira letra maiúscula
                                    String formattedKey = key.substring(0, 1).toUpperCase() + key.substring(1);
                                    writer.write(formattedKey + ": " + value + "\n");
                                }
                            }
                        } else {
                            writer.write("Formato de arquivo não suportado: " + fileName + "\n");
                        }

                        writer.write("END\n");
                        writer.flush();  // Envia a resposta de volta ao cliente
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    // Fecha o socket após o processamento completo
                    socket.close();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}