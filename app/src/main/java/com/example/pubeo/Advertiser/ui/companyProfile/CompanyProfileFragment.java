package com.example.pubeo.Advertiser.ui.companyProfile;

import android.os.Bundle;
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

import com.example.pubeo.R;

public class CompanyProfileFragment extends Fragment {

    private CompanyProfileViewModel companyProfileViewModel;

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
        return root;
    }
}