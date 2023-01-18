package se.gabnet.vibrationapp;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class FragmentVibration extends Fragment {
    Context context;
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    Vibrator vibrator;
    Button vibrate;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        vibrate = view.findViewById(R.id.button_vibrate);
        vibrate.setOnClickListener(v -> {
            VibrationEffect vibrationEffect;
            vibrationEffect = VibrationEffect.createOneShot(1000,VibrationEffect.DEFAULT_AMPLITUDE);
            vibrator.vibrate(vibrationEffect);
        });
    }

    public FragmentVibration() {
        // Required empty public constructor
    }

    public static FragmentVibration newInstance(String param1, String param2) {
        FragmentVibration fragment = new FragmentVibration();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_vibration, container, false);
    }
}