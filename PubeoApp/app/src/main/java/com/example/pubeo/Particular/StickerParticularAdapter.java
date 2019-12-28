package com.example.pubeo.Particular;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pubeo.R;
import com.example.pubeo.model.Sticker;

import java.util.ArrayList;
import java.util.List;

public class StickerParticularAdapter extends RecyclerView.Adapter<StickerParticularAdapter.StickerParticularHolder> {

    private List<Sticker> stickersList = new ArrayList<>();
    private List<Sticker> stickersListFull;

    @NonNull
    @Override
    public StickerParticularHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.sticker_item_particular, parent, false);
        return new StickerParticularHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull StickerParticularHolder holder, int position) {
        Sticker currentSticker = stickersList.get(position);
        holder.stickerTitleParticular.setText(currentSticker.getTitle());
        holder.stickerDescriptionParticular.setText(currentSticker.getDescription());
    }

    @Override
    public int getItemCount() {
        return stickersList == null ? 0 : stickersList.size();
    }

    public void setStickers(List<Sticker> stickersList) {
        this.stickersList = stickersList;
        stickersListFull = new ArrayList<>(stickersList);
        notifyDataSetChanged();
    }

    public Sticker getStickerAt(int position){
        return stickersList.get(position);
    }

    class StickerParticularHolder extends RecyclerView.ViewHolder{
        private TextView stickerTitleParticular;
        private TextView stickerDescriptionParticular;

        public StickerParticularHolder(@NonNull View itemView) {
            super(itemView);
            stickerTitleParticular = itemView.findViewById(R.id.stickerTitleParticular);
            stickerDescriptionParticular = itemView.findViewById(R.id.stickerDescriptionParticular);
        }
    }
}
