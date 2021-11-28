package com.example.monitorbudimas.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.monitorbudimas.API.APIreq;
import com.example.monitorbudimas.API.retroServer;
import com.example.monitorbudimas.Adapter.RecyclerKonfirm;
import com.example.monitorbudimas.Model.mobil.mobil;
import com.example.monitorbudimas.R;
import com.example.monitorbudimas.Model.mobil.responseMobil;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Konfirmasi extends Fragment {

    public static Konfirmasi getInstance() {
        return new Konfirmasi();
    }

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1, mParam2;
    private RecyclerKonfirm adDatak;
    private final List<mobil> itemk = new ArrayList<>();

    public Konfirmasi() {
        // Required empty public constructor
    }

    public static Konfirmasi newInstance(String param1, String param2) {
        Konfirmasi fragment = new Konfirmasi();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_konfirmasi, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.viewk);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adDatak = new RecyclerKonfirm(getActivity(), itemk);
        recyclerView.setAdapter(adDatak);
        retrievedata();
        return view;
    }

    public void retrievedata() {
        APIreq ardData = retroServer.konekRetrofit().create(APIreq.class);
        Call<responseMobil> tampilData = ardData.ardretrievemobilpulang();

        tampilData.enqueue(new Callback<responseMobil>() {
            @Override
            public void onResponse(Call<responseMobil> call, Response<responseMobil> response) {
                assert response.body() != null;
                int kode = response.body().getKode();
                String pesan = response.body().getPesan();

                //Toast.makeText(getContext(), "" + pesan, Toast.LENGTH_SHORT).show();
                responseMobil responseModel = response.body();

                if (responseModel != null) {
                    final List<mobil> itemmobillist = responseModel.getData();
                    if(itemmobillist == null){
                        Toast.makeText(getContext(), "Belum ada yang pulang", Toast.LENGTH_SHORT).show();
                    }else{
                    itemk.addAll(itemmobillist);
                    adDatak.notifyDataSetChanged();
                }}
            }

            @Override
            public void onFailure(Call<responseMobil> call, Throwable t) {
                Toast.makeText(getContext(), "Gagal menampilkan data" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}