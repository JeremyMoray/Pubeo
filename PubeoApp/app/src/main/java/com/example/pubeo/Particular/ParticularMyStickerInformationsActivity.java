package com.example.pubeo.Particular;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pubeo.Advertiser.AddEditStickerActivity;
import com.example.pubeo.DAO.ParticularDAO;
import com.example.pubeo.R;
import com.example.pubeo.tools.CheckNetClass;
import com.google.android.material.textfield.TextInputLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ParticularMyStickerInformationsActivity extends AppCompatActivity {

    private Button deleteParticularStickerButton;
    private TextInputLayout titleMyStickerInfoInput;
    private TextInputLayout descriptionMyStickerInfoInput;
    private TextInputLayout heightMyStickerInfoInput;
    private TextInputLayout widthMyStickerInfoInput;
    private TextInputLayout numberOfUseMyStickerInfoInput;

    private static final String SHARED_PREFS = "sharedPrefs";

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_particular_my_sticker_informations);

        Intent intent = getIntent();

        deleteParticularStickerButton = findViewById(R.id.deleteParticularStickerButton);
        titleMyStickerInfoInput = findViewById(R.id.titleMyStickerInfoInput);
        descriptionMyStickerInfoInput = findViewById(R.id.descriptionMyStickerInfoInput);
        heightMyStickerInfoInput = findViewById(R.id.heightMyStickerInfoInput);
        widthMyStickerInfoInput = findViewById(R.id.widthMyStickerInfoInput);
        numberOfUseMyStickerInfoInput = findViewById(R.id.numberOfUseMyStickerInfoInput);

        getSupportActionBar().setTitle(getString(R.string.infoSticker));
        titleMyStickerInfoInput.getEditText().setText(intent.getStringExtra("EXTRA_TITLE"));
        descriptionMyStickerInfoInput.getEditText().setText(intent.getStringExtra("EXTRA_DESCRIPTION"));
        heightMyStickerInfoInput.getEditText().setText(String.format("%s", intent.getIntExtra("EXTRA_HEIGHT", 1)));
        widthMyStickerInfoInput.getEditText().setText(String.format("%s", intent.getIntExtra("EXTRA_WIDTH", 1)));
        numberOfUseMyStickerInfoInput.getEditText().setText(String.format("%s", intent.getIntExtra("EXTRA_NUMBER_OF_USE", 1)));

        deleteParticularStickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CheckNetClass.checknetwork(getApplicationContext())) {
                    SharedPreferences sharedPref = getApplicationContext().getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
                    String token = sharedPref.getString("access_token", null);

                    ParticularDAO particularDAO = new ParticularDAO();
                    Call<Void> call = particularDAO.deleteParticipation(token, intent.getStringExtra("EXTRA_ID"));
                    call.enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            if(response.isSuccessful()){
                                Toast.makeText(getApplicationContext(), R.string.deleteConfirmed, Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {

                        }
                    });
                }
                else {
                    Toast.makeText(ParticularMyStickerInformationsActivity.this, R.string.lossConnection, Toast.LENGTH_SHORT).show();
                }
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
