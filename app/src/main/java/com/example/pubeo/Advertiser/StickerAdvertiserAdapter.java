package com.example.pubeo.Advertiser;

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

public class StickerAdvertiserAdapter extends RecyclerView.Adapter<StickerAdvertiserAdapter.StickerAdvertiserHolder> implements Filterable {

    private List<Sticker> stickersList = new ArrayList<>();
    private List<Sticker> stickersListFull;

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
        stickersListFull = new ArrayList<>(stickersList);
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


    @Override
    public Filter getFilter() {
        return exampleFilter;
    }

    private Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Sticker> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(stickersListFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (Sticker item : stickersListFull) {
                    if (item.getTitle().toLowerCase().contains(filterPattern) || item.getDescription().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            stickersList.clear();
            stickersList.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };
}
