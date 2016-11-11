package barros.jeferson.avaliadentista;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RatingBar;
import android.widget.Toast;

import barros.jeferson.avaliadentista.fragments.DatePickerFragmentAgendamento;
import barros.jeferson.avaliadentista.fragments.DatePickerFragmentConsulta;
import barros.jeferson.avaliadentista.fragments.DatePickerFragmentRetorno;

/**
 * Created by jbalves on 11/6/16.
 */
public class CadastroConsultaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_consulta);
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

}
