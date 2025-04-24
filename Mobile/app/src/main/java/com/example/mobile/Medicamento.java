package com.example.mobile;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
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
                abrirTelaCadastro(null);
            }
        });
    }

    private void abrirTelaCadastro(MedicamentoModel medicamento) {
        Intent intent = new Intent(this, TelaCadastro.class);
        if (medicamento != null) {
            intent.putExtra("medicamento", medicamento);
        }

    }
}