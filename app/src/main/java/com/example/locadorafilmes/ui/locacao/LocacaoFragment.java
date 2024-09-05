package com.example.locadorafilmes.ui.locacao;

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
import android.widget.Toast;

import com.example.locadorafilmes.R;
import com.example.locadorafilmes.control.DatabaseHelper;
import com.example.locadorafilmes.control.DatabaseHelperFilmes;
import com.example.locadorafilmes.control.DatabaseHelperLocacao;
import com.example.locadorafilmes.control.DatabaseHelperPessoas;
import com.example.locadorafilmes.model.Filmes;
import com.example.locadorafilmes.model.Locacao;
import com.example.locadorafilmes.model.Pessoas;
import com.santalu.maskara.widget.MaskEditText;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class LocacaoFragment extends Fragment {
    private DatabaseHelperLocacao dbHelper;
    private DatabaseHelperPessoas dbHelperPessoas;
    private DatabaseHelperFilmes dbHelperFilmes;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_locacao, container, false);

        AutoCompleteTextView autoNomePessoa = rootView.findViewById(R.id.autoedt_locacao_pessoas_nome);
        AutoCompleteTextView autoNomeFilme = rootView.findViewById(R.id.autoedt_locacao_filme_nome);

        if (autoNomePessoa!=null)
            configAutoCompleteTextViewPessoa(autoNomePessoa);

        if (autoNomeFilme!=null)
            configAutoCompleteTextViewFilme(autoNomeFilme);

        Button btnCadastro = rootView.findViewById(R.id.btn_locacao_cadastrar);
        btnCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        return rootView;
    }

    private void configAutoCompleteTextViewPessoa(AutoCompleteTextView textView) {
        textView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 1) {
                    dbHelperPessoas = new DatabaseHelperPessoas(getActivity().getApplicationContext());
                    ArrayList<String> nomes = dbHelperPessoas.atualizacaoLista(s.toString(), textView);

                    ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_dropdown_item_1line, nomes);
                    textView.setAdapter(adapter);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    private void configAutoCompleteTextViewFilme(AutoCompleteTextView textView) {
        textView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 1) {
                    dbHelper= new DatabaseHelperLocacao(getActivity().getApplicationContext());
                    ArrayList<String> nomes = dbHelper.atualizacaoLista(s.toString(), textView);

                    ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_dropdown_item_1line, nomes);
                    textView.setAdapter(adapter);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    private void cadastrar(){
        AutoCompleteTextView edtNomePessoa = getActivity().findViewById(R.id.autoedt_locacao_pessoas_nome);
        AutoCompleteTextView edtNomeFilme = getActivity().findViewById(R.id.autoedt_locacao_filme_nome);
        MaskEditText edtDataAluga = getActivity().findViewById(R.id.edt_locacao_data_aluga);
        MaskEditText edtDataDevolucao = getActivity().findViewById(R.id.edt_locacao_data_devolucao);
        EditText edtValor = getActivity().findViewById(R.id.edt_locacao_valor);

        String nomePessoa = edtNomePessoa.getText().toString();
        String nomeFilme = edtNomeFilme.getText().toString();
        String dataAlugaTxt = edtDataAluga.getText().toString();
        String dataDevolucaoTxt= edtDataDevolucao.getText().toString();
        double valor = Double.parseDouble(edtValor.getText().toString());

        Date dataAluga, dataDevolucao;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            dataAluga = sdf.parse(dataAlugaTxt);
            dataDevolucao = sdf.parse(dataDevolucaoTxt);
        } catch (ParseException e) {
            e.printStackTrace();
            Toast.makeText(getActivity(), "Erro ao formatar a data", Toast.LENGTH_SHORT).show();
            return;
        }

        Pessoas pessoa = dbHelper.buscarPessoa(nomePessoa);
        Filmes filme = dbHelper.buscarFilme(nomeFilme);

        Locacao locacao = new Locacao(pessoa, filme, dataAluga, dataDevolucao, valor);

        long resultado = dbHelper.inserir(locacao);

        if(resultado != -1){
            Toast.makeText(getActivity(), "Locação Cadastrada com Sucesso!", Toast.LENGTH_SHORT).show();
            edtNomePessoa.setText("");
            edtNomeFilme.setText("");
            edtDataAluga.setText("");
            edtDataDevolucao.setText("");
            edtValor.setText("");
        }else
            Toast.makeText(getActivity(), "Erro ao Cadastrar!", Toast.LENGTH_SHORT).show();
    }


}