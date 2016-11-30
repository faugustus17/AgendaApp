package br.com.fernandoaag.agendaapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import br.com.fernandoaag.agendaapp.R;
import br.com.fernandoaag.agendaapp.View.AlteraContato;
import br.com.fernandoaag.agendaapp.View.BuscaPessoa;
import br.com.fernandoaag.agendaapp.View.MainActivity;
import br.com.fernandoaag.agendaapp.model.Contatos;

/**
 * Created by Nando on 23/11/2016.
 */

public class ContatosAdapter extends RecyclerView.Adapter<ContatosAdapter.ContatoViewHolder> {

    private List<Contatos> contatosList;
    private int rowLayout;
    private Context context;

    public ContatosAdapter(List<Contatos> contatosList, int rowLayout, Context context) {
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
    public ContatosAdapter.ContatoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new ContatoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ContatosAdapter.ContatoViewHolder holder, final int position) {
        holder.nome.setText(contatosList.get(position).getNome());
        holder.apelido.setText(contatosList.get(position).getApelido());
        holder.telefone.setText(contatosList.get(position).getTelefone());
        holder.tipo.setText(contatosList.get(position).getTipo());

        holder.nome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(view.getContext(), "Clicou"+contatosList.get(position).getNome(), Toast.LENGTH_SHORT).show();
                int idContato = (Integer.valueOf(contatosList.get(position).getIdContato()));
                String nome = (contatosList.get(position).getNome());
                String apelido = (contatosList.get(position).getApelido());
                String dtNasc = (contatosList.get(position).getDtNasc());
                String telefone = (contatosList.get(position).getTelefone());
                String tipo = (contatosList.get(position).getTipo());
                String email = (contatosList.get(position).getEmail());

                Intent intent = new Intent(view.getContext(), AlteraContato.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("idContato", idContato);
                intent.putExtra("nome", nome);
                intent.putExtra("apelido", apelido);
                intent.putExtra("dtNasc", dtNasc);
                intent.putExtra("telefone", telefone);
                intent.putExtra("tipo", tipo);
                intent.putExtra("email", email);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return contatosList.size();
    }

}
