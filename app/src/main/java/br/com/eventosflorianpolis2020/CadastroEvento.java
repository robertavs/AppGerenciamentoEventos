package br.com.eventosflorianpolis2020;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import br.com.eventosflorianpolis2020.modelo.Eventos;

import static android.widget.Toast.LENGTH_SHORT;

public class CadastroEvento extends AppCompatActivity {

    private final int RESULT_CODE_NOVO_EVENTO = 10;
    private final int RESULT_CODE_EDITAR_EVENTO = 20;

    private EditText editTextNome;
    private EditText editTextLocal;
    private EditText editTextData;

    private boolean edicao = false;

    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_evento);

        editTextNome = findViewById(R.id.ed_edicaoNovoEvento);
        editTextLocal = findViewById(R.id.ed_edicaoNovoLocal);
        editTextData = findViewById(R.id.ed_edicaoNovaData);

        mostrarEvento();

    }

    public void mostrarEvento() {
        Intent intent = getIntent();
        if (intent != null && intent.getExtras() != null && intent.getExtras().get("eventoEditado") != null ) {
            Eventos evento = (Eventos) intent.getExtras().get("eventoEditado");
            editTextNome.setText(evento.getNome());
            editTextLocal.setText(evento.getLocal());
            editTextData.setText(evento.getData().toString());
            edicao = true;
            id = evento.getId();
        }
    }

    public void onClickVoltar(View v) {
        finish();
    }

    public void onClickSalvar(View v) {
        String nome = editTextNome.getText().toString();
        String local = editTextLocal.getText().toString();
        String data = editTextData.getText().toString();
        if (nome.length() == 0 || local.length() == 0 || data.length() == 0) {
            editTextNome.setError("Nome obrigatório");
            editTextLocal.setError("Local obrigatório");
            editTextData.setError("Data obrigatória");
        }
        else {
            Eventos evento = new Eventos(id, nome, local, data);
            Intent intent = new Intent();
            if (edicao) {
                intent.putExtra("eventoEditado", evento);
                setResult(RESULT_CODE_EDITAR_EVENTO, intent);
            }
            else {
                intent.putExtra("novoEvento", evento);
                setResult(RESULT_CODE_NOVO_EVENTO, intent);
            }
            finish();
        }

    }
}