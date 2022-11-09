package com.pinheirosdev.nutrilife;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class MainActivity extends AppCompatActivity {

    EditText editNome, editPeso, editAltura;
    Button btnCalcular;
    RadioGroup grupo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editNome = findViewById(R.id.editNome);
        editPeso = findViewById(R.id.editPeso);
        editAltura = findViewById(R.id.editAltura);
        btnCalcular = findViewById(R.id.btnCalcular);
        grupo = findViewById(R.id.grupo);

        btnCalcular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                double imc;
                String msg;
                double peso = Double.parseDouble(editPeso.getText().toString());
                double altura = Double.parseDouble(editAltura.getText().toString());
                imc = peso/(altura * altura);

                int opcao = grupo.getCheckedRadioButtonId();
                if(opcao == R.id.radioMasculino){
                    if(imc < 20){
                        msg = "Abaixo do normal";
                    }else if((imc >= 20) && (imc <= 24.9)){
            msg = "Normal";
                    }else if((imc > 24.9) && (imc <= 29.9)){
            msg = "Obesidade leve";
                    } else if((imc > 29.9) && (imc <= 43)){
            msg = "Obesidade moderada";
                    }else msg = "Obesidade Morbida";
                }

                else {
                    if(imc < 19){
                        msg = "Abaixo do normal";
                    }else if((imc >= 19) && (imc <= 23.9)){
                        msg = "Normal";
                    }else if((imc > 23.9) && (imc <= 28.9)){
                        msg = "Obesidade leve";
                    }else if((imc > 28.9) && (imc <= 39)){
                        msg = "Obesidade moderada";
                    }else msg = "Obesidade Morbida";
                }
                AlertDialog.Builder janela = new AlertDialog.Builder(MainActivity.this);
                janela.setTitle(R.string.app_name);
                janela.setMessage(String.format("IMC = %2f\n %s\n", imc, msg));
                janela.setNeutralButton("OK", null);
                janela.show();

            }
        });


    }
}