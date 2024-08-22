package com.example.locadorafilmes.model;

import android.content.ContentValues;

public class Filmes {
    private int id = 0;
    private String nome;
    private String genero;
    private String duracao;
    private int classificacaoIndicativa;
    private double valor;
    private boolean disponivel;

    public Filmes(String nome, String genero, boolean disponivel, int classificacaoIndicativa, String duracao, double valor) {
        this.nome = nome;
        this.genero = genero;
        this.disponivel = disponivel;
        this.classificacaoIndicativa = classificacaoIndicativa;
        this.duracao = duracao;
        this.valor = valor;
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

    public int getClassificacaoIndicativa() {
        return classificacaoIndicativa;
    }

    public void setClassificacaoIndicativa(int classificacaoIndicativa) {
        this.classificacaoIndicativa = classificacaoIndicativa;
    }

    public String getDuracao() {
        return duracao;
    }

    public void setDuracao(String duracao) {
        this.duracao = duracao;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    //MANDAR OS VALORES PARA ALGUM LUGAR
    public ContentValues getContentValues(){
        ContentValues values = new ContentValues();
        if(id > 0)
            values.put("id", id);
        values.put("nome", nome);
        values.put("genero", genero);
        values.put("disponivel", disponivel);
        values.put("classificacao_indicativa", classificacaoIndicativa);
        values.put("duracao", duracao);
        values.put("valor", valor);
        return values;
    }
}
