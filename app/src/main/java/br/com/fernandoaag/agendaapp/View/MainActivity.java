package br.com.fernandoaag.agendaapp.View;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import java.util.List;
import br.com.fernandoaag.agendaapp.R;
import br.com.fernandoaag.agendaapp.adapter.ContatosAdapter;
import br.com.fernandoaag.agendaapp.model.Contatos;
import br.com.fernandoaag.agendaapp.rest.ApiClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements ContatosAdapter.Callback{

    private RecyclerView recyclerView;
    Toolbar toolbar;
    String erro ="";
    ContatosAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initToolBar();

        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        RecyclerView.LayoutManager layout = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.VERTICAL, false);


        addListenerOnButtonConsultar();
        addListenerOnButtonNovo();

        final Call<List<Contatos>> request = ApiClient.INSTANCE.apiInterface().listaContatos();
        request.enqueue(new Callback<List<Contatos>>() {
            @Override
            public void onResponse(Call<List<Contatos>> call, Response<List<Contatos>> response) {
                /*ContatosAdapter*/ adapter = new ContatosAdapter(response.body());
                adapter.setCallback(MainActivity.this);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<Contatos>> call, Throwable t) {
                String erroTM = t.getMessage();
                CharSequence csT = "null";
                /*if(erroTM.contains(csT)){
                    Intent intent = new Intent(MainActivity.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                }else {*/
                    AlertDialog alerta;
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setTitle("Erro");
                    String erroT = t.toString();
                    CharSequence cs1 = "failed to connect";
                    if (erroT.contains(cs1)) {
                        erro = "\n\nErro\nHttp: 404\nServidor Desconectado!";
                    }
                    builder.setMessage(t.getMessage() + "\n" + erro);
                    builder.setPositiveButton("Ok", null);
                    alerta = builder.create();
                    alerta.show();
                //}
            }
        });
        //recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layout);
    }

    @Override
    public void Item(int Positon, Contatos contatos){
        final Intent intent = new Intent(MainActivity.this, AlteraContato.class);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.menuRefresh:
                Intent intent = new Intent(MainActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initToolBar() {
        toolbar = (Toolbar) findViewById(R.id.toolbarCad);
        toolbar.setTitle(R.string.app_name);

        setSupportActionBar(toolbar);

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
