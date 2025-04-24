package com.example.mobile;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.util.List;

public class MedicamentoAdapter extends ArrayAdapter<MedicamentoModel> {

    public MedicamentoAdapter(@NonNull Context context, @NonNull List<MedicamentoModel> medicamentos) {
        super(context, 0, medicamentos);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.activity_cadastro, parent, false);
        }


        MedicamentoModel medicamento = getItem(position);

        if (medicamento != null) {
            TextView tvNome = convertView.findViewById(R.id.editNome);
            TextView tvHorario = convertView.findViewById(R.id.editHorario);
            TextView tvDescricao = convertView.findViewById(R.id.editDescricao);

            tvNome.setText(medicamento.getNome());
            tvHorario.setText(medicamento.getHorario());
            tvDescricao.setText(medicamento.getDescricao());

        }
            return convertView;
        }
    }