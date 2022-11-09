package com.pinheirosdev.listagemdecomprasapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class CadastroProdutoActivity extends AppCompatActivity {

    private EditText nome, qtd, preco;
    private ProdutoDAO dao;
    private Produto produto = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_produto);

        nome = findViewById(R.id.editProdutoNome);
        qtd = findViewById(R.id.editProdutoQtd);
        preco = findViewById(R.id.editProdutoPreco);
        dao = new ProdutoDAO(this);

        Intent it = getIntent();
        if (it.hasExtra("produto"))
        {
            produto = (Produto) it.getSerializableExtra("produto");
            nome.setText(produto.getNome());
            qtd.setText(produto.getQtd());
            preco.setText((int) Double.parseDouble(produto.getPreco().toString()));
        }
    }

    public void Salvar(View view) {
        if (produto == null) {
            produto = new Produto();
            produto.setNome(nome.getText().toString());
            produto.setQtd(qtd.getText().toString());
            produto.setPreco(preco.getText().toString());
            long id = dao.inserir(produto);
            Toast.makeText(this, "Produto com id: " + id + " inserido com sucesso", Toast.LENGTH_SHORT).show();
        }
        else
        {
            produto.setNome(nome.getText().toString());
            produto.setQtd(qtd.getText().toString());
            produto.setPreco(preco.getText().toString());
            dao.atualizar(produto);
            Toast.makeText(this, "Produto atualizado com sucesso", Toast.LENGTH_SHORT).show();
        }
    }
}