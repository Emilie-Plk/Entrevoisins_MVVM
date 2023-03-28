package com.example.entrevoisins_mvvm.view.detail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.example.entrevoisins_mvvm.R;
import com.example.entrevoisins_mvvm.databinding.ActivityDetailProfileNeighbourBinding;
import com.example.entrevoisins_mvvm.utils.ViewModelFactory;

public class DetailProfileNeighbourActivity extends AppCompatActivity {

    private static final String NEIGHBOUR_ID = "NEIGHBOUR_ID";
    private ActivityDetailProfileNeighbourBinding binding;

    private DetailProfileNeighbourViewModel viewModel;

    private long neighbourId;

    private boolean isFavorite;

    public static Intent navigate(Context context, long neighbourId) {
        Intent intent = new Intent(context, DetailProfileNeighbourActivity.class);
        intent.putExtra(NEIGHBOUR_ID, neighbourId);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailProfileNeighbourBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        setViewModel();

        neighbourId = getIntent().getLongExtra(NEIGHBOUR_ID, -1);


        updateFabFavorite();

        binding.addFavoriteFab.setOnClickListener(v -> {
            viewModel.updateNeighbourToFav(neighbourId, isFavorite);
        });


        viewModel.getDetailNeighbourViewStateItem(neighbourId).observe(this, detailNeighbourViewStateItem -> {
            Glide.with(this)
                    .load(detailNeighbourViewStateItem.getAvatarUrl())
                    .centerCrop()
                    .into(binding.neighbourProfilePic);
            binding.neighbourProfilePicName.setText(detailNeighbourViewStateItem.getName());
            binding.CVNeighbourNameTV.setText(detailNeighbourViewStateItem.getName());
            binding.CVNeighbourCityTV.setText(detailNeighbourViewStateItem.getAddress());
            binding.CVNeighbourPhoneTV.setText(detailNeighbourViewStateItem.getPhoneNumber());
            String neighbourSocial = this.getResources().getString(R.string.social, detailNeighbourViewStateItem.getName().toLowerCase());
            binding.CVNeighbourSocial.setText(neighbourSocial);
            binding.aboutMeDetailTV.setText(detailNeighbourViewStateItem.getAboutMe());
            isFavorite = detailNeighbourViewStateItem.getIsFavorite();
        });

        binding.backImagebtn.setOnClickListener(v -> finish());
    }

    private void updateFabFavorite() {
        viewModel.isNeighbourFavorite(neighbourId).observe(this, isFav -> {
            if (Boolean.TRUE.equals(isFav)) {
                Log.i("Emilie", "is Fav!");
                binding.addFavoriteFab.setImageResource(R.drawable.baseline_star_24);
            } else {
                Log.i("Emilie", "is not Fav!");
                binding.addFavoriteFab.setImageResource(R.drawable.ic_star_border_white_24dp);
            }
        });
    }

    private void setViewModel() {
        viewModel = new ViewModelProvider(this, ViewModelFactory.getInstance())
                .get(DetailProfileNeighbourViewModel.class);
    }
}