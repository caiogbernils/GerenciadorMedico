package br.com.bernilscaio.gerenciadormedico;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AdicionarPacienteActivity extends AppCompatActivity {

    SQLiteDatabase db;

    EditText etNome;
    EditText etLogradouro;
    EditText etNumero;
    EditText etCidade;
    EditText etCelular;
    EditText etFixo;
    Spinner spGrpSanguineo;
    Spinner spUF;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar_paciente);

        etNome = findViewById(R.id.etNome);
        etLogradouro = findViewById(R.id.etLogradouro);
        etNumero = findViewById(R.id.etNumero);
        etCidade = findViewById(R.id.etCidade);
        etCelular = findViewById(R.id.etCelular);
        etFixo = findViewById(R.id.etFixo);
        spGrpSanguineo = findViewById(R.id.spGrpSanguineo);
        spUF = findViewById(R.id.spUF);

        ArrayAdapter spArrayAdapterEstados = ArrayAdapter.createFromResource(getApplicationContext(), R.array.estados, android.R.layout.simple_list_item_checked);
        spUF.setAdapter(spArrayAdapterEstados);

        ArrayAdapter spArrayAdapterGrpSanguineo = ArrayAdapter.createFromResource(getApplicationContext(), R.array.grupoSanguineo, android.R.layout.simple_list_item_checked);
        spGrpSanguineo.setAdapter(spArrayAdapterGrpSanguineo);

        Button clickAdicionar = findViewById(R.id.btnEditar);
        clickAdicionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salvarBD();
            }
        });
    }

    private void salvarBD() {
        String nome = etNome.getText().toString().trim();
        String logradouro = etLogradouro.getText().toString().trim();
        String numero = etNumero.getText().toString().trim();
        String cidade = etCidade.getText().toString().trim();
        String celular = etCelular.getText().toString().trim();
        String fixo = etFixo.getText().toString().trim();

        if (nome.equals("")) {
            Toast.makeText(getApplicationContext(), "Por favor, informe o nome!", Toast.LENGTH_LONG).show();
        } else if (logradouro.equals("")) {
            Toast.makeText(getApplicationContext(), "Por favor, informe o logradouro!", Toast.LENGTH_LONG).show();
        } else if (numero.equals("")) {
            Toast.makeText(getApplicationContext(), "Por favor, informe o numero!", Toast.LENGTH_LONG).show();
        } else if (cidade.equals("")) {
            Toast.makeText(getApplicationContext(), "Por favor, informe a cidade!", Toast.LENGTH_LONG).show();
        } else if (celular.equals("")) {
            Toast.makeText(getApplicationContext(), "Por favor, informe o celular!", Toast.LENGTH_LONG).show();
        } else if (fixo.equals("")) {
            Toast.makeText(getApplicationContext(), "Por favor, informe o telefone!", Toast.LENGTH_LONG).show();
        } else if (spUF.getSelectedItemPosition() == 0) {
            Toast.makeText(getApplicationContext(), "Por favor, informe o seu estado!", Toast.LENGTH_LONG).show();
        } else if (spGrpSanguineo.getSelectedItemPosition() == 0) {
            Toast.makeText(getApplicationContext(), "Por favor, informe o seu tipo sanguineo!", Toast.LENGTH_LONG).show();
        } else {
            db = openOrCreateDatabase("consulta.db", Context.MODE_PRIVATE, null);
            StringBuilder sql = new StringBuilder();
            String grpSanguineo = spGrpSanguineo.getSelectedItem().toString();
            String estado = spUF.getSelectedItem().toString();
            sql.append("INSERT INTO paciente (nome_,grp_sanguineo,logradouro,numero,cidade,uf,celular,fixo) VALUES (");
            sql.append("'" + nome + "', ");
            sql.append("'" + grpSanguineo + "', ");
            sql.append("'" + logradouro + "', ");
            sql.append(numero + ", ");
            sql.append("'" + cidade + "', ");
            sql.append("'" + estado + "', ");
            sql.append("'" + celular + "', ");
            sql.append("'" + fixo + "'");
            sql.append(");");
            try {
                db.execSQL(sql.toString());
                Toast.makeText(getApplicationContext(), "Paciente inserido", Toast.LENGTH_LONG).show();
                etNome.setText("");
                etLogradouro.setText("");
                etNumero.setText("");
                etCidade.setText("");
                etCelular.setText("");
                etFixo.setText("");

                spGrpSanguineo.setSelection(0);
                spUF.setSelection(0);
            } catch (SQLException e) {
                Toast.makeText(getApplicationContext(), "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
            }
            db.close();
        }
    }
}