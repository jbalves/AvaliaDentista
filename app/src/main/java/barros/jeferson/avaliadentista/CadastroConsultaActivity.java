package barros.jeferson.avaliadentista;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import barros.jeferson.avaliadentista.fragments.DatePickerFragmentAgendamento;
import barros.jeferson.avaliadentista.fragments.DatePickerFragmentConsulta;
import barros.jeferson.avaliadentista.fragments.DatePickerFragmentRetorno;
import barros.jeferson.avaliadentista.model.Agendamento;
import barros.jeferson.avaliadentista.model.UnidadeSaude;

/**
 * Created by jbalves on 11/6/16.
 */
public class CadastroConsultaActivity extends AppCompatActivity {
    private TextView mDataDoAgendamento;
    private TextView mDataDaConsulta;
    private TextView mDataDoRetorno;
    private RatingBar mRatingBar;

    private DatabaseReference mDatabase;
    private DatabaseReference mDatabaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_consulta);

        mDataDoAgendamento = (TextView) findViewById(R.id.dataAgendamentoText);
        mDataDaConsulta = (TextView) findViewById(R.id.dataConsultaText);
        mDataDoRetorno = (TextView) findViewById(R.id.dataRetornoText);
        mRatingBar = (RatingBar) findViewById(R.id.ratingBar);

    }

    public void showDatePickerDialogAgendamento(View view) {
        DialogFragment newFragment = new DatePickerFragmentAgendamento();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    public void showDatePickerDialogConsulta(View view) {
        DialogFragment newFragment = new DatePickerFragmentConsulta();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    public void showDatePickerDialogRetorno(View view) {
        DialogFragment newFragment = new DatePickerFragmentRetorno();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }



    public void salvarConsulta(View view) {
        // Write a message to the database
        mDatabase = FirebaseDatabase.getInstance().getReference("agendamentos");
        mDatabaseUser = FirebaseDatabase.getInstance().getReference("usuarios");

        Agendamento agendamento = new Agendamento();

        //Get current user
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null) {
            // User is signed in
            agendamento.setUnidadeSaude("UBS Morro da Liberdade");
            agendamento.setDataAgendamento((String) mDataDoAgendamento.getText());
            agendamento.setDataConsulta((String) mDataDaConsulta.getText());
            agendamento.setDataRetorno((String) mDataDoRetorno.getText());
            agendamento.setRating(mRatingBar.getRating());
            agendamento.setUid(user.getUid());
            agendamento.setEmail(user.getEmail());

            String key = mDatabase.push().getKey();

            mDatabase.child(key).setValue(agendamento);
            mDatabaseUser.push().child(user.getUid()).setValue(key);
        } else {
            // No user is signed in
        }

        finish();
    }

}
