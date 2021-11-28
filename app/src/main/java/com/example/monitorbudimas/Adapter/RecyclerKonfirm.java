package com.example.monitorbudimas.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.monitorbudimas.API.APIreq;
import com.example.monitorbudimas.API.retroServer;
import com.example.monitorbudimas.Model.mobil.mobil;
import com.example.monitorbudimas.Model.mobil.responseMobil;
import com.example.monitorbudimas.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecyclerKonfirm extends RecyclerView.Adapter<RecyclerKonfirm.MyViewHolder> {
    private List<mobil> itemTrack;
    Context context;

    public RecyclerKonfirm(Context ct, List<mobil> item) {
        this.context = ct;
        this.itemTrack = item;
    }

    @NonNull
    @Override
    public RecyclerKonfirm.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_konfirm, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerKonfirm.MyViewHolder holder, int position) {
        final mobil IT = itemTrack.get(position);
        holder.text1.setText(IT.getSopir());
        holder.text2.setText(IT.getUsername());

        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                APIreq ardData = retroServer.konekRetrofit().create(APIreq.class);
                Call<responseMobil> tampilData = ardData.ardupdateloadingstatusselesai(IT.getUser_id());

                tampilData.enqueue(new Callback<responseMobil>() {
                    @Override
                    public void onResponse(Call<responseMobil> call, Response<responseMobil> response) {
                        Toast.makeText(holder.button.getContext(), "Berhasil update status", Toast.LENGTH_SHORT).show();
                        holder.button.setEnabled(false);
                    }
                    @Override
                    public void onFailure(Call<responseMobil> call, Throwable t) {
                        Toast.makeText(holder.button.getContext(), "Gagal update status", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemTrack.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView text1, text2;
        Button button;
        CardView crd;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            text1 = itemView.findViewById(R.id.cv_sopir);
            text2 = itemView.findViewById(R.id.cv_nomobil);
            button = itemView.findViewById(R.id.btn_konfirm);
            crd = itemView.findViewById(R.id.card_view);
        }
    }
}