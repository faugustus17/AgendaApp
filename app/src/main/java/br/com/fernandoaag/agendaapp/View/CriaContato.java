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

public class CriaContato extends AppCompatActivity{
    private static final String TAG = CriaContato.class.getSimpleName();
    Toolbar toolbar;
    private EditText edtNome;
    private EditText edtApelido;
    private EditText dtNasc;
    private EditText telefone;
    private Spinner spnTipo;
    private EditText email;
    ValidaDados vD = new ValidaDados();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);
        initToolBar();

        edtNome = (EditText) findViewById(R.id.edtCadNome);
        edtApelido = (EditText) findViewById(R.id.edtCadApelido);
        dtNasc = (EditText) findViewById(R.id.edtCadDtNasc);
        telefone = (EditText) findViewById(R.id.edtCadTelefone);
        spnTipo = (Spinner) findViewById(R.id.spnTipo);
        email = (EditText) findViewById(R.id.edtCadEmail);

        carregarTipo();
        addListenerOnButtonSalvarPreferencias();
        addListenerOnButtonCriarContato();
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
                Intent intent = new Intent(CriaContato.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
            }
        });
    }

    private void addListenerOnButtonCriarContato() {
        Button btnAdd = (Button) findViewById(R.id.btnAdicionar);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edtNome.length() == 0 || telefone.length() == 0 || spnTipo.getSelectedItem() == "SELECIONE") {
                    alert("Campos NOME, TELEFONE E TIPO não podem ser vazios");
                } else {
                    Contatos c = new Contatos();
                    c.setNome(edtNome.getText().toString());
                    c.setApelido(edtApelido.getText().toString());
                    if (!vD.valData(dtNasc.getText().toString())) {
                        dtNasc.setError("Informe uma data válida!");
                    }
                    try {
                        if (!vD.verificaVencimentoData(dtNasc.getText().toString())) {
                            dtNasc.setError("Data deve ser anterior ou igual a data atual!");
                        }
                        c.setDtNasc(data(dtNasc.getText().toString()));
                        c.setTelefone(telefone.getText().toString());
                        c.setTipo(spnTipo.getSelectedItem().toString());
                        if (email.getText().toString().length() != 0 && !vD.validaEmail(email.getText().toString())) {
                            email.setError("Informe um email válido!");
                        } else {
                            c.setEmail(email.getText().toString());
                            ApiClient.INSTANCE.apiInterface().criaContato(c).enqueue(new Callback<Contatos>() {
                                @Override
                                public void onResponse(Call<Contatos> call, Response<Contatos> response) {

                                }

                                @Override
                                public void onFailure(Call<Contatos> call, Throwable t) {

                                }
                            });
                            Intent intent = new Intent(CriaContato.this, MainActivity.class);
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

    private void carregarTipo() {
        Spinner spinner = (Spinner) findViewById(R.id.spnTipo);
        List<String> list = new ArrayList<>();
        list.add("SELECIONE");
        list.add("Celular");
        list.add("Comercial");
        list.add("Residencial");
        //preenche a combo com a lista
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        int sel = PrefUtil.getInteger(getBaseContext(), "Tipo.cod");
        spinner.setSelection(sel);
    }

    private void addListenerOnButtonSalvarPreferencias() {
        final Spinner spinner = (Spinner) findViewById(R.id.spnTipo);
        Button btnSubmit = (Button) findViewById(R.id.btnSalvarPref);

        btnSubmit.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Toast.makeText(getBaseContext(), "Item Selecionado: "+ String.valueOf(spinner.getSelectedItem()), Toast.LENGTH_SHORT).show();
                //armazena a descrição e o codigo da uf selecionada nas preferencias do usuario
                PrefUtil.setString(getBaseContext(), "Tipo.des", String.valueOf(spinner.getSelectedItem()));
                PrefUtil.setInteger(getBaseContext(), "Tipo.cod", spinner.getSelectedItemPosition());

                Toast.makeText(getBaseContext(), "Item salvo nas preferencias: "+String.valueOf(spinner.getSelectedItem()), Toast.LENGTH_SHORT).show();
                Log.d(TAG, "Item salvo nas preferencias: "+ String.valueOf(spinner.getSelectedItem()));
            }
        });
    }

    /**
     * Método para gerar alertas ao usuario
     */
    private void alert(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }

    private String data(String data){
        return (data.substring(6,10)+"-"+data.substring(3,5)+"-"+data.substring(0,2));
    }
}
