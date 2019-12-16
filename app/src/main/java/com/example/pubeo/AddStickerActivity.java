package com.example.pubeo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.pubeo.model.Sticker;
import com.example.pubeo.ui.home.HomeViewModel;
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
                Intent intent = new Intent();
                intent.putExtra("title", titleStickerInput.getEditText().getText().toString());
                intent.putExtra("description", descriptionStickerInput.getEditText().getText().toString());
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }
}
