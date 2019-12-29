package com.example.pubeo.Advertiser;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.pubeo.Advertiser.ui.home.HomeViewModel;
import com.example.pubeo.DAO.AdvertiserDAO;
import com.example.pubeo.R;
import com.example.pubeo.model.Advertiser;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AdvertiserSignInActivity extends AppCompatActivity {

    @BindView(R.id.signInButtonAdvertiser)Button signInButtonAdvertiser;
    @BindView(R.id.mailAdvertiserSignInField)TextInputLayout mailAdvertiserSignInField;
    @BindView(R.id.passwordAdvertiserSignInField)TextInputLayout passwordAdvertiserSignInField;
    private ImageView whiteArrowSignInAdvertiser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advertiser_sign_in);

        ButterKnife.bind(this);
        signInButtonAdvertiser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                openHomeActivity();
            }
        });

        whiteArrowSignInAdvertiser = findViewById(R.id.whiteArrowSignInAdvertiser);
        whiteArrowSignInAdvertiser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                finish();
            }
        });
    }

    public void openHomeActivity(){

        new LoadAdvertiser().execute();
    }

    private class LoadAdvertiser extends AsyncTask<String, Void, ArrayList<Advertiser>>{
        @Override
        protected ArrayList<Advertiser> doInBackground(String... params){
            AdvertiserDAO advertiserDAO = new AdvertiserDAO();
            ArrayList<Advertiser> advertisers = new ArrayList<>();
            try{
                advertisers = advertiserDAO.getAllAdvertisers();
            }
            catch (Exception e){
                e.printStackTrace();
                return advertisers;
            }
            return advertisers;
        }

        @Override
        protected void onPostExecute(ArrayList<Advertiser> advertisers){
            boolean isValid = true;
            int i = 0;

            if(mailAdvertiserSignInField.getEditText().getText().toString().isEmpty()){
                isValid = false;
                mailAdvertiserSignInField.setError(getString(R.string.fieldNotEmpty));
            }
            else{
                while(i < advertisers.size() && !advertisers.get(i).getMail().equals(mailAdvertiserSignInField.getEditText().getText().toString())){
                    i++;
                }

                if(i == advertisers.size()){
                    isValid = false;
                    mailAdvertiserSignInField.setError(getString(R.string.mailNotExist));
                }
                else{
                    mailAdvertiserSignInField.setErrorEnabled(false);
                }
            }

            if(passwordAdvertiserSignInField.getEditText().getText().toString().isEmpty()){
                isValid = false;
                passwordAdvertiserSignInField.setError(getString(R.string.fieldNotEmpty));
            }
            else{
                passwordAdvertiserSignInField.setErrorEnabled(false);
            }

            if (isValid) {
                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                intent.putExtra("Advertiser", advertisers.get(i));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        }
    }
}
