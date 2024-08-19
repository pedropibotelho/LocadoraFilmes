package com.example.locadorafilmes.control;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.locadorafilmes.model.Pessoas;

public class DatabaseHelperPessoas {
    DatabaseHelper dbHelper;

    //PEGANDO O CONTEXT DO BANCO
    public DatabaseHelperPessoas (Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public long cadastrar(Pessoas pessoas){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        long newId = db.insert("pessoas", null, pessoas.getContentValues());
        return  newId;
    }
}
