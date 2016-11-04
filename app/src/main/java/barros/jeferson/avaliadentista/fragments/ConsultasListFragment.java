package barros.jeferson.avaliadentista.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;

import barros.jeferson.avaliadentista.model.UnidadeSaude;

/**
 * Created by Jeferson Barros <im.jbalves@gmail.com> on 03/11/2016.
 */

public class ConsultasListFragment extends ListFragment {
    List<UnidadeSaude> mUnidadesSaude;
    ArrayAdapter<UnidadeSaude> mAdapter;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mUnidadesSaude = carregarUnidadesSaude();

        mAdapter = new ArrayAdapter<UnidadeSaude>(
                getActivity(),
                android.R.layout.simple_list_item_1,
                mUnidadesSaude);
        setListAdapter(mAdapter);
    }

    private List<UnidadeSaude> carregarUnidadesSaude() {
        List<UnidadeSaude> unidadeSaudes = new ArrayList<>();
        unidadeSaudes.add(new UnidadeSaude("UBS Morro da Liberdade1","rua maués"));
        unidadeSaudes.add(new UnidadeSaude("UBS Morro da Liberdade2","rua maués"));
        unidadeSaudes.add(new UnidadeSaude("UBS Morro da Liberdade3","rua maués"));
        unidadeSaudes.add(new UnidadeSaude("UBS Morro da Liberdade4","rua maués"));
        unidadeSaudes.add(new UnidadeSaude("UBS Morro da Liberdade5","rua maués"));
        unidadeSaudes.add(new UnidadeSaude("UBS Morro da Liberdade6","rua maués"));
        unidadeSaudes.add(new UnidadeSaude("UBS Morro da Liberdade7","rua maués"));
        unidadeSaudes.add(new UnidadeSaude("UBS Morro da Liberdade8","rua maués"));

        return unidadeSaudes;
    }
}
