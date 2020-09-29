package com.example.posthometask.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.posthometask.R;
import com.example.posthometask.adapters.FragmentAdapters;
import com.example.posthometask.data.local.PreferenceUtils;
import com.example.posthometask.data.models.PostModel;
import com.example.posthometask.data.network.AndroidClient;
import com.example.posthometask.ui.auth.AuthActivity;
import com.example.posthometask.ui.list.ListFragment;
import com.example.posthometask.ui.save.SaveFragment;
import com.example.posthometask.ui.user.UserFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    ViewPager viewPager;

    private ArrayList<Fragment> fragmentArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (PreferenceUtils.getUserName().isEmpty()) {
            startActivity(new Intent(this, AuthActivity.class));
        }
        viewPager = findViewById(R.id.main_view_pager);
        bottomNavigationView = findViewById(R.id.main_bottom_nav);
        fragmentArrayList = new ArrayList<>();
        fillFragment();

        viewPager.setAdapter(new FragmentAdapters(fragmentArrayList,getSupportFragmentManager()));

        viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener(){
            @Override
            public void onPageSelected(int position) {
                bottomNavigationView.getMenu().getItem(position).setChecked(true);
            }
        });

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.user_item){
                    viewPager.setCurrentItem(1);
                }else if (item.getItemId() == R.id.list_item){
                    viewPager.setCurrentItem(0);
                }else if (item.getItemId() == R.id.save_item){
                    viewPager.setCurrentItem(2);
                }
                return true;
            }
        });

    }

    private void fillFragment(){
        fragmentArrayList.add(new ListFragment());
        fragmentArrayList.add(new UserFragment());
        fragmentArrayList.add(new SaveFragment());
    }
}