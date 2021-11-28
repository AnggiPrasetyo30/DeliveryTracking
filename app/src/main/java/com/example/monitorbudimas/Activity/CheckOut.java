package com.example.monitorbudimas.Activity;


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

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.monitorbudimas.API.APIreq;
import com.example.monitorbudimas.API.retroServer;
import com.example.monitorbudimas.Model.toko.responseToko;
import com.example.monitorbudimas.Model.user.responseLogin;
import com.example.monitorbudimas.R;
import com.example.monitorbudimas.Fragment.Faktur;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CheckOut extends AppCompatActivity {
    private double lati, longi;
    androidx.viewpager.widget.ViewPager viewPager;
    public Button button;
    public static String id, user_id,nama;
    public static final String key_toko = "toko";
    public static final String USER_ID = "user_id";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_out);
        viewPager = findViewById(R.id.pager);

        Intent intent = getIntent();
        id = intent.getStringExtra(key_toko);
        user_id = intent.getStringExtra(USER_ID);
        TextView a = findViewById(R.id.tes);
        a.setText(id);
//        Toast.makeText(this, "Data ID " + id, Toast.LENGTH_SHORT).show();
        getTabs();

        FusedLocationProviderClient mFusedLocation = LocationServices.getFusedLocationProviderClient(CheckOut.this);
        if (ActivityCompat.checkSelfPermission(CheckOut.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mFusedLocation.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if (location != null){
                        // Do it all with location
                        Log.d("My Current location", "Lat : " + location.getLatitude() + " Long : " + location.getLongitude());
                        // Display in Toast
//                        Toast.makeText(CheckOut.this,
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

        button = (Button) findViewById(R.id.btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateloc(user_id, longi, lati);
                updateStatus();
                Intent intent = new Intent(CheckOut.this, Sopir.class);
                intent.putExtra(USER_ID, user_id);
                startActivity(intent);
            }
        });

    }

    public void updateStatus() {
        APIreq ardData = retroServer.konekRetrofit().create(APIreq.class);
        Call<responseToko> update = ardData.ardfinishToko(id);

        update.enqueue((new Callback<responseToko>() {
            @Override
            public void onResponse(Call<responseToko> call, Response<responseToko> response) {
            }

            @Override
            public void onFailure(Call<responseToko> call, Throwable t) {
            }
        }));
    }


    public void getTabs() {
        final com.example.monitorbudimas.Adapter.ViewPager pageadapter = new com.example.monitorbudimas.Adapter.ViewPager(getSupportFragmentManager());
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                pageadapter.addFragment(Faktur.newInstance(id, nama), "");
                viewPager.setAdapter(pageadapter);
            }
        });
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
                Toast.makeText(CheckOut.this, "Gagal" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}

