package com.example.locadorafilmes.model;

import android.content.ContentValues;

public class Filmes {
    private int id = 0;
    private String nome;
    private String genero;
    private boolean disponivel;

    public Filmes(int id, String nome, String genero, boolean disponivel) {
        this.id = id;
        this.nome = nome;
        this.genero = genero;
        this.disponivel = disponivel;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public boolean isDisponivel() {
        return disponivel;
    }

    public void setDisponivel(boolean disponivel) {
        this.disponivel = disponivel;
    }

    //MANDAR OS VALORES PARA ALGUM LUGAR
    public ContentValues getContentValues(){
        ContentValues values = new ContentValues();
        if(id > 0)
            values.put("id", id);
        values.put("nome", nome);
        values.put("genero", genero);
        values.put("disponivel", disponivel);
        return values;
    }
}
