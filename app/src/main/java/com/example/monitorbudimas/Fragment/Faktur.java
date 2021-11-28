package com.example.monitorbudimas.Fragment;

import android.content.Intent;
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
import com.example.monitorbudimas.Adapter.AdapterFaktur;
import com.example.monitorbudimas.R;
import com.example.monitorbudimas.Model.faktur.responseFaktur;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Faktur#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Faktur extends Fragment {

    public static Faktur getInstance() {
        Faktur fragmentFaktur = new Faktur();
        return fragmentFaktur;
    }

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2, idtoko;
    private AdapterFaktur adData;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager lmData;
    private List<com.example.monitorbudimas.Model.faktur.faktur> item = new ArrayList<com.example.monitorbudimas.Model.faktur.faktur>();
    private com.example.monitorbudimas.Model.faktur.faktur faktur;
    private static final String key_toko = "toko";

    public Faktur() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment fragment_faktur.
     */
    // TODO: Rename and change types and number of parameters
    public static Faktur newInstance(String param1, String param2) {
        Faktur fragment = new Faktur();
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
            //toko = (item_toko) getArguments().getSerializable(checkout.key_toko);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_faktur, container, false);
        recyclerView = view.findViewById(R.id.rec_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        Intent intent = getActivity().getIntent();
        idtoko = intent.getStringExtra(key_toko);
        //Toast.makeText(getContext(), ""+idtoko, Toast.LENGTH_SHORT).show();

        retrievefaktur(idtoko);

        adData = new AdapterFaktur(getActivity(), item);
        recyclerView.setAdapter(adData);

        return view;
    }

    public void retrievefaktur(String idtoko) {
        APIreq ardData = retroServer.konekRetrofit().create(APIreq.class);
        Call<responseFaktur> tampilData = ardData.ardretrievefaktur(idtoko);

        tampilData.enqueue(new Callback<responseFaktur>() {
            @Override
            public void onResponse(Call<responseFaktur> call, Response<responseFaktur> response) {
                //int kode = response.body().getKode();
                //String pesan = response.body().getPesan();
                responseFaktur responseModel = response.body();

                if (responseModel != null) {
                   // Log.d("Fragment Faktur", "response is any" + responseModel.getPesan());
                    //Toast.makeText(getContext(), "response is any", Toast.LENGTH_SHORT).show();
                    final List<com.example.monitorbudimas.Model.faktur.faktur> itemfakturlist = responseModel.getData();
                    item.addAll(itemfakturlist);
                    adData.notifyDataSetChanged();
                } else {
                    //Toast.makeText(getContext(), "response is null", Toast.LENGTH_SHORT).show();
                    //Log.d("ProductListFragment", "response is null");
                }
                /*final List<item_faktur> itemfakturlist = response.body().getData();
                item.addAll(itemfakturlist);

                adData.notifyDataSetChanged();*/
            }

            @Override
            public void onFailure(Call<responseFaktur> call, Throwable t) {
                Toast.makeText(getContext(), "gagal menampilkan Data" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}