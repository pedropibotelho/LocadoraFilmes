package com.example.locadorafilmes.model;

import android.content.ContentValues;
import android.content.Context;

import java.util.Date;

public class Pessoas {
    private int id = 0;
    private String nome;
    private String cpf;
    private Date dataNascimento;
    private String telefone;

    public Pessoas(String nome, String cpf, Date dataNascimento, String telefone) {
        this.nome = nome;
        this.cpf = cpf;
        this.dataNascimento = dataNascimento;
        this.telefone = telefone;
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

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public Date getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    //MANDAR OS VALORES PARA ALGUM LUGAR, TEM QUE TER OS MSM NOMES DO BANCO
    public ContentValues getContentValues(){
        ContentValues values = new ContentValues();
        if(id > 0)
            values.put("id", id);
        values.put("nome", nome);
        values.put("cpf", cpf);
        values.put("data_nascimento", dataNascimento.getTime());
        values.put("telefone", telefone);
        return values;
    }
}
