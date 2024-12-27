package com.API.AP1_SistemasDistribuidos;

public class Dados {
    private String nome;
    private String CPF;
    private int idade;
    private String mensagem;

    public String getNome() {
        return nome;
    }

    public void setNome(String Nome) {
        this.nome = Nome;
    }

    public String getCpf() {
        return CPF;
    }

    public void setCpf(String cpf) {
        this.CPF = cpf;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    @Override
    public String toString() {
        return "Dados{" +
                "nome='" + nome + '\'' +
                ", CPF='" + CPF + '\'' +
                ", idade=" + idade +
                ", mensagem='" + mensagem + '\'' +
                '}';
    }
}
