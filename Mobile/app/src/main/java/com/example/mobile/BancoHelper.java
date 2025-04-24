package com.example.mobile;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class BancoHelper extends SQLiteOpenHelper {

    // Informações do banco de dados
    private static final String DATABASE_NAME = "medicamentos.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_MEDICAMENTOS = "medicamentos";

    // Colunas da tabela
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NOME = "nome";
    private static final String COLUMN_DESCRICAO = "descricao";
    private static final String COLUMN_HORARIO = "horario";
    private static final String COLUMN_TOMADO = "tomado";

    // SQL para criar a tabela
    private static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_MEDICAMENTOS + "(" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    COLUMN_NOME + " TEXT," +
                    COLUMN_DESCRICAO + " TEXT," +
                    COLUMN_HORARIO + " TEXT," +
                    COLUMN_TOMADO + " INTEGER)";

    public BancoHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MEDICAMENTOS);
        onCreate(db);
    }

    // Método para adicionar um novo medicamento
    public long adicionarMedicamento(MedicamentoModel medicamento) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_NOME, medicamento.getNome());
        values.put(COLUMN_DESCRICAO, medicamento.getDescricao());
        values.put(COLUMN_HORARIO, medicamento.getHorario());
        values.put(COLUMN_TOMADO, medicamento.isTomado() ? 1 : 0);

        long id = db.insert(TABLE_MEDICAMENTOS, null, values);
        db.close();

        return id;
    }

    // Método para obter um medicamento pelo ID
    public MedicamentoModel getMedicamento(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_MEDICAMENTOS,
                new String[]{COLUMN_ID, COLUMN_NOME, COLUMN_DESCRICAO, COLUMN_HORARIO, COLUMN_TOMADO},
                COLUMN_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        MedicamentoModel medicamento = new MedicamentoModel(
                cursor.getInt(cursor.getColumnIndex(COLUMN_ID)),
                cursor.getString(cursor.getColumnIndex(COLUMN_NOME)),
                cursor.getString(cursor.getColumnIndex(COLUMN_DESCRICAO)),
                cursor.getString(cursor.getColumnIndex(COLUMN_HORARIO)),
                cursor.getInt(cursor.getColumnIndex(COLUMN_TOMADO)) == 1);

        cursor.close();
        return medicamento;
    }

    public ArrayList<MedicamentoModel> listarMedicamentos() {
        ArrayList<MedicamentoModel> medicamentos = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_MEDICAMENTOS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                MedicamentoModel medicamento = new MedicamentoModel();
                medicamento.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_ID)));
                medicamento.setNome(cursor.getString(cursor.getColumnIndex(COLUMN_NOME)));
                medicamento.setDescricao(cursor.getString(cursor.getColumnIndex(COLUMN_DESCRICAO)));
                medicamento.setHorario(cursor.getString(cursor.getColumnIndex(COLUMN_HORARIO)));
                medicamento.setTomado(cursor.getInt(cursor.getColumnIndex(COLUMN_TOMADO)) == 1);

                medicamentos.add(medicamento);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return medicamentos;
    }

    public int atualizarMedicamento(MedicamentoModel medicamento) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_NOME, medicamento.getNome());
        values.put(COLUMN_DESCRICAO, medicamento.getDescricao());
        values.put(COLUMN_HORARIO, medicamento.getHorario());
        values.put(COLUMN_TOMADO, medicamento.isTomado() ? 1 : 0);

        int rowsAffected = db.update(TABLE_MEDICAMENTOS, values,
                COLUMN_ID + " = ?",
                new String[]{String.valueOf(medicamento.getId())});

        db.close();
        return rowsAffected;
    }

    public void deletarMedicamento(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_MEDICAMENTOS,
                COLUMN_ID + " = ?",
                new String[]{String.valueOf(id)});
        db.close();
    }

    public int marcarComoTomado(int id, boolean tomado) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_TOMADO, tomado ? 1 : 0);

        int rowsAffected = db.update(TABLE_MEDICAMENTOS, values,
                COLUMN_ID + " = ?",
                new String[]{String.valueOf(id)});

        db.close();
        return rowsAffected;
    }

    // Método para contar quantos medicamentos existem
    public int getMedicamentosCount() {
        String countQuery = "SELECT * FROM " + TABLE_MEDICAMENTOS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();
        cursor.close();

        return count;
    }
}