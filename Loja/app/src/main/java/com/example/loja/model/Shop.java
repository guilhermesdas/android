package com.example.loja.model;

public class Shop {

    private int id;
    private int id_usuario;
    private int id_produto;
    private int qnt;

    public Shop(int id_usuario, int id_produto, int qnt) {
        this.id_usuario = id_usuario;
        this.id_produto = id_produto;
        this.qnt = qnt;
    }

    public Shop(int id, int id_usuario, int id_produto, int qnt) {
        this.id = id;
        this.id_usuario = id_usuario;
        this.id_produto = id_produto;
        this.qnt = qnt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    public int getId_produto() {
        return id_produto;
    }

    public void setId_produto(int id_produto) {
        this.id_produto = id_produto;
    }

    public int getQnt() {
        return qnt;
    }

    public void setQnt(int qnt) {
        this.qnt = qnt;
    }
}
