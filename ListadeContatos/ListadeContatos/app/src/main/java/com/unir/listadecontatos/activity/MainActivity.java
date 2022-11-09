package com.unir.listadecontatos.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.unir.listadecontatos.R;
import com.unir.listadecontatos.adapter.ContatoAdpater;
import com.unir.listadecontatos.databinding.ActivityMainBinding;
import com.unir.listadecontatos.helper.ContatoDAO;
import com.unir.listadecontatos.helper.RecyclerItemClickListener;
import com.unir.listadecontatos.model.Contato;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private RecyclerView recyclerView;
    private ContatoAdpater contatoAdpater;
    RecyclerView.LayoutManager layoutManager;
    private List<Contato> listaContatos = new ArrayList<Contato>();
    private Contato contatoSelecionado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        recyclerView = findViewById(R.id.recyclerview);

        setSupportActionBar(binding.toolbar);

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ContatoActivity.class);
                startActivity(intent);
            }
        });

        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getApplicationContext(),
                        recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        contatoSelecionado = listaContatos.get(position);
                        Intent intent = new Intent(MainActivity.this, ContatoActivity.class);
                        intent.putExtra("contatoSelecionado", contatoSelecionado);
                        startActivity(intent);
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {
                        contatoSelecionado = listaContatos.get(position);
                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        builder.setTitle("Confirmar exclusão");
                        builder.setMessage("Deseja confirmar a exlusão do contato: " + contatoSelecionado.getNomeContato()+"?");
                        builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                ContatoDAO contatoDAO = new ContatoDAO(getApplicationContext());
                                if (contatoDAO.deletar(contatoSelecionado)){
                                    carregarContatos();
                                    Toast.makeText(getApplicationContext(), "Contato exlucluído!",
                                            Toast.LENGTH_SHORT).show();
                                }else{
                                    Toast.makeText(getApplicationContext(), "Erro ao excluir contato!",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                        builder.setNegativeButton("Não", null);
                        builder.create().show();
                    }

                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                    }
                }));
    }

    public void carregarContatos(){
        ContatoDAO contatoDAO = new ContatoDAO(getApplicationContext());
        listaContatos = contatoDAO.listar();

        contatoAdpater = new ContatoAdpater(listaContatos);
        layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(),
                LinearLayout.VERTICAL));
        recyclerView.setAdapter(contatoAdpater);

    }

    @Override
    protected void onStart() {
        carregarContatos();
        super.onStart();
    }
}