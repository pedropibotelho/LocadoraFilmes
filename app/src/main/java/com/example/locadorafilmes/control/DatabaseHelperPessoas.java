package com.example.locadorafilmes.control;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.AutoCompleteTextView;

import com.example.locadorafilmes.model.Pessoas;

import java.util.ArrayList;

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

    @SuppressLint("Range")
    public ArrayList<String> atualizacaoLista(String seachQuery, AutoCompleteTextView textView){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT nome FROM pessoas WHERE nome LIKE ?", new String[]{"%" + seachQuery + "%"});
        ArrayList<String> names = new ArrayList<>();
        while(cursor.moveToNext())
            names.add(cursor.getString(cursor.getColumnIndex("nome")));
        cursor.close();

        return names;
    }
}
