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
import com.example.pubeo.R;
import com.example.pubeo.model.Advertiser;
import com.example.pubeo.model.Sticker;
import com.example.pubeo.tools.validation.ValidationTextWatcher;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.provider.Telephony.Mms.Part.TEXT;
import static com.example.pubeo.tools.constants.constantTags.*;

public class CompanyInformationsActivity extends AppCompatActivity {

    @BindView(R.id.continueInformationsAdvertiserButton) Button continueInformationsAdvertiserButton;
    @BindView(R.id.companyNameField) TextInputLayout companyNameField;
    @BindView(R.id.companyVATField) TextInputLayout companyVATField;
    @BindView(R.id.companyPhoneField) TextInputLayout companyPhoneField;
    @BindView(R.id.companyPhoneFieldEditText) TextInputEditText companyPhoneFieldEditText;
    @BindView(R.id.companyAddressField) TextInputLayout companyAddressField;

    private ImageView whiteArrowCompanyInformations;
    private ImageView companyLogoId;
    private Uri imageUrl;

    private String mail;
    private String password;
    private Advertiser advertiser;

    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String IMAGEPATH = "imagePath";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_informations);

        ButterKnife.bind(this);

        mail = getIntent().getStringExtra("mail");
        password = getIntent().getStringExtra("password");

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
            imageUrl = data.getData();
            try{
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),imageUrl);
                companyLogoId.setImageBitmap(bitmap);
            }
            catch(IOException e){
                e.printStackTrace();
            }
        }
    }

    public void openHomeActivity(){
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
            try{
                 advertiser = new Advertiser(
                        null,
                        companyNameField.getEditText().getText().toString(),
                        companyAddressField.getEditText().getText().toString(),
                        companyPhoneField.getEditText().getText().toString(),
                        mail,
                        companyVATField.getEditText().getText().toString(),
                        new ArrayList<>()
                );
                advertiserDAO.addAdvertiser(advertiser);
            }
            catch (Exception e){
                e.printStackTrace();
            }

            Intent intent = new Intent(this, HomeActivity.class);
            intent.putExtra("Advertiser", advertiser);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }
}
