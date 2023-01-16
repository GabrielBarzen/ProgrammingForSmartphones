package se.gabnet.youtubeplayer;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class youtube_video_selector extends Fragment {

    Button video1, video2, video3, video4, video5;
    Context context;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        video1 = view.findViewById(R.id.video1);
        video2 = view.findViewById(R.id.video2);
        video3 = view.findViewById(R.id.video3);
        video4 = view.findViewById(R.id.video4);
        video5 = view.findViewById(R.id.video5);
        List<Button> buttons = new ArrayList<>();
        buttons.add(video1);
        buttons.add(video2);
        buttons.add(video3);
        buttons.add(video4);
        buttons.add(video5);
        String[] links = {"Cv9NSR-2DwM","zqNTltOGh5c","uKYrNgbui1w","dgx9e-5_fE8","SIf559UarPE&list=OLAK5uy_norak3Q_aYAj1v8IAyUqioZjZEpdDnXx0"};
        ArrayList<String> linksList = new ArrayList<>(Arrays.asList(links));

        for (Button button : buttons) {
            button.setOnClickListener(v -> {
                String id = linksList.remove(0);
                Intent youtubeAppIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + id ));
                Intent youtubeWebIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(("https://www.youtube.com/watch?v=" + id)));
                try {
                    //context.startActivity(youtubeAppIntent);
                    context.startActivity(youtubeWebIntent);

                }catch (Exception e) {
                }
            });
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_youtube_video_selector, container, false);
    }
}