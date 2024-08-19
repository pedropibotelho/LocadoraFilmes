package com.example.locadorafilmes.control;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.locadorafilmes.model.Locacao;

public class DatabaseHelperLocacao {
    DatabaseHelper dbHelper;

    public DatabaseHelperLocacao(Context context){
        dbHelper = new DatabaseHelper(context);
    }

    public long inserir(Locacao locacao){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        long newId = db.insert("locacao", null, locacao.getContentValues());
        return newId;
    }
}
