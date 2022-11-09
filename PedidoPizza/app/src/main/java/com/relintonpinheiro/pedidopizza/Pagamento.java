package com.relintonpinheiro.pedidopizza;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class Pagamento extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener {

Button btnVoltar;
double total;
String pizzas;
TextView txtPagar;
RadioGroup grupo;
RadioButton rbpix, rbdinheiro, rbcartao;
String saida;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagamento);

        grupo = findViewById(R.id.grupo);
        grupo.setOnCheckedChangeListener(this);
        rbdinheiro = findViewById(R.id.rbDindin);
        rbpix = findViewById(R.id.rbPix);
        rbcartao = findViewById(R.id.rbCartao);

        btnVoltar = findViewById(R.id.btVoltar);
        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        txtPagar = findViewById(R.id.edtpagar);
        Bundle parametros = getIntent().getExtras();
        if(parametros != null){
            total = parametros.getDouble("total");
            pizzas = parametros.getString("pizzas");
            txtPagar.setText(String.format("Total a Pagar $%5.2f", total));
        }
    }// fim onCreate

    public void onCheckedChanged(RadioGroup grupo, int i) {
        saida="";
        if (rbdinheiro.isChecked()) saida="Pagamento em Dinheiro";
        else if (rbpix.isChecked()) saida="Pagamento via Pix";
        else saida="Pagamento através de Cartão";
        AlertDialog.Builder alerta = new AlertDialog.Builder(this);
        alerta.setIcon(R.drawable.minipizza2);
        alerta.setTitle("Forma de Pagamento");
       // String textao = String.format("%s\nPreço R$%5.2f\nAs Pizzas Escolhidas foram\n %s",saida,total, pizzas);

        alerta.setMessage(""+saida+"\nPreço R$ "+ total + "\n\nAs Pizzas Escolhidas foram\n"+pizzas);
        alerta.setNeutralButton("OK",null);
        alerta.show();
    }
}