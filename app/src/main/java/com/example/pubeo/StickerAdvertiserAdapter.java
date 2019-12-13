package com.example.pubeo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.awt.font.TextAttribute;
import java.util.ArrayList;
import java.util.List;

public class StickerAdvertiserAdapter extends RecyclerView.Adapter<StickerAdvertiserAdapter.StickerAdvertiserHolder> {

    private List<Sticker> stickersList = new ArrayList<>();

    @NonNull
    @Override
    public StickerAdvertiserHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.sticker_item, parent, false);
        return new StickerAdvertiserHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull StickerAdvertiserHolder holder, int position) {
        Sticker currentNote = stickersList.get(position);
        holder.stickerTitleAdvertiser.setText(currentNote.getTitle());
        holder.stickerDescriptionAdvertiser.setText(currentNote.getDescription());
    }

    @Override
    public int getItemCount() {
        return stickersList == null ? 0 : stickersList.size();
    }

    public void setStickers(List<Sticker> stickersList) {
        this.stickersList = stickersList;
        notifyDataSetChanged();
    }

    class StickerAdvertiserHolder extends RecyclerView.ViewHolder{
        private TextView stickerTitleAdvertiser;
        private TextView stickerDescriptionAdvertiser;

        public StickerAdvertiserHolder(@NonNull View itemView) {
            super(itemView);
            stickerTitleAdvertiser = itemView.findViewById(R.id.stickerTitleAdvertiser);
            stickerDescriptionAdvertiser = itemView.findViewById(R.id.stickerDescriptionAdvertiser);
        }
    }
}
