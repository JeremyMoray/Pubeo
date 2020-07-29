package com.example.pubeo.DAO;

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
            .baseUrl("https://10.0.2.2:5001/")
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

    public void addAdvertiser(Advertiser advertiserReceived) throws Exception{
        disableSSLCertificateChecking();

        serviceAPI = retrofit.create(ServiceAPI.class);

        Call<Advertiser> call = serviceAPI.addAdvertiser(advertiserReceived);
        call.enqueue(new Callback<Advertiser>() {
            @Override
            public void onResponse(Call<Advertiser> call, Response<Advertiser> response) {
                if (!response.isSuccessful()) {
                    return;
                }
            }

            @Override
            public void onFailure(Call<Advertiser> call, Throwable t) {
            }
        });
    }

    public void updateAdvertiser(Advertiser advertiserReceived) throws Exception{
        disableSSLCertificateChecking();

        serviceAPI = retrofit.create(ServiceAPI.class);

        Advertiser advertiser = new Advertiser(advertiserReceived.getId(), advertiserReceived.getNomEntreprise(), advertiserReceived.getAdresse(), advertiserReceived.getNumeroTel(), advertiserReceived.getMail(), advertiserReceived.getNumeroTVA(), advertiserReceived.getStickers());
        Call<Advertiser> call = serviceAPI.putAdvertiser(advertiserReceived.getId(), advertiser);
        call.enqueue(new Callback<Advertiser>() {
            @Override
            public void onResponse(Call<Advertiser> call, Response<Advertiser> response) {
                if (!response.isSuccessful()) {
                    return;
                }
            }

            @Override
            public void onFailure(Call<Advertiser> call, Throwable t) {
            }
        });
    }

    public boolean deleteAdvertiser(String id) throws Exception{
        disableSSLCertificateChecking();

        serviceAPI = retrofit.create(ServiceAPI.class);

        Call<Advertiser> call = serviceAPI.deleteAdvertiser(id);
        call.enqueue(new Callback<Advertiser>() {
            @Override
            public void onResponse(Call<Advertiser> call, Response<Advertiser> response) {
                if (!response.isSuccessful()) {
                    return;
                }
            }

            @Override
            public void onFailure(Call<Advertiser> call, Throwable t) {
            }
        });

        return true;
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
