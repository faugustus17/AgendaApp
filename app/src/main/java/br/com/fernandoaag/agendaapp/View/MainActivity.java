package br.com.fernandoaag.agendaapp.View;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;
import java.util.List;
import br.com.fernandoaag.agendaapp.R;
import br.com.fernandoaag.agendaapp.adapter.ContatosAdapter;
import br.com.fernandoaag.agendaapp.model.Contatos;
import br.com.fernandoaag.agendaapp.rest.ApiClient;
import br.com.fernandoaag.agendaapp.rest.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private ContatosAdapter adapter;
    ImageView imageView;

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initToolBar();

        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(MainActivity.this);
        recyclerView.setLayoutManager(layoutManager);

        final ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        Call<List<Contatos>> call = apiService.listaContatos();

        call.enqueue(new Callback<List<Contatos>>() {
            @Override
            public void onResponse(Call<List<Contatos>> call, Response<List<Contatos>> response) {
                List<Contatos> contatosList = response.body();
                adapter = new ContatosAdapter(MainActivity.this, contatosList);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<Contatos>> call, Throwable t) {
                alert(toString());
                final String str=t.getMessage();

                alert(str);

                Log.e(TAG, toString());
            }
        });

        addListenerOnButtonConsultar();
        addListenerOnButtonNovo();

        addListenerImageView();

    }

    private void addListenerImageView() {

    }

    private void initToolBar() {
        toolbar = (Toolbar) findViewById(R.id.toolbarCad);
        toolbar.setTitle(R.string.app_name);

    }

    private void addListenerOnButtonConsultar() {
        ImageButton btnBuscar = (ImageButton) findViewById(R.id.imgBtnBuscar);
        btnBuscar.setOnClickListener(new View.OnClickListener() {
            final EditText edtBusca = (EditText) findViewById(R.id.edtBuscaContato);

            @Override
            public void onClick(View view){
                String nome = edtBusca.getText().toString();
                if(edtBusca.length()==0){
                    alert("Informe um NOME ou PARTE DO NOME");
                }else{
                    Intent intent = new Intent(MainActivity.this, BuscaPessoa.class);
                    intent.putExtra("nome", nome);
                    startActivity(intent);
                    edtBusca.setText("");
                }
            }
        });
    }

    private void addListenerOnButtonNovo() {
        Button btnNovo = (Button) findViewById(R.id.btnNovo);
        btnNovo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CriaContato.class);
                startActivity(intent);
            }
        });
    }

    /**
     * Método para gerar alertas ao usuario
     */
    private void alert(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }

}
