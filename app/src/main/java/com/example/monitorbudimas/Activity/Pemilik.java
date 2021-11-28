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
import com.example.monitorbudimas.Fragment.Selesai;
import com.example.monitorbudimas.Fragment.Track;
import com.example.monitorbudimas.R;
import com.google.android.material.tabs.TabLayout;

import java.util.Objects;

public class Pemilik extends AppCompatActivity {
    TabLayout tabLayoutp;
    androidx.viewpager.widget.ViewPager viewPagerp;
    Session sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pemilik);
        sessionManager = new Session(Pemilik.this);
        if(!sessionManager.isLoggedIn()){
            moveToLogin();
        }

        Toolbar toolbar = findViewById(R.id.toolbarp);
        setSupportActionBar(toolbar);
        tabLayoutp = findViewById(R.id.tabLayoutp);
        viewPagerp = findViewById(R.id.viewPagerp);
        tabLayoutp.setupWithViewPager(viewPagerp);

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
                pageadapter.addFragment(Track.getInstance(), "Track");

                viewPagerp.setAdapter(pageadapter);
                tabLayoutp.setupWithViewPager(viewPagerp);

                Objects.requireNonNull(tabLayoutp.getTabAt(0)).setIcon(R.drawable.ongoing);
                Objects.requireNonNull(tabLayoutp.getTabAt(1)).setIcon(R.drawable.finish);
                Objects.requireNonNull(tabLayoutp.getTabAt(2)).setIcon(R.drawable.track);
                tabLayoutp.setTabIconTint(state);
                tabLayoutp.setBackgroundResource(R.color.tab);
                tabLayoutp.setSelectedTabIndicatorColor(Color.parseColor("#ffffff"));
                tabLayoutp.setSelectedTabIndicatorHeight((int) (5 * getResources().getDisplayMetrics().density));
                tabLayoutp.setTabTextColors(Color.parseColor("#000000"), Color.parseColor("#ffffff"));
            }
        });
//        BadgeDrawable badgeDrawable = tabLayoutp.getTabAt(0).getOrCreateBadge();
//        badgeDrawable.setVisible(true);
//        badgeDrawable.setNumber(Ongoing.count);
//        BadgeDrawable badgeDrawable1 = tabLayoutp.getTabAt(1).getOrCreateBadge();
//        badgeDrawable1.setVisible(true);
//        badgeDrawable1.setNumber(Selesai.count);
//        BadgeDrawable badgeDrawable2 = tabLayoutp.getTabAt(2).getOrCreateBadge();
//        badgeDrawable2.setVisible(true);
//        badgeDrawable2.setNumber(Track.count);
    }
    private void moveToLogin() {
        Intent intent = new Intent(Pemilik.this, Login.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
    }
}