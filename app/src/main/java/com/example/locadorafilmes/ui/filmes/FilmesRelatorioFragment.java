package com.example.locadorafilmes.ui.filmes;

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
import android.widget.Switch;
import android.widget.Toast;

import com.example.locadorafilmes.R;
import com.example.locadorafilmes.control.DatabaseHelperFilmes;
import com.example.locadorafilmes.control.DatabaseHelperPessoas;
import com.example.locadorafilmes.model.Filmes;
import com.santalu.maskara.widget.MaskEditText;

import java.util.ArrayList;

public class FilmesRelatorioFragment extends Fragment {

    private DatabaseHelperFilmes dbHelper;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.fragment_filmes_relatorio, container, false);

        AutoCompleteTextView autoCompleteTextView = rootView.findViewById(R.id.autoedt_filmes_nome);
        if(autoCompleteTextView != null)
            configAutoCompleteTextView(autoCompleteTextView);

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
        EditText edtClassificacao = getActivity().findViewById(R.id.edt_relatorio_filmes_classificacao);
        MaskEditText edtDuracao = getActivity().findViewById(R.id.edt_relatorio_filmes_duracao);
        EditText edtValor = getActivity().findViewById(R.id.edt_relatorio_filme_valor);
        Switch switchDisponivel = getActivity().findViewById(R.id.switch_filmes_relatorio_disponivel);

        edtNome.setText(filme.getNome());
        edtGenero.setText(filme.getGenero());
        edtClassificacao.setText(filme.getClassificacaoIndicativa());
        edtDuracao.setText(filme.getDuracao());
        edtValor.setText(String.valueOf(filme.getValor()));
        switchDisponivel.setChecked(filme.isDisponivel());

        liberarCampos();
        Toast.makeText(getActivity(), "Consulta Realizada com Sucesso!", Toast.LENGTH_SHORT).show();

    }


    private boolean isPreenchido(){
        EditText edtNome = getActivity().findViewById(R.id.edt_relatorio_filmes_nome);
        EditText edtDuracao = getActivity().findViewById(R.id.edt_relatorio_filmes_duracao);
        EditText edtValor = getActivity().findViewById(R.id.edt_relatorio_filme_valor);
        EditText edtClassificacao = getActivity().findViewById(R.id.edt_relatorio_filmes_classificacao);
        EditText edtGenero = getActivity().findViewById(R.id.edt_relatorio_filmes_genero);

        return !edtNome.equals("") && !edtDuracao.equals("") && !edtValor.equals("") && !edtClassificacao.equals("") && !edtGenero.equals("");
    }

    //DEIXA OS CAMPOS VAZIOS E SEM MANEIRA DE EDITAR
    private void limparCampos(){
        EditText edtNome = getActivity().findViewById(R.id.edt_relatorio_filmes_nome);
        EditText edtGenero = getActivity().findViewById(R.id.edt_relatorio_filmes_genero);
        EditText edtClassificacao = getActivity().findViewById(R.id.edt_relatorio_filmes_classificacao);
        MaskEditText edtDuracao = getActivity().findViewById(R.id.edt_relatorio_filmes_duracao);
        EditText edtValor = getActivity().findViewById(R.id.edt_relatorio_filme_valor);
        Switch switchDisponivel = getActivity().findViewById(R.id.switch_filmes_relatorio_disponivel);
        Button btnAlterar = getActivity().findViewById(R.id.btn_relatorio_filmes_alterar);
        Button btnExcluir = getActivity().findViewById(R.id.btn_relatorio_filmes_excluir);

        edtNome.setText("");
        edtGenero.setText("");
        edtClassificacao.setText("");
        edtDuracao.setText("");

        edtNome.setFocusable(false);
        edtGenero.setFocusable(false);
        edtClassificacao.setFocusable(false);
        edtDuracao.setFocusable(false);
        edtNome.setFocusableInTouchMode(false);
        edtGenero.setFocusableInTouchMode(false);
        edtClassificacao.setFocusableInTouchMode(false);
        edtDuracao.setFocusableInTouchMode(false);
        edtValor.setFocusable(false);
        edtValor.setFocusableInTouchMode(false);
        switchDisponivel.setEnabled(false);

        btnAlterar.setEnabled(false);
        btnExcluir.setEnabled(false);


    }

    //LIBERA OS CAMPOS PARA EDIÇÃO
    private void liberarCampos(){
        EditText edtNome = getActivity().findViewById(R.id.edt_relatorio_filmes_nome);
        EditText edtGenero = getActivity().findViewById(R.id.edt_relatorio_filmes_genero);
        EditText edtClassificacao = getActivity().findViewById(R.id.edt_relatorio_filmes_classificacao);
        MaskEditText edtDuracao = getActivity().findViewById(R.id.edt_relatorio_filmes_duracao);
        EditText edtValor = getActivity().findViewById(R.id.edt_relatorio_filme_valor);
        Switch switchDisponivel = getActivity().findViewById(R.id.switch_filmes_relatorio_disponivel);
        Button btnAlterar = getActivity().findViewById(R.id.btn_relatorio_filmes_alterar);
        Button btnExcluir = getActivity().findViewById(R.id.btn_relatorio_filmes_excluir);

        edtNome.setFocusable(true);
        edtGenero.setFocusable(true);
        edtClassificacao.setFocusable(true);
        edtDuracao.setFocusable(true);
        edtNome.setFocusableInTouchMode(true);
        edtGenero.setFocusableInTouchMode(true);
        edtClassificacao.setFocusableInTouchMode(true);
        edtDuracao.setFocusableInTouchMode(true);
        edtValor.setFocusable(true);
        edtValor.setFocusableInTouchMode(true);
        switchDisponivel.setEnabled(true);

        btnAlterar.setEnabled(true);
        btnExcluir.setEnabled(true);

    }
}