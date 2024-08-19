package com.example.locadorafilmes.control;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.locadorafilmes.model.Filmes;

public class DatabaseHelperFilmes {
    DatabaseHelper dbHelper;

    //PEGANDO O CONTEXT DO BANCO
    public DatabaseHelperFilmes(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public long cadastrar(Filmes filmes){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        long newId = db.insert("filmes", null, filmes.getContentValues());
        return  newId;
    }
}
