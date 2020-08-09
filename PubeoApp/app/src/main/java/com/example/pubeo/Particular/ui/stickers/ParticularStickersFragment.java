package com.example.pubeo.Particular.ui.stickers;

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

import com.example.pubeo.DTO.StickerSimpleDTO;
import com.example.pubeo.Particular.ParticularMyStickerInformationsActivity;
import com.example.pubeo.Particular.StickerParticularAdapter;
import com.example.pubeo.R;
import com.example.pubeo.tools.CheckNetClass;

import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class ParticularStickersFragment extends Fragment {

    private static final String SHARED_PREFS = "sharedPrefs";

    private ParticularStickersViewModel particularStickersViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        particularStickersViewModel = ViewModelProviders.of(this).get(ParticularStickersViewModel.class);

        SharedPreferences sharedPref = getActivity().getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        String token = sharedPref.getString("access_token", null);
        particularStickersViewModel.initStickers(token);
        View root = inflater.inflate(R.layout.fragment_particular_stickers, container, false);

        RecyclerView recyclerView = root.findViewById(R.id.recyclerParticularMyStickers);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        SearchView searchView = root.findViewById(R.id.searchBarParticularStickers);

        StickerParticularAdapter adapter = new StickerParticularAdapter();

        adapter.setOnItemClickListener(new StickerParticularAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(StickerSimpleDTO sticker) {
                if(CheckNetClass.checknetwork(getActivity())) {
                    Intent intent = new Intent(getActivity(), ParticularMyStickerInformationsActivity.class);
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

        particularStickersViewModel.getStickersList().observe(this, new Observer<List<StickerSimpleDTO>>() {
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
