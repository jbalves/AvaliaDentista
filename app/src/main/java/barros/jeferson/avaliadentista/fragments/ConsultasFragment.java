package barros.jeferson.avaliadentista.fragments;

import android.content.ClipData;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.oceanbrasil.libocean.Ocean;
import com.oceanbrasil.libocean.control.http.Request;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import barros.jeferson.avaliadentista.R;
import barros.jeferson.avaliadentista.adapter.MyAdapter;
import barros.jeferson.avaliadentista.model.UnidadeSaude;

/**
 * Created by Jeferson Barros <im.jbalves@gmail.com> on 11/4/16.
 */

public class ConsultasFragment extends Fragment implements Request.RequestListener {

    private ArrayList<UnidadeSaude> mLista = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_consultas,container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Ocean
         //       .newRequest("http://mobile-aceite.tcu.gov.br:80/mapa-da-saude/rest/estabelecimentos?categoria=POSTO%20DE%20SA%C3%9ADE&quantidade=3",this).get().send();

        //Ocean.newRequest("https://www.udacity.com/public-api/v0/courses", this).get().send();

        Ocean.newRequest("https://gitlab.com/snippets/30874/raw",this).get().send();

        Log.d("Jeferson","Tamanho da lista [antes]: "+mLista.size());
        criarAdapter(view, mLista);
        Log.d("Jeferson","Tamanho da lista [depois]: "+mLista.size());

    }

    @Override
    public void onRequestOk(String resposta, JSONObject jsonObject, int code) {
         if (code == Request.NENHUM_ERROR) {
             stringToJson(resposta);
         }
        Log.d("Jeferson","Entrou no onRequestOk");
    }

    public void stringToJson (String resposta) {
        Log.d("Jeferson","Entrou no stringToJson");
        ArrayList<UnidadeSaude> lista = new ArrayList<>();

        if (resposta !=null) {
            Log.d("Jeferson","resposta != null ");
            try {

                //JSONObject object = new JSONObject(resposta);
                //JSONArray unidades = object.getJSONArray("");

                JSONArray unidades = new JSONArray(resposta);

                for (int i = 0; i < unidades.length(); i++) {
                    JSONObject unidade = unidades.getJSONObject(i);
                    String nomeFantasia = unidade.getString("nomeFantasia");

                    UnidadeSaude unidadeSaude = new UnidadeSaude();
                    unidadeSaude.setNome(nomeFantasia);
                    unidadeSaude.setDataAtendimento("07/11/2016");
                    unidadeSaude.setDiasEspera(10);
                    unidadeSaude.setRating((float)3.0);

                    mLista.add(unidadeSaude);
                }

            } catch (JSONException e) {
                e.printStackTrace();
                Log.d("Jeferson","problema exception");
            }
        }
    }

    private void criarAdapter(View view, ArrayList<UnidadeSaude> lista) {
        MyAdapter adapter = new MyAdapter(view.getContext(), lista);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.lista_consulta_recyclerview);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
    }
}
