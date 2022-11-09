package com.pinheirosdev.listagemdecomprasapp;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class ProdutoAdapter extends BaseAdapter {

    private List<Produto> produtos;
    private Activity activity;

    public ProdutoAdapter(Activity activity, List<Produto> produtos){
        this.activity = activity;
        this.produtos = produtos;
    }
    @Override
    public int getCount() {
        return produtos.size();
    }

    @Override
    public Object getItem(int i) {
        return produtos.get(i);
    }

    @Override
    public long getItemId(int i) {
        return produtos.get(i).getId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v  = activity.getLayoutInflater().inflate(R.layout.item, viewGroup, false);
        TextView nome = v.findViewById(R.id.txt_nome);
        TextView qtd = v.findViewById(R.id.txt_qtd);
        TextView preco = v.findViewById(R.id.txt_preco);
        Produto p = produtos.get(i);
        nome.setText(p.getNome());
        qtd.setText(p.getQtd());
        preco.setText(p.getPreco());

        return v;
    }
}
