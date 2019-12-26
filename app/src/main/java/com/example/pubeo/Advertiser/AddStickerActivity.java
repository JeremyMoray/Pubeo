package com.example.pubeo.Advertiser;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.pubeo.R;
import com.google.android.material.textfield.TextInputLayout;

public class AddStickerActivity extends AppCompatActivity {

    private Button saveButtonAddSticker;
    private TextInputLayout titleStickerInput;
    private TextInputLayout descriptionStickerInput;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_sticker);

        getSupportActionBar().setTitle(getString(R.string.addSticker));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        titleStickerInput = findViewById(R.id.titleStickerInput);
        descriptionStickerInput = findViewById(R.id.descriptionStickerInput);

        saveButtonAddSticker = findViewById(R.id.saveButtonAddSticker);
        saveButtonAddSticker.getText();
        saveButtonAddSticker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                boolean isValid = true;
                String title = titleStickerInput.getEditText().getText().toString();
                String description = descriptionStickerInput.getEditText().getText().toString();

                if(title.isEmpty()){
                    isValid = false;
                    titleStickerInput.setError(getString(R.string.fieldNotEmpty));
                }
                else{
                    titleStickerInput.setErrorEnabled(false);
                }

                if(description.isEmpty()){
                    isValid = false;
                    descriptionStickerInput.setError(getString(R.string.fieldNotEmpty));
                }
                else{
                    descriptionStickerInput.setErrorEnabled(false);
                }

                if (isValid) {
                    Intent intent = new Intent();
                    intent.putExtra("title", title);
                    intent.putExtra("description", description);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });
    }
}
