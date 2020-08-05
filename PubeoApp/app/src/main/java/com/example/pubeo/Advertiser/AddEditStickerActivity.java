package com.example.pubeo.Advertiser;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.pubeo.R;
import com.example.pubeo.tools.CheckNetClass;
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
                if(CheckNetClass.checknetwork(getApplicationContext())) {
                    boolean isValid = true;

                    String title = titleStickerInput.getEditText().getText().toString();
                    String description = descriptionStickerInput.getEditText().getText().toString();
                    String height = heightStickerInput.getEditText().getText().toString();
                    String width = widthStickerInput.getEditText().getText().toString();
                    String numberOfUse = numberOfUseStickerInput.getEditText().getText().toString();

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

                    if(heightStickerInput.getEditText().getText().toString().isEmpty()){
                        isValid = false;
                        heightStickerInput.setError(getString(R.string.fieldNotEmpty));
                    }
                    else{
                        heightStickerInput.setErrorEnabled(false);
                    }

                    if(widthStickerInput.getEditText().getText().toString().isEmpty()){
                        isValid = false;
                        widthStickerInput.setError(getString(R.string.fieldNotEmpty));
                    }
                    else{
                        widthStickerInput.setErrorEnabled(false);
                    }

                    if(numberOfUseStickerInput.getEditText().getText().toString().isEmpty()){
                        isValid = false;
                        numberOfUseStickerInput.setError(getString(R.string.fieldNotEmpty));
                    }
                    else{
                        numberOfUseStickerInput.setErrorEnabled(false);
                    }

                    if (isValid) {
                        Intent intent = new Intent();
                        intent.putExtra("EXTRA_TITLE", title);
                        intent.putExtra("EXTRA_DESCRIPTION", description);
                        intent.putExtra("EXTRA_HEIGHT", parseInt(height));
                        intent.putExtra("EXTRA_WIDTH", parseInt(width));
                        intent.putExtra("EXTRA_NUMBER_OF_USE", parseInt(numberOfUse));

                        String id = getIntent().getStringExtra("EXTRA_ID");
                        if(id != null && !id.equals("-1")){
                            intent.putExtra("EXTRA_ID", id);
                        }

                        setResult(RESULT_OK, intent);
                        finish();
                    }
                }
                else {
                    Toast.makeText(AddEditStickerActivity.this, R.string.lossConnection, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
