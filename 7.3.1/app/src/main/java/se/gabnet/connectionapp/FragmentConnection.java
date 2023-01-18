package se.gabnet.connectionapp;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.net.InetAddress;


public class FragmentConnection extends Fragment {



    public FragmentConnection() {
        // Required empty public constructor
    }
    Context context;
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    TextView connection;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        connection = view.findViewById(R.id.connection_status_text);
        setConnectionText();

    }

    public void setConnectionText() {
        connection.setText(internetStatus());
    }

    public String internetStatus(){
        String status = "";
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm.getActiveNetworkInfo() != null) {
            if (cm.getActiveNetworkInfo().isConnected()) {
                status = "network found, connected";
            } else {
                status = "network found, no connection";
            }
        } else {
            status = "no network found";
        }
        return status;
    }

    public static FragmentConnection newInstance(String param1, String param2) {
        FragmentConnection fragment = new FragmentConnection();

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
        return inflater.inflate(R.layout.fragment_connection, container, false);
    }


}