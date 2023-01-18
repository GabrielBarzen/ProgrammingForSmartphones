package se.gabnet.notificationapp;

import static android.content.Context.NOTIFICATION_SERVICE;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;


public class NotificationControlPanel extends Fragment {

    Button notification, dialog, toast;

    Context context;
    private ActivityResultLauncher<String> permissionActivity;




    int NOTIFICATION_CODE = 1;
    private boolean requestPermission(String check) {
        if (ActivityCompat.checkSelfPermission(context, check) == PackageManager.PERMISSION_GRANTED){
            return true;
        } else {
            permissionActivity.launch(check);
            return permission;
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }
    NotificationManager notificationManager;
    @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        notification = view.findViewById(R.id.notification_button);
        dialog = view.findViewById(R.id.dialouge_button);
        toast = view.findViewById(R.id.toast_button);

        notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        NotificationChannel notificationChannel = new NotificationChannel("EX_CHANNEL","Example channel",NotificationManager.IMPORTANCE_LOW);
        notificationManager.createNotificationChannel(notificationChannel);
        notification.setOnClickListener(v -> {
            Notification.Builder notificationBuilder = new Notification.Builder(context,"EX_CHANNEL");
            notificationBuilder.setSmallIcon(R.drawable.ic_launcher_background);
            notificationBuilder.setContentText("This is the main meat text of the notification");
            notificationBuilder.setSubText("This is the smaller text, on the side of the bigger text");
            Notification notification = notificationBuilder.build();
            notificationManager.notify(1,notification);
        });
        dialog.setOnClickListener(v -> {
            ExampleDialogFragment dialog = new ExampleDialogFragment();
            dialog.show(getParentFragmentManager(),"Example");
        });
        toast.setOnClickListener(v -> {
            Toast.makeText(context,"This is a toast shown after pressing the button.", Toast.LENGTH_SHORT).show();
        });
    }

    public NotificationControlPanel() {
        // Required empty public constructor
    }

    public static NotificationControlPanel newInstance(String param1, String param2) {
        NotificationControlPanel fragment = new NotificationControlPanel();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    boolean permission = false;
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
        return inflater.inflate(R.layout.fragment_notification_control_panel, container, false);
    }
}