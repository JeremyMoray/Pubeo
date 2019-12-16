package com.example.pubeo.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.pubeo.model.Sticker;

import java.util.ArrayList;
import java.util.List;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<List<Sticker>> stickersList;
    private List<Sticker> stickersListTest;

    public HomeViewModel() {
        stickersList = new MutableLiveData<>();
        stickersListTest = new ArrayList<>();
        stickersListTest.add(new Sticker(1, "Burger King stickers", "Great hamburgers"));
        stickersListTest.add(new Sticker(2, "Mac donalds logo", "Bonnes frites"));
        stickersListTest.add(new Sticker(3, "Pizza hut", "Les nouvelles pizzas hawai"));
        stickersListTest.add(new Sticker(4, "Dominos pizza", "Pizzas au chocolat"));
        stickersListTest.add(new Sticker(5, "Mac donalds", "Burger"));
        stickersListTest.add(new Sticker(6, "Mac donalds", "frites"));
        stickersListTest.add(new Sticker(7, "Mac donalds", "frites"));
        stickersListTest.add(new Sticker(8, "Mac donalds", "frites"));
        stickersListTest.add(new Sticker(9, "Burger donalds", "frites"));
        stickersListTest.add(new Sticker(10, "Mac donalds", "frites"));
        stickersList.setValue(stickersListTest);
    }

    public MutableLiveData<List<Sticker>> getStickersList(){
        return stickersList;
    }

    public void addSticker(Sticker sticker){
        stickersListTest.add(sticker);
        stickersList.setValue(stickersListTest);
    }
}