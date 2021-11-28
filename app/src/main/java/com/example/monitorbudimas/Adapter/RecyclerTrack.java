package com.example.monitorbudimas.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.monitorbudimas.Activity.Map;
import com.example.monitorbudimas.Model.mobil.mobil;
import com.example.monitorbudimas.R;

import java.util.List;

public class RecyclerTrack extends RecyclerView.Adapter<RecyclerTrack.MyViewHolder> {
    private List<mobil> itemTrack;
    Context context;
    static String USER_ID = "USER_ID";

    public RecyclerTrack(Context ct, List<mobil> item) {
        this.context = ct;
        this.itemTrack = item;
    }

    @NonNull
    @Override
    public RecyclerTrack.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_track, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerTrack.MyViewHolder holder, int position) {
        final mobil IT = itemTrack.get(position);
        holder.text1.setText(IT.getSopir());
        holder.text2.setText(IT.getUsername());

        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Map.class);
                intent.putExtra(USER_ID, IT.getUser_id());
                context.startActivity(intent);
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
            button = itemView.findViewById(R.id.btn_track);
            crd = itemView.findViewById(R.id.card_view);
        }
    }
}