package com.example.pubeo.DAO;

import com.example.pubeo.BuildConfig;
import com.example.pubeo.DTO.StickerCreateDTO;
import com.example.pubeo.DTO.StickerDetailsDTO;
import com.example.pubeo.Service.ServiceAPI;
import com.example.pubeo.model.Sticker;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.List;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Header;
import retrofit2.http.Path;

public class StickerDAO {

    private ServiceAPI serviceAPI;
    private Gson gson = new GsonBuilder().serializeNulls().create();

    private Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BuildConfig.Base_URL)
            .client(getUnsafeOkHttpClient())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build();

    public Call<List<StickerDetailsDTO>> GetAllByProfessionnelId(String token, String professionnelId) {
        disableSSLCertificateChecking();
        serviceAPI = retrofit.create(ServiceAPI.class);

        return serviceAPI.GetAllByProfessionnelId(token, professionnelId);
    }

    public Call<Sticker> addSticker(String token, StickerCreateDTO sticker){
        disableSSLCertificateChecking();
        serviceAPI = retrofit.create(ServiceAPI.class);

        return serviceAPI.addSticker(token, sticker);
    }

    public Call<Sticker> updateSticker(String token, String id, StickerCreateDTO sticker){
        disableSSLCertificateChecking();
        serviceAPI = retrofit.create(ServiceAPI.class);

        return serviceAPI.updateSticker(token, id, sticker);
    }

    public Call<Void> deleteSticker(@Header("Authorization") String token, @Path("id") String id){
        disableSSLCertificateChecking();
        serviceAPI = retrofit.create(ServiceAPI.class);

        return serviceAPI.deleteSticker(token, id);
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
