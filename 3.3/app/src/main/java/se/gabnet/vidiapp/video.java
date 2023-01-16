package se.gabnet.vidiapp;

import static android.app.Activity.RESULT_OK;

import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.VideoView;

import java.util.UUID;


public class video extends Fragment {



    public video() {
        // Required empty public constructor
    }

    Button record,play;
    VideoView videoView;


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        record = view.findViewById(R.id.record_button);
        play = view.findViewById(R.id.play_button);
        videoView = view.findViewById(R.id.videoView);
        record.setOnClickListener(v -> {
            recordVideo();
        });
        play.setOnClickListener(v -> {

            videoView.start();
            System.out.println("playing");

        });

    }

    Uri camUri;
    ActivityResultLauncher<Intent> startCamera;

    private void recordVideo() {
        ContentValues contentValues = new ContentValues();
        contentValues.put(MediaStore.Images.Media.TITLE,new UUID(0,Long.MAX_VALUE).toString());
        contentValues.put(MediaStore.Images.Media.DESCRIPTION, "From Camera");
        camUri = requireContext().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
        Intent cameraIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, camUri);

        startCamera.launch(cameraIntent);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println("Created view");
        startCamera = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        // There are no request codes
                        videoView.setVideoURI(camUri);
                    }
                }
        );
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_video, container, false);
    }
}