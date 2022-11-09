package com.relintonpinheirodev.consultordeenderecos.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.relintonpinheirodev.consultordeenderecos.R;
import com.relintonpinheirodev.consultordeenderecos.adapter.EnderecoAdapter;
import com.relintonpinheirodev.consultordeenderecos.helper.EnderecoDAO;
import com.relintonpinheirodev.consultordeenderecos.model.Endereco;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ListarEnderecosActivity extends AppCompatActivity {

    private ListView listView;
    private EnderecoDAO dao;
    private List<Endereco> enderecos;
    private List<Endereco>  enderecosFiltrados = new ArrayList<>();
    Double lat, lgtd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_enderecos);

        inicializador();
        dao = new EnderecoDAO(this);
        enderecos = dao.obterTodos();
        enderecosFiltrados.addAll(enderecos);
        //ArrayAdapter<Endereco> adaptador = new ArrayAdapter<Endereco>(this, android.R.layout.simple_list_item_1, enderecosFiltrados);
        EnderecoAdapter adaptador = new EnderecoAdapter(this, enderecosFiltrados);
        listView.setAdapter(adaptador);

        if(adaptador.getCount() != 0)
        {
            registerForContextMenu(listView);
        }
        else
        {
            Toast.makeText(this, "Sua lista de endereços está vazia", Toast.LENGTH_SHORT).show();
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick (AdapterView <?> adapter, View view,int posicao, long id){
                String enderecoCompleto = enderecosFiltrados.get(posicao).getEnderecoCompleto();
                String searchString = enderecoCompleto;
                Geocoder geocoder = new Geocoder(ListarEnderecosActivity.this);
                List<Address> list = new ArrayList<>();
                try {
                    list = geocoder.getFromLocationName(searchString, 1);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if(list.size() > 0){
                    Address address = list.get(0);

                    lat = address.getLatitude();
                    lgtd = address.getLongitude();
                    //Toast.makeText(getApplicationContext(),"Latitude "+ lat + "Longitude "+ lgtd, Toast.LENGTH_SHORT).show();
                }
                if(ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(ListarEnderecosActivity.this,
                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                }else{
                    Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
                    intent.putExtra("INFO_LAT", lat);
                    intent.putExtra("INFO_LONG", lgtd);
                    startActivity(intent);
                }
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Endereco enderecoExcluir = enderecosFiltrados.get(i);
                AlertDialog dialog = new AlertDialog.Builder(ListarEnderecosActivity.this)
                        .setTitle("Atenção")
                        .setMessage("Realmente deseja excluir este endereço?")
                        .setNegativeButton("NÃO", null)
                        .setPositiveButton("SIM", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i)
                            {
                                enderecosFiltrados.remove(enderecoExcluir);
                                enderecos.remove(enderecoExcluir);
                                dao.excluir(enderecoExcluir);
                                listView.invalidateViews();
                                Toast.makeText(getApplicationContext(), "Endereço excluido com sucesso", Toast.LENGTH_SHORT).show();
                            }
                        }).create();
                dialog.show();
                return true;
            }
        });
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
                procurarPorEnderecoSearch(s);
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

    public void procurarPorEnderecoSearch(String endereco)
    {
        enderecosFiltrados.clear();
        for (Endereco e : enderecos)
        {
            if (e.getEnderecoCompleto().toLowerCase().contains(endereco.toLowerCase()))
            {
                enderecosFiltrados.add(e);
            }
        }
        listView.invalidateViews();
    }

    public void Cadastrar(MenuItem item)
    {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void inicializador() {
        listView = findViewById(R.id.ltvlistenderecos);
    }

    @Override
    public void onResume()
    {
        super.onResume();
        enderecos = dao.obterTodos();
        enderecosFiltrados.clear();
        enderecosFiltrados.addAll(enderecos);
        listView.invalidateViews();
    }
}