package com.example.monitorbudimas.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.monitorbudimas.API.APIreq;
import com.example.monitorbudimas.API.retroServer;
import com.example.monitorbudimas.Model.faktur.faktur;
import com.example.monitorbudimas.Model.faktur.responseFaktur;
import com.example.monitorbudimas.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AdapterFaktur extends RecyclerView.Adapter<AdapterFaktur.MyViewHolder> {

    private List<faktur> itemFaktur;
    Context context;
    private onItemClickListener onItemClickListener;
    private String selectedStatus;

    public AdapterFaktur(Context ct, List<faktur> item) {
        this.context = ct;
        this.itemFaktur = item;

    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_usfaktur, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final faktur IT = itemFaktur.get(position);
        holder.text1.setText(IT.getIdFaktur());
        holder.text2.setText(IT.getStatus());
        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showOptionDialog();
            }

            private void showOptionDialog() {
                String[] Status = {"Diterima", "Diterima Sebagian", "Ditolak"};
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Ubah Status Faktur");
                builder.setSingleChoiceItems(Status, 0, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        selectedStatus = Status[which];
                    }
                });
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Toast.makeText(context, selectedStatus, Toast.LENGTH_SHORT).show();
                        APIreq ardData = retroServer.konekRetrofit().create(APIreq.class);
                        Call<responseFaktur> updateFaktur = ardData.ardupdatefaktur(IT.getIdFaktur(), selectedStatus);

                        updateFaktur.enqueue(new Callback<responseFaktur>() {
                            @Override
                            public void onResponse(Call<responseFaktur> call, Response<responseFaktur> response) {
                                Toast.makeText(context, "Berhasil Update" , Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onFailure(Call<responseFaktur> call, Throwable t) {
                                Toast.makeText(context, "Gagal Update"+ t.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                        dialog.dismiss();
                        holder.button.setEnabled(false);
                    }
                });
                builder.setNegativeButton("BATAL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.create();
                builder.show();
            }
        });
    }

    public void setOnItemClickListener(AdapterFaktur.onItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface onItemClickListener {
        void onItemClick(faktur faktur);
    }

    @Override
    public int getItemCount() {
        return itemFaktur.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView text1,text2;
        Button button;
        CardView a;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            text1 = itemView.findViewById(R.id.no_faktur);
            text2 = itemView.findViewById(R.id.status);
            button = itemView.findViewById(R.id.update_btn);
        }
    }
}


