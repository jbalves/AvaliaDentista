package barros.jeferson.avaliadentista.fragments;

import android.support.annotation.Nullable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.*;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.oceanbrasil.libocean.Ocean;
import com.oceanbrasil.libocean.control.http.Request;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import barros.jeferson.avaliadentista.R;
import barros.jeferson.avaliadentista.model.UnidadeSaude;

public class MapsFragment extends Fragment implements OnMapReadyCallback, Request.RequestListener {

    private GoogleMap mMap;
    private ArrayList<Marker> mMarkers = new ArrayList<Marker>();
    //private ArrayList<UnidadeSaude> mLista = new ArrayList<>();

    /*
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }
    */


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        return inflater.inflate(R.layout.fragment_maps, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Ocean
                .newRequest("http://mobile-aceite.tcu.gov.br:80/mapa-da-saude/rest/estabelecimentos?municipio=manaus&uf=am&campos=nomeFantasia,lat,long",this).get().send();

        SupportMapFragment fragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        fragment.getMapAsync(this);

    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        //LatLng sydney = new LatLng(-3.14483642578116,-59.3457841873152);
        //mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        //LatLng cameraBase = new LatLng(mMarkers.get(mMarkers.size()).getPosition().latitude,mMarkers.get(mMarkers.size()).getPosition().longitude);
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(cameraBase));
    }

    @Override
    public void onRequestOk(String resposta, JSONObject jsonObject, int code) {
        if (code == Request.NENHUM_ERROR) {
            stringToJson(resposta);
            //criarAdapter(mView, mLista);
        }
    }

    private void stringToJson(String resposta) {
        ArrayList<UnidadeSaude> lista = new ArrayList<>();

        if (resposta != null) {
            try {

                JSONArray unidades = new JSONArray(resposta);

                for (int i = 0; i < unidades.length(); i++) {
                    JSONObject unidade = unidades.getJSONObject(i);
                    String nomeFantasia = unidade.getString("nomeFantasia");
                    double latitude = unidade.getDouble("lat");
                    double longitude = unidade.getDouble("long");
                    /*
                    UnidadeSaude unidadeSaude = new UnidadeSaude();
                    unidadeSaude.setNome(nomeFantasia);
                    unidadeSaude.setLatitude(latitude);
                    unidadeSaude.setLongitude(longitude);
                    mLista.add(unidadeSaude);
                    */
                    Marker marker = mMap.addMarker(new MarkerOptions().position(new LatLng(latitude,longitude)).title(nomeFantasia));
                    //mMarkers.add(marker);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
