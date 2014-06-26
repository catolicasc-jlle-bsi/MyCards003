package com.mycards003.app;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.mycards.api.Upload;
import com.mycards.business.Card;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Renan on 25/06/14.
 */
public class CadCartaoActivity extends Activity {

    private Button btn;
    private Spinner spinner;
    private Card card;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_cad_bandeira);

        btn = (Button)findViewById(R.id.btnCadastro);
        spinner = (Spinner)findViewById(R.id.spinner);
    }

    @Override
    protected void onStart() {
        super.onStart();
        card = (Card) Parametros.getInstance().model;

        EditText edNome = (EditText)this.findViewById(R.id.etNome);
        edNome.setText(card.name);
        btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                salvarESair();
            }
        });
/*
        List<String> lista = new ArrayList<String>();
        lista.add("Bradesco");
        lista.add("Itau");
        lista.add("Santander");

        ArrayAdapter<String> arrayAdapter;
        arrayAdapter = new ArrayAdapter.createFromResource(this, lista, androidR.layout.simple) ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,lista);

        spinner.setAdapter(arrayAdapter);*/
    }

    private void salvarESair() {
        try {
            EditText edNome = (EditText)this.findViewById(R.id.etNome);
            if (edNome.getText().toString().trim().equals("")) {
                edNome.requestFocus();
                throw new Exception("Informe o nome da bandeira");
            }

            new Upload().execute(card);

            Toast.makeText(this, "Cartão salvo com sucesso", Toast.LENGTH_SHORT).show();
            finalizar();
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    private void finalizar() {
        this.finish();
    }
}
