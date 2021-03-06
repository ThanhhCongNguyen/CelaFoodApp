package com.example.celafoodapp.ui.activity;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.celafoodapp.R;
import com.example.celafoodapp.databinding.ActivityMainBinding;
import com.example.celafoodapp.ui.base.BaseActivity;
import com.example.celafoodapp.ui.fragment.CartFragment;
import com.example.celafoodapp.ui.fragment.HomeFragment;
import com.example.celafoodapp.ui.fragment.OrderedFragment;
import com.example.celafoodapp.ui.fragment.ProfileFragment;
import com.example.celafoodapp.util.AppData;
import com.example.celafoodapp.util.SetUpSharePreferences;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends BaseActivity {
    private ActivityMainBinding binding;
    private Bundle bundle;
    private BottomNavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        String userId = SetUpSharePreferences.getUserId();
        bundle = new Bundle();
        bundle.putString(AppData.Key.userId, userId);

        initMenu();
        binding.bottomNavigation.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener);
        loadFragment(new HomeFragment(), bundle);
    }

    private void initMenu() {
        onNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment;
                switch (item.getItemId()) {
                    case R.id.home:
                        fragment = new HomeFragment();
                        loadFragment(fragment, bundle);
                        return true;
                    case R.id.cart:
                        fragment = new CartFragment();
                        loadFragment(fragment, bundle);
                        return true;
                    case R.id.order:
                        fragment = new OrderedFragment();
                        loadFragment(fragment, bundle);
                        return true;
                    case R.id.profile:
                        fragment = new ProfileFragment();
                        loadFragment(fragment, bundle);
                        return true;
                }
                return false;
            }
        };
    }

    private void loadFragment(Fragment fragment, Bundle bundle) {
        fragment.setArguments(bundle);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frameLayout, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}