package com.example.locadorafilmes.model;

import android.content.ContentValues;

import java.util.Date;

public class Locacao {
    private int id;
    private Pessoas pessoa;
    private Filmes filme;
    private Date dataAluga;
    private Date dataDevolucao;
    private double valor;

    public Locacao(Pessoas pessoa, Filmes filme, Date dataAluga, Date dataDevolucao, double valor) {
        this.pessoa = pessoa;
        this.filme = filme;
        this.dataAluga = dataAluga;
        this.dataDevolucao = dataDevolucao;
        this.valor = valor;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Pessoas getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoas pessoa) {
        this.pessoa = pessoa;
    }

    public Filmes getFilme() {
        return filme;
    }

    public void setFilme(Filmes filme) {
        this.filme = filme;
    }

    public Date getDataAluga() {
        return dataAluga;
    }

    public void setDataAluga(Date dataAluga) {
        this.dataAluga = dataAluga;
    }

    public Date getDataDevolucao() {
        return dataDevolucao;
    }

    public void setDataDevolucao(Date dataDevolucao) {
        this.dataDevolucao = dataDevolucao;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    //MANDAR OS DADOS PARA ALGUM LUGAR
    public ContentValues getContentValues(){
        ContentValues values = new ContentValues();
        if(id > 0)
            values.put("id", id);
        values.put("pessoa", pessoa.getId());
        values.put("filme", filme.getId());
        values.put("dataAluga", dataAluga.getTime());
        values.put("dataDevolucao", dataDevolucao.getTime());
        return values;
    }
}
