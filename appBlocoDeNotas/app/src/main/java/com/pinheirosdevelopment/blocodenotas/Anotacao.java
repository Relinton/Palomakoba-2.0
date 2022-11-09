package com.pinheirosdevelopment.blocodenotas;

import android.content.Context;
import android.content.SharedPreferences;

public class Anotacao {
    private Context context;
    private SharedPreferences preferences;
    private final String ARQUIVO_PREFERENCIA = "anotacao";
    private final String CHAVE_ARQUIVO = "nome";
    private SharedPreferences.Editor editor;

    //Construtor da classe
    public Anotacao(Context context) {
        this.context = context;
        this.preferences = context.getSharedPreferences(ARQUIVO_PREFERENCIA, context.MODE_PRIVATE);
        this.editor = this.preferences.edit();
    }

    public void salvarAnotacao(String anotacao){
        this.editor.putString(CHAVE_ARQUIVO, anotacao);
        this.editor.commit();
    }

    public String getAnotacao(){
        return preferences.getString(CHAVE_ARQUIVO, "");
    }

    public void limparAnotacao(){
        this.editor.clear();
        this.editor.commit();
    }
}
