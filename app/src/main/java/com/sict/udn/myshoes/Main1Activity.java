package com.sict.udn.myshoes;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ViewFlipper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.sict.udn.Model.Users;

public class Main1Activity extends AppCompatActivity {

    private ViewFlipper viewFlipper;
    Toolbar toolbar;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView bottomNav = findViewById(R.id.nav_view);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        mAuth = FirebaseAuth.getInstance();
        String email = mAuth.getCurrentUser().getEmail();
        String part[] = email.split("@");
        toolbar.setTitle(part[0]);
        setSupportActionBar(toolbar);
        getSupportFragmentManager().beginTransaction().replace(R.id.frament_container, new HomeFragment()).commit();

    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedfragment = null;

                    switch (item.getItemId()){
                        case R.id.nav_home :
                            selectedfragment = new HomeFragment();
                            break;
                        case R.id.nav_favorite :
                            selectedfragment = new FavoriteFragment();
                            break;
                        case R.id.nav_shopingcart :
                            selectedfragment = new CartFragment();
                            break;
                        case R.id.nav_profile :
                            selectedfragment = new ProfileFragment();
                            break;
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.frament_container,selectedfragment).commit();

                    return true;
                }
            };

}
