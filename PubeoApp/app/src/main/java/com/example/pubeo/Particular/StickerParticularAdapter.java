package com.example.pubeo.Particular;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pubeo.Advertiser.StickerAdvertiserAdapter;
import com.example.pubeo.DTO.StickerDetailsDTO;
import com.example.pubeo.DTO.StickerSimpleDTO;
import com.example.pubeo.R;
import com.example.pubeo.model.Sticker;

import java.util.ArrayList;
import java.util.List;

public class StickerParticularAdapter extends RecyclerView.Adapter<StickerParticularAdapter.StickerParticularHolder> implements Filterable {

    private List<StickerSimpleDTO> stickersList;
    private List<StickerSimpleDTO> stickersListFull;
    private OnItemClickListener listener;

    @NonNull
    @Override
    public StickerParticularHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.sticker_item_particular, parent, false);
        return new StickerParticularHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull StickerParticularHolder holder, int position) {
        StickerSimpleDTO currentSticker = stickersList.get(position);
        holder.stickerTitleParticular.setText(currentSticker.getTitre());
        holder.stickerDescriptionParticular.setText(currentSticker.getDescription());
        String participants = "participants restants : " + currentSticker.getNbUtilisationsRestantes();
        holder.participantsTextParticular.setText(participants);
    }

    @Override
    public int getItemCount() {
        return stickersList == null ? 0 : stickersList.size();
    }

    public void setStickers(List<StickerSimpleDTO> stickersList) {
        this.stickersList = stickersList;
        stickersListFull = new ArrayList<>(stickersList);
        notifyDataSetChanged();
    }

    public StickerSimpleDTO getStickerAt(int position){
        return stickersList.get(position);
    }

    class StickerParticularHolder extends RecyclerView.ViewHolder{
        private TextView stickerTitleParticular;
        private TextView stickerDescriptionParticular;
        private TextView participantsTextParticular;

        public StickerParticularHolder(@NonNull View itemView) {
            super(itemView);
            stickerTitleParticular = itemView.findViewById(R.id.stickerTitleParticular);
            stickerDescriptionParticular = itemView.findViewById(R.id.stickerDescriptionParticular);
            participantsTextParticular = itemView.findViewById(R.id.participantsTextParticular);

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
        void onItemClick(StickerSimpleDTO sticker);
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
            List<StickerSimpleDTO> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(stickersListFull);
            }
            else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (StickerSimpleDTO item : stickersListFull) {
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
