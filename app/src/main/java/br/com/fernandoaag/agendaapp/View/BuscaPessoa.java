package br.com.fernandoaag.agendaapp.View;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import java.util.List;

import br.com.fernandoaag.agendaapp.R;
import br.com.fernandoaag.agendaapp.adapter.ContatosAdapter;
import br.com.fernandoaag.agendaapp.model.Contatos;
import br.com.fernandoaag.agendaapp.rest.ApiClient;
import br.com.fernandoaag.agendaapp.rest.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BuscaPessoa extends AppCompatActivity implements ContatosAdapter.Callback{

    private static final String TAG = MainActivity.class.getSimpleName();
    private RecyclerView recyclerView;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_busca);

        initToolBar();

        Bundle extras = getIntent().getExtras();
        String nome = extras.getString("nome");

        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mostraContatos(nome);

        /*final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerBusca);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        Call<List<Contatos>> call = apiService.listaContatoNome(nome);
        call.enqueue(new Callback<List<Contatos>>() {
            @Override
            public void onResponse(Call<List<Contatos>> call, Response<List<Contatos>> response) {
                List<Contatos> contato = response.body();

                recyclerView.setAdapter(new ContatosAdapter(BuscaPessoa.this, contato));
            }

            @Override
            public void onFailure(Call<List<Contatos>> call, Throwable t) {
                Log.e(TAG, toString());
            }
        });*/
    }

    private void mostraContatos(String nome) {
        ApiClient.INSTANCE.apiInterface().listaContatoNome(nome)
                .enqueue(new Callback<List<Contatos>>() {
                    @Override
                    public void onResponse(Call<List<Contatos>> call, Response<List<Contatos>> response) {
                        final ContatosAdapter adapter = new ContatosAdapter(response.body());
                        adapter.setCallback(BuscaPessoa.this);
                        recyclerView.setAdapter(adapter);
                    }

                    @Override
                    public void onFailure(Call<List<Contatos>> call, Throwable t) {
                        AlertDialog alerta;
                        t.printStackTrace();
                        AlertDialog.Builder builder = new AlertDialog.Builder(BuscaPessoa.this);
                        builder.setTitle("Erro");
                        builder.setMessage(t.getMessage()+"\n"+toString());
                        builder.setPositiveButton("Ok", null);
                        alerta = builder.create();
                        alerta.show();

                    }
                });
    }

    @Override
    public void Item(int Positon, Contatos contatos){
        final Intent intent = new Intent(BuscaPessoa.this, AlteraContato.class);
        String idContato = String.valueOf(contatos.getIdContato());
        String nome = (contatos.getNome());
        String apelido = (contatos.getApelido());
        String dtNasc = (contatos.getDtNasc());
        String telefone = (contatos.getTelefone());
        String tipo = (contatos.getTipo());
        String email = (contatos.getEmail());

        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("idContato", idContato);
        intent.putExtra("nome", nome);
        intent.putExtra("apelido", apelido);
        intent.putExtra("dtNasc", dtNasc);
        intent.putExtra("telefone", telefone);
        intent.putExtra("tipo", tipo);
        intent.putExtra("email", email);
        startActivity(intent);
    }

    private void initToolBar() {
        toolbar = (Toolbar) findViewById(R.id.toolbarCad);
        toolbar.setTitle(R.string.app_name);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BuscaPessoa.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
            }
        });
    }
}
