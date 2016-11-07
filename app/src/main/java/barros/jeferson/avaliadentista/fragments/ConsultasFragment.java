package barros.jeferson.avaliadentista.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import barros.jeferson.avaliadentista.R;
import barros.jeferson.avaliadentista.adapter.MyAdapter;
import barros.jeferson.avaliadentista.model.UnidadeSaude;

/**
 * Created by Jeferson Barros <im.jbalves@gmail.com> on 11/4/16.
 */

public class ConsultasFragment extends Fragment {

    private ArrayList<UnidadeSaude> mlista = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_consultas,container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //TextView textView = (TextView)view.findViewById(R.id.tvTeste);

        UnidadeSaude unidadeSaude1 = new UnidadeSaude("UBS Morro da Liberdade", "06/11/2016", 20, (float)2.0);
        UnidadeSaude unidadeSaude2 = new UnidadeSaude("UBS Betânia", "01/10/2016", 10, (float)3.0);
        UnidadeSaude unidadeSaude3 = new UnidadeSaude("nome", "dataAtendimento", 10, (float)3.0);
        UnidadeSaude unidadeSaude4 = new UnidadeSaude("nome", "dataAtendimento", 10, (float)3.0);
        UnidadeSaude unidadeSaude5 = new UnidadeSaude("nome", "dataAtendimento", 10, (float)3.0);
        UnidadeSaude unidadeSaude6 = new UnidadeSaude("nome", "dataAtendimento", 10, (float)3.0);

        mlista.add(unidadeSaude1);
        mlista.add(unidadeSaude2);
        mlista.add(unidadeSaude3);
        mlista.add(unidadeSaude4);
        mlista.add(unidadeSaude5);
        mlista.add(unidadeSaude6);

        criarAdapter(view, mlista);

    }

    private void criarAdapter(View view, ArrayList<UnidadeSaude> lista) {
        MyAdapter adapter = new MyAdapter(view.getContext(), lista);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.lista_consulta_recyclerview);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
    }
}
