package com.example.pubeo.Particular.ui.stickers;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.pubeo.DAO.ParticularDAO;
import com.example.pubeo.DAO.StickerDAO;
import com.example.pubeo.DTO.StickerSimpleDTO;
import com.example.pubeo.model.Particular;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ParticularStickersViewModel extends ViewModel {

    private MutableLiveData<List<StickerSimpleDTO>> stickersList;
    private ParticularDAO particularDAO = new ParticularDAO();

    public ParticularStickersViewModel() {
        stickersList = new MutableLiveData<>();

    }
    public MutableLiveData<List<StickerSimpleDTO>> getStickersList(){
        return stickersList;
    }

    public void initStickers(String token){
        Call<Particular> call = particularDAO.getMeParticular(token);
        call.enqueue(new Callback<Particular>() {
            @Override
            public void onResponse(Call<Particular> call, Response<Particular> response) {
                if(response.isSuccessful()){
                    Call<List<StickerSimpleDTO>> callStickers = particularDAO.getAllStickersByParticulierId(token, response.body().getId());
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

            @Override
            public void onFailure(Call<Particular> call, Throwable t) {

            }
        });
    }
}
