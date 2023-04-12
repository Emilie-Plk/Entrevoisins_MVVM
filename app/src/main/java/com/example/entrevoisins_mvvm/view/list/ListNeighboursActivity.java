package com.example.entrevoisins_mvvm.view.list;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.entrevoisins_mvvm.databinding.ListNeighboursActivityBinding;
import com.example.entrevoisins_mvvm.view.create.AddNeighbourActivity;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ListNeighboursActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ListNeighboursActivityBinding binding;
        super.onCreate(savedInstanceState);
        binding = ListNeighboursActivityBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        setSupportActionBar(binding.toolbar);

        ListNeighbourPagerAdapter pagerAdapter = new ListNeighbourPagerAdapter(this, getSupportFragmentManager());
        binding.container.setAdapter(pagerAdapter);
        binding.tabs.setupWithViewPager(binding.container);

        binding.addNeighbourFab.setOnClickListener(v ->
            startActivity(AddNeighbourActivity.navigate(this)));
    }
}