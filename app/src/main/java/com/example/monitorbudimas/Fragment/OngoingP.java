package com.example.monitorbudimas.Fragment;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.monitorbudimas.API.APIreq;
import com.example.monitorbudimas.API.retroServer;
import com.example.monitorbudimas.Model.user.responseLogin;
import com.example.monitorbudimas.R;
import com.example.monitorbudimas.Adapter.AdapterToko;
import com.example.monitorbudimas.Model.toko.responseToko;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link OngoingP#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OngoingP extends Fragment {
// implements AdapterToko.onItemClickListener
    public static OngoingP getInstance() {
        OngoingP fragmenBerjalan = new OngoingP();
        return fragmenBerjalan;
    }
    private String userId;
    Context context;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private double lati, longi;
    private AdapterToko adData;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager lmData;
    private List<com.example.monitorbudimas.Model.toko.toko> item = new ArrayList<com.example.monitorbudimas.Model.toko.toko>();
    private com.example.monitorbudimas.Model.toko.toko toko;
//    private static final String key_toko = "toko";
//    private static final String key_nama = "nama";
//    private static final String key_long = "long";
//    private static final String key_lat = "lat";
    public static final String USER_ID = "user_id";


    public OngoingP() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment fragment_berjalan.
     */
    // TODO: Rename and change types and number of parameters
    public static OngoingP newInstance(String param1, String param2) {
        OngoingP fragment = new OngoingP();
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
        View view = inflater.inflate(R.layout.fragment_ongoing, container, false);

        Intent intent = getActivity().getIntent();
        userId = intent.getStringExtra(USER_ID);
        TextView a = view.findViewById(R.id.tes);
        a.setText(userId);

        FusedLocationProviderClient mFusedLocation = LocationServices.getFusedLocationProviderClient(getActivity());
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mFusedLocation.getLastLocation().addOnSuccessListener(getActivity(), new OnSuccessListener<Location>() {
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
                        updateloc(userId, longi, lati);
                    }
                }
            });
        } else {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
        }

        recyclerView = view.findViewById(R.id.viewo);
        lmData = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(lmData);
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = df.format(calendar.getTime());
        retrievedata(userId, formattedDate);
        adData = new AdapterToko(getActivity(), item);
        recyclerView.setAdapter(adData);

        return view;
    }

    public void retrievedata(String uid, String tanggal) {
        APIreq ardData = retroServer.konekRetrofit().create(APIreq.class);
        Call<responseToko> tampilData = ardData.ardretrievetoko(userId, tanggal);

        tampilData.enqueue(new Callback<responseToko>() {
            @Override
            public void onResponse(Call<responseToko> call, Response<responseToko> response) {
                final List<com.example.monitorbudimas.Model.toko.toko> itemtokolist = response.body().getData();
                if (itemtokolist != null) {
                    item.addAll(itemtokolist);
                    adData.notifyDataSetChanged();
                }
            }
            @Override
            public void onFailure(Call<responseToko> call, Throwable t) {
                Toast.makeText(getContext(), "gagal menampilkan Data " + t.getMessage(), Toast.LENGTH_SHORT).show();
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
                Toast.makeText(getContext(), "Gagal" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

//    @Override
//    public void onItemClick(toko toko) {
//        Intent intent = new Intent(getActivity(), CheckIn.class);
//        intent.putExtra(key_toko, toko);
//        intent.putExtra(USER_ID, userId);
//        startActivity(intent);
//    }

}