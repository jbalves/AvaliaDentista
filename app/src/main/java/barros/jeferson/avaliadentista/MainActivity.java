package barros.jeferson.avaliadentista;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;

import com.oceanbrasil.libocean.Ocean;
import com.oceanbrasil.libocean.control.http.Request;

import org.json.JSONObject;

import java.util.ArrayList;

import barros.jeferson.avaliadentista.fragments.ConsultasFragment;
import barros.jeferson.avaliadentista.fragments.MapFragment;
import barros.jeferson.avaliadentista.fragments.PerfilFragment;
import barros.jeferson.avaliadentista.model.UnidadeSaude;


public class MainActivity extends AppCompatActivity {

    Fragment fragmentMap = new MapFragment();
    Fragment fragmentConsultas = new ConsultasFragment();
    Fragment fragmentPerfil = new PerfilFragment();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addFragment(fragmentMap);

        setBottomNavigation();
        getSupportActionBar().hide();
    }


    private void setBottomNavigation() {

        BottomNavigationView bottomNavigationView = (BottomNavigationView)
                findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.action_map:
                                replaceFragment(fragmentMap);
                                break;

                            case R.id.action_consultas:
                                replaceFragment(fragmentConsultas);
                                break;

                            case R.id.action_perfil:
                                replaceFragment(fragmentPerfil);
                                break;
                        }
                        return false;
                    }
                });

    }

    private void addFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        transaction.add(R.id.layout_fragments, fragment);
        transaction.addToBackStack(null);

        transaction.commit();
    }

    private void replaceFragment(Fragment fragment) {
        FragmentTransaction  transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.layout_fragments, fragment);
        //transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void addConsulta(View view){
        Intent intent = new Intent(this, CadastroConsultaActivity.class);
        startActivity(intent);
    }
}
