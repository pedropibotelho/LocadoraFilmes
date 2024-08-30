package com.example.locadorafilmes.control;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

import com.example.locadorafilmes.model.Filmes;

import java.util.ArrayList;

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

    @SuppressLint("Range")
    public ArrayList<String> atualizacaoLista(String seachQuery, AutoCompleteTextView textView){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT nome FROM filmes WHERE nome LIKE ?", new String[]{"%" + seachQuery + "%"});
        ArrayList<String> names = new ArrayList<>();
        while(cursor.moveToNext())
            names.add(cursor.getString(cursor.getColumnIndex("nome")));
        cursor.close();

        return names;
    }

    public Filmes consultar(String nomeFilme) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Filmes filme = null;

        Cursor cursor = db.rawQuery("SELECT * FROM filmes WHERE nome = ?", new String[]{nomeFilme});

        if (cursor.moveToNext()) {
            String nome = cursor.getString(cursor.getColumnIndexOrThrow("nome"));
            String genero = cursor.getString(cursor.getColumnIndexOrThrow("genero"));
            String duracao = cursor.getString(cursor.getColumnIndexOrThrow("duracao"));
            String classificacao = cursor.getString(cursor.getColumnIndexOrThrow("classificacao_indicativa"));
            double valor = cursor.getDouble(cursor.getColumnIndexOrThrow("valor"));

            // Aqui estamos pegando o valor do campo `disponivel` que foi armazenado como 0 ou 1 no banco de dados
            int disponivelInt = cursor.getInt(cursor.getColumnIndexOrThrow("disponivel"));
            boolean disponivel = disponivelInt == 1;

            // Cria uma nova instância do objeto `Filmes` com os dados obtidos
            filme = new Filmes(nome, genero, disponivel, classificacao, duracao, valor );
        }
        cursor.close();
        return filme;
    }

    public boolean alterar(String nomeFilme, Filmes filme){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = filme.getContentValues();

        String whereCause = "nome = ?";
        String[] whereArgs = {nomeFilme};

        int resultado = db.update("filmes", values, whereCause, whereArgs);

        return resultado > 0;
    }

    public long excluir(String nome){
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        String whereClause = "nome = ?";
        String[] whereArgs = {nome};

        long newId = db.delete("filmes", whereClause, whereArgs);
        return newId;

    }
}
