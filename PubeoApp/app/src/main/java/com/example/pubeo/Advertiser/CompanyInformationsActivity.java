package com.example.pubeo.Advertiser;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.pubeo.DAO.AdvertiserDAO;
import com.example.pubeo.DTO.AdvertiserCreateDTO;
import com.example.pubeo.R;
import com.example.pubeo.model.Advertiser;
import com.example.pubeo.model.LoginAdvertiser;
import com.example.pubeo.model.Token;
import com.example.pubeo.tools.CheckNetClass;
import com.example.pubeo.tools.validation.ValidationTextWatcher;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.pubeo.tools.constants.constantTags.*;

public class CompanyInformationsActivity extends AppCompatActivity {

    @BindView(R.id.continueInformationsAdvertiserButton) Button continueInformationsAdvertiserButton;
    @BindView(R.id.companyNameField) TextInputLayout companyNameField;
    @BindView(R.id.companyVATField) TextInputLayout companyVATField;
    @BindView(R.id.companyPhoneField) TextInputLayout companyPhoneField;
    @BindView(R.id.companyPhoneFieldEditText) TextInputEditText companyPhoneFieldEditText;
    @BindView(R.id.companyAddressField) TextInputLayout companyAddressField;
    @BindView(R.id.companyPostalCodeField) TextInputLayout companyPostalCodeField;

    private ImageView whiteArrowCompanyInformations;
    private ImageView companyLogoId;

    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String IMAGEPATH = "imagePath";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_informations);

        ButterKnife.bind(this);

        companyPhoneField.setTag(PHONENUMBER_TAG);

        continueInformationsAdvertiserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                openHomeActivity();
            }
        });

        whiteArrowCompanyInformations = findViewById(R.id.whiteArrowCompanyInformations);
        whiteArrowCompanyInformations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                finish();
            }
        });

        companyLogoId = findViewById(R.id.companyLogoId);
        companyLogoId.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent gallery = new Intent();
                gallery.setType("image/*");
                gallery.setAction(Intent.ACTION_GET_CONTENT);

                startActivityForResult(Intent.createChooser(gallery, String.valueOf(R.string.choosePicture)), 1);
            }
        });

        companyPhoneFieldEditText
                .addTextChangedListener(new ValidationTextWatcher(this, companyPhoneField));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 1 && resultCode == RESULT_OK){
            Uri imageUrl = data.getData();
            try{
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUrl);
                companyLogoId.setImageBitmap(bitmap);
            }
            catch(IOException e){
                e.printStackTrace();
            }
        }
    }

    public void openHomeActivity(){
        if(CheckNetClass.checknetwork(getApplicationContext())) {
            boolean isValid = true;

            if(companyPhoneField.isErrorEnabled()){
                isValid = false;
            }
            else{
                if(companyNameField.getEditText().getText().toString().isEmpty()){
                    isValid = false;
                    companyNameField.setError(getString(R.string.fieldNotEmpty));
                }
                else{
                    companyNameField.setErrorEnabled(false);
                }

                if(companyVATField.getEditText().getText().toString().isEmpty()){
                    isValid = false;
                    companyVATField.setError(getString(R.string.fieldNotEmpty));
                }
                else{
                    companyVATField.setErrorEnabled(false);
                }

                if(companyPhoneField.getEditText().getText().toString().isEmpty()){
                    isValid = false;
                    companyPhoneField.setError(getString(R.string.fieldNotEmpty));
                }
                else{
                    companyPhoneField.setErrorEnabled(false);
                }

                if(companyAddressField.getEditText().getText().toString().isEmpty()){
                    isValid = false;
                    companyAddressField.setError(getString(R.string.fieldNotEmpty));
                }
                else{
                    companyAddressField.setErrorEnabled(false);
                }
            }

            if (isValid) {
                companyLogoId.buildDrawingCache();
                Bitmap bitmap = companyLogoId.getDrawingCache();

                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 90, stream);
                byte[] image = stream.toByteArray();
                String img_str = Base64.encodeToString(image, 0);

                SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();

                editor.putString(IMAGEPATH, img_str);
                editor.apply();

                AdvertiserDAO advertiserDAO = new AdvertiserDAO();
                String postalCode = (companyPostalCodeField.getEditText() == null?null:companyPostalCodeField.getEditText().getText().toString());

                AdvertiserCreateDTO advertiser = new AdvertiserCreateDTO(
                        companyNameField.getEditText().getText().toString(),
                        companyAddressField.getEditText().getText().toString(),
                        companyPhoneField.getEditText().getText().toString(),
                        getIntent().getStringExtra("password"),
                        getIntent().getStringExtra("mail"),
                        companyVATField.getEditText().getText().toString(),
                        postalCode
                );

                Call<Advertiser> call = advertiserDAO.addAdvertiser(advertiser);

                call.enqueue(new Callback<Advertiser>() {
                    @Override
                    public void onResponse(Call<Advertiser> call, Response<Advertiser> response) {
                        if(response.isSuccessful()){
                            LoginAdvertiser loginAdvertiser = new LoginAdvertiser(advertiser.getMail(), advertiser.getMotDePasse());
                            AdvertiserDAO advertiserDAO = new AdvertiserDAO();

                            Call<Token> tokenCall = advertiserDAO.login(loginAdvertiser);
                            tokenCall.enqueue(new Callback<Token>() {
                                @Override
                                public void onResponse(Call<Token> call, Response<Token> response) {
                                    if(response.isSuccessful()){
                                        editor.putString("access_token", "Bearer " + response.body().getAccess_token());
                                        editor.apply();
                                        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(intent);
                                    }
                                }

                                @Override
                                public void onFailure(Call<Token> call, Throwable t) {

                                }
                            });
                        }

                        if(response.code() == 409)
                            Toast.makeText(getApplicationContext(), R.string.companyNameConflict, Toast.LENGTH_SHORT).show();

                        if(response.code() == 404)
                            Toast.makeText(getApplicationContext(), R.string.postalCodeNotValid, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<Advertiser> call, Throwable t) {

                    }
                });
            }
        }
        else {
            Toast.makeText(this, R.string.lossConnection, Toast.LENGTH_SHORT).show();
        }
    }
}
