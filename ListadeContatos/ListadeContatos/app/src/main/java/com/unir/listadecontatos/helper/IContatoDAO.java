package com.unir.listadecontatos.helper;

import com.unir.listadecontatos.model.Contato;

import java.util.List;

public interface IContatoDAO {

    public boolean salvar(Contato contato);
    public boolean atualizar(Contato contato);
    public boolean deletar(Contato contato);
    public List<Contato> listar();
}
