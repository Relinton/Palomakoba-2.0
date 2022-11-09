package com.pinheirosdev.listagemdecomprasapp;

import android.text.Editable;

import java.io.Serializable;

public class Produto implements Serializable {

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getQtd() {
        return qtd;
    }

    public void setQtd(String qtd) {
        this.qtd = qtd;
    }

    public String getPreco() {
        return preco;
    }

    public void setPreco(String preco) {
        this.preco = preco;
    }

    private Integer id;
    private String nome;
    private String qtd;
    private String preco;




    @Override
    public String toString(){
        return nome;
    }
}
