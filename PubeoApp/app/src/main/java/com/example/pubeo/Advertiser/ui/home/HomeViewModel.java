package com.example.pubeo.Advertiser.ui.home;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.pubeo.DAO.AdvertiserDAO;
import com.example.pubeo.DAO.StickerDAO;
import com.example.pubeo.DTO.StickerDetailsDTO;
import com.example.pubeo.model.Advertiser;
import com.example.pubeo.model.Sticker;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<List<StickerDetailsDTO>> stickersList;
    private StickerDAO stickerDAO = new StickerDAO();
    private AdvertiserDAO advertiserDAO = new AdvertiserDAO();

    public HomeViewModel() {
        stickersList = new MutableLiveData<>();
    }

    public void initStickers(String token){
        Call<Advertiser> callAdvertiser = advertiserDAO.getMeAdvertiser(token);
        callAdvertiser.enqueue(new Callback<Advertiser>() {
            @Override
            public void onResponse(Call<Advertiser> call, Response<Advertiser> response) {
                if(response.isSuccessful()){
                    Call<List<StickerDetailsDTO>> callStickers = stickerDAO.GetAllByProfessionnelId(token, response.body().getId());
                    callStickers.enqueue(new Callback<List<StickerDetailsDTO>>() {
                        @Override
                        public void onResponse(Call<List<StickerDetailsDTO>> call, Response<List<StickerDetailsDTO>> response) {
                            Log.e("dffsd", "bbb " + response.code());
                            if(response.isSuccessful())
                                stickersList.setValue(response.body());
                        }

                        @Override
                        public void onFailure(Call<List<StickerDetailsDTO>> call, Throwable t) {

                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<Advertiser> call, Throwable t) {

            }
        });
    }

    public MutableLiveData<List<StickerDetailsDTO>> getStickersList(){
        return stickersList;
    }

    public void addSticker(StickerDetailsDTO sticker){
        /*stickersListTest.add(sticker);
        stickersList.setValue(stickersListTest);*/
    }

    public void updateSticker(StickerDetailsDTO sticker){
        /*stickersListTest.set(stickersListTest.indexOf(sticker), sticker);
        stickersList.setValue(stickersListTest);*/
    }

    public void deleteSticker(StickerDetailsDTO sticker){
        /*stickersListTest.remove(sticker);
        stickersList.setValue(stickersListTest);*/
    }
}