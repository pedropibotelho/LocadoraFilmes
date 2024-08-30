package com.example.locadorafilmes.ui.pessoas;

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
import com.example.locadorafilmes.control.DatabaseHelperPessoas;
import com.example.locadorafilmes.model.Pessoas;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class PessoasConsultaFragment extends Fragment {

    private DatabaseHelperPessoas dbHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_pessoas_consulta, container, false);

        AutoCompleteTextView autoCompleteTextView = rootView.findViewById(R.id.autoedt_pessoas_nome);
        if(autoCompleteTextView !=null)
            configAutoCompleteTextView(autoCompleteTextView);

        //BOTÃO PARA CONSULTA
        Button btnProcurar = rootView.findViewById(R.id.btn_relatorio_pessoas_procurar);
        btnProcurar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nomePessoa = autoCompleteTextView.getText().toString().trim();
                //VERIFICA SE O AUTO ESTÁ PREENCHIDO
                if(!nomePessoa.equals("")) {
                    dbHelper = new DatabaseHelperPessoas(getActivity().getApplicationContext());
                    Pessoas pessoa = dbHelper.consultar(nomePessoa);
                    //VERIFICA SE A PESSOA DIGITADA EXISTE
                    if(pessoa != null)
                        consultar(pessoa);
                    else {
                        Toast.makeText(getActivity(), "Pessoa não encontrada!", Toast.LENGTH_SHORT).show();
                        limparCampos();
                    }
                }else {
                    limparCampos();
                    Toast.makeText(getActivity(), "Preecha o nome que deseja consultar!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //BOTÃO PARA ALTERAR
        Button btnAlterar = rootView.findViewById(R.id.btn_relatorio_pessoas_alterar);
        btnAlterar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nomePessoa = autoCompleteTextView.getText().toString();
                if(isPreenchido())
                    alterar(nomePessoa);
                else
                    Toast.makeText(getActivity(), "Preencha todos os Dados!", Toast.LENGTH_SHORT).show();
            }
        });

        //BOTÃO PARA EXCLUIR
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
                    dbHelper = new DatabaseHelperPessoas(getActivity().getApplicationContext());
                    ArrayList<String> nomes = dbHelper.atualizacaoLista(s.toString(), textView);

                    ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_dropdown_item_1line, nomes);
                    textView.setAdapter(adapter);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    public void consultar(Pessoas pessoa){
        EditText edtNome = getActivity().findViewById(R.id.edt_relatorio_pessoas_nome);
        EditText edtCpf = getActivity().findViewById(R.id.edt_relatorio_pessoas_cpf);
        EditText edtDataNascimento = getActivity().findViewById(R.id.edt_relatorio_pessoas_data);
        EditText edtTelefone = getActivity().findViewById(R.id.edt_relatorio_pessoas_telefone);

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        edtNome.setText(pessoa.getNome());
        edtCpf.setText(pessoa.getCpf());
        edtDataNascimento.setText(sdf.format(pessoa.getDataNascimento()));
        edtTelefone.setText(pessoa.getTelefone());

        liberarCampos();
        Toast.makeText(getContext(), "Consulta Realizada!", Toast.LENGTH_SHORT).show();

    }

    public void alterar(String nomePessoa){
        EditText edtNome = getActivity().findViewById(R.id.edt_relatorio_pessoas_nome);
        EditText edtCpf = getActivity().findViewById(R.id.edt_relatorio_pessoas_cpf);
        EditText edtDataNascimento = getActivity().findViewById(R.id.edt_relatorio_pessoas_data);
        EditText edtTelefone = getActivity().findViewById(R.id.edt_relatorio_pessoas_telefone);

        String nome = edtNome.getText().toString();
        String cpf = edtCpf.getText().toString();
        String dataNascimentoTxt = edtDataNascimento.getText().toString();
        String telefone = edtTelefone.getText().toString();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date dataNascimento = null;

        try {
            dataNascimento = sdf.parse(dataNascimentoTxt);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        Pessoas pessoa = new Pessoas(nome, cpf, dataNascimento, telefone);

        dbHelper = new DatabaseHelperPessoas(getActivity().getApplicationContext());
        boolean resultado = dbHelper.alterar(pessoa, nomePessoa);

        if(resultado)
            Toast.makeText(getActivity(), "Alteração realizada como Sucesso!", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(getActivity(), "Erro", Toast.LENGTH_SHORT).show();
    }

    public void excluir(){
        AutoCompleteTextView autoNome = getActivity().findViewById(R.id.autoedt_pessoas_nome);
        String autoNomeTxt = autoNome.getText().toString();

        dbHelper = new DatabaseHelperPessoas(getActivity().getApplicationContext());
        long resultado = dbHelper.excluir(autoNomeTxt);

        if(resultado != -1){
            Toast.makeText(getActivity(), "Pessoa Excluída com Sucesso!", Toast.LENGTH_SHORT).show();
            limparCampos();
        }else
            Toast.makeText(getActivity(), "Erro na exclusão!", Toast.LENGTH_SHORT).show();

    }

    private boolean isPreenchido(){
        EditText edtNome = getActivity().findViewById(R.id.edt_relatorio_pessoas_nome);
        EditText edtCpf = getActivity().findViewById(R.id.edt_relatorio_pessoas_cpf);
        EditText edtDataNascimento = getActivity().findViewById(R.id.edt_relatorio_pessoas_data);
        EditText edtTelefone = getActivity().findViewById(R.id.edt_relatorio_pessoas_telefone);

        return !edtNome.equals("") && !edtCpf.equals("") && !edtTelefone.equals("") && !edtDataNascimento.equals("");
    }

    //DEIXA OS CAMPOS VAZIOS E SEM MANEIRA DE EDITAR
    private void limparCampos(){
        EditText edtNome = getActivity().findViewById(R.id.edt_relatorio_pessoas_nome);
        EditText edtCpf = getActivity().findViewById(R.id.edt_relatorio_pessoas_cpf);
        EditText edtDataNascimento = getActivity().findViewById(R.id.edt_relatorio_pessoas_data);
        EditText edtTelefone = getActivity().findViewById(R.id.edt_relatorio_pessoas_telefone);
        Button btnAlterar = getActivity().findViewById(R.id.btn_relatorio_pessoas_alterar);
        Button btnExcluir = getActivity().findViewById(R.id.btn_relatorio_pessoas_excluir);

        edtNome.setText("");
        edtCpf.setText("");
        edtDataNascimento.setText("");
        edtTelefone.setText("");

        edtNome.setFocusable(false);
        edtCpf.setFocusable(false);
        edtDataNascimento.setFocusable(false);
        edtTelefone.setFocusable(false);
        edtNome.setFocusableInTouchMode(false);
        edtCpf.setFocusableInTouchMode(false);
        edtDataNascimento.setFocusableInTouchMode(false);
        edtTelefone.setFocusableInTouchMode(false);

        btnAlterar.setEnabled(false);
        btnExcluir.setEnabled(false);


    }

    //LIBERA OS CAMPOS PARA EDIÇÃO
    private void liberarCampos(){
        EditText edtNome = getActivity().findViewById(R.id.edt_relatorio_pessoas_nome);
        EditText edtCpf = getActivity().findViewById(R.id.edt_relatorio_pessoas_cpf);
        EditText edtDataNascimento = getActivity().findViewById(R.id.edt_relatorio_pessoas_data);
        EditText edtTelefone = getActivity().findViewById(R.id.edt_relatorio_pessoas_telefone);
        Button btnAlterar = getActivity().findViewById(R.id.btn_relatorio_pessoas_alterar);
        Button btnExcluir = getActivity().findViewById(R.id.btn_relatorio_pessoas_excluir);

        edtNome.setFocusable(true);
        edtCpf.setFocusable(true);
        edtDataNascimento.setFocusable(true);
        edtTelefone.setFocusable(true);
        edtNome.setFocusableInTouchMode(true);
        edtCpf.setFocusableInTouchMode(true);
        edtDataNascimento.setFocusableInTouchMode(true);
        edtTelefone.setFocusableInTouchMode(true);

        btnAlterar.setEnabled(true);
        btnExcluir.setEnabled(true);

    }
}