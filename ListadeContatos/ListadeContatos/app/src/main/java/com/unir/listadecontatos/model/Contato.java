package com.unir.listadecontatos.model;

import java.io.Serializable;

public class Contato implements Serializable {

    private String nomeContato;
    private String email;
    private String telefone;
    private long id;

    public Contato(String nomeContato, String email, String telefone, long id) {
        this.nomeContato = nomeContato;
        this.email = email;
        this.telefone = telefone;
        this.id = id;
    }
    public Contato(){

    }

    public String getNomeContato() {
        return nomeContato;
    }

    public void setNomeContato(String nomeContato) {
        this.nomeContato = nomeContato;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
