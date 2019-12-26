package com.example.pubeo.Advertiser.ui.manageParticipants;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ManageParticipantsViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public ManageParticipantsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is manage participants fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}