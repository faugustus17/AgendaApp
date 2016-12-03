package br.com.fernandoaag.agendaapp.View;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.com.fernandoaag.agendaapp.R;
import br.com.fernandoaag.agendaapp.Utils.PrefUtil;

public class AlteraContato extends AppCompatActivity {
    private static final String TAG = AlteraContato.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_altera);

        EditText edtNome = (EditText) findViewById(R.id.edtAltNome);
        EditText edtApelido = (EditText) findViewById(R.id.edtAltApelido);
        EditText edtDtNasc = (EditText) findViewById(R.id.edtAltDtNasc);
        EditText edtTelefone = (EditText) findViewById(R.id.edtAltTelefone);
        Spinner spnTipo = (Spinner) findViewById(R.id.spnALtTipo);
        EditText edtEmail = (EditText) findViewById(R.id.edtAltEmail);

        Intent intent = getIntent();
        String idContato = intent.getStringExtra("idContato");
        String nome = intent.getStringExtra("nome");
        String apelido = intent.getStringExtra("apelido");
        String dtNasc = intent.getStringExtra("dtNasc");
        String telefone = intent.getStringExtra("telefone");
        String tipo = intent.getStringExtra("tipo");
        String email = intent.getStringExtra("email");

        carregarTipo();

        edtNome.setText(nome);
        edtApelido.setText(apelido);
        edtDtNasc.setText(dtNasc);
        edtTelefone.setText(telefone);
        if(tipo.equals("SELECIONE")){
            spnTipo.setSelection(0);
        }else if (tipo.equals("Celular")){
           spnTipo.setSelection(1);
        }else if (tipo.equals("Comercial")){
            spnTipo.setSelection(2);
        }else if (tipo.equals("Residencial")){
            spnTipo.setSelection(3);
        }
        edtEmail.setText(email);

        addListenerOnButtonSalvarPreferencias();
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
        int sel = PrefUtil.getInteger(getBaseContext(), "Tipo.cod");
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
}
