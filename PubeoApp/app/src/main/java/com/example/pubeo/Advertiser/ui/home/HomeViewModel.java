package com.example.pubeo.Advertiser.ui.home;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.pubeo.DAO.AdvertiserDAO;
import com.example.pubeo.DAO.StickerDAO;
import com.example.pubeo.DTO.StickerCreateDTO;
import com.example.pubeo.DTO.StickerDetailsDTO;
import com.example.pubeo.model.Advertiser;
import com.example.pubeo.model.Sticker;

import java.lang.reflect.Array;
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

    public void addSticker(String token, StickerCreateDTO sticker){
        Call<Sticker> call = stickerDAO.addSticker(token, sticker);
        call.enqueue(new Callback<Sticker>() {
            @Override
            public void onResponse(Call<Sticker> call, Response<Sticker> response) {
                if(response.isSuccessful()){
                    List<StickerDetailsDTO> stickerDetails = stickersList.getValue();
                    StickerDetailsDTO stickerAdd = new StickerDetailsDTO(
                            response.body().getId(),
                            response.body().getTitre(),
                            response.body().getDescription(),
                            response.body().getHauteur(),
                            response.body().getLargeur(),
                            response.body().getNbUtilisationsRestantes(),
                            response.body().getProfessionnel(),
                            response.body().getParticipations()
                    );
                    stickerDetails.add(stickerAdd);
                    stickersList.setValue(stickerDetails);
                }
            }

            @Override
            public void onFailure(Call<Sticker> call, Throwable t) {

            }
        });
    }

    public void updateSticker(String token, String id, StickerCreateDTO sticker){
        Call<Sticker> call = stickerDAO.updateSticker(token, id, sticker);
        call.enqueue(new Callback<Sticker>() {
            @Override
            public void onResponse(Call<Sticker> call, Response<Sticker> response) {
                if(response.isSuccessful()){
                    List<StickerDetailsDTO> stickerDetails = stickersList.getValue();
                    StickerDetailsDTO stickerUpdate = new StickerDetailsDTO(
                            response.body().getId(),
                            response.body().getTitre(),
                            response.body().getDescription(),
                            response.body().getHauteur(),
                            response.body().getLargeur(),
                            response.body().getNbUtilisationsRestantes(),
                            response.body().getProfessionnel(),
                            response.body().getParticipations()
                    );
                    int index = getIndexById(id, stickerDetails);
                    stickerDetails.set(index, stickerUpdate);
                    stickersList.setValue(stickerDetails);
                }
            }

            @Override
            public void onFailure(Call<Sticker> call, Throwable t) {
            }
        });
    }

    public void deleteSticker(String token, StickerDetailsDTO sticker){

        Call<Void> call = stickerDAO.deleteSticker(token, sticker.getId());
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.isSuccessful()){
                    List<StickerDetailsDTO> stickerDetails = stickersList.getValue();
                    stickerDetails.remove(sticker);
                    stickersList.setValue(stickerDetails);
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
    }

    public int getIndexById(String id, List<StickerDetailsDTO> stickerDetails){
        for (int i = 0; i < stickerDetails.size(); i++) {
            if (stickerDetails.get(i) != null && stickerDetails.get(i).getId().equals(id)) {
                return i;
            }
        }
        return -1;
    }
}