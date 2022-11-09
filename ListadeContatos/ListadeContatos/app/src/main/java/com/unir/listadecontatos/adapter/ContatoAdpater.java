package com.unir.listadecontatos.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.unir.listadecontatos.R;
import com.unir.listadecontatos.model.Contato;

import java.util.ArrayList;
import java.util.List;

public class ContatoAdpater extends RecyclerView.Adapter<ContatoAdpater.MyViewHolder> {

    private List<Contato> contatos = new ArrayList<Contato>();

    public ContatoAdpater(List<Contato> lista){
        this.contatos = lista;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemLista = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.contatos_adapter_view, parent, false);

        return new MyViewHolder(itemLista);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.nomeContato.setText(this.contatos.get(position).getNomeContato());
        holder.email.setText(this.contatos.get(position).getEmail());
        holder.telefone.setText(this.contatos.get(position).getTelefone());
    }

    @Override
    public int getItemCount() {
        return this.contatos.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView nomeContato, email, telefone;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            nomeContato = itemView.findViewById(R.id.txtNome);
            email = itemView.findViewById(R.id.txtEmail);
            telefone = itemView.findViewById(R.id.txtTelefone);
        }
    }

}
