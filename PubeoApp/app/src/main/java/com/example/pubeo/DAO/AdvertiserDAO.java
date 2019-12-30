package com.example.pubeo.DAO;

import android.content.Intent;
import android.util.Log;

import com.example.pubeo.MainActivity;
import com.example.pubeo.Service.ServiceAPI;
import com.example.pubeo.model.Advertiser;
import com.example.pubeo.model.Sticker;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.ArrayList;

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

    public ArrayList<Advertiser> getAllAdvertisers() throws Exception {
        disableSSLCertificateChecking();
        URL url = new URL("https://10.0.2.2:5001/Professionnels");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        BufferedReader buffer = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder builder = new StringBuilder();
        String stringJSON = "",line;
        while((line = buffer.readLine()) != null){
            builder.append(line);
        }
        buffer.close();
        stringJSON = builder.toString();
        return jsonToAdvertisers(stringJSON);
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

    private ArrayList<Advertiser>jsonToAdvertisers(String stringJSON) throws Exception{
        ArrayList<Advertiser> advertisers = new ArrayList<>();
        Advertiser advertiser;
        JSONArray jsonArray = new JSONArray(stringJSON);
        for(int i = 0; i < jsonArray.length(); i++){
            JSONObject jsonAdvertiser = jsonArray.getJSONObject(i);

            JSONArray ja = jsonAdvertiser.getJSONArray("stickers");
            int len = ja.length();

            ArrayList<Sticker> stickers = new ArrayList<>();

            for(int j=0; j<len; j++) {
                JSONObject jsonStickers = ja.getJSONObject(j);
                stickers.add(
                        new Sticker(
                            jsonStickers.getString("id"),
                            jsonStickers.getString("titre"),
                            jsonStickers.getString("description"),
                            jsonStickers.getInt("hauteur"),
                            jsonStickers.getInt("largeur"),
                            jsonStickers.getInt("nbUtilisationsRestantes")
                        )
                );
            }

            advertiser = new Advertiser(
                    jsonAdvertiser.getString("id"),
                    jsonAdvertiser.getString("nomEntreprise"),
                    jsonAdvertiser.getString("adresse"),
                    jsonAdvertiser.getString("numeroTel"),
                    jsonAdvertiser.getString("mail"),
                    jsonAdvertiser.getString("numeroTVA"),
                    stickers
            );
            advertisers.add(advertiser);
        }

        return advertisers;
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
