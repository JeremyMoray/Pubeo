package com.example.pubeo.Advertiser.ui.home;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.pubeo.model.Advertiser;
import com.example.pubeo.model.Sticker;

import java.util.ArrayList;
import java.util.List;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<Advertiser> advertiser;
    private MutableLiveData<List<Sticker>> stickersList;
    private ArrayList<Sticker> stickersListTest;

    public HomeViewModel() {
        advertiser = new MutableLiveData<>();
        stickersList = new MutableLiveData<>();
        /*stickersListTest.add(new Sticker("105d1b4d-ba0f-4197-899f-0571fde47bb9", "Burger King stickers", "Great hamburgers",24,24,3));
        stickersListTest.add(new Sticker("3c1e4089-83e3-4c86-8e7d-d5d4ceb5517c", "Mac donalds logo", "Bonnes frites",24,24,3));
        stickersListTest.add(new Sticker("75131ff7-6e8c-4927-ae31-0be0441c9015", "Pizza hut", "Les nouvelles pizzas hawai",24,24,3));
        stickersListTest.add(new Sticker("7be0bf3e-07e2-4ffc-b140-145eaae19261", "Dominos pizza", "Pizzas au chocolat",24,24,3));
        stickersListTest.add(new Sticker("4d6f3167-d6c2-429b-8d53-75ac8adbb069", "Mac donalds", "Burger",24,24,3));
        stickersListTest.add(new Sticker("3da68b4f-276e-4c87-99d9-ceb98a0042f2", "Mac donalds", "frites",24,24,3));
        stickersListTest.add(new Sticker("90791164-be48-4df0-bdf4-bba0212e98ec", "Mac donalds", "frites",24,24,3));
        stickersListTest.add(new Sticker("6cfeda6e-cf5f-49d2-b989-58a81ed359eb", "Mac donalds", "frites",24,24,3));
        stickersListTest.add(new Sticker("042904b7-aa11-413c-97dd-d445a92b4b51", "Burger donalds", "frites",24,24,3));
        stickersListTest.add(new Sticker("1f37ad6e-092d-4248-ba61-8f71cbbc746e", "Mac donalds", "frites",24,24,3));
        stickersList.setValue(stickersListTest);*/
        stickersListTest = new ArrayList<>();
    }

    public void setAdvertiser(Advertiser advertiser){
        this.advertiser.setValue(advertiser);
        this.stickersList.setValue(advertiser.getStickers());
    }

    public MutableLiveData<List<Sticker>> getStickersList(){
        return stickersList;
    }

    public void addSticker(Sticker sticker){
        stickersListTest.add(sticker);
        stickersList.setValue(stickersListTest);
    }

    public void updateSticker(Sticker sticker){
        stickersListTest.set(stickersListTest.indexOf(sticker), sticker);
        stickersList.setValue(stickersListTest);
    }

    public void deleteSticker(Sticker sticker){
        stickersListTest.remove(sticker);
        stickersList.setValue(stickersListTest);
    }
}