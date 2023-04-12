package com.example.entrevoisins_mvvm.view.list;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.entrevoisins_mvvm.databinding.NeighbourListFragmentBinding;
import com.example.entrevoisins_mvvm.view.detail.DetailProfileNeighbourActivity;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class NeighbourListFragment extends Fragment {

    private NeighbourListViewModel viewModel;

    private NeighbourListFragmentBinding binding;

    @NonNull
    private static final String IS_FAV = "IS_FAV";

    public static NeighbourListFragment newInstance(boolean isFav) {
        NeighbourListFragment fragment = new NeighbourListFragment();

        Bundle args = new Bundle();
        args.putBoolean(IS_FAV, isFav);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(
        @NonNull LayoutInflater inflater,
        ViewGroup container,
        Bundle savedInstanceState
    ) {
        binding = NeighbourListFragmentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(
        @NonNull View view,
        @Nullable Bundle savedInstanceState
    ) {
        setUpViewModel();
        initRecyclerView();
    }

    private void setUpViewModel() {
        viewModel = new ViewModelProvider(this).get(NeighbourListViewModel.class);
    }

    private void initRecyclerView() {
        RecyclerView recyclerView = binding.listNeighbours;

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL);
        binding.listNeighbours.addItemDecoration(dividerItemDecoration);

        NeighboursListAdapter adapter = new NeighboursListAdapter(new OnNeighbourClickedListener() {
            @Override
            public void onNeighbourClicked(long id) {
                startActivity(DetailProfileNeighbourActivity.navigate(requireContext(), id));
            }

            @Override
            public void onNeighbourDelete(long id) {
                viewModel.deleteNeighbour(id);
            }
        }
        );

        binding.listNeighbours.setAdapter(adapter);
        binding.listNeighbours.setLayoutManager(new LinearLayoutManager(getContext()));

        viewModel.getNeighbourViewStateItemLiveData(getArguments().getBoolean(IS_FAV)).observe(getViewLifecycleOwner(), adapter::submitList);
    }
}