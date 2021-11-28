package com.example.monitorbudimas.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.monitorbudimas.Activity.ListToko;
import com.example.monitorbudimas.Model.mobil.mobil;
import com.example.monitorbudimas.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


public class  RecyclerMobil extends RecyclerView.Adapter<RecyclerMobil.MyViewHolder> {
    private final List<mobil> itemMobil;
    Context context;
    static String USER_ID = "USER_ID";
    static String TANGGAL = "tanggal";
    static String PLAT = "plat";

    public RecyclerMobil(Context ct, List<mobil> item){
        this.context = ct;
        this.itemMobil = item;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_mobil, parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final mobil IT = itemMobil.get(position);
        holder.text1.setText(IT.getUsername());
        holder.text2.setText(IT.getSopir());

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = df.format(calendar.getTime());

        holder.crd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ListToko.class);
                intent.putExtra(USER_ID, IT.getUser_id());
                intent.putExtra(TANGGAL, formattedDate);
                intent.putExtra(PLAT, IT.getUsername());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemMobil.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView text1,text2;
        CardView crd;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            text1 = itemView.findViewById(R.id.cv_nomobil);
            text2 = itemView.findViewById(R.id.cv_sopir);
            crd = itemView.findViewById(R.id.card_view);
        }
    }


}
