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
import com.example.monitorbudimas.Adapter.RecyclerFaktur;
import com.example.monitorbudimas.Model.faktur.responseFaktur;
import com.example.monitorbudimas.Model.faktur.faktur;
import com.example.monitorbudimas.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListFaktur extends AppCompatActivity {
    static String id, ID_TOKO = "id toko", NAMATOK = "nama toko";
    private RecyclerFaktur adDataf;
    TextView daftur;
    private final List<faktur> itemf = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listfaktur);

        Intent intent = getIntent();
        daftur = findViewById(R.id.daftarfaktur);
        id = intent.getStringExtra(ID_TOKO);
        daftur.setText("Daftar Faktur - "+intent.getStringExtra(NAMATOK));

        RecyclerView recyclerView = findViewById(R.id.viewf);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adDataf = new RecyclerFaktur(this, itemf);
        recyclerView.setAdapter(adDataf);

        retrievedata(id);
    }
    public void retrievedata(String uid) {
        APIreq ardData = retroServer.konekRetrofit().create(APIreq.class);
        Call<responseFaktur> tampilData = ardData.ardretrievefaktur(uid);

        tampilData.enqueue(new Callback<responseFaktur>() {
            @Override
            public void onResponse(Call<responseFaktur> call, Response<responseFaktur> response) {
                assert response.body() != null;
                String pesan = response.body().getPesan();

                //Toast.makeText(ListToko.this, "" + pesan, Toast.LENGTH_SHORT).show();
                final List<faktur> itemfakturlist = response.body().getData();

                if (itemfakturlist != null) {
                    itemf.addAll(itemfakturlist);
                    adDataf.notifyDataSetChanged();
                }else{
                    Toast.makeText(ListFaktur.this, "Tidak ada daftar faktur", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<responseFaktur> call, Throwable t) {
                Toast.makeText(ListFaktur.this, "Gagal menampilkan data" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}