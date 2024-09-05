package com.example.locadorafilmes.control;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "locadora.db";
    private static final int DATABASE_VERSION = 1;

    //CONTEXT DO DATABASE
    public DatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //ONDE DEVO CRIAR AS TABELAS DO BANCO QUANDO ELE INICIAR A PRIMEIRA VEZ
    @Override
    public void onCreate(SQLiteDatabase db) {
        //CRIAÇÃO DA TABELA DE FILMES
        db.execSQL("CREATE TABLE filmes(" +
                "id INTEGER PRIMARY KEY," +
                "nome TEXT," +
                "genero TEXT," +
                "classificacao_indicativa TEXT," +
                "duracao TEXT," +
                "disponivel BOOLEAN)");

        //CRIAÇÃO DA TABELA DE PESSOAS
        db.execSQL("CREATE TABLE pessoas(" +
                "id INTEGER PRIMARY KEY," +
                "nome TEXT," +
                "cpf TEXT," +
                "data_nascimento DATE," +
                "telefone TEXT)");

        //CRIAÇÃO DA TABELA DE LOCACAO
        db.execSQL("CREATE TABLE locacao(" +
                "id INTEGER PRIMARY KEY," +
                "pessoa_id INTEGER," +
                "filme_id INTEGER," +
                "data_aluga DATE," +
                "data_devolucao DATE," +
                "valor DOUBLE," +
                "FOREIGN KEY (pessoa_id) REFERENCES pessoas(id)," +
                "FOREIGN KEY (filme_id) REFERENCES filmes(id))");
    }

    //ATUALIZAÇÃO DO BANCO
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
