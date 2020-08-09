package com.example.pubeo.Particular.ui.home;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.pubeo.DAO.ParticularDAO;
import com.example.pubeo.DAO.StickerDAO;
import com.example.pubeo.DTO.StickerDetailsDTO;
import com.example.pubeo.DTO.StickerSimpleDTO;
import com.example.pubeo.model.Advertiser;
import com.example.pubeo.model.Particular;
import com.example.pubeo.model.Sticker;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ParticularHomeViewModel extends ViewModel {

    private MutableLiveData<List<StickerSimpleDTO>> stickersList;
    private StickerDAO stickerDAO = new StickerDAO();

    public ParticularHomeViewModel() {
        stickersList = new MutableLiveData<>();

    }
    public MutableLiveData<List<StickerSimpleDTO>> getStickersList(){
        return stickersList;
    }

    public void initStickers(String token){
        Call<List<StickerSimpleDTO>> callStickers = stickerDAO.getAll(token);
        callStickers.enqueue(new Callback<List<StickerSimpleDTO>>() {
            @Override
            public void onResponse(Call<List<StickerSimpleDTO>> call, Response<List<StickerSimpleDTO>> response) {
                if(response.isSuccessful())
                    stickersList.setValue(response.body());
            }

            @Override
            public void onFailure(Call<List<StickerSimpleDTO>> call, Throwable t) {
            }
        });
    }
}