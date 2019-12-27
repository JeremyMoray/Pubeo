package com.example.pubeo.Advertiser.ui.home;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pubeo.Advertiser.AddEditStickerActivity;
import com.example.pubeo.R;
import com.example.pubeo.model.Sticker;
import com.example.pubeo.Advertiser.StickerAdvertiserAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import static android.app.Activity.RESULT_OK;

public class HomeFragment extends Fragment {
    public static final int ADD_STICKER_REQUEST = 1;
    public static final int EDIT_STICKER_REQUEST = 2;

    private HomeViewModel homeViewModel;
    private FloatingActionButton fabAddButton;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        SearchView searchView;

        RecyclerView recyclerView = root.findViewById(R.id.recyclerAdvertiserStickers);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        searchView = root.findViewById(R.id.searchBarAdvertiser);

        StickerAdvertiserAdapter adapter = new StickerAdvertiserAdapter();

        adapter.setOnItemClickListener(new StickerAdvertiserAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Sticker sticker) {
                Intent intent = new Intent(getActivity(), AddEditStickerActivity.class);
                intent.putExtra("EXTRA_ID", sticker.getId());
                intent.putExtra("EXTRA_TITLE", sticker.getTitle());
                intent.putExtra("EXTRA_DESCRIPTION", sticker.getDescription());
                startActivityForResult(intent, EDIT_STICKER_REQUEST);
            }
        });

        recyclerView.setAdapter(adapter);

        homeViewModel.getStickersList().observe(this, new Observer<List<Sticker>>() {
            @Override
            public void onChanged(@Nullable List<Sticker> stickersList) {
                adapter.setStickers(stickersList);
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                adapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        fabAddButton = root.findViewById(R.id.fabAddButton);

        fabAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                openAddStickerActivity();
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                new AlertDialog.Builder(viewHolder.itemView.getContext())
                        .setMessage(R.string.confirmDeleteSticker)
                        .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                homeViewModel.deleteSticker(adapter.getStickerAt(viewHolder.getAdapterPosition()));
                            }
                        })
                        .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                adapter.notifyItemChanged(viewHolder.getAdapterPosition());
                            }
                        })
                        .create()
                        .show();
                        }
            }).attachToRecyclerView(recyclerView);
        return root;
    }

    public void openAddStickerActivity(){
        Intent intent = new Intent(getActivity(), AddEditStickerActivity.class);
        startActivityForResult(intent, ADD_STICKER_REQUEST);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_STICKER_REQUEST && resultCode == RESULT_OK) {
                String title = data.getStringExtra("EXTRA_TITLE");
                String description = data.getStringExtra("EXTRA_DESCRIPTION");

                Sticker sticker = new Sticker(11, title, description);
                homeViewModel.addSticker(sticker);
        }
        else if (requestCode == EDIT_STICKER_REQUEST && resultCode == RESULT_OK) {
            int id = data.getIntExtra("EXTRA_ID", -1);

            String title = data.getStringExtra("EXTRA_TITLE");
            String description = data.getStringExtra("EXTRA_DESCRIPTION");

            Sticker sticker = new Sticker(id, title, description);
            Toast.makeText(getContext(), String.valueOf(id), Toast.LENGTH_SHORT).show();
            homeViewModel.updateSticker(sticker);
        }
    }
}