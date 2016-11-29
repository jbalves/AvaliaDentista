package barros.jeferson.avaliadentista.fragments;

import android.app.AlertDialog;
import android.content.ClipData;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import barros.jeferson.avaliadentista.MainActivity;
import barros.jeferson.avaliadentista.R;
import barros.jeferson.avaliadentista.TutorialActivity;
import barros.jeferson.avaliadentista.adapter.AgendamentoAdapter;
import barros.jeferson.avaliadentista.adapter.MyAdapter;
import barros.jeferson.avaliadentista.model.Agendamento;

 /**
 * Created by Jeferson Barros <im.jbalves@gmail.com> on 11/4/16.
 */

public class ConsultasFragment extends Fragment {

    private View mView;
    private DatabaseReference mDatabase;

    private ArrayList<Agendamento> mLista = new ArrayList<>();
    private static final String AGENDAMENTO_DATASET = "agendamentos";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.consultas_fragment);

        return inflater.inflate(R.layout.fragment_consultas,container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mView = view;
        mDatabase = FirebaseDatabase.getInstance().getReference("agendamentos");
        getAgendamentos();
    }

    private void criarAdapter(View view, ArrayList<Agendamento> lista) {
        AgendamentoAdapter adapter = new AgendamentoAdapter(view.getContext(), lista);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.lista_consulta_recyclerview);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
    }

    public void getAgendamentos(){
        //Connect to database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(AGENDAMENTO_DATASET);

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                if (!dataSnapshot.exists() || dataSnapshot.getValue() == null){

                    AlertDialog alertDialog = new AlertDialog.Builder(getActivity())
                            .create();
                    alertDialog.setCancelable(false);
                    alertDialog.setTitle("Dados de agendamento");
                    alertDialog.setMessage("Não foi possível coletar os dados");
                    alertDialog.setCanceledOnTouchOutside(true);
                    alertDialog.show();

                    Log.e("Jeferson","Failed to read value");
                }
                for (DataSnapshot dataSnap : dataSnapshot.getChildren()){
                    Agendamento agendamento = dataSnap.getValue(Agendamento.class);
                    mLista.add(agendamento);
                    Log.e("Jeferson","Agendamento(uid): " + agendamento.getUid());
                }

                criarAdapter(mView, mLista);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                //Failed to read value
            }
        });
    }

}
