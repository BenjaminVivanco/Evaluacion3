package com.example.evaluacion1;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FirebaseActivity extends AppCompatActivity {
    private EditText txtCodigo, txtNombre, txtPrecio;
    private ListView lista;

    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firebase);

        CargarListaFirestore();
        db=FirebaseFirestore.getInstance();

        txtCodigo = findViewById(R.id.txtCodigo);
        txtNombre = findViewById(R.id.txtNombre);
        txtPrecio = findViewById(R.id.txtPrecio);
        lista = findViewById(R.id.lista);

    }

    public void enviarDatosFirestore(View view){
        String codigo = txtCodigo.getText().toString();
        String nombre = txtNombre.getText().toString();
        String precio = txtPrecio.getText().toString();

        Map<String, Object> alimento = new HashMap<>();
        alimento.put("codigo", codigo);
        alimento.put("nombre", nombre);
        alimento.put("precio", precio);

        db.collection("alimentos")
                .document(codigo)
                .set(alimento)
                .addOnSuccessListener(aVoid ->{
                    Toast.makeText(FirebaseActivity.this,"Datos enviados", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(FirebaseActivity.this,"Error", Toast.LENGTH_SHORT).show();
                });
    }

    public void CargarLista(View view){
        CargarListaFirestore();
    }
    public void CargarListaFirestore(){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("alimentos")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            List<String> listaAlimentos = new ArrayList<>();

                            for (QueryDocumentSnapshot document : task.getResult()){
                                String linea = "|| " + document.getString("codigo") + " || " +
                                        document.getString("nombre") + " || " +
                                        document.getString("precio");
                                listaAlimentos.add(linea);
                            }
                            ArrayAdapter<String> adaptador = new ArrayAdapter<>(
                                    FirebaseActivity.this,
                                    android.R.layout.simple_list_item_1,
                                    listaAlimentos
                            );
                            lista.setAdapter(adaptador);
                        }else {
                            Log.e("TAG", "Error al obtener datos", task.getException());
                        }
                    }
                });
    }
}
