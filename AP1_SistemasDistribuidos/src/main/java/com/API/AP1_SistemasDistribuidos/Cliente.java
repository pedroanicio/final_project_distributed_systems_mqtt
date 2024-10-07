package com.API.AP1_SistemasDistribuidos;

import java.net.Socket;

public class Cliente {
    public static void main(String[] args) {
        try{
            Socket socket = new Socket("localhost", 8000);

            String nome = "Pedro Anicio";
            String cpf = "12344567898";
            int idade = 21;
            String mensagem = "mensagem de teste";

        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }
}
