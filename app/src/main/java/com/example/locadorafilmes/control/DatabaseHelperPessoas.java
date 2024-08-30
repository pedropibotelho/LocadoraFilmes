package com.example.locadorafilmes.control;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.AutoCompleteTextView;

import com.example.locadorafilmes.model.Pessoas;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

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

    public Pessoas consultar(String nomePessoa){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Pessoas pessoa = null;
        Cursor cursor = db.rawQuery("SELECT * FROM pessoas WHERE nome = ?", new String[]{nomePessoa});

        if(cursor.moveToNext()){
            String nome = cursor.getString(cursor.getColumnIndexOrThrow("nome"));
            String cpf = cursor.getString(cursor.getColumnIndexOrThrow("cpf"));
            long dataNascimentoTxt = cursor.getLong(cursor.getColumnIndexOrThrow("data_nascimento"));
            String telefone = cursor.getString(cursor.getColumnIndexOrThrow("telefone"));

            Date dataNascimento = new Date(dataNascimentoTxt);

            pessoa = new Pessoas(nome, cpf, dataNascimento, telefone);
        }
        cursor.close();

        return pessoa;
    }

    public boolean alterar(Pessoas pessoa, String nomePessoa){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = pessoa.getContentValues();

        String whereClause = "nome = ?";
        String[] whereArgs = {nomePessoa};

        int resultado = db.update("pessoas", values, whereClause, whereArgs);
        return resultado > 0;
    }

    public long excluir(String nome){
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        String whereClause = "nome = ?";
        String[] whereArgs = {nome};

        long newId = db.delete("pessoas", whereClause, whereArgs);
        return newId;
    }
}
