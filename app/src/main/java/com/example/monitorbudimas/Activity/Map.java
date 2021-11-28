package com.example.monitorbudimas.Activity;

import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.monitorbudimas.API.APIreq;
import com.example.monitorbudimas.API.retroServer;
import com.example.monitorbudimas.Model.mobil.responseLocMobil;
import com.example.monitorbudimas.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Map extends FragmentActivity implements OnMapReadyCallback {
    private String latitude, longitude, userId;
    int count = 0;
    static String USER_ID = "USER_ID";

    public Map() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        userId = intent.getStringExtra(USER_ID);
        getlocmobil(userId);
        setContentView(R.layout.activity_map);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        content();

        Button back = findViewById(R.id.button2);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Map.this, Pemilik.class);
                startActivity(intent);
            }
        });
    }

    public void getlocmobil(String id) {
        APIreq ardData = retroServer.konekRetrofit().create(APIreq.class);
        Call<responseLocMobil> update = ardData.getlocmobil(id);

        update.enqueue((new Callback<responseLocMobil>() {
            @Override
            public void onResponse(Call<responseLocMobil> call, Response<responseLocMobil> response) {
                longitude = response.body().getLongitude();
                latitude = response.body().getLatitude();
            }

            @Override
            public void onFailure(Call<responseLocMobil> call, Throwable t) {
                Toast.makeText(Map.this, "Gagal Check In" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }));
    }

    private void content() {
        count++;
        getlocmobil(userId);
        refresh(1000);
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
        getlocmobil(userId);
        // Add a marker in Sydney and move the camera
        LatLng loc = new LatLng( Double.parseDouble(latitude), Double.parseDouble(longitude));
        googleMap.addMarker(new MarkerOptions().position(loc).title("Lokasi Terakhir"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(loc, 15));
    }
}