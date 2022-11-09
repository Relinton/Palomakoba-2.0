package com.pinheirosdev.listagemdecomprasapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;

import java.util.ArrayList;
import java.util.List;

public class ListarProdutosActivity extends AppCompatActivity {



    private ListView listView;
    private ProdutoDAO dao;
    private List<Produto> produtos;
    private List<Produto> produtosFiltrados = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_produtos);

        listView = findViewById(R.id.lista_produtos);
        dao = new ProdutoDAO(this);
        produtos = dao.obterTodos();
        produtosFiltrados.addAll(produtos);
        //ArrayAdapter<Produto> adaptador = new ArrayAdapter<Produto>(this, android.R.layout.simple_list_item_1, produtosFiltrados);
        ProdutoAdapter adaptador = new ProdutoAdapter(this, produtosFiltrados);
        listView.setAdapter(adaptador);
        if(adaptador.getCount() != 0)
        {
            registerForContextMenu(listView);
        }
        else
        {

        }
    }

    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater i = getMenuInflater();
        i.inflate(R.menu.menu_principal, menu);

        SearchView sv = (SearchView) menu.findItem(R.id.app_bar_search).getActionView();
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                //System.out.println("Digitou " + s);
                procurarPorProdutoSearch(s);
                return false;
            }
        });

        return true;
    }

    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo)
    {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater i = getMenuInflater();
        i.inflate(R.menu.menu_contexto, menu);
    }

    public void procurarPorProdutoSearch(String nome)
    {
        produtosFiltrados.clear();
        for (Produto p : produtos)
        {
            if (p.getNome().toLowerCase().contains(nome.toLowerCase()))
            {
                produtosFiltrados.add(p);
            }
        }
        listView.invalidateViews();
    }

    public void Excluir(MenuItem item)
    {
        AdapterView.AdapterContextMenuInfo menuInfo =
                (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        Produto produtoExcluir = produtosFiltrados.get(menuInfo.position);
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("Atenção")
                .setMessage("Realmente deseja excluir este produto?")
                .setNegativeButton("NÃO", null)
                .setPositiveButton("SIM", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i)
                    {
                        produtosFiltrados.remove(produtoExcluir);
                        produtos.remove(produtoExcluir);
                        dao.excluir(produtoExcluir);
                        listView.invalidateViews();
                    }
                }).create();
        dialog.show();
    }

    public void Cadastrar(MenuItem item)
    {
        Intent intent = new Intent(this, CadastroProdutoActivity.class);
        startActivity(intent);
    }

    public void Atualizar(MenuItem item)
    {
        AdapterView.AdapterContextMenuInfo menuInfo =
                (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        final Produto produtoAtualizar = produtosFiltrados.get(menuInfo.position);
        Intent intent = new Intent(this, CadastroProdutoActivity.class);
        intent.putExtra("produto", produtoAtualizar);
        startActivity(intent);
    }

    @Override
    public void onResume()
    {
        super.onResume();
        produtos = dao.obterTodos();
        produtosFiltrados.clear();
        produtosFiltrados.addAll(produtos);
        listView.invalidateViews();
    }
}