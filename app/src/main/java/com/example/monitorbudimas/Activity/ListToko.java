package com.example.monitorbudimas.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.monitorbudimas.API.APIreq;
import com.example.monitorbudimas.API.retroServer;
import com.example.monitorbudimas.Adapter.RecyclerToko;
import com.example.monitorbudimas.Model.toko.responseToko;
import com.example.monitorbudimas.Model.toko.toko;
import com.example.monitorbudimas.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListToko extends AppCompatActivity {
    static String USER_ID = "USER_ID", TANGGAL = "tanggal", PLAT = "plat";
    private RecyclerToko adData;
    TextView daftok;
    private final List<toko> item = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listtoko);
        Intent intent = getIntent();
        String uid = intent.getStringExtra(USER_ID);
        String tanggal = intent.getStringExtra(TANGGAL);
        String plat = intent.getStringExtra(PLAT);

        RecyclerView recyclerView = findViewById(R.id.view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adData = new RecyclerToko(this, item);
        recyclerView.setAdapter(adData);
        daftok = findViewById(R.id.daftartoko);
        daftok.setText("Daftar Toko - "+plat);

        retrievedata(uid, tanggal);
    }

    public void retrievedata(String uid, String tanggal) {
        APIreq ardData = retroServer.konekRetrofit().create(APIreq.class);
        Call<responseToko> tampilData = ardData.ardretrievetoko(uid, tanggal);

        tampilData.enqueue(new Callback<responseToko>() {
            @Override
            public void onResponse(Call<responseToko> call, Response<responseToko> response) {
                assert response.body() != null;
                String pesan = response.body().getPesan();

                //Toast.makeText(ListToko.this, "" + pesan, Toast.LENGTH_SHORT).show();
                final List<toko> itemtokolist = response.body().getData();

                if (itemtokolist != null) {
                    item.addAll(itemtokolist);
                    adData.notifyDataSetChanged();
                }else{
                    Toast.makeText(ListToko.this, "Tidak ada daftar toko", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<responseToko> call, Throwable t) {
                Toast.makeText(ListToko.this, "Gagal menampilkan data" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}