package br.com.fernandoaag.agendaapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import br.com.fernandoaag.agendaapp.R;
import br.com.fernandoaag.agendaapp.model.Contatos;

/**
 * Created by Nando on 25/11/2016.
 */

public class BuscaAdapter extends RecyclerView.Adapter<BuscaAdapter.ContatoViewHolder> {

        private List<Contatos> contatosList;
        private int rowLayout;
        private Context context;

        public BuscaAdapter(List<Contatos> contatosList, int rowLayout, Context context) {
            this.contatosList = contatosList;
            this.rowLayout = rowLayout;
            this.context = context;
        }

        public class ContatoViewHolder extends RecyclerView.ViewHolder {
            LinearLayout contatosLayout;
            TextView nome;
            TextView apelido;
            TextView telefone;
            TextView tipo;

            public ContatoViewHolder(View itemView) {
                super(itemView);

                contatosLayout = (LinearLayout) itemView.findViewById(R.id.contatosLayout);
                nome = (TextView) itemView.findViewById(R.id.txtNome);
                apelido = (TextView) itemView.findViewById(R.id.txtApelido);
                telefone = (TextView) itemView.findViewById(R.id.txtTelefone);
                tipo = (TextView) itemView.findViewById(R.id.txtTipo);
            }
        }

        @Override
        public BuscaAdapter.ContatoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
            return new ContatoViewHolder(view);
        }

        @Override
        public void onBindViewHolder(BuscaAdapter.ContatoViewHolder holder, int position) {
            holder.nome.setText(contatosList.get(position).getNome());
            holder.apelido.setText(contatosList.get(position).getApelido());
            holder.telefone.setText(contatosList.get(position).getTelefone());
            holder.tipo.setText(contatosList.get(position).getTipo());
        }

        @Override
        public int getItemCount() {
            return contatosList.size();
        }

}


