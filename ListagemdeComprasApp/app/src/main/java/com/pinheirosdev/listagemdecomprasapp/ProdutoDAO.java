package com.pinheirosdev.listagemdecomprasapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.proto.ProtoOutputStream;

import java.util.ArrayList;
import java.util.List;

public class ProdutoDAO {
    private Conexao conexao;
    private SQLiteDatabase banco;

    public ProdutoDAO(Context context)
    {
        conexao = new Conexao(context);
        banco = conexao.getWritableDatabase();
    }

    public long inserir(Produto produto)
    {
        ContentValues values = new ContentValues();
        values.put("nome", produto.getNome());
        values.put("qtd", produto.getQtd());
        values.put("preco", produto.getPreco());
        return banco.insert("produto", null, values);
    }

    public List<Produto> obterTodos()
    {
        List<Produto> produtos = new ArrayList<>();
        Cursor cursor = banco.query("produto", new String[]{"id", "nome", "qtd", "preco"},
                null, null, null, null, null);
        while (cursor.moveToNext())
        {
            Produto p = new Produto();
            p.setId(cursor.getInt(0));
            p.setNome(cursor.getString(1));
            p.setQtd(cursor.getString(2));
            p.setPreco(cursor.getString(3));
            produtos.add(p);
        }
        return produtos;
    }

    public void excluir(Produto p)
    {
        banco.delete("produto", "id = ?", new String[]{p.getId().toString()});
    }

    public void atualizar(Produto produto)
    {
        ContentValues values = new ContentValues();
        values.put("nome", produto.getNome());
        values.put("qtd", produto.getQtd());
        values.put("preco", produto.getPreco());
        banco.update("produto", values, "id = ?", new String[]{produto.getId().toString()});
    }
}
