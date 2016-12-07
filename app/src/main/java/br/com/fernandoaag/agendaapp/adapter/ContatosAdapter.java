package br.com.fernandoaag.agendaapp.adapter;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import br.com.fernandoaag.agendaapp.R;
import br.com.fernandoaag.agendaapp.model.Contatos;


public class ContatosAdapter extends RecyclerView.Adapter<ContatosAdapter.ViewHolder>{

    private final List<Contatos> contatosList;
    private Callback callback;

    public ContatosAdapter(List<Contatos> contatosList){
        this.contatosList = contatosList;
    }

    public void setCallback(Callback callback){
        this.callback = callback;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_contato, parent, false);
        return new ViewHolder(v, callback);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(contatosList.get(position));

    }

    @Override
    public int getItemCount() {
        return contatosList.size();
    }

    public interface Callback{
        void Item(int position, Contatos contatos);
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        LinearLayout linearLayout;
        TextView nome;
        TextView apelido;
        TextView telefone;
        TextView tipo;
        ImageView imageView;
        private Contatos contatos;

        ViewHolder(View itemView, final Callback callback){
            super(itemView);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.contatosLayout);
            nome = (TextView) itemView.findViewById(R.id.txtNome);
            apelido = (TextView) itemView.findViewById(R.id.txtApelido);
            telefone = (TextView) itemView.findViewById(R.id.txtTelefone);
            tipo = (TextView) itemView.findViewById(R.id.txtTipo);


            nome.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(callback != null){
                        if(contatos != null){
                            callback.Item(getAdapterPosition(), contatos);
                        }
                    }
                }
            });

            /*imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String telefone = (contatos.getTelefone());
                    chamada(view);
                }
            });*/
        }

        public void chamada(View view) {
            Intent intent = new Intent(Intent.ACTION_CALL);
            String telefone = intent.getStringExtra("telefone");
            intent.setData(Uri.parse(telefone));
            if (ActivityCompat.checkSelfPermission(view.getContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                return;
            }else {
                try {
                    view.getContext().startActivity(intent);
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(view.getContext().getApplicationContext(), "Chamada não realizada" + ex.getMessage(), Toast.LENGTH_LONG).show();
                }
            }

        }

        void bind(final Contatos contatos){
            this.contatos = contatos;
            nome.setText(contatos.getNome());
            apelido.setText(contatos.getApelido());
            telefone.setText(contatos.getTelefone());
            tipo.setText(contatos.getTipo());
        }
    }
}

