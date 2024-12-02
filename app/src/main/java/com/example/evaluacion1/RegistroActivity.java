package com.example.evaluacion1;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
//Librerias de SQLite
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
public class RegistroActivity extends AppCompatActivity{

    Spinner spSpinner;

    String[] roles = new String[]{"Administrador", "Usuario"};

    EditText edtNom, edtCont;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        edtNom = (EditText) findViewById(R.id.edtNom);
        edtCont = (EditText) findViewById(R.id.edtCont);
        spSpinner = (Spinner) findViewById(R.id.spSpinner);

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, roles);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spSpinner.setAdapter(spinnerAdapter);
    }

    //Registrar usuarios a la base de datos
    public void onClickAñadirUsuario (View view){
        DataHelper dh=new DataHelper(this, "usuarios.db", null, 1);
        SQLiteDatabase bd= dh.getWritableDatabase();
        ContentValues reg= new ContentValues();
        reg.put("nombre", edtNom.getText().toString());
        reg.put("contraseña", edtCont.getText().toString());
        reg.put("rol", spSpinner.getSelectedItem().toString());
        long resp = bd.insert("usuarios", null, reg);
        bd.close();
        if(resp==-1){
            Toast.makeText(this,"No se puede ingresar el usuario", Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(this,"Registro Agregado", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }


}
