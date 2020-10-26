package br.com.eventosflorianpolis2020;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import br.com.eventosflorianpolis2020.modelo.Eventos;

public class MainActivity extends AppCompatActivity {

    private ListView listViewEvento;
    private ArrayAdapter<Eventos> adapterEvento;

    private final int REQUEST_CODE_NOVO_EVENTO = 1;
    private final int REQUEST_CODE_EDITAR_EVENTO = 2;

    private final int RESULT_CODE_NOVO_EVENTO = 10;
    private final int RESULT_CODE_EDITAR_EVENTO = 20;

    private int id = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listViewEvento = findViewById(R.id.listView_evento);
        ArrayList<Eventos> evento = cadastroNovoEvento();
        adapterEvento = new ArrayAdapter<Eventos>(MainActivity.this, android.R.layout.simple_list_item_1, evento);
        listViewEvento.setAdapter(adapterEvento);

        onClickListenerView();

        onLongClickListener();
    }

    private void onClickListenerView() {
        listViewEvento.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final Eventos eventoClicado = adapterEvento.getItem(position);

                new AlertDialog.Builder(MainActivity.this)
                        .setIcon(android.R.drawable.ic_menu_edit)
                        .setTitle("Editar")
                        .setMessage("Editar este item?")
                        .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(MainActivity.this, CadastroEvento.class);
                                intent.putExtra("eventoEditado", eventoClicado);
                                startActivityForResult(intent, REQUEST_CODE_EDITAR_EVENTO);
                            }
                        })
                .setNegativeButton("Não", null).show();
            }
        });
    }

    private void onLongClickListener() {
        listViewEvento.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                final Eventos eventoClicado = adapterEvento.getItem(position);

                new AlertDialog.Builder(MainActivity.this)
                    .setIcon(android.R.drawable.ic_menu_delete)
                    .setTitle("Excluir item")
                    .setMessage("Excluir permanentemente este item?")
                    .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            adapterEvento.remove(eventoClicado);
                            adapterEvento.notifyDataSetChanged();
                            Toast.makeText(MainActivity.this, "Item excluido com sucesso", Toast.LENGTH_LONG).show();
                        }
                    })
                    .setNegativeButton("Não", null).show();
                return true;
            }
        });
    }

    public void onClickNovoEvento(View v) {
        Intent intent = new Intent(MainActivity.this, CadastroEvento.class);
        startActivityForResult(intent, REQUEST_CODE_NOVO_EVENTO);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQUEST_CODE_NOVO_EVENTO && resultCode == RESULT_CODE_NOVO_EVENTO) {
            Eventos evento = (Eventos) data.getExtras().getSerializable("novoEvento");
            evento.setId(++id);
            this.adapterEvento.add(evento);
        }
        else if (requestCode == REQUEST_CODE_EDITAR_EVENTO && resultCode == RESULT_CODE_EDITAR_EVENTO) {
            Eventos eventoEditado = (Eventos) data.getExtras().getSerializable("eventoEditado");
            for (int i = 0; i < adapterEvento.getCount(); i++) {
                Eventos evento = adapterEvento.getItem(i);
                if (evento.getId() == eventoEditado.getId()) {
                    adapterEvento.remove(evento);
                    this.adapterEvento.insert(eventoEditado, i);
                    break;
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private ArrayList<Eventos> cadastroNovoEvento() {
        ArrayList<Eventos> evento = new ArrayList<Eventos>();
        evento.add(new Eventos(1,"Excursão para Cachoeira", "Alto do Capivari - SC", "01/11/2020"));
        evento.add(new Eventos(2,"O poder do foco", "Florianópolis - SC", "20/10/2020" ));
        return evento;
    }


}