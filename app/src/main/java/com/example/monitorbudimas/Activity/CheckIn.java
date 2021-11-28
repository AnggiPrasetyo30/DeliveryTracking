package com.example.monitorbudimas.Activity;


import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.monitorbudimas.API.APIreq;
import com.example.monitorbudimas.API.retroServer;
import com.example.monitorbudimas.Model.toko.responseToko;
import com.example.monitorbudimas.Model.user.responseLogin;
import com.example.monitorbudimas.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CheckIn extends FragmentActivity implements OnMapReadyCallback {
    private double lati, longi;
    TextView namatoko;
    public String id, user_id, latitude, longitude,namtok;
    private static final String key_toko = "toko";
    private static final String key_nama = "nama";
    private static final String key_long = "long";
    private static final String key_lat = "lat";
    public static final String USER_ID = "user_id";
    Button back;
    int count = 0;

    public CheckIn() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_in);

        Intent intent = getIntent();
        namatoko = findViewById(R.id.namatoko);
        user_id = intent.getStringExtra(USER_ID);
        id = intent.getStringExtra(key_toko);
        longitude = intent.getStringExtra(key_long);
        latitude = intent.getStringExtra(key_lat);
        namtok = intent.getStringExtra(key_nama);
        namatoko.setText(namtok);
        back = findViewById(R.id.button2);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        content();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateStatus(id);
                Intent intent = new Intent(CheckIn.this, CheckOut.class);
                intent.putExtra(key_toko, id);
                intent.putExtra(USER_ID, user_id);
//                updateloc(user_id, longi, lati);
                startActivity(intent);
            }
        });
    }

    private void content() {
        count++;

        FusedLocationProviderClient mFusedLocation = LocationServices.getFusedLocationProviderClient(CheckIn.this);
        if (ActivityCompat.checkSelfPermission(CheckIn.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mFusedLocation.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if (location != null){
                        // Do it all with location
                        Log.d("My Current location", "Lat : " + location.getLatitude() + " Long : " + location.getLongitude());
                        // Display in Toast
//                        Toast.makeText(CheckIn.this,
//                                "Lat : " + location.getLatitude() + " Long : " + location.getLongitude(),
//                                Toast.LENGTH_LONG).show();
                        longi = location.getLongitude();
                        lati = location.getLatitude();
                        updateloc(user_id, longi, lati);
                    }
                }
            });
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
        }

        refresh(5000);
    }

    private void refresh(int i) {
        final Handler handler = new Handler();
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                content();
            }
        };
        handler.postDelayed(runnable, i);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        // Add a marker in Sydney and move the camera
        LatLng loc = new LatLng( Double.parseDouble(latitude), Double.parseDouble(longitude));
        googleMap.addMarker(new MarkerOptions().position(loc).title(namtok));
//        googleMap.addMarker(new MarkerOptions().position(new LatLng(lati,longi)).title("You"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(loc, 20));
    }

    public void updateStatus(String id) {
        APIreq ardData = retroServer.konekRetrofit().create(APIreq.class);
        Call<responseToko> update = ardData.ardupdateToko(id);

        update.enqueue((new Callback<responseToko>() {
            @Override
            public void onResponse(Call<responseToko> call, Response<responseToko> response) {
                Toast.makeText(CheckIn.this, "Berhasil Check In", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<responseToko> call, Throwable t) {
                Toast.makeText(CheckIn.this, "Gagal Check In" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }));
    }

    public void updateloc(String a, double b, double c) {
        APIreq ardData = retroServer.konekRetrofit().create(APIreq.class);
        Call<responseLogin> tampilData = ardData.updateloc(a, b, c);

        tampilData.enqueue(new Callback<responseLogin>() {
            @Override
            public void onResponse(Call<responseLogin> call, Response<responseLogin> response) {
                assert response.body() != null;
                String pesann = response.body().getPesan();
            }

            @Override
            public void onFailure(Call<responseLogin> call, Throwable t) {
                Toast.makeText(CheckIn.this, "Gagal" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}