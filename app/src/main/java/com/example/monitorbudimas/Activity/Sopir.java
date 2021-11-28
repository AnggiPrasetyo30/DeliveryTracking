 package com.example.monitorbudimas.Activity;

 import android.content.res.ColorStateList;
 import android.graphics.Color;
 import android.os.Bundle;
 import android.os.Handler;

 import androidx.annotation.Nullable;
 import androidx.appcompat.app.AppCompatActivity;
 import androidx.appcompat.widget.Toolbar;

 import com.example.monitorbudimas.Adapter.ViewPager;
 import com.example.monitorbudimas.Fragment.SelesaiP;
 import com.example.monitorbudimas.Fragment.OngoingP;
 import com.example.monitorbudimas.R;
 import com.google.android.material.tabs.TabLayout;

 public class Sopir extends AppCompatActivity {
     private double lati, longi;
    TabLayout tabLayout;
    androidx.viewpager.widget.ViewPager viewPager;
    Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sopir);


        tabLayout = findViewById(R.id.tab_layout);
        viewPager = findViewById(R.id.pager);
        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getTabs();
    }

    public void getTabs() {
        ColorStateList state = new ColorStateList(new int[][]{
                new int[]{android.R.attr.state_selected}, //1
                new int[]{android.R.attr.state_enabled}, //2
        },
                new int[] {
                        Color.WHITE, //1
                        Color.parseColor("#000000"), //2
                }
        );

        final ViewPager pageadapter = new ViewPager(getSupportFragmentManager());
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                pageadapter.addFragment(OngoingP.getInstance(), "Ongoing");
                pageadapter.addFragment(SelesaiP.getInstance(), "Selesai");

                viewPager.setAdapter(pageadapter);
                tabLayout.setupWithViewPager(viewPager);

                tabLayout.getTabAt(0).setIcon(R.drawable.ongoing);
                tabLayout.getTabAt(1).setIcon(R.drawable.ic_selesai);

                tabLayout.setTabIconTint(state);
                tabLayout.setBackgroundResource(R.color.tab);
                tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#ffffff"));
                tabLayout.setSelectedTabIndicatorHeight((int) (5 * getResources().getDisplayMetrics().density));
                tabLayout.setTabTextColors(Color.parseColor("#000000"), Color.parseColor("#ffffff"));

            }
        });
    }
}
