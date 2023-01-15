package se.gabnet.mediapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import se.gabnet.mediapp.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .add(R.id.capfrag, MediaFragment.class, null)
                    .commit();
        }
    }
}