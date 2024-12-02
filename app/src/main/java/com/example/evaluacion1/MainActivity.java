package com.example.evaluacion1;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

 //Librerias para thread
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;



import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText usuarioEditText;
    private EditText contrasenaEditText;
    private Spinner spinner;

    //Variables para thread
    private TextView textView;
    private ImageView imageView;
    private ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        usuarioEditText = findViewById(R.id.usuario);
        contrasenaEditText = findViewById(R.id.contraseña);
        spinner = findViewById(R.id.spinnerRoles);

        String[] roles = {"Administrador", "Usuario"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, roles);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinner.setAdapter(adapter);
        //Enlace de ID
        textView = findViewById(R.id.text1);
        imageView = findViewById(R.id.imagenLogo);
        progressBar = findViewById(R.id.barraProgreso);


        //Implementación de thread, declaración de tiempo de espera y carga de imagen
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progressBar.setVisibility(View.GONE);
                        textView.setText("Carga completa");
                        imageView.setVisibility(View.VISIBLE);
                        imageView.setImageResource(R.drawable.crustaceo);
                    }
                });
            }
        });
        thread.start();





    }


    public void onClickAcceder(View view){
        String nombre = usuarioEditText.getText().toString().trim();
        String contraseña = contrasenaEditText.getText().toString().trim();
        String rol = spinner.getSelectedItem().toString();

        //Reproduce el sonido

        MediaPlayer mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.sonido);
        mediaPlayer.start();

        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener(){
            @Override
            public void onCompletion(MediaPlayer mp){
                mp.release();
            }
        });

        if(nombre.isEmpty()){
            Toast.makeText(this,"El campo usuario esta vacio", Toast.LENGTH_SHORT).show();
            return;
        }

        if(contraseña.isEmpty()){
            Toast.makeText(this,"El campo contraseña esta vacio", Toast.LENGTH_SHORT).show();
            return;
        }

        //Verificar credenciales para iniciar sesion llamando a la base de datos

        DataHelper dh = new DataHelper(this, "usuarios.db", null, 1);

        boolean validarCredenciales = dh.verificarCredenciales(nombre, contraseña, rol);

        if(validarCredenciales){
            if(rol.equals("Usuario")){
                Intent intent = new Intent(this, AccesoUsuarioActivity.class);
                startActivity(intent);
            } else if(rol.equals("Administrador")){
                Intent intent = new Intent(this, AccesoAdministradorActivity.class);
                startActivity(intent);
            }
        } else {
            Toast.makeText(this, "Credenciales incorrectas", Toast.LENGTH_SHORT).show();
        }

    }

    public void onClickRegistrar (View view){
        Intent intent = new Intent(this, RegistroActivity.class);
        startActivity(intent);
    }



}