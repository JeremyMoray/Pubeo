package com.example.pubeo.Particular;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pubeo.DAO.ParticularDAO;
import com.example.pubeo.DAO.StickerDAO;
import com.example.pubeo.DTO.ParticipationCreateDTO;
import com.example.pubeo.R;
import com.example.pubeo.model.Particular;
import com.example.pubeo.tools.CheckNetClass;
import com.google.android.material.textfield.TextInputLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ParticularStickerInformationsActivity extends AppCompatActivity {

    private Button chooseButtonSticker;
    private TextInputLayout titleStickerInfoInput;
    private TextInputLayout descriptionStickerInfoInput;
    private TextInputLayout heightStickerInfoInput;
    private TextInputLayout widthStickerInfoInput;
    private TextInputLayout numberOfUseStickerInfoInput;

    private static final String SHARED_PREFS = "sharedPrefs";

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_particular_sticker_informations);

        Intent intent = getIntent();

        titleStickerInfoInput = findViewById(R.id.titleStickerInfoInput);
        descriptionStickerInfoInput = findViewById(R.id.descriptionStickerInfoInput);
        heightStickerInfoInput = findViewById(R.id.heightStickerInfoInput);
        widthStickerInfoInput = findViewById(R.id.widthStickerInfoInput);
        numberOfUseStickerInfoInput = findViewById(R.id.numberOfUseStickerInfoInput);

        getSupportActionBar().setTitle(getString(R.string.infoSticker));
        titleStickerInfoInput.getEditText().setText(intent.getStringExtra("EXTRA_TITLE"));
        descriptionStickerInfoInput.getEditText().setText(intent.getStringExtra("EXTRA_DESCRIPTION"));
        heightStickerInfoInput.getEditText().setText(String.format("%s", intent.getIntExtra("EXTRA_HEIGHT", 1)));
        widthStickerInfoInput.getEditText().setText(String.format("%s", intent.getIntExtra("EXTRA_WIDTH", 1)));
        numberOfUseStickerInfoInput.getEditText().setText(String.format("%s", intent.getIntExtra("EXTRA_NUMBER_OF_USE", 1)));

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        chooseButtonSticker = findViewById(R.id.chooseButtonSticker);
        chooseButtonSticker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CheckNetClass.checknetwork(getApplicationContext())) {
                    SharedPreferences sharedPref = getApplicationContext().getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
                    String token = sharedPref.getString("access_token", null);

                    ParticularDAO particularDAO = new ParticularDAO();
                    Call<Particular> call = particularDAO.getMeParticular(token);
                    call.enqueue(new Callback<Particular>() {
                        @Override
                        public void onResponse(Call<Particular> call, Response<Particular> response) {
                            if(response.isSuccessful()){
                                ParticipationCreateDTO participation = new ParticipationCreateDTO(response.body().getId(), intent.getStringExtra("EXTRA_ID"));

                                StickerDAO stickerDAO = new StickerDAO();
                                Call<Void> addCall = stickerDAO.addParticipation(token, participation);

                                addCall.enqueue(new Callback<Void>() {
                                    @Override
                                    public void onResponse(Call<Void> call, Response<Void> response) {
                                        if(response.isSuccessful()){
                                            Toast.makeText(getApplicationContext(), R.string.dataSaved, Toast.LENGTH_SHORT).show();
                                            finish();
                                        }

                                        if(response.code() == 409){
                                            Toast.makeText(getApplicationContext(), R.string.youAlreadyParticipate, Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<Void> call, Throwable t) {
                                    }
                                });
                            }
                        }

                        @Override
                        public void onFailure(Call<Particular> call, Throwable t) {
                        }
                    });
                }
                else {
                    Toast.makeText(ParticularStickerInformationsActivity.this, R.string.lossConnection, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
