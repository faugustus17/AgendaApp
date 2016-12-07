package br.com.fernandoaag.agendaapp.View;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import br.com.fernandoaag.agendaapp.R;
import br.com.fernandoaag.agendaapp.Utils.PrefUtil;
import br.com.fernandoaag.agendaapp.Utils.ValidaDados;
import br.com.fernandoaag.agendaapp.model.Contatos;
import br.com.fernandoaag.agendaapp.rest.ApiClient;
import br.com.fernandoaag.agendaapp.rest.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AlteraContato extends AppCompatActivity {
    private static final String TAG = AlteraContato.class.getSimpleName();
    private EditText edtNome;
    private EditText edtApelido;
    private EditText edtDtNasc;
    private EditText edtTelefone;
    private Spinner spnTipo;
    private EditText edtEmail;
    private String idContato;
    private String tipo;
    ValidaDados vD = new ValidaDados();
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_altera);

        initToolBar();

        edtNome = (EditText) findViewById(R.id.edtAltNome);
        edtApelido = (EditText) findViewById(R.id.edtAltApelido);
        edtDtNasc = (EditText) findViewById(R.id.edtAltDtNasc);
        edtTelefone = (EditText) findViewById(R.id.edtAltTelefone);
        spnTipo = (Spinner) findViewById(R.id.spnALtTipo);
        edtEmail = (EditText) findViewById(R.id.edtAltEmail);

        Intent intent = getIntent();
        idContato = intent.getStringExtra("idContato");
        String nome = intent.getStringExtra("nome");
        String apelido = intent.getStringExtra("apelido");
        String dtNasc = intent.getStringExtra("dtNasc");
        String telefone = intent.getStringExtra("telefone");
        tipo = intent.getStringExtra("tipo");
        String email = intent.getStringExtra("email");

        carregarTipo();

        edtNome.setText(nome);
        edtApelido.setText(apelido);
        edtDtNasc.setText(data2(dtNasc));
        edtTelefone.setText(telefone);
        edtEmail.setText(email);

        addListenerOnButtonSalvarPreferencias();
        addListenerOnButtonAlterar();
        addListenerOnButtonExcluir();
    }

    private void addListenerOnButtonAlterar() {
        Button btnAlterar = (Button) findViewById(R.id.btnAlterar);
        btnAlterar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edtNome.length() == 0 || edtTelefone.length() == 0 || spnTipo.getSelectedItem() == "SELECIONE") {
                    alert("Campos NOME, TELEFONE E TIPO não podem ser vazios");
                } else {
                    Contatos c = new Contatos();
                    c.setIdContato(Integer.parseInt(idContato));
                    c.setNome(edtNome.getText().toString());
                    c.setApelido(edtApelido.getText().toString());
                    if (!vD.valData(edtDtNasc.getText().toString())) {
                        edtDtNasc.setError("Informe uma data válida!");
                    }
                    try {
                        if (!vD.verificaVencimentoData(edtDtNasc.getText().toString())) {
                            edtDtNasc.setError("Data deve ser anterior ou igual a data atual!");
                        }
                        c.setDtNasc(data(edtDtNasc.getText().toString()));
                        c.setTelefone(edtTelefone.getText().toString());
                        c.setTipo(spnTipo.getSelectedItem().toString());
                        if (edtEmail.getText().toString().length() != 0 && !vD.validaEmail(edtEmail.getText().toString())) {
                            edtEmail.setError("Informe um email válido!");
                        } else {
                            c.setEmail(edtEmail.getText().toString());

                            ApiClient.INSTANCE.apiInterface().alteraContato(c).enqueue(new Callback<Contatos>() {
                                @Override
                                public void onResponse(Call<Contatos> call, Response<Contatos> response) {

                                }

                                @Override
                                public void onFailure(Call<Contatos> call, Throwable t) {

                                }
                            });

                            Intent intent = new Intent(AlteraContato.this, MainActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
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
                Intent intent = new Intent(AlteraContato.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
            }
        });
    }

    private void addListenerOnButtonExcluir() {
        Button btnExcluir = (Button) findViewById(R.id.btnExcluir);
        btnExcluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ApiClient.INSTANCE.apiInterface().delContato(Integer.parseInt(idContato)).enqueue(new Callback<Contatos>() {
                    @Override
                    public void onResponse(Call<Contatos> call, Response<Contatos> response) {

                    }

                    @Override
                    public void onFailure(Call<Contatos> call, Throwable t) {

                    }
                });
                Intent intent = new Intent(AlteraContato.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
            }
        });
    }

    private void carregarTipo() {
        Spinner spinner = (Spinner) findViewById(R.id.spnALtTipo);
        List<String> list = new ArrayList<>();
        list.add("SELECIONE");
        list.add("Celular");
        list.add("Comercial");
        list.add("Residencial");
        //preenche a combo com a lista
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        //int sel = PrefUtil.getInteger(getBaseContext(), "Tipo.cod");
        int sel;
        switch (tipo){
            case "SELECIONE":sel = 0;
                break;
            case "Celular":sel = 1;
                break;
            case "Comercial":sel = 2;
                break;
            case "Residencial":sel = 3;
                break;
            default:sel = 0;
                break;
        }
        spinner.setSelection(sel);
    }

    private void addListenerOnButtonSalvarPreferencias() {
        final Spinner spinner = (Spinner) findViewById(R.id.spnALtTipo);
        Button btnSubmit = (Button) findViewById(R.id.btnALtSalvarPref);

        btnSubmit.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Toast.makeText(getBaseContext(), "Item Selecionado: "+ String.valueOf(spinner.getSelectedItem()), Toast.LENGTH_SHORT).show();
                //armazena a descrição e o codigo do tipo de telefone selecionado nas preferencias do usuario
                PrefUtil.setString(getBaseContext(), "Tipo.des", String.valueOf(spinner.getSelectedItem()));
                PrefUtil.setInteger(getBaseContext(), "Tipo.cod", spinner.getSelectedItemPosition());

                Toast.makeText(getBaseContext(), "Item salvo nas preferencias: "+String.valueOf(spinner.getSelectedItem()), Toast.LENGTH_SHORT).show();
                Log.d(TAG, "Item salvo nas preferencias: "+ String.valueOf(spinner.getSelectedItem()));
            }
        });
    }

    private String data(String data){
        return (data.substring(6,10)+"-"+data.substring(3,5)+"-"+data.substring(0,2));
    }

    public String data2(String data){
        return (data.substring(7,10)+data.substring(5,7)+data.substring(0,4));
    }

    /**
     * Método para gerar alertas ao usuario
     */
    private void alert(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }


}
