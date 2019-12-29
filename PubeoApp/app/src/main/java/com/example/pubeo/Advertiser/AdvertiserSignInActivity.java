package com.example.pubeo.Advertiser;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.pubeo.DAO.AdvertiserDAO;
import com.example.pubeo.R;
import com.example.pubeo.Services.ServiceAPI;
import com.example.pubeo.model.Advertiser;
import com.google.android.material.textfield.TextInputLayout;

import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

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
/*
    public static OkHttpClient.Builder getUnsafeOkHttpClient() {

        try {
            // Create a trust manager that does not validate certificate chains
            final TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager() {
                        @Override
                        public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                            return new java.security.cert.X509Certificate[]{};
                        }
                    }
            };

            // Install the all-trusting trust manager
            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());

            // Create an ssl socket factory with our all-trusting manager
            final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.sslSocketFactory(sslSocketFactory, (X509TrustManager) trustAllCerts[0]);
            builder.hostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });
            return builder;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }*/

    public void openHomeActivity(){
/*
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ServiceAPI.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(getUnsafeOkHttpClient().build())
                .build();


        ServiceAPI api = retrofit.create(ServiceAPI.class);

        Call<List<Advertiser>> call = api.getAdvertisers();

        call.enqueue(new Callback<List<Advertiser>>() {
            @Override
            public void onResponse(Call<List<Advertiser>> call, Response<List<Advertiser>> response) {
                boolean isValid = true;

                List<Advertiser> advertisers = response.body();

                if(mailAdvertiserSignInField.getEditText().getText().toString().isEmpty()){
                    isValid = false;
                    mailAdvertiserSignInField.setError(getString(R.string.fieldNotEmpty));
                }
                else{
                    int i = 0;
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
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<List<Advertiser>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });*/

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
                return advertisers;
            }
            return advertisers;
        }

        @Override
        protected void onPostExecute(ArrayList<Advertiser> advertisers){
            boolean isValid = true;

            if(mailAdvertiserSignInField.getEditText().getText().toString().isEmpty()){
                isValid = false;
                mailAdvertiserSignInField.setError(getString(R.string.fieldNotEmpty));
            }
            else{
                int i = 0;
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
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        }
    }
}
