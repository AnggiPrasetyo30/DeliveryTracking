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

import com.example.monitorbudimas.Activity.CheckIn;
import com.example.monitorbudimas.Model.toko.toko;
import com.example.monitorbudimas.R;

import java.util.List;

public class AdapterToko extends RecyclerView.Adapter<AdapterToko.MyViewHolder> {
    private List<toko> itemToko;
    Context context;
//    private onItemClickListener onItemClickListener;
    private static final String key_toko = "toko";
    private static final String key_nama = "nama";
    private static final String key_long = "long";
    private static final String key_lat = "lat";
    private static final String USER_ID = "user_id";


    public AdapterToko(Context ct, List<toko> item) {
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

        holder.a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                onItemClickListener.onItemClick(IT);
                Intent intent = new Intent(context, CheckIn.class);
                intent.putExtra(key_toko, IT.getId());
                intent.putExtra(key_nama, IT.getNama());
                intent.putExtra(key_long, IT.getLongitude());
                intent.putExtra(key_lat, IT.getLatitude());
                intent.putExtra(USER_ID, IT.getUid());
                context.startActivity(intent);
            }
        });
    }

//    public void setOnItemClickListener(AdapterToko.onItemClickListener onItemClickListener) {
//        this.onItemClickListener = onItemClickListener;
//    }

    public interface onItemClickListener {
        void onItemClick(toko toko);
    }

    @Override
    public int getItemCount() {
        return itemToko.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView text1, text2;
        CardView a;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            text1 = itemView.findViewById(R.id.cv_nama);
            text2 = itemView.findViewById(R.id.cv_alamat);
            a = itemView.findViewById(R.id.card_view);
        }
    }


}
