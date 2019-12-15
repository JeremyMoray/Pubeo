package com.example.pubeo.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pubeo.R;
import com.example.pubeo.Sticker;
import com.example.pubeo.StickerAdvertiserAdapter;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        SearchView searchView;

        RecyclerView recyclerView = root.findViewById(R.id.recyclerAdvertiserStickers);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        searchView = root.findViewById(R.id.searchBarAdvertiser);

        StickerAdvertiserAdapter adapter = new StickerAdvertiserAdapter();
        List<Sticker> stickersList = new ArrayList<>();

        //Pour les tests
        stickersList.add(new Sticker(1, "Burger King stickers", "Great hamburgers"));
        stickersList.add(new Sticker(2, "Mac donalds logo", "Bonnes frites"));
        stickersList.add(new Sticker(3, "Pizza hut", "Les nouvelles pizzas hawai"));
        stickersList.add(new Sticker(4, "Dominos pizza", "Pizzas au chocolat"));
        stickersList.add(new Sticker(5, "Mac donalds", "Burger"));
        stickersList.add(new Sticker(6, "Mac donalds", "frites"));
        stickersList.add(new Sticker(7, "Mac donalds", "frites"));
        stickersList.add(new Sticker(8, "Mac donalds", "frites"));
        stickersList.add(new Sticker(9, "Burger donalds", "frites"));
        stickersList.add(new Sticker(10, "Mac donalds", "frites"));

        adapter.setStickers(stickersList);
        recyclerView.setAdapter(adapter);

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