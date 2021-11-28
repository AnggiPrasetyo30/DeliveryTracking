package com.example.monitorbudimas.Activity;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.monitorbudimas.Adapter.Session;
import com.example.monitorbudimas.Adapter.ViewPager;
import com.example.monitorbudimas.Fragment.Ongoing;
import com.example.monitorbudimas.Fragment.Konfirmasi;
import com.example.monitorbudimas.Fragment.Selesai;
import com.example.monitorbudimas.R;
import com.google.android.material.tabs.TabLayout;

import java.util.Objects;

public class KepalaGudang extends AppCompatActivity {
    TabLayout tabLayout;
    androidx.viewpager.widget.ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kepala_gudang);
        Session sessionManager = new Session(KepalaGudang.this);
        if (!sessionManager.isLoggedIn()) {
            moveToLogin();
        }

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);
        tabLayout.setupWithViewPager(viewPager);

        ColorStateList state = new ColorStateList(new int[][]{
                new int[]{android.R.attr.state_selected}, //1
                new int[]{android.R.attr.state_enabled}, //2
        },
                new int[] {
                        Color.WHITE, //1
                        Color.parseColor("#000000"), //2
                }
        );

        setSupportActionBar(toolbar);
                final ViewPager pageadapter = new ViewPager(getSupportFragmentManager());
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                pageadapter.addFragment(Ongoing.getInstance(), "Ongoing");
                pageadapter.addFragment(Selesai.getInstance(), "Selesai");
                pageadapter.addFragment(Konfirmasi.getInstance(), "Konfirmasi");

                viewPager.setAdapter(pageadapter);
                tabLayout.setupWithViewPager(viewPager);

                Objects.requireNonNull(tabLayout.getTabAt(0)).setIcon(R.drawable.ongoing);
                Objects.requireNonNull(tabLayout.getTabAt(1)).setIcon(R.drawable.finish);
                Objects.requireNonNull(tabLayout.getTabAt(2)).setIcon(R.drawable.konfirmasi);
                tabLayout.setTabIconTint(state);
                tabLayout.setBackgroundResource(R.color.tab);
                tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#ffffff"));
                tabLayout.setSelectedTabIndicatorHeight((int) (5 * getResources().getDisplayMetrics().density));
                tabLayout.setTabTextColors(Color.parseColor("#000000"), Color.parseColor("#ffffff"));
            }
        });
    }
    private void moveToLogin() {
        Intent intent = new Intent(KepalaGudang.this, Login.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
    }
}