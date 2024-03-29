package se.gabnet.copypaster;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().setReorderingAllowed(true).add(R.id.copy_paste_view, FragmentCopyPaste.class,null).commit();
        }
    }

}