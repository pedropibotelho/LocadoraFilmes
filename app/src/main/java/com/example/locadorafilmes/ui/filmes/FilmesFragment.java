package com.example.locadorafilmes.ui.filmes;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.locadorafilmes.R;

public class FilmesFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_filmes, container, false);

        Button btnCadastrar = getActivity().findViewById(R.id.btn_filmes_cadastrar);
        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cadastrarFilme();
            }
        });

        return rootView;
    }

    public void cadastrarFilme(){
        EditText edtNome = getActivity().findViewById(R.id.edt_filmes_nome);
        String nomeFilme = edtNome.getText().toString();

        EditText edtGenero = getActivity().findViewById(R.id.edt_filmes_genero);
        String generoFilme = edtGenero.getText().toString();

        Spinner spinner = getActivity().findViewById(R.id.spinner_filmes_classificacao);
        String classificacaoFilme = spinner.getSelectedItem().toString();
    }
}