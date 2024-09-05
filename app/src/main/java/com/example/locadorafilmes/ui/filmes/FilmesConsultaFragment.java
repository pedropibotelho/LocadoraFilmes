package com.example.locadorafilmes.ui.filmes;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import com.example.locadorafilmes.R;
import com.example.locadorafilmes.control.DatabaseHelperFilmes;
import com.example.locadorafilmes.model.Filmes;
import com.santalu.maskara.widget.MaskEditText;

import java.util.ArrayList;
import java.util.Arrays;

public class FilmesConsultaFragment extends Fragment {

    private DatabaseHelperFilmes dbHelper;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.fragment_filmes_consulta, container, false);
        Spinner spinner = rootView.findViewById(R.id.spinner_relatorio_filmes_classificacao);
        spinner.setEnabled(false);

        AutoCompleteTextView autoCompleteTextView = rootView.findViewById(R.id.autoedt_filmes_nome);
        if(autoCompleteTextView != null)
            configAutoCompleteTextView(autoCompleteTextView);

        //BOTAO PARA PROCURAR
        Button btnProcurar = rootView.findViewById(R.id.btn_relatorio_filmes_procurar);
        btnProcurar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nomeFilme = autoCompleteTextView.getText().toString();

                if(!nomeFilme.equals("")){
                    dbHelper = new DatabaseHelperFilmes(getActivity().getApplicationContext());
                    Filmes filme = dbHelper.consultar(nomeFilme);
                    if(filme != null){
                        consultar(filme);
                    }else {
                        Toast.makeText(getActivity(), "Filme Inválido!", Toast.LENGTH_SHORT).show();
                        limparCampos();
                    }
                }else {
                    Toast.makeText(getActivity(), "Preencha com o nome do Filme!", Toast.LENGTH_SHORT).show();
                    limparCampos();
                }
            }
        });

        //BOTAO PARA ALTERAR
        Button btnAlterar = rootView.findViewById(R.id.btn_relatorio_filmes_alterar);
        btnAlterar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nomeFilme = autoCompleteTextView.getText().toString();
                if(isPreenchido())
                    alterar(nomeFilme);
                else
                    Toast.makeText(getActivity(), "Preencha todos os campos!", Toast.LENGTH_SHORT).show();
            }
        });

        //BOTAO PARA EXCLUIR
        Button btnExcluir = rootView.findViewById(R.id.btn_relatorio_filmes_excluir);
        btnExcluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nomeFilme = autoCompleteTextView.getText().toString();
                excluir(nomeFilme);
            }
        });


        return rootView;
    }

    private void configAutoCompleteTextView(AutoCompleteTextView textView) {
        textView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 1) {
                    dbHelper = new DatabaseHelperFilmes(getActivity().getApplicationContext());
                    ArrayList<String> nomes = dbHelper.atualizacaoLista(s.toString(), textView);

                    ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_dropdown_item_1line, nomes);
                    textView.setAdapter(adapter);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    public void consultar(Filmes filme){
        EditText edtNome = getActivity().findViewById(R.id.edt_relatorio_filmes_nome);
        EditText edtGenero = getActivity().findViewById(R.id.edt_relatorio_filmes_genero);
        Spinner spinnerClassificacao = getActivity().findViewById(R.id.spinner_relatorio_filmes_classificacao);
        MaskEditText edtDuracao = getActivity().findViewById(R.id.edt_relatorio_filmes_duracao);
        @SuppressLint("UseSwitchCompatOrMaterialCode") Switch switchDisponivel = getActivity().findViewById(R.id.switch_filmes_relatorio_disponivel);

        edtNome.setText(filme.getNome());
        edtGenero.setText(filme.getGenero());

        String classificacaoFilme = filme.getClassificacaoIndicativa();
        String[] classificacoes = getResources().getStringArray(R.array.idades);
        int i = Arrays.asList(classificacoes).indexOf(classificacaoFilme);
        if(i>=0)
            spinnerClassificacao.setSelection(i);

        edtDuracao.setText(filme.getDuracao());
        switchDisponivel.setChecked(filme.isDisponivel());

        liberarCampos();
        Toast.makeText(getActivity(), "Consulta Realizada com Sucesso!", Toast.LENGTH_SHORT).show();

    }

    public void alterar(String nomeFilme){
        EditText edtNome = getActivity().findViewById(R.id.edt_relatorio_filmes_nome);
        EditText edtGenero = getActivity().findViewById(R.id.edt_relatorio_filmes_genero);
        MaskEditText edtDuracao = getActivity().findViewById(R.id.edt_relatorio_filmes_duracao);
        Spinner spinnerClassificacao = getActivity().findViewById(R.id.spinner_relatorio_filmes_classificacao);
        @SuppressLint("UseSwitchCompatOrMaterialCode") Switch swicthDisponivel = getActivity().findViewById(R.id.switch_filmes_relatorio_disponivel);


        String nome = edtNome.getText().toString();
        String genero = edtGenero.getText().toString();
        String duracao = edtDuracao.getText().toString();
        String classificacao = spinnerClassificacao.getSelectedItem().toString();
        Boolean disponivel = swicthDisponivel.isChecked();

        Filmes filme = new Filmes(nome, genero, disponivel, classificacao, duracao);
        boolean resultado = dbHelper.alterar(nomeFilme, filme);

        if(resultado)
            Toast.makeText(getActivity(), "Alteração realizada como Sucesso!", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(getActivity(), "Erro", Toast.LENGTH_SHORT).show();

    }

    public void excluir(String nomeFilme){
        dbHelper = new DatabaseHelperFilmes(getActivity().getApplicationContext());
        long resultado = dbHelper.excluir(nomeFilme);

        if(resultado != -1){
            Toast.makeText(getActivity(), "Filme Excluído com Sucesso!", Toast.LENGTH_SHORT).show();
            limparCampos();
        }else
            Toast.makeText(getActivity(), "Erro", Toast.LENGTH_SHORT).show();

    }

    private boolean isPreenchido(){
        EditText edtNome = getActivity().findViewById(R.id.edt_relatorio_filmes_nome);
        EditText edtDuracao = getActivity().findViewById(R.id.edt_relatorio_filmes_duracao);
        EditText edtGenero = getActivity().findViewById(R.id.edt_relatorio_filmes_genero);

        return !edtNome.equals("") && !edtDuracao.equals("") && !edtGenero.equals("");
    }

    //DEIXA OS CAMPOS VAZIOS E SEM MANEIRA DE EDITAR
    private void limparCampos(){
        EditText edtNome = getActivity().findViewById(R.id.edt_relatorio_filmes_nome);
        EditText edtGenero = getActivity().findViewById(R.id.edt_relatorio_filmes_genero);
        Spinner spinnerClassificacao = getActivity().findViewById(R.id.spinner_relatorio_filmes_classificacao);
        MaskEditText edtDuracao = getActivity().findViewById(R.id.edt_relatorio_filmes_duracao);
        @SuppressLint("UseSwitchCompatOrMaterialCode") Switch switchDisponivel = getActivity().findViewById(R.id.switch_filmes_relatorio_disponivel);
        Button btnAlterar = getActivity().findViewById(R.id.btn_relatorio_filmes_alterar);
        Button btnExcluir = getActivity().findViewById(R.id.btn_relatorio_filmes_excluir);

        edtNome.setText("");
        edtGenero.setText("");
        spinnerClassificacao.setSelection(0);
        edtDuracao.setText("");

        edtNome.setFocusable(false);
        edtGenero.setFocusable(false);
        edtDuracao.setFocusable(false);
        edtNome.setFocusableInTouchMode(false);
        edtGenero.setFocusableInTouchMode(false);
        edtDuracao.setFocusableInTouchMode(false);
        spinnerClassificacao.setClickable(false);
        spinnerClassificacao.setEnabled(false);
        switchDisponivel.setEnabled(false);

        btnAlterar.setEnabled(false);
        btnExcluir.setEnabled(false);


    }

    //LIBERA OS CAMPOS PARA EDIÇÃO
    private void liberarCampos(){
        EditText edtNome = getActivity().findViewById(R.id.edt_relatorio_filmes_nome);
        EditText edtGenero = getActivity().findViewById(R.id.edt_relatorio_filmes_genero);
        Spinner spinnerClassificacao = getActivity().findViewById(R.id.spinner_relatorio_filmes_classificacao);
        MaskEditText edtDuracao = getActivity().findViewById(R.id.edt_relatorio_filmes_duracao);
        @SuppressLint("UseSwitchCompatOrMaterialCode") Switch switchDisponivel = getActivity().findViewById(R.id.switch_filmes_relatorio_disponivel);
        Button btnAlterar = getActivity().findViewById(R.id.btn_relatorio_filmes_alterar);
        Button btnExcluir = getActivity().findViewById(R.id.btn_relatorio_filmes_excluir);

        edtNome.setFocusable(true);
        edtGenero.setFocusable(true);
        edtDuracao.setFocusable(true);
        edtNome.setFocusableInTouchMode(true);
        edtGenero.setFocusableInTouchMode(true);
        edtDuracao.setFocusableInTouchMode(true);
        switchDisponivel.setEnabled(true);
        spinnerClassificacao.setClickable(true);
        spinnerClassificacao.setEnabled(true);

        btnAlterar.setEnabled(true);
        btnExcluir.setEnabled(true);

    }
}