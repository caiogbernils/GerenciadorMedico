package br.com.bernilscaio.gerenciadormedico;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class AdicionarConsultasActivity extends AppCompatActivity {

    SQLiteDatabase db;

    EditText etHoraInicio;
    EditText etHoraFinal;
    TextView tvObservacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar_consulta);

        etHoraInicio = findViewById(R.id.etHoraInicio);
        etHoraFinal = findViewById(R.id.etHoraFim);
        tvObservacao = findViewById(R.id.tvObservacao);
    }
}
