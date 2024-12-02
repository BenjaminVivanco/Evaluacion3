package com.example.evaluacion1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class AccesoUsuarioActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acceso_usuario);
    }

    public void onClickVentana(View view){
        Intent intent = new Intent(this,VentanaActivity.class);
        startActivity(intent);
    }

    public void onClickElementos(View view){
        Intent intent = new Intent(this, MapaActivity.class);
        startActivity(intent);
    }

    public void onClickVideos(View view){
        Intent intent = new Intent(this, VideoActivity.class);
        startActivity(intent);
    }
}
