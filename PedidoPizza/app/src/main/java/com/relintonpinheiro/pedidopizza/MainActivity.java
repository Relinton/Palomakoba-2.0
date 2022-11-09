package com.relintonpinheiro.pedidopizza;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button btnLimpar, btnPagar;
    double total;
    CheckBox ckcalabresa, ckpalmito, ckmargarita, ck4queijos, ckmodacasa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnLimpar = findViewById(R.id.btnLimpar);
        btnPagar = findViewById(R.id.btnPagar);
        ckcalabresa = findViewById(R.id.ckCalabresa);
        ckpalmito = findViewById(R.id.ckPalmito);
        ckmargarita = findViewById(R.id.ckMargarita);
        ck4queijos = findViewById(R.id.ck4Queijos);
        ckmodacasa = findViewById(R.id.ckModaCasa);

        btnLimpar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                total = 0;
                ckcalabresa.setChecked(false);
                ckpalmito.setChecked(false);
                ckmargarita.setChecked(false);
                ck4queijos.setChecked(false);
                ckmodacasa.setChecked(false);
            }
        });

        btnPagar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                total = 0;
                String txtPizza = "";
                if(ckcalabresa.isChecked()) {total += 70; txtPizza += "Calabresa\n"; }
                if(ckpalmito.isChecked()) {total += 70; txtPizza += "Palmito\n"; }
                if(ckmargarita.isChecked()) {total += 70; txtPizza += "Margarita\n"; }
                if(ck4queijos.isChecked()) {total += 85; txtPizza += "4 Queijos\n"; }
                if(ckmodacasa.isChecked()) {total += 85; txtPizza += "Moda da Casa\n"; }



                String msg = String.format("Total Pedido = $%5.2f", total);
                Toast.makeText(getBaseContext(),
                        msg, Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getBaseContext(), Pagamento.class);
                Bundle parametros = new Bundle();
                parametros.putDouble("total", total);
                parametros.putString("pizzas", txtPizza);
                intent.putExtras(parametros);
                startActivity(intent);
            }
        });
    }
}