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
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.pubeo.R;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.content.Context.MODE_PRIVATE;

public class CompanyProfileFragment extends Fragment {

    private CompanyProfileViewModel companyProfileViewModel;
    private static final String SHARED_PREFS = "sharedPrefs";
    private static final String IMAGEPATH = "imagePath";
    private CircleImageView advertiserLogoProfileImageView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        companyProfileViewModel =
                ViewModelProviders.of(this).get(CompanyProfileViewModel.class);
        View root = inflater.inflate(R.layout.fragment_company_profile, container, false);

        final TextView textView = root.findViewById(R.id.text_company_profile);
        final RecyclerView recyclerAdvertiserStickers = root.findViewById(R.id.recyclerAdvertiserStickers);

        companyProfileViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
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
}