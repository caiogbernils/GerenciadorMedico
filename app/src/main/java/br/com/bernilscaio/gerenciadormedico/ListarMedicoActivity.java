package br.com.bernilscaio.gerenciadormedico;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ListarMedicoActivity extends AppCompatActivity {

    SQLiteDatabase db;
    ListView lvMedico;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_medico);

        lvMedico = findViewById(R.id.lvMedico);

        select();
        lvMedico.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                View v = lvMedico.getChildAt(position);
                TextView tvListId = v.findViewById(R.id.tvListId);
                TextView tvListNome = v.findViewById(R.id.tvListNome);
                TextView tvListCRM = v.findViewById(R.id.tvListCRM);
                TextView tvListLogradouro = v.findViewById(R.id.tvListLogradouro);
                TextView tvListNumero = v.findViewById(R.id.tvListNumero);
                TextView tvListCidade = v.findViewById(R.id.tvListCidade);
                TextView tvListEstado = v.findViewById(R.id.tvListEstado);
                TextView tvListCelular = v.findViewById(R.id.tvListCelular);
                TextView tvListFixo = v.findViewById(R.id.tvListFixo);

                Intent i = new Intent(getApplicationContext(), EditarPacienteActivity.class);
                i.putExtra("id", tvListId.getText().toString());
                i.putExtra("nome", tvListNome.getText().toString());
                i.putExtra("crm", tvListCRM.getText().toString());
                i.putExtra("logradouro", tvListLogradouro.getText().toString());
                i.putExtra("numero", tvListNumero.getText().toString());
                i.putExtra("cidade", tvListCidade.getText().toString());
                i.putExtra("estado", tvListEstado.getText().toString());
                i.putExtra("celular", tvListCelular.getText().toString());
                i.putExtra("fixo", tvListFixo.getText().toString());
                startActivity(i);
            }
        });
    }

    private void select() {
        db = openOrCreateDatabase("consulta.db", Context.MODE_PRIVATE, null);
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT * FROM paciente;");
        Cursor dados_patient = db.rawQuery(sql.toString(), null);
        String[] from = {"_id", "nome_", "grp_sanguineo", "logradouro", "numero", "cidade", "uf", "celular", "fixo"};
        int[] to = {R.id.tvListId, R.id.tvListNome, R.id.tvListGrpSanguineo, R.id.tvListLogradouro, R.id.tvListNumero, R.id.tvListCidade, R.id.tvListEstado, R.id.tvListCelular, R.id.tvListFixo};

        SimpleCursorAdapter scAdapter = new SimpleCursorAdapter(getApplicationContext(), R.layout.dados_paciente, dados_patient, from, to, 0);
        lvMedico.setAdapter(scAdapter);

        db.close();
    }
}

