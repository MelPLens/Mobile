package com.example.mobile;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class TelaCadastro extends AppCompatActivity {

    // Componentes da interface
    private EditText editNome;
    private EditText editDescricao;
    private EditText editHorario;
    private Button btnSalvar;

    private MedicamentoModel medicamento;
    private BancoHelper bancoHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        bancoHelper = new BancoHelper(this);

        editNome = findViewById(R.id.editNome);
        editDescricao = findViewById(R.id.editDescricao);
        editHorario = findViewById(R.id.editHorario);
        btnSalvar = findViewById(R.id.btnSalvar);

        medicamento = (MedicamentoModel) getIntent().getSerializableExtra("medicamento");
        if (medicamento != null) {
            preencherCampos();
        }

        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salvarMedicamento();
            }
        });
    }

    private void preencherCampos() {
        editNome.setText(medicamento.getNome());
        editDescricao.setText(medicamento.getDescricao());
        editHorario.setText(medicamento.getHorario());
    }

    private void salvarMedicamento() {
        String nome = editNome.getText().toString().trim();
        String descricao = editDescricao.getText().toString().trim();
        String horario = editHorario.getText().toString().trim();

        if (nome.isEmpty() || horario.isEmpty()) {
            Toast.makeText(this, "Preencha o nome e o horário", Toast.LENGTH_SHORT).show();
            return;
        }

        if (medicamento == null) {
            medicamento = new MedicamentoModel();
            medicamento.setNome(nome);
            medicamento.setDescricao(descricao);
            medicamento.setHorario(horario);
            medicamento.setTomado(false);
            bancoHelper.adicionarMedicamento(medicamento);
        } else {
            medicamento.setNome(nome);
            medicamento.setDescricao(descricao);
            medicamento.setHorario(horario);
            bancoHelper.atualizarMedicamento(medicamento);
        }

        setResult(RESULT_OK);
        finish();
    }
}