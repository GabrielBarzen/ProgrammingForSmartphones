package se.gabnet.sqliteapp;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;


public class PrimeViewer extends Fragment {

    public PrimeViewer() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    private Context context;
    private ArrayAdapter<String> adapter;
    private ListView primeListView;

    @Override
    public void onPrimaryNavigationFragmentChanged(boolean isPrimaryNavigationFragment) {
        super.onPrimaryNavigationFragmentChanged(isPrimaryNavigationFragment);

    }

    Button back;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        back = view.findViewById(R.id.back_button);
        primeListView = view.findViewById(R.id.prime_list_view);
        adapter = new ArrayAdapter<String>(context, R.layout.activity_listview, new ArrayList<>());
        primeListView.setAdapter(adapter);
        Bundle bundle = getArguments();
        if (bundle != null) {
            long[] longs = bundle.getLongArray("found_primes");
            adapter.clear();
            for (int i = longs.length - 1; i >= 0; i--) {

                adapter.add(String.valueOf(longs[i]));
            }


        } else {
            Log.w("ERROR", "onPrimaryNavigationFragmentChanged: bundle not found ",null );
        }


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_primeViewer_to_primeFinder);
            }
        });

    }

    public static PrimeViewer newInstance(String param1, String param2) {
        PrimeViewer fragment = new PrimeViewer();

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
        return inflater.inflate(R.layout.fragment_prime_viewer, container, false);
    }
}