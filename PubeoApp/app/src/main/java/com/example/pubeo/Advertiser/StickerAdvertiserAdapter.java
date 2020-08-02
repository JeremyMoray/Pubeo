package com.example.pubeo.Advertiser;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pubeo.DTO.StickerDetailsDTO;
import com.example.pubeo.R;
import com.example.pubeo.model.Sticker;

import java.util.ArrayList;
import java.util.List;

public class StickerAdvertiserAdapter extends RecyclerView.Adapter<StickerAdvertiserAdapter.StickerAdvertiserHolder> implements Filterable {

    private List<StickerDetailsDTO> stickersList;
    private List<StickerDetailsDTO> stickersListFull;
    private OnItemClickListener listener;

    @NonNull
    @Override
    public StickerAdvertiserHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.sticker_item, parent, false);
        return new StickerAdvertiserHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull StickerAdvertiserHolder holder, int position) {
        StickerDetailsDTO currentSticker = stickersList.get(position);
        holder.stickerTitleAdvertiser.setText(currentSticker.getTitre());
        holder.stickerDescriptionAdvertiser.setText(currentSticker.getDescription());
        String participants = "participants restants : " + currentSticker.getNbUtilisationsRestantes();
        holder.participantsTextAdvertiser.setText(participants);
    }

    @Override
    public int getItemCount() {
        return stickersList == null ? 0 : stickersList.size();
    }

    public void setStickers(List<StickerDetailsDTO> stickersList) {
        this.stickersList = stickersList;
        stickersListFull = new ArrayList<>(stickersList);
        notifyDataSetChanged();
    }

    public StickerDetailsDTO getStickerAt(int position){
        return stickersList.get(position);
    }

    class StickerAdvertiserHolder extends RecyclerView.ViewHolder{
        private TextView stickerTitleAdvertiser;
        private TextView stickerDescriptionAdvertiser;
        private TextView participantsTextAdvertiser;

        public StickerAdvertiserHolder(@NonNull View itemView) {
            super(itemView);
            stickerTitleAdvertiser = itemView.findViewById(R.id.stickerTitleAdvertiser);
            stickerDescriptionAdvertiser = itemView.findViewById(R.id.stickerDescriptionAdvertiser);
            participantsTextAdvertiser = itemView.findViewById(R.id.participantsTextAdvertiser);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(stickersList.get(position));
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(StickerDetailsDTO sticker);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }

    @Override
    public Filter getFilter() {
        return exampleFilter;
    }

    private Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<StickerDetailsDTO> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(stickersListFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (StickerDetailsDTO item : stickersListFull) {
                    if (item.getTitre().toLowerCase().contains(filterPattern) || item.getDescription().toLowerCase().contains(filterPattern)) {
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
