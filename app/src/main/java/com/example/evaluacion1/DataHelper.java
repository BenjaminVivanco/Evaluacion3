package com.example.evaluacion1;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.Cursor;
import org.jetbrains.annotations.Nullable;
public class DataHelper extends SQLiteOpenHelper{
    public  DataHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version){
        super(context, name, factory, version);
    }

    //Crea la tabla de la base de datos

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL("CREATE TABLE IF NOT EXISTS usuarios(nombre TEXT PRIMARY KEY, contraseña TEXT, rol TEXT)");
    }

    //Actualizar base de datos
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS usuarios");
        db.execSQL("CREATE TABLE usuarios(nombre TEXT PRIMARY KEY, contraseña TEXT, rol TEXT)");
    }


    // Verificar las credenciales para el inicio de sesion con la base de datos usando select con sql
    public boolean verificarCredenciales(String nombre, String contraseña, String rol) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM usuarios WHERE nombre = ? AND contraseña = ? AND rol = ?";
        Cursor cursor = db.rawQuery(query, new String[]{nombre, contraseña, rol});

        if (cursor != null && cursor.moveToFirst()) {
            cursor.close();
            return true;
        } else {
            if (cursor != null) cursor.close();
            return false;
        }
    }


}
