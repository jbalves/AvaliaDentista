package barros.jeferson.avaliadentista.fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import barros.jeferson.avaliadentista.R;

/**
 * Created Jeferson Barros <im.jbalves@gmail.com> on 11/1/16.
 */

public class MinhasConsultasFragment extends Fragment {

    TextView mNomeUnidade;
    TextView mDataConsulta;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        //Inflate the layout for this fragment
        //return super.onCreateView(inflater, container, savedInstanceState);

        View layout = inflater.inflate(R.layout.fragment_minhas_consultas, container, false);

        mNomeUnidade = (TextView) layout.findViewById(R.id.nomeUnidade);
        mDataConsulta = (TextView) layout.findViewById(R.id.dataConsulta);

        return layout;
    }
}
