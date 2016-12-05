package br.com.fernandoaag.agendaapp.View;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.List;

import br.com.fernandoaag.agendaapp.R;
import br.com.fernandoaag.agendaapp.adapter.ContatosAdapter;
import br.com.fernandoaag.agendaapp.model.Contatos;
import br.com.fernandoaag.agendaapp.rest.ApiClient;
import br.com.fernandoaag.agendaapp.rest.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BuscaPessoa extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_busca);
        Bundle extras = getIntent().getExtras();
        String nome = extras.getString("nome");

        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerBusca);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        Call<List<Contatos>> call = apiService.listaContatoNome(nome);
        call.enqueue(new Callback<List<Contatos>>() {
            @Override
            public void onResponse(Call<List<Contatos>> call, Response<List<Contatos>> response) {
                List<Contatos> contato = response.body();
                recyclerView.setAdapter(new ContatosAdapter(contato));
                //recyclerView.setAdapter(new BuscaAdapter(contato, R.layout.list_item_contato, getApplicationContext()));
            }

            @Override
            public void onFailure(Call<List<Contatos>> call, Throwable t) {
                Log.e(TAG, toString());
            }
        });
    }
}
