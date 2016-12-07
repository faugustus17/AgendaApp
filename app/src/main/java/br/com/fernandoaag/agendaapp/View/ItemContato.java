package br.com.fernandoaag.agendaapp.View;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import br.com.fernandoaag.agendaapp.R;

public class ItemContato extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_item_contato);

        ImageView imageView = (ImageView) findViewById(R.id.ic_phone);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Intent i = getIntent();
                String telefone = i.getStringExtra("telefone");*/
                //chamada(view);
            }
        });
    }

    public void chamada(View view) {
        Intent intent = new Intent(Intent.ACTION_CALL);
        String telefone = intent.getStringExtra("telefone");
        intent.setData(Uri.parse(telefone));
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
           return;
        }else{
            try{
                startActivity(intent);
            }catch (android.content.ActivityNotFoundException ex){
                Toast.makeText(getApplicationContext(), "Chamada não realizada"+ex.getMessage(), Toast.LENGTH_LONG).show();
            }

        }

    }
}
