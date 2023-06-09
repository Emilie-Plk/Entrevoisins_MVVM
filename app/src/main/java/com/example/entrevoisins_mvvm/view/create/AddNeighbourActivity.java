package com.example.entrevoisins_mvvm.view.create;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.example.entrevoisins_mvvm.databinding.AddNeighbourActivityBinding;

import java.util.function.Consumer;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class AddNeighbourActivity extends AppCompatActivity {
    private AddNeighbourActivityBinding binding;

    private AddNeighbourViewModel viewModel;

    public static Intent navigate(@NonNull Context context) {
        return new Intent(context, AddNeighbourActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = AddNeighbourActivityBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        setViewModel();
        setupObservers();
        checkForFieldsCompletion();
        addNewNeighbour();
    }


    private void setViewModel() {
        viewModel = new ViewModelProvider(this).get(AddNeighbourViewModel.class);
    }

    private void checkForFieldsCompletion() {
        addTextWatcher(binding.name, viewModel::setValueForName);
        addTextWatcher(binding.address, viewModel::setValueForAddress);
        addTextWatcher(binding.phoneNumber, viewModel::setValueForPhoneNumber);
        addTextWatcher(binding.aboutMe, viewModel::setValueForAboutMe);
    }

    private void setupObservers() {
        // Button enabling's observer
        viewModel.getViewStateLiveData().observe(this, viewState -> {
            Glide
                .with(this)
                .load(viewState.getImageUrl())
                .transition(DrawableTransitionOptions.withCrossFade())
                .apply(RequestOptions.circleCropTransform())
                .into(binding.avatar);
            binding.create.setEnabled(viewState.isButtonEnabled());
        });

        // Close activity's observer
        viewModel.getCloseSingleLiveData().observe(this, closeActivity ->
            finish()
        );
    }

    private void addNewNeighbour() {
        //noinspection ConstantConditions
        binding.create.setOnClickListener(v ->
            viewModel.addNeighbour(
                binding.name.getText().toString(),
                binding.address.getText().toString(),
                binding.phoneNumber.getText().toString(),
                binding.aboutMe.getText().toString()
            )
        );
    }

    private void addTextWatcher(@NonNull EditText editText, @NonNull Consumer<String> valueSetter) {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                valueSetter.accept(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }
}