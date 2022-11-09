package com.unir.listadecontatos.helper;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.unir.listadecontatos.model.Contato;

import java.util.ArrayList;
import java.util.List;

public class ContatoDAO implements IContatoDAO{

    private SQLiteDatabase leitura;
    private SQLiteDatabase escrita;

    public ContatoDAO(Context context){
        DbHelper dbHelper = new DbHelper(context);
        leitura = dbHelper.getReadableDatabase();
        escrita = dbHelper.getWritableDatabase();
    }

    @Override
    public boolean salvar(Contato contato) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("nomeContato", contato.getNomeContato());
        contentValues.put("telefone", contato.getTelefone());
        contentValues.put("email", contato.getEmail());
        this.escrita.insert(DbHelper.TABELA_CONTATOS, null, contentValues);
        return true;
    }

    @Override
    public boolean atualizar(Contato contato) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("nomeContato", contato.getNomeContato());
        contentValues.put("telefone", contato.getTelefone());
        contentValues.put("email", contato.getEmail());
        String [] args = {String.valueOf(contato.getId())};
        this.escrita.update(DbHelper.TABELA_CONTATOS, contentValues, "id=?", args );
        return true;
    }

    @Override
    public boolean deletar(Contato contato) {
        String [] args = {String.valueOf(contato.getId())};
        this.escrita.delete(DbHelper.TABELA_CONTATOS, "id=?", args );
        return true;
    }

    @SuppressLint("Range")
    @Override
    public List<Contato> listar() {
        List<Contato> contatos = new ArrayList<>();
        String sql = "SELECT * FROM " + DbHelper.TABELA_CONTATOS + ";";
        Cursor cursor = leitura.rawQuery(sql, null);

        while(cursor.moveToNext()){
            Long id = cursor.getLong(cursor.getColumnIndex("id"));
            String nomeContato = cursor.getString(cursor.getColumnIndex("nomeContato"));
            String email = cursor.getString(cursor.getColumnIndex("email"));
            String telefone = cursor.getString(cursor.getColumnIndex("telefone"));
            contatos.add(new Contato(nomeContato, email, telefone, id));
        }

        return contatos;
    }
}
