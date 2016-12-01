package br.com.fernandoaag.agendaapp.View;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

import br.com.fernandoaag.agendaapp.R;

/**
 * Created by Nando on 30/11/2016.
 */

public class AlteraContato extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_altera);

        EditText edtNome = (EditText) findViewById(R.id.edtAltNome);

        Intent intent = getIntent();
        int idContato = Integer.valueOf(intent.getStringExtra("idContato"));
        String nome = intent.getStringExtra("nome");
        String apelido = intent.getStringExtra("apelido");
        String dtNasc = intent.getStringExtra("dtNasc");
        String telefone = intent.getStringExtra("telefone");
        String tipo = intent.getStringExtra("tipo");
        String email = intent.getStringExtra("email");

        edtNome.setText(nome);
    }
}
