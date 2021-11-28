package com.example.monitorbudimas.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.monitorbudimas.API.APIreq;
import com.example.monitorbudimas.API.retroServer;
import com.example.monitorbudimas.Adapter.Session;
import com.example.monitorbudimas.Model.loading.responseStart;
import com.example.monitorbudimas.Model.loading.responseUstart;
import com.example.monitorbudimas.Model.user.responseLogin;
import com.example.monitorbudimas.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Welcome extends AppCompatActivity {
    private double lati, longi;
    com.example.monitorbudimas.Adapter.Session sessionManager;
    String userId, posisi, uname, pesan;
    TextView etPosisi;
    public static final String USER_ID = "user_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        sessionManager = new Session(Welcome.this);
        if (!sessionManager.isLoggedIn()) {
            moveToLogin();
        }
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = df.format(calendar.getTime());

        userId = sessionManager.getUserDetail().get(Session.USER_ID);
        uname = sessionManager.getUserDetail().get(Session.USERNAME);
        posisi = sessionManager.getUserDetail().get(Session.POSISI);

        cekstart(userId,formattedDate);
        etPosisi = findViewById(R.id.posisi);
        if (posisi.equals("pemilik")) {
            etPosisi.setText("Pemilik");
        } else if (posisi.equals("kepala gudang")) {
            etPosisi.setText("Kepala Gudang");
        } else if (posisi.equals("mobil")) {
            etPosisi.setText("Sopir");
        }

        FusedLocationProviderClient mFusedLocation = LocationServices.getFusedLocationProviderClient(Welcome.this);
        if (ActivityCompat.checkSelfPermission(Welcome.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mFusedLocation.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if (location != null){
                        // Do it all with location
                        Log.d("My Current location", "Lat : " + location.getLatitude() + " Long : " + location.getLongitude());
                        // Display in Toast
                       // Toast.makeText(Welcome.this,
//                                "Lat : " + location.getLatitude() + " Long : " + location.getLongitude(),
//                                Toast.LENGTH_LONG).show();
                        longi = location.getLongitude();
                        lati = location.getLatitude();
                        updateloc(userId, longi, lati);
                    }
                }
            });
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
        }

        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(posisi.equals("pemilik")){
                    Intent intent = new Intent(Welcome.this, Pemilik.class);
                    updateloc(userId, longi, lati);
                    startActivity(intent);
                }else if(posisi.equals("kepala gudang")){
                    Intent intent = new Intent(Welcome.this, KepalaGudang.class);
                    updateloc(userId, longi, lati);
                    startActivity(intent);
                }else if(posisi.equals("mobil")){
                    if(pesan.equals("Siap Berangkat!")){
                        ardstart(userId);
                        updateloc(userId, longi, lati);
                        Intent intent = new Intent(Welcome.this, Sopir.class);
                        intent.putExtra(USER_ID, userId);
                        startActivity(intent);
                    }else if (pesan.equals("Data Belum Loading!!")){
                        Toast.makeText(Welcome.this, pesan, Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
    public void ardstart(String a) {
        APIreq ardData = retroServer.konekRetrofit().create(APIreq.class);
        Call<responseUstart> tampilData = ardData.ardstart(a);

        tampilData.enqueue(new Callback<responseUstart>() {
            @Override
            public void onResponse(Call<responseUstart> call, Response<responseUstart> response) {
                assert response.body() != null;
                Toast.makeText(Welcome.this, "Berangkat!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<responseUstart> call, Throwable t) {
                Toast.makeText(Welcome.this, "Gagal", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void cekstart(String a, String b) {
        APIreq ardData = retroServer.konekRetrofit().create(APIreq.class);
        Call<responseStart> tampilData = ardData.cekstart(a, b);

        tampilData.enqueue(new Callback<responseStart>() {
            @Override
            public void onResponse(Call<responseStart> call, Response<responseStart> response) {
                assert response.body() != null;
                int kode = response.body().getKode();
                pesan = response.body().getPesan();
            }

            @Override
            public void onFailure(Call<responseStart> call, Throwable t) {
                Toast.makeText(Welcome.this, "Gagal menampilkan data" + t.getMessage(), Toast.LENGTH_SHORT).show();
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
                Toast.makeText(Welcome.this, "Gagal" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void moveToLogin() {
        Intent intent = new Intent(Welcome.this, Login.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
    }
}