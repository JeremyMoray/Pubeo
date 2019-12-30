package com.example.pubeo.Advertiser.ui.companyProfile;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.pubeo.Advertiser.AdvertiserSignInActivity;
import com.example.pubeo.DAO.AdvertiserDAO;
import com.example.pubeo.R;
import com.example.pubeo.Service.ServiceAPI;
import com.example.pubeo.model.Advertiser;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.Context.MODE_PRIVATE;

public class CompanyProfileFragment extends Fragment {

    private CompanyProfileViewModel companyProfileViewModel;
    private static final String SHARED_PREFS = "sharedPrefs";
    private static final String IMAGEPATH = "imagePath";
    private CircleImageView advertiserLogoProfileImageView;
    private EditText companyProfileNameField;
    private EditText companyProfileVATField;
    private EditText companyProfilePhoneField;
    private EditText companyProfileAddressField;
    private EditText mailAdvertiserProfileField;
    private Button saveProfileAdvertiserButton;
    private String originalName;
    private Advertiser advertiser;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        companyProfileViewModel =
                ViewModelProviders.of(this).get(CompanyProfileViewModel.class);
        View root = inflater.inflate(R.layout.fragment_company_profile, container, false);

        final TextView textView = root.findViewById(R.id.text_company_profile);

        companyProfileViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        companyProfileViewModel = ViewModelProviders.of(this).get(CompanyProfileViewModel.class);
        companyProfileNameField = root.findViewById(R.id.companyProfileNameField);
        companyProfileVATField = root.findViewById(R.id.companyProfileVATField);
        companyProfilePhoneField = root.findViewById(R.id.companyProfilePhoneField);
        companyProfileAddressField = root.findViewById(R.id.companyProfileAddressField);
        mailAdvertiserProfileField = root.findViewById(R.id.mailAdvertiserProfileField);
        saveProfileAdvertiserButton = root.findViewById(R.id.saveProfileAdvertiserButton);

        advertiser = (Advertiser) getActivity().getIntent().getSerializableExtra("Advertiser");
        companyProfileViewModel.setAdvertiser(advertiser);

        companyProfileNameField.setText(advertiser.getNomEntreprise());
        companyProfileVATField.setText(advertiser.getNumeroTVA());
        companyProfilePhoneField.setText(advertiser.getNumeroTel());
        companyProfileAddressField.setText(advertiser.getAdresse());
        mailAdvertiserProfileField.setText(advertiser.getMail());

        saveProfileAdvertiserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Advertiser newAdvertiser = new Advertiser(
                        advertiser.getId(),
                        companyProfileNameField.getText().toString(),
                        companyProfileAddressField.getText().toString(),
                        companyProfilePhoneField.getText().toString(),
                        mailAdvertiserProfileField.getText().toString(),
                        companyProfileVATField.getText().toString(),
                        advertiser.getStickers()
                );
                updateAdvertiser(newAdvertiser);
            }
        });

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        String img_str = sharedPreferences.getString(IMAGEPATH, "");

        byte[] decodedString = Base64.decode(img_str, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

        advertiserLogoProfileImageView = root.findViewById(R.id.advertiserLogoProfileImageView);

        Glide.with(getActivity()).load(decodedByte).override(200,200).into(advertiserLogoProfileImageView);
        return root;
    }

    public void updateAdvertiser(Advertiser advertiser){

        AdvertiserDAO advertiserDAO = new AdvertiserDAO();
        try{
            advertiserDAO.updateAdvertiser(advertiser);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}