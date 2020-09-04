package com.example.contatos.model;

public class Contato {

    private Long id;
    private String nome;
    private String empresa;
    private String telefone;
    private String email;

    public Contato(String nome, String telefone, String email, String empresa ) {
        this.nome = nome;
        this.empresa = empresa;
        this.telefone = telefone;
        this.email = email;
    }

    public Contato(Long id, String nome, String telefone, String email, String empresa) {
        this.id = id;
        this.nome = nome;
        this.empresa = empresa;
        this.telefone = telefone;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
