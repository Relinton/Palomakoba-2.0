package com.relintonpinheirodev.consultordeenderecos.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.relintonpinheirodev.consultordeenderecos.R;
import com.relintonpinheirodev.consultordeenderecos.model.Endereco;

import java.util.List;

public class EnderecoAdapter extends BaseAdapter {

    private List<Endereco> enderecos;
    private Activity activity;

    public EnderecoAdapter(Activity activity, List<Endereco> enderecos){
        this.activity = activity;
        this.enderecos = enderecos;
    }
    @Override
    public int getCount() {
        return enderecos.size();
    }

    @Override
    public Object getItem(int i) {
        return enderecos.get(i);
    }

    @Override
    public long getItemId(int i) {
        return enderecos.get(i).getId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v  = activity.getLayoutInflater().inflate(R.layout.item, viewGroup, false);
        TextView enderecoCompleto = v.findViewById(R.id.txt_enderecocompleto);
        Endereco endereco = enderecos.get(i);
        enderecoCompleto.setText(endereco.getEnderecoCompleto());

        return v;
    }
}
