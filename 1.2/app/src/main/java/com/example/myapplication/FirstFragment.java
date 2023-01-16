package com.example.myapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.fragment.NavHostFragment;

import com.example.myapplication.databinding.FragmentFirstBinding;

public class FirstFragment extends Fragment {

    private FragmentFirstBinding binding;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        View fragmentFirstLayout = inflater.inflate(R.layout.fragment_first,container,false);
        showCountTextView = fragmentFirstLayout.findViewById(R.id.textview_first);

        return fragmentFirstLayout;

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.toast_button).setOnClickListener(v -> {
            Toast myToast = Toast.makeText(getActivity(), R.string.toast_button_message_text, Toast.LENGTH_SHORT);
            myToast.show();
        });
        view.findViewById(R.id.count_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countMe(v);
            }
        });

        view.findViewById(R.id.random_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int currentCount;
                if (showCountTextView.getText().toString().endsWith("Hello world!")) {
                    currentCount = 1;
                } else {
                    currentCount = Integer.parseInt(showCountTextView.getText().toString());
                }
                NavDirections action = (NavDirections) FirstFragmentDirections.actionFirstFragmentToSecondFragment(currentCount);
                NavHostFragment.findNavController(FirstFragment.this).navigate(action);
            }
        });



    }

    private void countMe(View v) {
        String countStr = showCountTextView.getText().toString();
        if (countStr.equals("Hello world!")) countStr = "0";
        Integer count = Integer.parseInt(countStr);
        count++;
        showCountTextView.setText(count.toString());
    }

    TextView showCountTextView;

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}