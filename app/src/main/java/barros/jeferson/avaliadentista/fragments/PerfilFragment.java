package barros.jeferson.avaliadentista.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import barros.jeferson.avaliadentista.R;

/**
 * Created by jbalves on 11/4/16.
 */

public class PerfilFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_perfil,container,false);
        return view;
    }
}
