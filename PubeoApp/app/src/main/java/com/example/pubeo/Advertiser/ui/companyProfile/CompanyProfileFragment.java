package com.example.pubeo.Advertiser.ui.companyProfile;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.bumptech.glide.Glide;
import com.example.pubeo.DAO.AdvertiserDAO;
import com.example.pubeo.DTO.AdvertiserUpdateDTO;
import com.example.pubeo.MainActivity;
import com.example.pubeo.R;
import com.example.pubeo.model.Advertiser;
import com.example.pubeo.tools.CheckNetClass;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
    private EditText postalCodeAdvertiserProfileField;
    private Button saveProfileAdvertiserButton;
    private TextView deleteProfileAdvertiserTextView;
    private Advertiser advertiser;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_company_profile, container, false);

        companyProfileViewModel = ViewModelProviders.of(this).get(CompanyProfileViewModel.class);
        companyProfileNameField = root.findViewById(R.id.companyProfileNameField);
        companyProfileVATField = root.findViewById(R.id.companyProfileVATField);
        companyProfilePhoneField = root.findViewById(R.id.companyProfilePhoneField);
        companyProfileAddressField = root.findViewById(R.id.companyProfileAddressField);
        mailAdvertiserProfileField = root.findViewById(R.id.mailAdvertiserProfileField);
        postalCodeAdvertiserProfileField = root.findViewById(R.id.postalCodeAdvertiserProfileField);
        saveProfileAdvertiserButton = root.findViewById(R.id.saveProfileAdvertiserButton);
        deleteProfileAdvertiserTextView = root.findViewById(R.id.deleteProfileAdvertiserTextView);
        advertiserLogoProfileImageView = root.findViewById(R.id.advertiserLogoProfileImageView);

        SharedPreferences sharedPref = getActivity().getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        String token = sharedPref.getString("access_token", null);

        if(CheckNetClass.checknetwork(getActivity())) {
            AdvertiserDAO advertiserDAO = new AdvertiserDAO();
            Call<Advertiser> call = advertiserDAO.getMeAdvertiser(token);
            call.enqueue(new Callback<Advertiser>() {
                @Override
                public void onResponse(Call<Advertiser> call, Response<Advertiser> response) {
                    advertiser = response.body();

                    companyProfileNameField.setText(advertiser.getNomEntreprise());
                    companyProfileVATField.setText(advertiser.getNumeroTVA());
                    companyProfilePhoneField.setText(advertiser.getNumeroTel());
                    companyProfileAddressField.setText(advertiser.getAdresse());
                    mailAdvertiserProfileField.setText(advertiser.getMail());
                    postalCodeAdvertiserProfileField.setText(advertiser.getLocalite().getCodePostal());

                    saveProfileAdvertiserButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(companyProfileNameField.getText().toString().isEmpty()){
                                Toast.makeText(getActivity(), R.string.fieldNotEmpty, Toast.LENGTH_SHORT).show();
                                return;
                            }
                            if(companyProfileVATField.getText().toString().isEmpty()){
                                Toast.makeText(getActivity(), R.string.fieldNotEmpty, Toast.LENGTH_SHORT).show();
                                return;
                            }
                            if(companyProfilePhoneField.getText().toString().isEmpty()){
                                Toast.makeText(getActivity(), R.string.fieldNotEmpty, Toast.LENGTH_SHORT).show();
                                return;
                            }
                            if(companyProfileAddressField.getText().toString().isEmpty()){
                                Toast.makeText(getActivity(), R.string.fieldNotEmpty, Toast.LENGTH_SHORT).show();
                                return;
                            }
                            if(mailAdvertiserProfileField.getText().toString().isEmpty()){
                                Toast.makeText(getActivity(), R.string.fieldNotEmpty, Toast.LENGTH_SHORT).show();
                                return;
                            }

                            Pattern emailPattern = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
                            Matcher emailMatcher = emailPattern.matcher((CharSequence) mailAdvertiserProfileField.getText().toString());
                            if(!emailMatcher.find()){
                                Toast.makeText(getActivity(), R.string.fieldNotValid, Toast.LENGTH_SHORT).show();
                                return;
                            }

                            Pattern phonePattern = Pattern.compile("\\d{10}");
                            Matcher phoneMatcher = phonePattern.matcher((CharSequence) companyProfilePhoneField.getText().toString());
                            if(!phoneMatcher.find()){
                                Toast.makeText(getActivity(), R.string.phoneNumberNotValid, Toast.LENGTH_SHORT).show();
                                return;
                            }

                            if(CheckNetClass.checknetwork(getActivity())) {
                                AdvertiserUpdateDTO newAdvertiser = new AdvertiserUpdateDTO(
                                        companyProfileNameField.getText().toString(),
                                        companyProfileAddressField.getText().toString(),
                                        companyProfilePhoneField.getText().toString(),
                                        mailAdvertiserProfileField.getText().toString(),
                                        companyProfileVATField.getText().toString(),
                                        postalCodeAdvertiserProfileField.getText().toString()
                                );

                                Call<Void> call = advertiserDAO.updateAdvertiser(token, newAdvertiser);
                                call.enqueue(new Callback<Void>() {
                                    @Override
                                    public void onResponse(Call<Void> call, Response<Void> response) {
                                        if(response.isSuccessful())
                                            Toast.makeText(getActivity(), R.string.dataSaved, Toast.LENGTH_SHORT).show();

                                        if(response.code() == 409)
                                            Toast.makeText(getActivity(), R.string.emailConflict, Toast.LENGTH_SHORT).show();

                                        if(response.code() == 404)
                                            Toast.makeText(getActivity(), R.string.postalCodeNotValid, Toast.LENGTH_SHORT).show();
                                    }

                                    @Override
                                    public void onFailure(Call<Void> call, Throwable t) {
                                    }
                                });
                            }
                            else {
                                Toast.makeText(getActivity(), R.string.lossConnection, Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                    deleteProfileAdvertiserTextView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            new AlertDialog.Builder(getContext())
                                    .setMessage(R.string.deleteProfileConfirm)
                                    .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            if(CheckNetClass.checknetwork(getActivity())) {
                                                Call<Void> call = advertiserDAO.deleteAdvertiser(token);
                                                call.enqueue(new Callback<Void>() {
                                                    @Override
                                                    public void onResponse(Call<Void> call, Response<Void> response) {
                                                        if(response.isSuccessful()){
                                                            Intent intent = new Intent(getActivity().getApplicationContext(), MainActivity.class);
                                                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                            startActivity(intent);
                                                        }
                                                    }

                                                    @Override
                                                    public void onFailure(Call<Void> call, Throwable t) {

                                                    }
                                                });
                                            }
                                            else {
                                                Toast.makeText(getActivity(), R.string.lossConnection, Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    })
                                    .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {

                                        }
                                    })
                                    .create()
                                    .show();
                        }
                    });
                }

                @Override
                public void onFailure(Call<Advertiser> call, Throwable t) {

                }
            });
        }
        else {
            Toast.makeText(getActivity(), R.string.lossConnection, Toast.LENGTH_SHORT).show();
        }

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        String img_str = sharedPreferences.getString(IMAGEPATH, "");

        byte[] decodedString = Base64.decode(img_str, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

        Glide.with(getActivity()).load(decodedByte).override(200,200).into(advertiserLogoProfileImageView);
        return root;
    }
}