package com.example.locadorafilmes.ui.pessoas;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.locadorafilmes.R;

public class PessoasRelatorioFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_pessoas_relatorio, container, false);

        Button btnProcurar = getActivity().findViewById(R.id.btn_relatorio_pessoas_procurar);
        btnProcurar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                consultar();
            }
        });

        Button btnAlterar = getActivity().findViewById(R.id.btn_relatorio_pessoas_alterar);
        btnAlterar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alterar();
            }
        });

        Button btnExcluir = getActivity().findViewById(R.id.btn_relatorio_pessoas_excluir);
        btnExcluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                excluir();
            }
        });

        return rootView;
    }

    public void consultar(){

    }

    public void alterar(){

    }

    public void excluir(){

    }
}