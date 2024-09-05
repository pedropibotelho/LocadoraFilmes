package com.example.locadorafilmes.control;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.AutoCompleteTextView;

import com.example.locadorafilmes.model.Filmes;
import com.example.locadorafilmes.model.Locacao;
import com.example.locadorafilmes.model.Pessoas;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

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

    @SuppressLint("Range")
    public ArrayList<String> atualizacaoLista(String seachQuery, AutoCompleteTextView textView){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT nome FROM filmes WHERE nome LIKE ? AND disponivel != 0", new String[]{"%" + seachQuery + "%"});
        ArrayList<String> names = new ArrayList<>();
        while(cursor.moveToNext())
            names.add(cursor.getString(cursor.getColumnIndex("nome")));
        cursor.close();

        return names;
    }

    @SuppressLint("Recycle")
    public Pessoas buscarPessoa(String nomePessoa) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM pessoa WHERE nome = ?", new String[]{nomePessoa});
        Pessoas pessoa = new Pessoas();
        if (cursor.moveToNext()) {
             pessoa.setId(cursor.getColumnIndex("id"));
             pessoa.setNome(String.valueOf(cursor.getColumnIndex("nome")));
             pessoa.setCpf(String.valueOf(cursor.getColumnIndex("cpf")));
             pessoa.setTelefone(String.valueOf(cursor.getColumnIndex("telefone")));
             @SuppressLint("Range") String dataNasicmentoTxt = cursor.getString(cursor.getColumnIndex("data_nascimento"));
             SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
             Date dataNascimento = null;
             try {
                 dataNascimento = formato.parse(dataNasicmentoTxt);
             } catch (ParseException e) {
                 e.printStackTrace();
             }
             pessoa.setDataNascimento(dataNascimento);

        }
        return pessoa;
    }

    @SuppressLint("Recycle")
    public Filmes buscarFilme(String nomeFilme){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM filme WHERE nome = ?", new String[]{nomeFilme});
        Filmes filme = new Filmes();
        if(cursor.moveToNext()){
            filme.setId(cursor.getColumnIndex("id"));
            filme.setNome(String.valueOf(cursor.getColumnIndex("nome")));
            filme.setGenero(String.valueOf(cursor.getColumnIndex("genero")));
            filme.setDuracao(String.valueOf(cursor.getColumnIndex("duracao")));
            filme.setClassificacaoIndicativa(String.valueOf(cursor.getColumnIndex("classificacao_indicativa")));
            @SuppressLint("Range") int disponivelInt = cursor.getInt(cursor.getColumnIndex("disponivel"));
            boolean disponivel = (disponivelInt == 1);
            filme.setDisponivel(disponivel);
        }
        return filme;
    }

}
