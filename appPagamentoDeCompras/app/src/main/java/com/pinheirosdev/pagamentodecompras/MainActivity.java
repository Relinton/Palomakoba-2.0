package com.pinheirosdev.pagamentodecompras;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    RadioGroup rdgrupocheck, rdgruporadio;
    CheckBox ckarroz, ckcarne, ckpao, ckleite, ckovos;
    RadioButton rbsemdesconto, rbdesc5porcento, rbdesc10porcento, rbdesc15porcento;
    Button btntotal, btnefetuarpagt;
    TextView txtvalortotal;
    EditText editvlrpago;
    double vlrbrutocomp, vlrpago, vlrtotalcompracomdesc, desconto, vlrdodesc, troco;
    String vlrdesconto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inicializarComponentes();

        btntotal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vlrbrutocomp = 0;
                if(ckarroz.isChecked()) vlrbrutocomp += 3.5;
                if(ckcarne.isChecked()) vlrbrutocomp += 12.3;
                if(ckpao.isChecked()) vlrbrutocomp += 2.2;
                if(ckleite.isChecked()) vlrbrutocomp += 5.5;
                if(ckovos.isChecked()) vlrbrutocomp += 7.5;

                txtvalortotal.setText("Valor: "+vlrbrutocomp);
            }
        });

        btnefetuarpagt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (rdgruporadio.getCheckedRadioButtonId()) {
                    case R.id.rbsemdesconto:
                        desconto = 0;
                        vlrdesconto = String.valueOf(0);
                        break;
                    case R.id.rb5porcento:
                        desconto = 0.05;
                        vlrdesconto = String.valueOf(5);
                        break;
                    case R.id.rb10porcento:
                        desconto = 0.1;
                        vlrdesconto = String.valueOf(10);
                        break;
                    case R.id.rb15porcento:
                        desconto = 0.15;
                        vlrdesconto = String.valueOf(15);
                        break;
                }

                vlrdodesc = vlrbrutocomp * desconto;
                vlrtotalcompracomdesc = vlrbrutocomp - vlrdodesc;
                if(!editvlrpago.getText().toString().isEmpty()){
                    vlrpago = Double.parseDouble(editvlrpago.getText().toString());
                }else{
                    vlrpago = 0;
                }
                troco = vlrpago - vlrtotalcompracomdesc;

                if (vlrpago == 0 ||
                        vlrpago < vlrtotalcompracomdesc) {
                    AlertDialog.Builder janela = new AlertDialog.Builder(MainActivity.this);
                    janela.setTitle("AVISO");
                    janela.setMessage("Valor incompatível com a compra!");
                    janela.setNeutralButton("OK", null);
                    janela.show();
                } else {
                    AlertDialog.Builder janela = new AlertDialog.Builder(MainActivity.this);
                    janela.setTitle("AVISO");
                    janela.setMessage("Valor total da compra: " + vlrtotalcompracomdesc +
                            "\nDesconto: " + vlrdesconto + "%" +
                            "\nValor pago: " + vlrpago +
                            "\nTroco: " + troco);
                    janela.setNeutralButton("OK", null);
                    janela.show();
                }
            }
        });
    }

    private void inicializarComponentes() {
        rdgrupocheck = findViewById(R.id.rdgrupocheck);
        ckarroz = findViewById(R.id.ckarroz);
        ckcarne = findViewById(R.id.ckcarne);
        ckpao = findViewById(R.id.ckpao);
        ckleite = findViewById(R.id.ckleite);
        ckovos = findViewById(R.id.ckovos);
        btntotal = findViewById(R.id.btntotal);
        txtvalortotal = findViewById(R.id.txtvalortotal);

        rdgruporadio = findViewById(R.id.rbgruporadio);
        rbsemdesconto = findViewById(R.id.rbsemdesconto);
        rbdesc5porcento = findViewById(R.id.rb5porcento);
        rbdesc10porcento = findViewById(R.id.rb10porcento);
        rbdesc15porcento = findViewById(R.id.rb15porcento);
        editvlrpago = findViewById(R.id.editvalorpago);
        btnefetuarpagt = findViewById(R.id.btnefetuarpagamento);
    }
}