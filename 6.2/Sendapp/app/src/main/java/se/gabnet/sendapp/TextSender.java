package se.gabnet.sendapp;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;


public class TextSender extends Fragment {

    Context context;

    public TextSender() {
        // Required empty public constructor
    }

    Button clear, send;
    EditText number, message;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        clear = view.findViewById(R.id.button_clear);
        send = view.findViewById(R.id.button_send);
        number = view.findViewById(R.id.phone_number_input_text);
        message = view.findViewById(R.id.text_message_input_text);


        clear.setOnClickListener(v -> {
            message.setText("");
            number.setText("");
        });
        send.setOnClickListener(v -> {
            requestPermission();
            if (permission) {
                String phoneNumber = (number.getText().toString());
                String textMessage = message.getText().toString();
                System.out.println("Sending to : " + phoneNumber + "\nMessage : " + textMessage);
                SmsManager sms = SmsManager.getDefault();
                sms.sendTextMessage(phoneNumber, null, textMessage, null, null);
            }
        });
    }
    boolean permission = false;
    ActivityResultLauncher<String> permissionActivity;
    int SEND_SMS_CODE = 1;
    private void requestPermission() {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED) {
            permission = true;
        } else {
            permissionActivity.launch(Manifest.permission.SEND_SMS);
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    public static TextSender newInstance(String param1, String param2) {
        return new TextSender();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        permissionActivity = registerForActivityResult(
                new ActivityResultContracts.RequestPermission(),
                result -> {
                    if (result) permission = true;
                    else permission = false;
                }

        );
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_text_sender, container, false);
    }
}