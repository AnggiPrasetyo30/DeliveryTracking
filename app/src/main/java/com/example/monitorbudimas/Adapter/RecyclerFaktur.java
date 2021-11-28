package com.example.monitorbudimas.Adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.monitorbudimas.Model.faktur.faktur;
import com.example.monitorbudimas.R;

import java.util.List;

public class RecyclerFaktur extends RecyclerView.Adapter<RecyclerFaktur.MyViewHolder> {
    private final List<faktur> itemFaktur;
    Context context;

    public RecyclerFaktur(Context ct, List<faktur> item){
        this.context = ct;
        this.itemFaktur = item;
    }

    @NonNull
    @Override
    public RecyclerFaktur.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_faktur, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerFaktur.MyViewHolder holder, int position) {
        final faktur IT = itemFaktur.get(position);
        holder.text1.setText(IT.getIdFaktur());
        holder.text2.setText(IT.getStatus());
    }

    @Override
    public int getItemCount() {
        return itemFaktur.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView text1, text2;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            text1 = itemView.findViewById(R.id.no_faktur);
            text2 = itemView.findViewById(R.id.status);
        }
    }
}
