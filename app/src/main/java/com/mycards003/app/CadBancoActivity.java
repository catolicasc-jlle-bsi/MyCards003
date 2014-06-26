package com.mycards003.app;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.mycards.api.Upload;
import com.mycards.business.Bank;

/**
 * Created by Renan on 25/06/14.
 */
public class CadBancoActivity extends Activity {

    private Button btn;
    private Bank bank;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_cad_banco);

        btn = (Button)findViewById(R.id.btnCadastro);

    }

    @Override
    protected void onStart() {
        super.onStart();

        bank = (Bank) Parametros.getInstance().model;

        EditText edNome = (EditText)this.findViewById(R.id.etNome);
        edNome.setText(bank.description);

        EditText edFenaban = (EditText)this.findViewById(R.id.etFenaban);
        edFenaban.setText(bank.code);

        btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                salvarESair();
            }
        });
    }

    private void salvarESair() {
        try {
            EditText edNome = (EditText)this.findViewById(R.id.etNome);
            if (edNome.getText().toString().trim().equals("")) {
                edNome.requestFocus();
                throw new Exception("Informe o nome do banco");
            }

            EditText edFenaban = (EditText)this.findViewById(R.id.etFenaban);
            if (edFenaban.getText().toString().trim().equals("")) {
                edFenaban.requestFocus();
                throw new Exception("Informe o código Fenaban");
            }

            bank.description = edNome.getText().toString();
            bank.code = edFenaban.getText().toString();

            new Upload().execute(bank);

            Toast.makeText(this, "Banco salvo com sucesso", Toast.LENGTH_SHORT).show();
            finalizar();
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    private void finalizar() {
        this.finish();
    }
}
