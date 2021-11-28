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
import com.example.monitorbudimas.Adapter.RecyclerToko;
import com.example.monitorbudimas.Model.toko.toko;
import com.example.monitorbudimas.R;
import com.example.monitorbudimas.Model.toko.responseToko;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Selesai extends Fragment {

    public static Selesai getInstance() {
        return new Selesai();
    }

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1, mParam2;
    private RecyclerToko adDatas;
    private final List<toko> items = new ArrayList<>();

    public Selesai() {
        // Required empty public constructor
    }

    public static Selesai newInstance(String param1, String param2) {
        Selesai fragment = new Selesai();
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
        View view = inflater.inflate(R.layout.fragment_selesai, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.views);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adDatas = new RecyclerToko(getActivity(), items);
        recyclerView.setAdapter(adDatas);

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = df.format(calendar.getTime());

        retrievedata(formattedDate);
        return view;
    }

    public void retrievedata(String formattedDate) {
        APIreq ardData = retroServer.konekRetrofit().create(APIreq.class);
        Call<responseToko> tampilData = ardData.ardretrievetokoselesaip(formattedDate);

        tampilData.enqueue(new Callback<responseToko>() {
            @Override
            public void onResponse(Call<responseToko> call, Response<responseToko> response) {
                assert response.body() != null;
                int kode = response.body().getKode();
                String pesan = response.body().getPesan();

                //Toast.makeText(getContext(), "" + pesan, Toast.LENGTH_SHORT).show();
                responseToko responseModel = response.body();

                if (responseModel != null) {
                    final List<toko> itemtokolist = responseModel.getData();
                    if(itemtokolist == null){
                        Toast.makeText(getContext(), "Belum ada yang selesai", Toast.LENGTH_SHORT).show();
                    }else{
                    items.addAll(itemtokolist);
                    adDatas.notifyDataSetChanged();
                }}
            }

            @Override
            public void onFailure(Call<responseToko> call, Throwable t) {
                Toast.makeText(getContext(), "Gagal menampilkan data" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}