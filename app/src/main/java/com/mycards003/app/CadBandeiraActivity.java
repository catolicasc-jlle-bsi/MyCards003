package com.mycards003.app;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.mycards.api.Upload;
import com.mycards.business.Flag;

/**
 * Created by Renan on 25/06/14.
 */
public class CadBandeiraActivity extends Activity {

    private Button btn;
    private Flag flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_cad_bandeira);

        btn = (Button)findViewById(R.id.btnCadastro);

    }

    @Override
    protected void onStart() {
        super.onStart();

        flag = (Flag) Parametros.getInstance().model;

        EditText edNome = (EditText)this.findViewById(R.id.etNome);
        edNome.setText(flag.description);

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
                throw new Exception("Informe o nome da bandeira");
            }

            new Upload().execute(flag);

            Toast.makeText(this, "Bandeira salva com sucesso", Toast.LENGTH_SHORT).show();
            finalizar();
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    private void finalizar() {
        this.finish();
    }

}
