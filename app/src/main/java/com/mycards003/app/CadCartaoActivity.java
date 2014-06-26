package com.mycards003.app;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.nio.charset.Charset;
import com.mycards.api.Upload;
import com.mycards.business.Card;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Renan on 25/06/14.
 */
public class CadCartaoActivity extends Activity {

    private Button btn;
    private Card card;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_cad_cartao);
    }

    @Override
    protected void onStart() {
        super.onStart();
        btn = (Button)findViewById(R.id.btnCadastro);
        card = (Card) Parametros.getInstance().model;

        EditText edNome = (EditText)this.findViewById(R.id.etNome);
        edNome.setText(card.name);
        btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                salvarESair();
            }
        });

        Spinner spinner = (Spinner)findViewById(R.id.spinner_banco);
        List<CharSequence> lista = new ArrayList<CharSequence>();
        lista.add("Bradesco");
        lista.add("Itau");
        lista.add("Santander");
        ArrayAdapter<CharSequence> arrayAdapter;
        arrayAdapter = new ArrayAdapter<CharSequence>(this,android.R.layout.simple_spinner_dropdown_item,lista);
        //arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        if (spinner != null) {
            spinner.setAdapter(arrayAdapter);
        }

        Spinner spinnerBandeira = (Spinner)findViewById(R.id.spinner_bandeira);
        lista = new ArrayList<CharSequence>();
        lista.add("Visa");
        lista.add("Mastercard");
        arrayAdapter = new ArrayAdapter<CharSequence>(this,android.R.layout.simple_spinner_dropdown_item,lista);
        //arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        if (spinnerBandeira != null) {
            spinnerBandeira.setAdapter(arrayAdapter);
        }

    }

    private void salvarESair() {

        try {
            EditText edNome = (EditText)this.findViewById(R.id.etNome);
            if (edNome.getText().toString().trim().equals("")) {
                edNome.requestFocus();
                throw new Exception("Informe o nome do Cartão");
            }
            //new Upload().execute(card);

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
