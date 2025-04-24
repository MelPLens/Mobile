package com.example.mobile;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class Medicamento extends AppCompatActivity {

    private ListView listaRemedios;
    private Button btnAdicionar;
    private BancoHelper bancoHelper;
    private ArrayList<MedicamentoModel> listaMedicamentos;
    private MedicamentoAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bancoHelper = new BancoHelper(this);

        listaRemedios = findViewById(R.id.listaRemedios);
        btnAdicionar = findViewById(R.id.btnAdicionar);

        listaMedicamentos = bancoHelper.listarMedicamentos();

        adapter = new MedicamentoAdapter(this, listaMedicamentos);
        listaRemedios.setAdapter(adapter);

        btnAdicionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirTelaCadastro(null); // Abre tela de cadastro sem medicamento (novo cadastro)
            }
        });

        listaRemedios.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                MedicamentoModel medicamento = listaMedicamentos.get(position);
                abrirTelaCadastro(medicamento); // Abre tela de cadastro com medicamento para edição
                return true;
            }
        });

        listaRemedios.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MedicamentoModel medicamento = listaMedicamentos.get(position);
                medicamento.setTomado(!medicamento.isTomado());
                bancoHelper.atualizarMedicamento(medicamento);
                adapter.notifyDataSetChanged(); // Atualiza a lista

                Toast.makeText(Medicamento.this,
                        medicamento.isTomado() ? "Marcado como tomado" : "Marcado como não tomado",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void abrirTelaCadastro(MedicamentoModel medicamento) {
        Intent intent = new Intent(this, TelaCadastro.class);
        if (medicamento != null) {
            intent.putExtra("medicamento", medicamento); // Passa o medicamento para edição
        }
        startActivityForResult(intent, 1); // Usa startActivityForResult para receber atualizações
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            listaMedicamentos.clear();
            listaMedicamentos.addAll(bancoHelper.listarMedicamentos());
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void onDestroy() {
        bancoHelper.close();
        super.onDestroy();
    }
}