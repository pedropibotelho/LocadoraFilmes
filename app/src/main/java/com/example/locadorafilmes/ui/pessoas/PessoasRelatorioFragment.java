package com.example.locadorafilmes.ui.pessoas;

import android.content.Context;
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

import com.example.locadorafilmes.R;
import com.example.locadorafilmes.control.DatabaseHelperPessoas;

import java.util.ArrayList;

public class PessoasRelatorioFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_pessoas_relatorio, container, false);

        AutoCompleteTextView autoCompleteTextView = rootView.findViewById(R.id.autoedt_pessoas_nome);
        if(autoCompleteTextView !=null)
            configAutoCompleteTextView(autoCompleteTextView);

        Button btnProcurar = rootView.findViewById(R.id.btn_relatorio_pessoas_procurar);
        btnProcurar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                consultar();
            }
        });

        Button btnAlterar = rootView.findViewById(R.id.btn_relatorio_pessoas_alterar);
        btnAlterar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alterar();
            }
        });

        Button btnExcluir = rootView.findViewById(R.id.btn_relatorio_pessoas_excluir);
        btnExcluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                excluir();
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
                    DatabaseHelperPessoas dbHelper = new DatabaseHelperPessoas(getActivity().getApplicationContext());
                    ArrayList<String> nomes = dbHelper.atualizacaoLista(s.toString(), textView);

                    ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_dropdown_item_1line, nomes);
                    textView.setAdapter(adapter);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    public void consultar(){

    }

    public void alterar(){

    }

    public void excluir(){

    }
}