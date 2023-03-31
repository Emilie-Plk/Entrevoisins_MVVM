package com.example.entrevoisins_mvvm.view.list;

import static com.bumptech.glide.request.RequestOptions.circleCropTransform;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.entrevoisins_mvvm.databinding.NeighbourItemBinding;

public class NeighboursListAdapter extends ListAdapter<NeighbourViewStateItem, NeighboursListAdapter.ViewHolder> {

    private final OnNeighbourClickedListener listener;

    public NeighboursListAdapter(OnNeighbourClickedListener listener) {
        super(new ListNeighbourItemCallback());
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        NeighbourItemBinding binding = NeighbourItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(getItem(position), listener);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final ImageView neighbourProfilePicture;
        private final ImageView deleteNeighbourBtn;
        private final TextView neighbourName;

        public ViewHolder(@NonNull NeighbourItemBinding binding) {
            super(binding.getRoot());
            neighbourProfilePicture = binding.itemListAvatar;
            neighbourName = binding.itemListName;
            deleteNeighbourBtn = binding.itemListDeleteButton;
        }

        public void bind(NeighbourViewStateItem item, OnNeighbourClickedListener listener) {
            itemView.setOnClickListener(v -> listener.onNeighbourClicked(item.getId()));

            Glide.with(itemView.getContext())
                .load(item.getAvatarUrl())
                .apply(circleCropTransform())
                .into(neighbourProfilePicture);

            neighbourName.setText(item.getName());

            deleteNeighbourBtn.setOnClickListener(v -> listener.onNeighbourDelete(item.getId()));
        }
    }

    private static class ListNeighbourItemCallback extends DiffUtil.ItemCallback<NeighbourViewStateItem> {
        @Override
        public boolean areItemsTheSame(@NonNull NeighbourViewStateItem oldItem, @NonNull NeighbourViewStateItem newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull NeighbourViewStateItem oldItem, @NonNull NeighbourViewStateItem newItem) {
            return oldItem.equals(newItem);
        }
    }
}
