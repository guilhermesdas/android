package com.example.cadastro;

public class Usuario {

    String nome;
    String profissao;
    int idade;
    Sexo sexo;

    public Usuario(String nome, String profissao, int idade, Sexo sexo) {
        this.nome = nome;
        this.profissao = profissao;
        this.idade = idade;
        this.sexo = sexo;
    }

    public Sexo getSexo() {
        return sexo;
    }

    public void setSexo(Sexo sexo) {
        this.sexo = sexo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getProfissao() {
        return profissao;
    }

    public void setProfissao(String profissao) {
        this.profissao = profissao;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }
}
