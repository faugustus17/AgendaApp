package br.com.fernandoaag.agendaapp.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.List;
import br.com.fernandoaag.agendaapp.R;
import br.com.fernandoaag.agendaapp.View.AlteraContato;
import br.com.fernandoaag.agendaapp.View.MainActivity;
import br.com.fernandoaag.agendaapp.model.Contatos;

public class ContatosAdapter extends RecyclerView.Adapter<ContatosAdapter.ContatoViewHolder>{

    private List<Contatos> contatosList;
    private Activity activity;

    ImageView imageView;

    public ContatosAdapter(Activity activity, List<Contatos> contatosList ) {
        this.contatosList = contatosList;
        this.activity = activity;
    }

    @Override
    public ContatosAdapter.ContatoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_contato, parent, false);
        return new ContatoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ContatosAdapter.ContatoViewHolder holder, final int position) {
        Contatos contatos = contatosList.get(position);
        holder.nome.setText(contatos.getNome());
        holder.apelido.setText(contatos.getApelido());
        holder.telefone.setText(contatos.getTelefone());
        holder.tipo.setText(contatos.getTipo());

        /*imageView = (ImageView) activity.findViewById(R.id.ic_phone);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String telefone = (contatosList.get(position).getTelefone());
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse(telefone));
                activity.startActivity(callIntent);
            }
        });*/

        holder.nome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String idContato = String.valueOf(contatosList.get(position).getIdContato());
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
                view.getContext().startActivity(intent);
            }
        });

        /*holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String idContato = String.valueOf(contatosList.get(position).getIdContato());
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
                view.getContext().startActivity(intent);
            }
        });*/
    }

    @Override
    public int getItemCount() {
        return contatosList.size();
    }

    public static class ContatoViewHolder extends RecyclerView.ViewHolder{
        LinearLayout contatosLayout;
        TextView nome;
        TextView apelido;
        TextView telefone;
        TextView tipo;

        public ContatoViewHolder(final View itemView) {
            super(itemView);

            contatosLayout = (LinearLayout) itemView.findViewById(R.id.contatosLayout);
            nome = (TextView) itemView.findViewById(R.id.txtNome);
            apelido = (TextView) itemView.findViewById(R.id.txtApelido);
            telefone = (TextView) itemView.findViewById(R.id.txtTelefone);
            tipo = (TextView) itemView.findViewById(R.id.txtTipo);
        }
    }
}
