package com.pinheirosdevelopment.blocodenotas;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.service.controls.actions.FloatAction;
import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.pinheirosdevelopment.blocodenotas.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;

    private Anotacao preferencias;
    private EditText editanotacao;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editanotacao = findViewById(R.id.editAnotacao);
        preferencias = new Anotacao(MainActivity.this);
        FloatingActionButton fab = findViewById(R.id.fab);

        String anotacao = preferencias.getAnotacao();
        if(!anotacao.isEmpty()){
            editanotacao.setText(anotacao);
        }

       fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String texto = editanotacao.getText().toString();

                if(texto.isEmpty()){
                    Snackbar.make(view, "Preencha a anotação!", Snackbar.LENGTH_LONG).show();
                }else{
                    preferencias.salvarAnotacao(texto);
                    Snackbar.make(view, "Anotação salva com sucesso", Snackbar.LENGTH_LONG)
                            .setAction("Desfazer", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    preferencias.limparAnotacao();
                                    editanotacao.setText("");
                                }
                            }).show();
                }
            }
        });
    }
}