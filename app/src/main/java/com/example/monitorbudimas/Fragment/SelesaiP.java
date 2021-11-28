 package com.example.monitorbudimas.Fragment;

import android.Manifest;
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
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.example.monitorbudimas.API.APIreq;
import com.example.monitorbudimas.API.retroServer;
import com.example.monitorbudimas.Activity.Login;
import com.example.monitorbudimas.Adapter.RecyclerToko;
import com.example.monitorbudimas.Model.loading.responseStart;
import com.example.monitorbudimas.Model.user.responseLogin;
import com.example.monitorbudimas.R;
import com.example.monitorbudimas.Model.toko.toko;
import com.example.monitorbudimas.Model.toko.responseToko;
import com.example.monitorbudimas.Adapter.Session;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SelesaiP#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SelesaiP extends Fragment {

    public static SelesaiP getInstance() {
        SelesaiP fragmentSelesaiP = new SelesaiP();
        return fragmentSelesaiP;
    }
    public static final String USER_ID = "user_id";
    private String userId;
    private double lati, longi;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    RecyclerView recyclerView;
    private RecyclerView.Adapter adData;
    private RecyclerView.LayoutManager lmData;
    com.example.monitorbudimas.Adapter.Session sessionManager;
    private List<toko> itemselesai = new ArrayList<>();
    public Button button1, button2;

    public SelesaiP() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment fragment_selesai.
     */
    // TODO: Rename and change types and number of parameters
    public static SelesaiP newInstance(String param1, String param2) {
        SelesaiP fragment = new SelesaiP();
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
        View view = inflater.inflate(R.layout.fragment_selesai_p, container, false);
        sessionManager = new Session(getActivity());
        if(!sessionManager.isLoggedIn()){
            moveToLogin();
        }
        Intent intent = getActivity().getIntent();
        userId = intent.getStringExtra(USER_ID);

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

        TextView a = view.findViewById(R.id.tes);
        a.setText(userId);
        recyclerView = view.findViewById(R.id.rec_view);
        lmData = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(lmData);
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = df.format(calendar.getTime());
        retrievedata(userId, formattedDate);
        adData = new RecyclerToko(getActivity(), itemselesai);
        recyclerView.setAdapter(adData);

        button1 = view.findViewById(R.id.pulang);
        button2 = view.findViewById(R.id.req_konfirmasi);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updatePulang(userId);
                updateloc(userId, longi, lati);
                button1.setEnabled(false);
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ardReqConfirmDelivery(userId);
                updateloc(userId, longi, lati);
                button2.setEnabled(false);
                sessionManager.logoutSession();
                moveToLogin();
            }
        });

        return view;
    }

    public void retrievedata(String uid, String tanggal) {
        APIreq ardData = retroServer.konekRetrofit().create(APIreq.class);
        Call<responseToko> tampilData = ardData.ardRetrieveDataSelesai(uid, tanggal);

        tampilData.enqueue(new Callback<responseToko>() {
            @Override
            public void onResponse(Call<responseToko> call, Response<responseToko> response) {
                final List<toko> itemtokolist = response.body().getData();
                if (itemtokolist != null) {
                    itemselesai.addAll(itemtokolist);
                    adData.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<responseToko> call, Throwable t) {
                Toast.makeText(getContext(), "gagal menampilkan Data " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void updatePulang(String userId) {
        APIreq ardData = retroServer.konekRetrofit().create(APIreq.class);
        Call<responseStart> tampilData = ardData.ardPulang(userId);

        tampilData.enqueue(new Callback<responseStart>() {
            @Override
            public void onResponse(Call<responseStart> call, Response<responseStart> response) {
                int kode = response.body().getKode();
            }
            @Override
            public void onFailure(Call<responseStart> call, Throwable t) {
                Toast.makeText(getContext(), "gagal" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void ardReqConfirmDelivery(String userId) {
        APIreq ardData = retroServer.konekRetrofit().create(APIreq.class);
        Call<responseStart> tampilData = ardData.ardReqConfirmDelivery(userId);

        tampilData.enqueue(new Callback<responseStart>() {
            @Override
            public void onResponse(Call<responseStart> call, Response<responseStart> response) {
                int kode = response.body().getKode();
            }
            @Override
            public void onFailure(Call<responseStart> call, Throwable t) {
                Toast.makeText(getContext(), "gagal" + t.getMessage(), Toast.LENGTH_SHORT).show();
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

    private void moveToLogin() {
        Intent intent = new Intent(getContext(), Login.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
    }
}