package com.relintonpinheirodev.consultordeenderecos.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.relintonpinheirodev.consultordeenderecos.Permissoes.Permissoes;
import com.relintonpinheirodev.consultordeenderecos.R;
import com.relintonpinheirodev.consultordeenderecos.api.CEPService;
import com.relintonpinheirodev.consultordeenderecos.broadcast.BroadcastBateria;
import com.relintonpinheirodev.consultordeenderecos.helper.EnderecoDAO;
import com.relintonpinheirodev.consultordeenderecos.model.Endereco;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private BroadcastBateria bb = new BroadcastBateria();
    private Sensor acelerometro;
    private SensorManager sensorManager;
    private SensorEventListener listener;

    private LoadingDialog loadingDialog;

    private Button btnEncontrar, btnResultado;
    private EditText editCEP;
    private Retrofit retrofit;

    private String enderecoCompleto;
    private EnderecoDAO dao;
    private Endereco endereco = null;
    Double lat, lgtd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.getSupportActionBar().hide();
        inicializador();
        loadingDialog = new LoadingDialog(MainActivity.this);

        registerReceiver(bb, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        acelerometro = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        listener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
                if(sensorEvent.values[0] > 20){
                    finish();
                }
            }
            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {
            }
        };

        dao = new EnderecoDAO(this);

        Intent it = getIntent();
        if (it.hasExtra("endereco"))
        {
            endereco = (Endereco) it.getSerializableExtra("endereco");
        }

        String urlCep = "https://viacep.com.br/ws/";
        retrofit =  new Retrofit.Builder()
                .baseUrl(urlCep)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        btnEncontrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                encontrarCep();
                closeKeyboard();

                loadingDialog.startLoadingDialog();
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        loadingDialog.dismissDialog();
                    }
                }, 2000);
            }
        });
    }

    private void encontrarCep(){
        if(editCEP.getText().length() == 8){
            CEPService cepService = retrofit.create(CEPService.class);
            String cep = editCEP.getText().toString();
            Call<Endereco> call = cepService.recuperarCEP(cep);
            call.enqueue(new Callback<Endereco>() {
                @Override
                public void onResponse(Call<Endereco> call, Response<Endereco> response) {
                    if(response.isSuccessful()){
                        Endereco cep = response.body();
                        btnResultado.
                                setText(cep.getLogradouro()+", Bairro: "+cep.getBairro()+", Cidade: "+cep.getLocalidade()+", Estado: "+cep.getUf());
                        enderecoCompleto = "Cep: "+ cep.getCep()+", Estado: "+cep.getUf()+", Cidade: "+cep.getLocalidade()+", Bairro: "+cep.getBairro()+", "+cep.getLogradouro();

                        String searchString = enderecoCompleto;
                        Geocoder geocoder = new Geocoder(MainActivity.this);
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
                        }
                    }
                }

                @Override
                public void onFailure(Call<Endereco> call, Throwable t) {

                }
            });
        }
        else{
            Toast.makeText(this, "Cep inválido ou inexistente.", Toast.LENGTH_SHORT).show();
        }
    }

    public void Cadastrar(View view){
        if(enderecoCompleto != null){
            if (endereco == null) {
                endereco = new Endereco();
                endereco.setEnderecoCompleto(enderecoCompleto);
                long id = dao.inserir(endereco);
                loadingDialog.startLoadingDialog();
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        loadingDialog.dismissDialog();
                    }
                }, 2000);
                Toast.makeText(this, "Endereço com id: " + id + " inserido com sucesso", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), ListarEnderecosActivity.class);
                startActivity(intent);
            }
            else
            {
                endereco.setEnderecoCompleto(enderecoCompleto);
                dao.atualizar(endereco);
                Toast.makeText(this, "Endereço atualizado com sucesso", Toast.LENGTH_SHORT).show();
            }
        }
        else{
            Toast.makeText(this, "Não é possível cadastrar um endereço vazio", Toast.LENGTH_SHORT).show();
        }
    }

    public void VerLista(View view){
        loadingDialog.startLoadingDialog();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                loadingDialog.dismissDialog();
            }
        }, 2000);
        Intent intent = new Intent(getApplicationContext(), ListarEnderecosActivity.class);
        startActivity(intent);
    }

    public void VerMapa(View view){
        if (editCEP.getText().length() == 8){

            if(ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            }else{
                Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
                intent.putExtra("INFO_LAT", lat);
                intent.putExtra("INFO_LONG", lgtd);
                this.startActivity(intent);
            }
        } else{
            Toast.makeText(this, "Cep inválido ou inexistente.", Toast.LENGTH_SHORT).show();
        }
    }

    private void closeKeyboard() {
        View view = this.getCurrentFocus();
        if(view != null){
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    private void inicializador() {
        btnEncontrar = findViewById(R.id.btnEncontrar);
        btnResultado = findViewById(R.id.btnResultado);
        editCEP = findViewById(R.id.editCep);
    }

    @Override
    protected  void onResume(){
        super.onResume();
        sensorManager.registerListener(listener, acelerometro, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause(){
        super.onPause();
        sensorManager.unregisterListener(listener);
    }

}