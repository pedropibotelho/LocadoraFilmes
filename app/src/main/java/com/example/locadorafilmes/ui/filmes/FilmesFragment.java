package com.example.locadorafilmes.ui.filmes;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.locadorafilmes.R;
import com.example.locadorafilmes.control.DatabaseHelperFilmes;
import com.example.locadorafilmes.model.Filmes;
import com.santalu.maskara.widget.MaskEditText;

public class FilmesFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflando o layout dos filmes
        View rootView = inflater.inflate(R.layout.fragment_filmes, container, false);

        Button btnCadastrar = rootView.findViewById(R.id.btn_filmes_cadastrar);
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
        EditText edtGenero = getActivity().findViewById(R.id.edt_filmes_genero);
        Spinner spinner = getActivity().findViewById(R.id.spinner_filmes_classificacao);
        EditText edtDuracao = getActivity().findViewById(R.id.edt_filmes_duracao);

        String nomeFilme = edtNome.getText().toString();
        String generoFilme = edtGenero.getText().toString();
        String classificacaoFilme = spinner.getSelectedItem().toString();
        String duracaoFilme = edtDuracao.getText().toString();
        boolean disponivelFilme = true;

        Filmes filme = new Filmes(nomeFilme, generoFilme, disponivelFilme, classificacaoFilme, duracaoFilme);
        DatabaseHelperFilmes dbHelper = new DatabaseHelperFilmes(getActivity().getApplicationContext());
        long resultado = dbHelper.cadastrar(filme);

        if(resultado != -1){
            Toast.makeText(getActivity(), "Filme cadastrado com sucesso!", Toast.LENGTH_SHORT).show();
            edtDuracao.setText("");
            edtGenero.setText("");
            edtNome.setText("");
            spinner.setSelection(0);
        }else
            Toast.makeText(getActivity(), "Erro ao cadastrar!", Toast.LENGTH_SHORT).show();

    }
}