package se.gabnet.recieveapp;

import android.Manifest;
import android.content.Context;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;


public class SMSReceive extends Fragment {


    Context context;
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    ListView smsList;
    ArrayAdapter<String> texts;
    ArrayList<String> textArray;

    SMSReceiver smsReceiver;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        receive = requestPermission(Manifest.permission.RECEIVE_SMS);
        smsList = view.findViewById(R.id.text_list_view);
        textArray = new ArrayList<>();

        texts = new ArrayAdapter<String>(context, R.layout.activity_listview, textArray);
        smsList.setAdapter(texts);

        System.out.println("Got receive permission: " + receive);
        if (receive) {
            read = requestPermission(Manifest.permission.READ_SMS);
            System.out.println("Got read permission: " + read);
            if (read) {
                System.out.println("Creating receiver");
                smsReceiver = new SMSReceiver(this);
                IntentFilter receiveFilter;
                receiveFilter = new IntentFilter();
                receiveFilter.addAction("android.provider.Telephony.SMS_RECEIVED");
                receiveFilter.setPriority(100);
                context.registerReceiver(smsReceiver, receiveFilter);
                System.out.println("Receiver registered");
            }
        } else {
            System.out.println("NO PERMISSION");
        }
    }
    boolean receive = false;
    boolean read = false;
    boolean permission = false;
    ActivityResultLauncher<String> permissionActivity;
    int SEND_SMS_CODE = 1;
    private boolean requestPermission(String check) {
        if (ActivityCompat.checkSelfPermission(context, check) == PackageManager.PERMISSION_GRANTED){
            return true;
        } else {
            permissionActivity.launch(check);
            return permission;
        }
    }


    public SMSReceive() {
        // Required empty public constructor
    }

    public static SMSReceive newInstance(String param1, String param2) {
        SMSReceive fragment = new SMSReceive();
        return fragment;
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
                new ActivityResultCallback<Boolean>() {
                    @Override
                    public void onActivityResult(Boolean result) {
                        if (result) permission = true;
                        else permission = false;
                    }
                }

        );
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sms_recieve, container, false);
    }

    public void recievedSms(String str) {
        System.out.println("Recieved text :" + str);
        texts.add(str);
    }
}