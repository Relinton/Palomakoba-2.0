package com.unir.listadecontatos.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.unir.listadecontatos.R;
import com.unir.listadecontatos.helper.ContatoDAO;
import com.unir.listadecontatos.model.Contato;

public class ContatoActivity extends AppCompatActivity {

    private TextInputEditText nomeContato, telefone, email;
    private Contato contatoAtual;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contato);
        nomeContato = findViewById(R.id.edtNomeContato);
        telefone = findViewById(R.id.edtTelefone);
        email = findViewById(R.id.edtEmail);
        Intent intent = getIntent();
        contatoAtual = (Contato) intent.getSerializableExtra("contatoSelecionado");
        if (contatoAtual != null){
            nomeContato.setText(contatoAtual.getNomeContato());
            telefone.setText(contatoAtual.getTelefone());
            email.setText(contatoAtual.getEmail());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.itemSalvar:
                ContatoDAO contatoDAO = new ContatoDAO(getApplicationContext());
                if (contatoAtual != null){
                    String nome = nomeContato.getText().toString();
                    String tel = telefone.getText().toString();
                    String mail = email.getText().toString();
                    Long id = contatoAtual.getId();
                    Contato contato = new Contato(nome, mail, tel, id);
                    if (contatoDAO.atualizar(contato)){
                        Toast.makeText(getApplicationContext(), "Contato atualizado!",
                                Toast.LENGTH_SHORT).show();
                        finish();
                    }else{
                        Toast.makeText(getApplicationContext(), "Erro ao atualizar Contato!",
                                Toast.LENGTH_SHORT).show();
                    }
                }else{
                    String nome = nomeContato.getText().toString();
                    String tel = telefone.getText().toString();
                    String mail = email.getText().toString();
                    Contato contato = new Contato();
                    contato.setNomeContato(nome);
                    contato.setEmail(mail);
                    contato.setTelefone(tel);
                    if (contatoDAO.salvar(contato)){
                        Toast.makeText(getApplicationContext(), "Contato salvo!",
                                Toast.LENGTH_SHORT).show();
                        finish();
                    }else{
                        Toast.makeText(getApplicationContext(), "Erro ao salvar Contato!",
                                Toast.LENGTH_SHORT).show();
                    }
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}