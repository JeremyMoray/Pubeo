package com.example.pubeo.DAO;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.example.pubeo.Advertiser.AdvertiserSignInActivity;
import com.example.pubeo.Advertiser.HomeActivity;
import com.example.pubeo.BuildConfig;
import com.example.pubeo.DTO.AdvertiserCreateDTO;
import com.example.pubeo.DTO.AdvertiserUpdateDTO;
import com.example.pubeo.R;
import com.example.pubeo.Service.ServiceAPI;
import com.example.pubeo.model.Advertiser;
import com.example.pubeo.model.LoginAdvertiser;
import com.example.pubeo.model.Token;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AdvertiserDAO {
    private ServiceAPI serviceAPI;
    private Gson gson = new GsonBuilder().serializeNulls().create();

    private Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BuildConfig.Base_URL)
            .client(getUnsafeOkHttpClient())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build();

    public Call<Token> login(LoginAdvertiser loginAdvertiser) {
        disableSSLCertificateChecking();
        serviceAPI = retrofit.create(ServiceAPI.class);

        return serviceAPI.loginAdvertiser(loginAdvertiser);
    }

    public Call<Advertiser> getMeAdvertiser(String token){
        disableSSLCertificateChecking();
        serviceAPI = retrofit.create(ServiceAPI.class);

        return serviceAPI.getMeAdvertiser(token);
    }

    public Call<Advertiser> addAdvertiser(AdvertiserCreateDTO advertiserReceived) {
        disableSSLCertificateChecking();

        serviceAPI = retrofit.create(ServiceAPI.class);

        return serviceAPI.addAdvertiser(advertiserReceived);
    }

    public Call<Void> updateAdvertiser(String token, AdvertiserUpdateDTO advertiser) {
        disableSSLCertificateChecking();

        serviceAPI = retrofit.create(ServiceAPI.class);

        return serviceAPI.updateAdvertiser(token, advertiser);
    }

    public Call<Void> deleteAdvertiser(String token) {
        disableSSLCertificateChecking();

        serviceAPI = retrofit.create(ServiceAPI.class);

        return serviceAPI.deleteAdvertiser(token);
    }

    public OkHttpClient getUnsafeOkHttpClient() {
            try {
                // Create a trust manager that does not validate certificate chains
                final TrustManager[] trustAllCerts = new TrustManager[] {
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
                builder.sslSocketFactory(sslSocketFactory, (X509TrustManager)trustAllCerts[0]);
                builder.hostnameVerifier(new HostnameVerifier() {
                    @Override
                    public boolean verify(String hostname, SSLSession session) {
                        return true;
                    }
                });

                OkHttpClient okHttpClient = builder.build();
                return okHttpClient;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
    }

    private static void disableSSLCertificateChecking() {
        TrustManager[] trustAllCerts = new TrustManager[] {
                new X509TrustManager() {

                    @Override
                    public void checkClientTrusted(java.security.cert.X509Certificate[] x509Certificates, String s) throws java.security.cert.CertificateException {
                        // not implemented
                    }

                    @Override
                    public void checkServerTrusted(java.security.cert.X509Certificate[] x509Certificates, String s) throws java.security.cert.CertificateException {
                        // not implemented
                    }

                    @Override
                    public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                        return null;
                    }

                }
        };

        try {

            HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {

                @Override
                public boolean verify(String s, SSLSession sslSession) {
                    return true;
                }

            });
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }
}
