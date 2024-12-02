package com.example.evaluacion1;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
//Librerias de SQLite
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;
public class CRUDActivity extends AppCompatActivity {

    Spinner spSpinner2;
    String[] roles = new String[]{"Administrador", "Usuario"};

    EditText edtNom2, edtCont2;
    ListView lista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crud);

        edtNom2 = (EditText) findViewById(R.id.edtNom2);
        edtCont2 = (EditText) findViewById(R.id.edtCont2);
        spSpinner2 = (Spinner) findViewById(R.id.spSpinner2);
        lista = (ListView) findViewById(R.id.lstLista);

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, roles);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spSpinner2.setAdapter(spinnerAdapter);
        CargarLista();
    }

    public void CargarLista(){
        DataHelper dh = new DataHelper(this, "usuarios.db", null, 1);
        SQLiteDatabase bd = dh.getWritableDatabase();
        Cursor c = bd.rawQuery("SELECT * FROM usuarios", null);
        String[] arr = new String[c.getCount()];
        if(c.moveToFirst() == true){
            int i = 0;
            do{
                String linea = "||" + c.getString(0) + "||" + c.getString(1)
                        + "||" + c.getString(2);
                arr[i] = linea;
                i++;
            }while (c.moveToNext() == true);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>
                    (this, android.R.layout.simple_expandable_list_item_1, arr);
            lista.setAdapter(adapter);
            bd.close();
        }
    }

    public void onClickAgregar (View view){
        DataHelper dh=new DataHelper(this, "usuarios.db", null, 1);
        SQLiteDatabase bd= dh.getWritableDatabase();
        ContentValues reg= new ContentValues();
        reg.put("nombre", edtNom2.getText().toString());
        reg.put("contraseña", edtCont2.getText().toString());
        reg.put("rol", spSpinner2.getSelectedItem().toString());
        long resp = bd.insert("usuarios", null, reg);
        bd.close();
        if(resp==-1){
            Toast.makeText(this,"No se puede ingresar el usuario", Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(this,"Registro Agregado", Toast.LENGTH_LONG).show();
        }
        CargarLista();
        limpiar();
    }

    public void limpiar(){
        edtNom2.setText("");
        edtCont2.setText("");
    }

    public void onClickModificar(View view){
        DataHelper dh = new DataHelper(this, "usuarios.db", null, 1);
        SQLiteDatabase bd = dh.getWritableDatabase();
        ContentValues reg = new ContentValues();

        reg.put("nombre", edtNom2.getText().toString());
        reg.put("contraseña", edtCont2.getText().toString());
        reg.put("rol", spSpinner2.getSelectedItem().toString());

        long respuesta = bd.update("usuarios", reg, "nombre=?", new String[]{edtNom2.getText().toString()});
        bd.close();

        if (respuesta == -1){
            Toast.makeText(this,"Dato No Modificado", Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(this,"Dato Modificado", Toast.LENGTH_LONG).show();
        }
        limpiar();
        CargarLista();
    }


    public void onClickEliminar(View view){

        DataHelper dh = new DataHelper(this, "usuarios.db", null, 1);
        SQLiteDatabase bd = dh.getWritableDatabase();

        String UsuarioEliminar = edtNom2.getText().toString();

        long respuesta = bd.delete("usuarios", "nombre = ?", new String[]{UsuarioEliminar});
        bd.close();

        if (respuesta == -1 || UsuarioEliminar.isEmpty()){
            Toast.makeText(this,"Dato No Eliminado", Toast.LENGTH_LONG).show();
        }else {
            Toast.makeText(this,"Dato Eliminado", Toast.LENGTH_LONG).show();
        }
        limpiar();
        CargarLista();

    }

}
