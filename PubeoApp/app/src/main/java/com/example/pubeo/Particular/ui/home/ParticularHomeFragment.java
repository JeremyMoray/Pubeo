package com.example.pubeo.Particular.ui.home;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pubeo.Advertiser.AddEditStickerActivity;
import com.example.pubeo.DTO.StickerDetailsDTO;
import com.example.pubeo.DTO.StickerSimpleDTO;
import com.example.pubeo.Particular.ParticularStickerInformationsActivity;
import com.example.pubeo.Particular.StickerParticularAdapter;
import com.example.pubeo.R;
import com.example.pubeo.model.Sticker;
import com.example.pubeo.tools.CheckNetClass;

import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class ParticularHomeFragment extends Fragment {

    private static final String SHARED_PREFS = "sharedPrefs";

    private ParticularHomeViewModel particularHomeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        particularHomeViewModel = ViewModelProviders.of(this).get(ParticularHomeViewModel.class);

        SharedPreferences sharedPref = getActivity().getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        String token = sharedPref.getString("access_token", null);
        particularHomeViewModel.initStickers(token);
        View root = inflater.inflate(R.layout.fragment_particular_home, container, false);

        RecyclerView recyclerView = root.findViewById(R.id.recyclerParticularStickers);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        SearchView searchView = root.findViewById(R.id.searchBarParticular);

        StickerParticularAdapter adapter = new StickerParticularAdapter();

        adapter.setOnItemClickListener(new StickerParticularAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(StickerSimpleDTO sticker) {
                if(CheckNetClass.checknetwork(getActivity())) {
                    Intent intent = new Intent(getActivity(), ParticularStickerInformationsActivity.class);
                    intent.putExtra("EXTRA_ID", sticker.getId());
                    intent.putExtra("EXTRA_TITLE", sticker.getTitre());
                    intent.putExtra("EXTRA_DESCRIPTION", sticker.getDescription());
                    intent.putExtra("EXTRA_HEIGHT", sticker.getHauteur());
                    intent.putExtra("EXTRA_WIDTH", sticker.getLargeur());
                    intent.putExtra("EXTRA_NUMBER_OF_USE", sticker.getNbUtilisationsRestantes());

                    startActivity(intent);
                }
                else {
                    Toast.makeText(getActivity(), R.string.lossConnection, Toast.LENGTH_SHORT).show();
                }
            }
        });

        recyclerView.setAdapter(adapter);

        particularHomeViewModel.getStickersList().observe(this, new Observer<List<StickerSimpleDTO>>() {
            @Override
            public void onChanged(@Nullable List<StickerSimpleDTO> stickersList) {
                adapter.setStickers(stickersList);
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });

        return root;
    }
}