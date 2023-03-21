package com.example.bterjavacode;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import android.database.sqlite.SQLiteDatabase;

import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.PlaceLikelihood;
import com.google.android.libraries.places.api.net.FindCurrentPlaceRequest;
import com.google.android.libraries.places.api.net.FindCurrentPlaceResponse;
import com.google.android.libraries.places.api.net.PlacesClient;

import java.util.Arrays;
import java.util.List;

/**
 * An activity that displays a map showing the place at the device's current location.
 */
public class MapsActivity extends AppCompatActivity
        implements OnMapReadyCallback {
    private GoogleMap map;
    private static String TAG = "MapsActivity";
    private static String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static String COURSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private Boolean mLocationPermissionsGranted = false;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private static final float DEFAULT_ZOOM = 12.5f;


    private void getDeviceLocation(){
        Log.d(TAG, "Getting device location");
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        try{
            if(mLocationPermissionsGranted){
                @SuppressLint("MissingPermission") Task location = mFusedLocationProviderClient.getLastLocation();
                location.addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if(task.isSuccessful()){
                            Log.d(TAG, "onComplete: found location!");
                            Location currentLocation = (Location) task.getResult();

                            moveCamera(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()), DEFAULT_ZOOM);
                        }else{
                            Log.d(TAG, "onComplete: location is null");
                            Toast.makeText(MapsActivity.this,
                                    "unable to get current location", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        }catch(SecurityException e){
            Log.d(TAG, "getDeviceLocation: Security Exception" + e.getMessage());
        }
    }

    private void moveCamera(LatLng latLng, float zoom){
        Log.d(TAG, "moveCamera: moving the camera to lat: " + latLng.latitude + ", " +
                "lng: " + latLng.longitude);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        getLocationPermission();
    }
    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(GoogleMap map) {
        Toast.makeText(this, "Map is Ready", Toast.LENGTH_SHORT).show();
        this.map = map;
        if(mLocationPermissionsGranted){
            getDeviceLocation();

            if(ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
                return;
            }
            map.setMyLocationEnabled(true);
        }
        DataBaseHelper databaseHelper = new DataBaseHelper(MapsActivity.this);

        //incorporate into current code
        LatLng feua = new LatLng(14.410699895415755, 121.0380546798303);//A
        map.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
                .position(feua).title("Node A: FEU Alabang Drop-off Point"));
        map.moveCamera(CameraUpdateFactory.newLatLng(feua));
        LatLng festi = new LatLng(14.41755, 121.04052);//B
        map.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
                .position(festi).title("Node B: Festival Mall Drop-Off Point 1"));
        /*
        LatLng mktTerminal = new LatLng(14.424237250117503, 121.031766999137);//C
        map.addMarker(new MarkerOptions().position(mktTerminal).title("Node C: Alabang MKT Terminal"));
        LatLng baclaranTerminal = new LatLng(14.53414433, 120.9981471);//D
        map.addMarker(new MarkerOptions().position(baclaranTerminal).title("Node D:Baclaran Terminal"));
        LatLng libradaSt = new LatLng(14.52223473, 120.9967539);//E
        map.addMarker(new MarkerOptions().position(libradaSt).title("Node E: Quirino Avenue Librada Street"));
        LatLng jalandoniSt = new LatLng(14.55438577, 120.9851946);//F
        map.addMarker(new MarkerOptions().position(jalandoniSt).title("Node F: Quirino Avenue Jalandoni Street"));
        LatLng genLuna = new LatLng(14.41264687, 120.9985847);//G
        map.addMarker(new MarkerOptions().position(genLuna).title("Node G: Quirino Avenue General Luna"));
        LatLng buenaventura = new LatLng(14.48550503, 120.9850211);//H
        map.addMarker(new MarkerOptions().position(buenaventura).title("Node H: Quirino Avenue Buenaventura"));
        LatLng bfGoodrich = new LatLng(14.57926916, 120.9985623);//I
        map.addMarker(new MarkerOptions().position(bfGoodrich).title("Node I: Quirino Avenue BF Goodrich"));
        LatLng castilloSt = new LatLng(14.47250629, 120.9911067);//J
        map.addMarker(new MarkerOptions().position(castilloSt).title("Node J: Alabang Castillo Street"));
        LatLng alabangMain = new LatLng(14.4171664, 121.0451872);//K
        map.addMarker(new MarkerOptions().position(alabangMain).title("Node K: Alabang Main Street"));
        LatLng alabangLPMuntiBldg = new LatLng(14.44508776, 120.9933165);//L
        map.addMarker(new MarkerOptions().position(alabangLPMuntiBldg).title("Node L: Alabang Las Pinas Muntinlupa Bldg."));
        LatLng vacPHTI = new LatLng(14.5676969003949, 121.0224387);//M
        map.addMarker(new MarkerOptions().position(vacPHTI).title("Node M: Alabang Vac PHTI"));
        LatLng troikaFurnishing = new LatLng(14.46260598, 120.9615692);//N
        map.addMarker(new MarkerOptions().position(troikaFurnishing).title("Node N: Alabang Troika Furnishing"));
        LatLng mAlvarez = new LatLng(14.42835051, 121.0026374);//O
        map.addMarker(new MarkerOptions().position(mAlvarez).title("Node O: Alabang M. Alvarez"));
        LatLng mod120Ave = new LatLng(14.42541484, 121.0126543);//P
        map.addMarker(new MarkerOptions().position(mod120Ave).title("Node P: Alabang Mod 120 Avenue"));
        LatLng twinCinema = new LatLng(14.42327501, 121.0299647);//Q
        map.addMarker(new MarkerOptions().position(twinCinema).title("Node Q: Alabang Twin Cinema"));
        LatLng acaciaSt = new LatLng(14.3930641, 121.0113036);//R
        map.addMarker(new MarkerOptions().position(acaciaSt).title("Node R: Alabang Acacia Street"));
        */
        //Current working nodes
        LatLng southStation = new LatLng(14.421636643674436, 121.04315613656041);
        map.addMarker(new MarkerOptions().position(southStation).title("South Station"));
        LatLng villageSquare = new LatLng(14.429156204261789, 121.02041268141576);
        map.addMarker(new MarkerOptions().position(villageSquare).title("Village Square"));
        LatLng puregold = new LatLng(14.438055604532122, 121.00458172498732);
        map.addMarker(new MarkerOptions().position(puregold).title("Puregold"));
        LatLng perpetualHelpHospital = new LatLng(14.448575259930099, 120.98579938040515);
        map.addMarker(new MarkerOptions().position(perpetualHelpHospital).title("Perpetual Help Hospital"));
        LatLng mamaLous = new LatLng(14.448632090532035, 121.01004273738914);
        map.addMarker(new MarkerOptions().position(mamaLous).title("Mama Lou's"));
        LatLng elGrande = new LatLng(14.455960461169026, 121.00753827328728);
        map.addMarker(new MarkerOptions().position(elGrande).title("El Grande"));
        LatLng bfSouthlandClassic = new LatLng(14.44624773037558, 121.00777026893012);
        map.addMarker(new MarkerOptions().position(bfSouthlandClassic).title("BF Southland Classic"));

        map.setMinZoomPreference(1f);
        map.setMaxZoomPreference(20.0f);
    }
    private void getLocationPermission(){
        Log.d(TAG, "getLocationPermissionResult: called");
        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION};
        if(ContextCompat.checkSelfPermission(this.getApplicationContext(),
                FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            if(ContextCompat.checkSelfPermission(this.getApplicationContext(),
                    COURSE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                mLocationPermissionsGranted = true;
            }else{
                ActivityCompat.requestPermissions(this, permissions, LOCATION_PERMISSION_REQUEST_CODE);
            }
        }
        else{
            ActivityCompat.requestPermissions(this, permissions, LOCATION_PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.d(TAG, "onRequestPermissionsResult: called");
        mLocationPermissionsGranted = false;
        switch (requestCode) {
            case LOCATION_PERMISSION_REQUEST_CODE: {
                if (grantResults.length > 0) {
                    for (int i = 0; i < grantResults.length; i++) {
                        if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                            mLocationPermissionsGranted = false;
                            return;
                        }
                    }
                    mLocationPermissionsGranted = true;
                }
            }
        }
    }


}
