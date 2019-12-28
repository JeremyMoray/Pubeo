package com.example.pubeo.Particular.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pubeo.Particular.StickerParticularAdapter;
import com.example.pubeo.R;
import com.example.pubeo.model.Sticker;

import java.util.List;

public class ParticularHomeFragment extends Fragment {

    private ParticularHomeViewModel particularHomeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        particularHomeViewModel =ViewModelProviders.of(this).get(ParticularHomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_particular_home, container, false);

        RecyclerView recyclerView = root.findViewById(R.id.recyclerParticularStickers);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        StickerParticularAdapter adapter = new StickerParticularAdapter();
        recyclerView.setAdapter(adapter);

        particularHomeViewModel.getStickersList().observe(this, new Observer<List<Sticker>>() {
            @Override
            public void onChanged(@Nullable List<Sticker> stickersList) {
                adapter.setStickers(stickersList);
            }
        });

        return root;
    }
}