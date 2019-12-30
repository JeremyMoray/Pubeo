package com.example.pubeo.Advertiser.ui.companyProfile;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.pubeo.model.Advertiser;

public class CompanyProfileViewModel extends ViewModel {

    private MutableLiveData<String> mText;
    private MutableLiveData<Advertiser> advertiser = new MutableLiveData<>();

    public CompanyProfileViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Profil");
    }
    public void setAdvertiser(Advertiser advertiser){
        this.advertiser.setValue(advertiser);
    }

    public Advertiser getAdvertiser(){
        return advertiser.getValue();
    }

    public LiveData<String> getText() {
        return mText;
    }
}