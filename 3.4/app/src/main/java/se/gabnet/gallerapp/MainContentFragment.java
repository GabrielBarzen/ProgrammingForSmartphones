package se.gabnet.gallerapp;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.List;


public class MainContentFragment extends Fragment {
    Context context;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    private Button next, previous;
    private ImageView imageView;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        next = view.findViewById(R.id.next_button);
        previous = view.findViewById(R.id.previous_button);
        imageView = view.findViewById(R.id.image);


        Log.i("Start", "Has OnClickListeners Next    ? : " + next.hasOnClickListeners());
        Log.i("Start", "Has OnClickListeners Previous? : " + previous.hasOnClickListeners());

        Log.i("Start", "MainContentFragment loaded");
        Log.i("Start", "Asking for storage permissions");
        if (requestPermission()) {
            Log.i("Start", "Got permission");
            //open gallery
            galleryLauncher.launch(new PickVisualMediaRequest());

        } else {
            Log.i("Start", "No permission");
            Toast.makeText(context,"Cannot use application without permission", Toast.LENGTH_SHORT).show();
        }


        next.setOnClickListener(v -> {
            Log.i("ImgNav", "Getting next image : " + (index+1));

            //if (!(index < images.size())) {
            //    Toast.makeText(context,"Already att end", Toast.LENGTH_SHORT).show();
            //} else {
            //    imageView.setImageURI(images.get(++index));
            //}

        });
        previous.setOnClickListener(v -> {
            Log.i("ImgNav", "Getting next image : " + (index-1));

            //if (index < 1) {
            //    Toast.makeText(context,"Already att beginning", Toast.LENGTH_SHORT).show();
            //} else {
            //    imageView.setImageURI(images.get(--index));
            //}

        });

    }

    int index = 0;
    private void refreshImageViewer() {
        index = 0;
        imageView.setImageURI(images.get(index));
    }

    public boolean requestPermission() {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            permissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE);
            return granted;
        }
    }

    public MainContentFragment() {

    }

    public static MainContentFragment newInstance() {
        MainContentFragment mainContentFragment = new MainContentFragment();
        return mainContentFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    ActivityResultLauncher<String> permissionLauncher;
    boolean granted = false;

    private ActivityResultLauncher<PickVisualMediaRequest> galleryLauncher;
    private List<Uri> images;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        permissionLauncher = registerForActivityResult(
                new ActivityResultContracts.RequestPermission(),
                isGranted -> granted = isGranted
        );

        galleryLauncher = registerForActivityResult(
                new ActivityResultContracts.PickMultipleVisualMedia(),
                result -> {
                    images = result;
                    Log.i("Image", "onCreateView: refreshing images with : " + images);
                    if (!images.isEmpty()) {
                        refreshImageViewer();
                    }
                }
        );

        return inflater.inflate(R.layout.fragment_main_content, container, false);
    }

}