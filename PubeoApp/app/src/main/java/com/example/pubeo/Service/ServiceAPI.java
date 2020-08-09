package com.example.pubeo.Service;

import com.example.pubeo.DTO.AdvertiserCreateDTO;
import com.example.pubeo.DTO.AdvertiserUpdateDTO;
import com.example.pubeo.DTO.ParticularCreateDTO;
import com.example.pubeo.DTO.StickerCreateDTO;
import com.example.pubeo.DTO.StickerDetailsDTO;
import com.example.pubeo.DTO.StickerSimpleDTO;
import com.example.pubeo.model.Advertiser;
import com.example.pubeo.model.Login;
import com.example.pubeo.model.Particular;
import com.example.pubeo.model.Sticker;
import com.example.pubeo.model.Token;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ServiceAPI {

    //Advertiser

    @POST("Jwt/LoginAdvertiser")
    Call<Token> loginAdvertiser(@Body Login login);

    @GET("Professionnels/GetMe")
    Call<Advertiser> getMeAdvertiser(@Header("Authorization") String token);

    @PUT("Professionnels/UpdateMyAccount")
    Call<Void> updateAdvertiser(@Header("Authorization") String token, @Body AdvertiserUpdateDTO advertiser);

    @DELETE("Professionnels/DeleteMyAccount")
    Call<Void> deleteAdvertiser(@Header("Authorization") String token);

    @POST("Professionnels")
    Call<Advertiser> addAdvertiser(@Body AdvertiserCreateDTO advertiser);

    @GET("Stickers/GetAllByProfessionnelId/{professionnelId}")
    Call<List<StickerDetailsDTO>> GetAllByProfessionnelId(@Header("Authorization") String token, @Path("professionnelId") String professionnelId);

    @POST("Stickers")
    Call<Sticker> addSticker(@Header("Authorization") String token, @Body StickerCreateDTO sticker);

    @PUT("Stickers/{id}")
    Call<Sticker> updateSticker(@Header("Authorization") String token, @Path("id") String id, @Body StickerCreateDTO sticker);

    @DELETE("Stickers/{id}")
    Call<Void> deleteSticker(@Header("Authorization") String token, @Path("id") String id);

    //Particular

    @POST("Jwt/LoginParticulier")
    Call<Token> loginParticular(@Body Login login);

    @GET("Particuliers/GetMe")
    Call<Particular> getMeParticular(@Header("Authorization") String token);

    @GET("Stickers")
    Call<List<StickerSimpleDTO>> getAll(@Header("Authorization") String token);

    @POST("Particuliers")
    Call<Particular> addParticular(@Body ParticularCreateDTO particular);

    //Participations

    @GET("Participation/GetAllStickersByParticulierId/{particulierId}")
    Call<List<StickerSimpleDTO>> getAllStickersByParticulierId(@Header("Authorization") String token, @Path("particulierId") String particulierId);

}
