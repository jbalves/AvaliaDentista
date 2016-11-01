package barros.jeferson.avaliadentista;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    private TextView textMapa;
    private TextView textConsultas;
    private TextView textPerfil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setBottomNavigation();
        getSupportActionBar().hide();

    }

    private void setBottomNavigation() {

        textMapa = (TextView) findViewById(R.id.text_mapa);
        textConsultas = (TextView) findViewById(R.id.text_consultas);
        textPerfil = (TextView) findViewById(R.id.text_perfil);

        BottomNavigationView bottomNavigationView = (BottomNavigationView)
                findViewById(R.id.bottom_navigation);

        //Intent intent = new Intent(this, MinhasConsultasActivity.class);
        //startActivity(intent);

        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.action_map:
                                textMapa.setVisibility(View.VISIBLE);
                                textConsultas.setVisibility(View.GONE);
                                textPerfil.setVisibility(View.GONE);
                                break;
                            case R.id.action_consultas:
                                textMapa.setVisibility(View.GONE);
                                textConsultas.setVisibility(View.VISIBLE);
                                textPerfil.setVisibility(View.GONE);
                                break;
                            case R.id.action_perfil:
                                textMapa.setVisibility(View.GONE);
                                textConsultas.setVisibility(View.GONE);
                                textPerfil.setVisibility(View.VISIBLE);
                                break;
                        }
                        return false;
                    }
                });

    }


}
