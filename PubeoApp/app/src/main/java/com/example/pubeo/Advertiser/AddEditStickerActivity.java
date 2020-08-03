package com.example.pubeo.Advertiser;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.pubeo.R;
import com.google.android.material.textfield.TextInputLayout;

import static java.lang.Integer.parseInt;

public class AddEditStickerActivity extends AppCompatActivity {

    private Button saveButtonAddSticker;
    private TextInputLayout titleStickerInput;
    private TextInputLayout descriptionStickerInput;
    private TextInputLayout heightStickerInput;
    private TextInputLayout widthStickerInput;
    private TextInputLayout numberOfUseStickerInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_sticker);

        Intent intent = getIntent();

        titleStickerInput = findViewById(R.id.titleStickerInput);
        descriptionStickerInput = findViewById(R.id.descriptionStickerInput);
        heightStickerInput = findViewById(R.id.heightStickerInput);
        widthStickerInput = findViewById(R.id.widthStickerInput);
        numberOfUseStickerInput = findViewById(R.id.numberOfUseStickerInput);

        if(intent.hasExtra("EXTRA_ID")){
            getSupportActionBar().setTitle(getString(R.string.editSticker));
            titleStickerInput.getEditText().setText(intent.getStringExtra("EXTRA_TITLE"));
            descriptionStickerInput.getEditText().setText(intent.getStringExtra("EXTRA_DESCRIPTION"));
            heightStickerInput.getEditText().setText(String.format("%s", intent.getIntExtra("EXTRA_HEIGHT", 1)));
            widthStickerInput.getEditText().setText(String.format("%s", intent.getIntExtra("EXTRA_WIDTH", 1)));
            numberOfUseStickerInput.getEditText().setText(String.format("%s", intent.getIntExtra("EXTRA_NUMBER_OF_USE", 1)));


        }
        else{
            getSupportActionBar().setTitle(getString(R.string.addSticker));
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        saveButtonAddSticker = findViewById(R.id.saveButtonAddSticker);
        saveButtonAddSticker.getText();
        saveButtonAddSticker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                boolean isValid = true;

                String title = titleStickerInput.getEditText().getText().toString();
                String description = descriptionStickerInput.getEditText().getText().toString();
                int height = parseInt(heightStickerInput.getEditText().getText().toString());
                int width = parseInt(widthStickerInput.getEditText().getText().toString());
                int numberOfUse = parseInt(numberOfUseStickerInput.getEditText().getText().toString());

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
                    intent.putExtra("EXTRA_TITLE", title);
                    intent.putExtra("EXTRA_DESCRIPTION", description);
                    intent.putExtra("EXTRA_HEIGHT", height);
                    intent.putExtra("EXTRA_WIDTH", width);
                    intent.putExtra("EXTRA_NUMBER_OF_USE", numberOfUse);

                    int id = getIntent().getIntExtra("EXTRA_ID", -1);
                    if(id != -1){
                        intent.putExtra("EXTRA_ID", id);
                    }

                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });
    }
}
