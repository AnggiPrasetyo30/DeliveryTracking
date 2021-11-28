package com.example.monitorbudimas.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.monitorbudimas.Activity.ListFaktur;
import com.example.monitorbudimas.Model.toko.toko;
import com.example.monitorbudimas.R;

import java.util.List;

public class RecyclerToko extends RecyclerView.Adapter<RecyclerToko.MyViewHolder> {
    private List<toko> itemToko;
    Context context;
    static String ID_TOKO = "id toko";
    static String NAMATOK = "nama toko";

    public RecyclerToko(Context ct, List<toko> item) {
        this.context = ct;
        this.itemToko = item;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_toko, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final toko IT = itemToko.get(position);
        holder.text1.setText(IT.getNama());
        holder.text2.setText(IT.getAlamat());

        holder.card_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ListFaktur.class);
                intent.putExtra(ID_TOKO, IT.getId());
                intent.putExtra(NAMATOK, IT.getNama());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemToko.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView text1, text2, text3;
        CardView card_view;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            text1 = itemView.findViewById(R.id.cv_nama);
            text2 = itemView.findViewById(R.id.cv_alamat);
            card_view = itemView.findViewById(R.id.card_view);
        }
    }

}
