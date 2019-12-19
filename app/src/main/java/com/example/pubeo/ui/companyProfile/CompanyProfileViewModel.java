package com.example.pubeo.ui.companyProfile;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CompanyProfileViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public CompanyProfileViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("JDoe24");
    }

    public LiveData<String> getText() {
        return mText;
    }
}