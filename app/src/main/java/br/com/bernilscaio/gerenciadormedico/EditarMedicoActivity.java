package br.com.bernilscaio.gerenciadormedico;

import android.content.Context;
import android.content.Intent;
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

public class EditarMedicoActivity extends AppCompatActivity {

    SQLiteDatabase db;

    EditText etNome;
    EditText etCRM;
    EditText etLogradouro;
    EditText etNumero;
    EditText etCidade;
    EditText etCelular;
    EditText etFixo;
    Spinner spUF;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_medico);

        etNome = findViewById(R.id.etNome);
        etCRM = findViewById(R.id.etCRM);
        etLogradouro = findViewById(R.id.etLogradouro);
        etNumero = findViewById(R.id.etNumero);
        etCidade = findViewById(R.id.etCidade);
        etCelular = findViewById(R.id.etCelular);
        etFixo = findViewById(R.id.etFixo);
        spUF = findViewById(R.id.spUF);

        ArrayAdapter spArrayAdapterEstados = ArrayAdapter.createFromResource(getApplicationContext(), R.array.estados, android.R.layout.simple_list_item_checked);
        spUF.setAdapter(spArrayAdapterEstados);

        Intent valores = getIntent();
        fillFields(valores);

        final String id = valores.getStringExtra("id");

        Button editar = findViewById(R.id.btnEditar);
        editar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                update(id);
            }
        });

        Button clickDelete = findViewById(R.id.btnExcluir);
        clickDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delete(id);
            }
        });
    }
    private void fillFields(Intent valores) {
        etNome.setText(valores.getStringExtra("nome"));
        etCRM.setText(valores.getStringExtra("CRM"));
        etLogradouro.setText(valores.getStringExtra("logradouro"));
        etNumero.setText(valores.getStringExtra("numero"));
        etCidade.setText(valores.getStringExtra("cidade"));
        etCelular.setText(valores.getStringExtra("celular"));
        etFixo.setText(valores.getStringExtra("fixo"));

        String statesExtras = valores.getStringExtra("estado");

        String[] statesArray = getResources().getStringArray(R.array.estados);
        int aux = 0;
        for (String s : statesArray) {
            if (s.equals(statesExtras))
                break;
            aux++;
        }
        spUF.setSelection(aux);
    }
    private void update(String id) {
        String nome = etNome.getText().toString().trim();
        String CRM = etCRM.getText().toString().trim();
        String logradouro = etLogradouro.getText().toString().trim();
        String numero = etNumero.getText().toString().trim();
        String cidade = etCidade.getText().toString().trim();
        String celular = etCelular.getText().toString().trim();
        String fixo = etFixo.getText().toString().trim();

        if (nome.equals("")) {
            Toast.makeText(getApplicationContext(), "Por favor, informe o nome!", Toast.LENGTH_LONG).show();
        } else if (CRM.equals("")) {
            Toast.makeText(getApplicationContext(), "Por favor, informe o CRM!", Toast.LENGTH_LONG).show();
        } else if (logradouro.equals("")) {
            Toast.makeText(getApplicationContext(), "Por favor, informe o endereço!", Toast.LENGTH_LONG).show();
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
        }
        else {
            db = openOrCreateDatabase("consulta.db", Context.MODE_PRIVATE, null);
            StringBuilder sql = new StringBuilder();
            String state = spUF.getSelectedItem().toString();
            sql.append("UPDATE medico SET");
            sql.append(" nome = '" + nome + "', ");
            sql.append("crm  = '" + CRM + "', ");
            sql.append("logradouro = '" + logradouro + "', ");
            sql.append("numero = " + numero + ", ");
            sql.append("cidade = '" + cidade + "', ");
            sql.append("uf = '" + state + "', ");
            sql.append("celular = '" + celular + "', ");
            sql.append("fixo = '" + fixo + "'");
            sql.append(" where _id = '" + id + "';");

            try {
                db.execSQL(sql.toString());
                Toast.makeText(getApplicationContext(), "Médico atualizado", Toast.LENGTH_LONG).show();
                etNome.setText("");
                etCRM.setText("");
                etLogradouro.setText("");
                etNumero.setText("");
                etCidade.setText("");
                etCelular.setText("");
                etFixo.setText("");
                Intent i = new Intent(getApplicationContext(), ListarMedicoActivity.class);
                startActivity(i);
            } catch (SQLException e) {
                Toast.makeText(getApplicationContext(), "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
            }
            db.close();
        }
    }

    private void delete (String id){
        db = openOrCreateDatabase("consulta.db", Context.MODE_PRIVATE, null);
        StringBuilder sql = new StringBuilder();
        sql.append("DELETE FROM medico");
        sql.append(" where _id = '" + id + "';");

        try {
            db.execSQL(sql.toString());
            Toast.makeText(getApplicationContext(), "Médico excluido", Toast.LENGTH_LONG).show();
            etNome.setText("");
            etCRM.setText("");
            etLogradouro.setText("");
            etNumero.setText("");
            etCidade.setText("");
            etCelular.setText("");
            etFixo.setText("");
            Intent i = new Intent(getApplicationContext(), ListarMedicoActivity.class);
            startActivity(i);
        } catch (SQLException e) {
            Toast.makeText(getApplicationContext(), "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
        db.close();
    }

}

