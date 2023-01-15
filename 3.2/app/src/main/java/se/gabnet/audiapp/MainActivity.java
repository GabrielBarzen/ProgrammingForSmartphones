package se.gabnet.AudiApp;

import static android.Manifest.permission.RECORD_AUDIO;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

import static androidx.core.content.PermissionChecker.PERMISSION_GRANTED;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    private MediaRecorder mediaRecorder;
    private MediaPlayer mediaPlayer;
    private String filename;

    private Button record, play;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        record = findViewById(R.id.rec10sec);
        play = findViewById(R.id.play10sec);

        record.setOnClickListener(v -> {
            System.out.println("pressed " + v.getTransitionName());
            startRecording();
        });
        play.setOnClickListener(v -> {
            playRecording();
        });

        filename = getApplicationContext().getFilesDir() + File.separator + "10sec.3gp";
    }

    private void startRecording() {
        System.out.println("entered start recording");
        if (ContextCompat.checkSelfPermission(getApplicationContext(), RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED) {
            System.out.println("entered try recording");
            mediaRecorder = new MediaRecorder();
            mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            mediaRecorder.setOutputFile(filename);
            try {
                mediaRecorder.prepare();
                CompletableFuture.runAsync(() -> {
                    try {
                        mediaRecorder.start();
                        System.out.println("Started recording");
                        TimeUnit.SECONDS.sleep(10);
                        mediaRecorder.stop();
                        mediaRecorder.release();
                        mediaRecorder = null;
                        System.out.println("Stopped recording");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                });



            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            ActivityCompat.requestPermissions(MainActivity.this,new String[]{RECORD_AUDIO, WRITE_EXTERNAL_STORAGE},REQUEST_AUDIO_PERMISSION_CODE);
        }
    }
    int REQUEST_AUDIO_PERMISSION_CODE = 1;


    private void playRecording() {
        try {
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setDataSource(filename);
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}