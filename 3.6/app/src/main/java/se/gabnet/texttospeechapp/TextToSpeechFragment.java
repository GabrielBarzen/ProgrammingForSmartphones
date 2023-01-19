package se.gabnet.texttospeechapp;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.speech.tts.TextToSpeech;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Locale;


public class TextToSpeechFragment extends Fragment implements TextToSpeech.OnInitListener {

    private Context context;
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }
    TextToSpeech textToSpeech;
    private Button speakButton;
    private EditText speechInput;
    private boolean ttsReady = false;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        speakButton = view.findViewById(R.id.speak_button);
        speechInput = view.findViewById(R.id.tts_input_view);
        textToSpeech = new TextToSpeech(context,this);

        speakButton.setOnClickListener(v -> {
            System.out.println("text to speach is ready : " + ttsReady);
            if (ttsReady) {
                Toast.makeText(context, "Speaking", Toast.LENGTH_SHORT).show();
                String text = speechInput.getText().toString();
                textToSpeech.setLanguage(Locale.UK);
                textToSpeech.speak(text,TextToSpeech.QUEUE_FLUSH,null,null);
            }
        });
    }

    public TextToSpeechFragment() {
        // Required empty public constructor
    }


    public static TextToSpeechFragment newInstance() {
        TextToSpeechFragment fragment = new TextToSpeechFragment();

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
        return inflater.inflate(R.layout.fragment_text_to_speech, container, false);
    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            //Setting speech Language
            ttsReady = true;
            textToSpeech.setLanguage(Locale.ENGLISH);
            textToSpeech.setPitch(1);
        } else {
            System.out.println("Failed to init tts");
            Toast.makeText(context, "TTS not supported on device Try enabling in system settings", Toast.LENGTH_LONG).show();
        }
    }
}