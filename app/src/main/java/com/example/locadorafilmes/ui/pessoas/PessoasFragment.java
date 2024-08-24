package com.example.locadorafilmes.ui.pessoas;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.locadorafilmes.R;
import com.example.locadorafilmes.control.DatabaseHelper;
import com.example.locadorafilmes.control.DatabaseHelperPessoas;
import com.example.locadorafilmes.model.Pessoas;
import com.santalu.maskara.widget.MaskEditText;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class PessoasFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Inflando o layout do fragmento de pessoas
        View rootView = inflater.inflate(R.layout.fragment_pessoas, container,false);

        Button btnCadastrar = rootView.findViewById(R.id.btn_pessoas_cadastrar);
        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cadastrarPessoa();
            }
        });

        return  rootView;


    }

    public void cadastrarPessoa() {
        EditText edtNome = getView().findViewById(R.id.edt_pessoas_nome);
        MaskEditText edtCpf = getView().findViewById(R.id.edt_pessoas_cpf);
        MaskEditText edtData = getView().findViewById(R.id.edt_pessoas_data);
        MaskEditText edtTelefone = getView().findViewById(R.id.edt_pessoas_telefone);

        String nomeTxt = edtNome.getText().toString();
        String cpfTxt = edtCpf.getText().toString();
        String dataTxt = edtData.getText().toString();
        String telefoneTxt = edtTelefone.getText().toString();

        // FORMATANDO A DATA
        Date dataNascimento;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            dataNascimento = sdf.parse(dataTxt);
        } catch (ParseException e) {
            e.printStackTrace();
            Toast.makeText(getActivity(), "Erro ao formatar a data", Toast.LENGTH_SHORT).show();
            return;
        }

        Pessoas pessoa = new Pessoas(nomeTxt, cpfTxt, dataNascimento, telefoneTxt);
        DatabaseHelperPessoas dbHelper = new DatabaseHelperPessoas(getActivity().getApplicationContext());
        long resultado = dbHelper.cadastrar(pessoa);

        if (resultado != -1) {
            Toast.makeText(getActivity(), "Pessoa cadastrada com sucesso!", Toast.LENGTH_SHORT).show();
            //LIMPA OS CAMPOS APOS O CADASTRO
            edtNome.setText("");
            edtData.setText("");
            edtCpf.setText("");
            edtTelefone.setText("");
        } else {
            Toast.makeText(getActivity(), "Erro ao cadastrar!", Toast.LENGTH_SHORT).show();
        }
    }
}