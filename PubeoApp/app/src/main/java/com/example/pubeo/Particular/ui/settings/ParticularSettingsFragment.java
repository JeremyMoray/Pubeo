package com.example.pubeo.Particular.ui.settings;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.pubeo.MainActivity;
import com.example.pubeo.R;

public class ParticularSettingsFragment extends Fragment {

    private ParticularSettingsViewModel particularSettingsViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        particularSettingsViewModel =
                ViewModelProviders.of(this).get(ParticularSettingsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_particular_settings, container, false);
        final Button buttonFinish = root.findViewById(R.id.disconnectButtonParticular);

        buttonFinish.setOnClickListener(new View.OnClickListener() {
                @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity().getApplicationContext(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        return root;
    }
}