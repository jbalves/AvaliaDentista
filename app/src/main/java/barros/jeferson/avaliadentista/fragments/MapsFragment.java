package barros.jeferson.avaliadentista.fragments;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
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

public class MapsFragment extends Fragment implements OnMapReadyCallback, Request.RequestListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, GoogleMap.OnInfoWindowClickListener, GoogleMap.OnMarkerClickListener {

    private static final int LOCATION_REQUEST_CODE = 1;
    private View mView;
    private GoogleMap mMap;
    private Location mLastLocation;
    private LatLng mPosition;

    private GoogleApiClient mGoogleApiClient;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_maps, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mView = view;
        setupMap();
        //Ocean
        //        .newRequest("http://mobile-aceite.tcu.gov.br:80/mapa-da-saude/rest/estabelecimentos?campos=nomeFantasia,lat,long&quantidade=20", this).get().send();

        Ocean
                .newRequest("http://mobile-aceite.tcu.gov.br:80/mapa-da-saude/rest/estabelecimentos?municipio=manaus&uf=am&campos=nomeFantasia,lat,long", this).get().send();

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            MapFragment fragment = (MapFragment) getChildFragmentManager().findFragmentById(R.id.map);
            fragment.getMapAsync(this);
        } else {
            MapFragment fragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
            fragment.getMapAsync(this);
        }

    }

    @Override
    public void onStart() {
        mGoogleApiClient.connect();
        super.onStart();
    }

    @Override
    public void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
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
        // Add a marker in Manaus and move the camera
        //LatLng manaus = new LatLng(-3.10719, -60.0261);
        //mMap.addMarker(new MarkerOptions().position(manaus).title("Marker in Manaus"));
        //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(manaus, 14));

        mMap.getUiSettings().setMyLocationButtonEnabled(true);
        mMap.getUiSettings().setCompassEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(true);

        //mMap.setInfoWindowAdapter(new MyAdapterMap(this));

        mMap.setOnInfoWindowClickListener(this);

        mMap.setOnMarkerClickListener(this);


        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                // Mostrar diálogo explicativo
            } else {
                // Solicitar permissao
                ActivityCompat.requestPermissions(
                        getActivity(),
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        LOCATION_REQUEST_CODE);
            }
        } else {
            Log.d("Label", "Ativando a localizacao");
            mMap.setMyLocationEnabled(true);
        }

    }


    @SuppressWarnings("MissingPermission")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == LOCATION_REQUEST_CODE) {
            // Verifica as permissoes permitidas
            if (permissions.length > 0 &&
                    permissions[0].equals(Manifest.permission.ACCESS_FINE_LOCATION) &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Seta o mapa para ativar a localizacao do usuario
                mMap.setMyLocationEnabled(true);
            } else {
                Toast.makeText(getActivity(), "Error de permissoes", Toast.LENGTH_LONG).show();
            }

        }
    }


    @Override
    public void onRequestOk(String resposta, JSONObject jsonObject, int code) {
        if (code == Request.NENHUM_ERROR) {
            stringToJson(resposta);
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

                    if (mMap == null) continue;

                    Marker marker = mMap
                            .addMarker(new MarkerOptions()
                                    .position(new LatLng(latitude, longitude))
                                    .title(nomeFantasia)
                                    .snippet("Unidade Saúde Bucal")
                                    //.icon(BitmapDescriptorFactory.fromResource());
                                    //.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))
                                    .icon(BitmapDescriptorFactory.defaultMarker(205.0F)));
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void setupMap() {
        // Create an instance of GoogleAPIClient.
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                // Mostrar diálogo explicativo
            } else {
                // Solicitar permissao
                ActivityCompat.requestPermissions(
                        getActivity(),
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        LOCATION_REQUEST_CODE);
            }
        }else {
            mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                    mGoogleApiClient);

            if (mLastLocation != null) {
                //mLatitudeText.setText(String.valueOf(mLastLocation.getLatitude()));
                //mLongitudeText.setText(String.valueOf(mLastLocation.getLongitude()));
                mPosition = new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude());
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mPosition, 14));
                //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mPosition, 14));
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(mPosition, 14));
            }
        }


    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        Log.d("Jeferson","Janela Marcador clicado: " + marker.getTitle());
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        Log.d("Jeferson","Marcador clicado: " + marker.getPosition());
        return false;
    }
}
