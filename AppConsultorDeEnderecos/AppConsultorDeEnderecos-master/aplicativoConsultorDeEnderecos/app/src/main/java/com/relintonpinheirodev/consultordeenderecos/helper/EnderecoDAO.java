package com.relintonpinheirodev.consultordeenderecos.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.relintonpinheirodev.consultordeenderecos.model.Endereco;

import java.util.ArrayList;
import java.util.List;

public class EnderecoDAO {
    private Conexao conexao;
    private SQLiteDatabase banco;

    public EnderecoDAO(Context context)
    {
        conexao = new Conexao(context);
        banco = conexao.getWritableDatabase();
    }

    public long inserir(Endereco endereco)
    {
        ContentValues values = new ContentValues();
        values.put("enderecoCompleto", endereco.getEnderecoCompleto());
        return banco.insert("endereco", null, values);
    }

    public List<Endereco> obterTodos()
    {
        List<Endereco> enderecos = new ArrayList<>();
        Cursor cursor = banco.query("endereco", new String[]{"id", "enderecoCompleto"},
                null, null, null, null, null);
        while (cursor.moveToNext())
        {
            Endereco endereco = new Endereco();
            endereco.setId(cursor.getInt(0));
            endereco.setEnderecoCompleto(cursor.getString(1));
            enderecos.add(endereco);
        }
        return enderecos;
    }

    public void excluir(Endereco endereco)
    {
        banco.delete("endereco", "id = ?", new String[]{endereco.getId().toString()});
    }

    public void atualizar(Endereco endereco)
    {
        ContentValues values = new ContentValues();
        values.put("enderecoCompleto", endereco.getEnderecoCompleto());
        banco.update("endereco", values, "id = ?", new String[]{endereco.getId().toString()});
    }
}

