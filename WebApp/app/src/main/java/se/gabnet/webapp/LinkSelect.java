package se.gabnet.webapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.fragment.NavHostFragment;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicInteger;


import se.gabnet.webapp.databinding.LinkSelectorBinding;

public class LinkSelect extends Fragment {

    private LinkSelectorBinding binding;

    LinkedList<String> links = new LinkedList<>();


    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = LinkSelectorBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    Button link1,link2,link3,link4,link5;

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        link1 = (view.findViewById(R.id.link1));
        link2 = (view.findViewById(R.id.link2));
        link3 = (view.findViewById(R.id.link3));
        link4 = (view.findViewById(R.id.link4));
        link5 = (view.findViewById(R.id.link5));

        link1.setOnClickListener(v -> {
            String url = "https://www.wikipedia.org/";
            NavDirections navDirections = LinkSelectDirections.actionLinkSelect(url);
            NavHostFragment.findNavController(LinkSelect.this).navigate(navDirections);
        });
        link2.setOnClickListener(v -> {
            String url = "https://www.reddit.com/";
            NavDirections navDirections = LinkSelectDirections.actionLinkSelect(url);
            NavHostFragment.findNavController(LinkSelect.this).navigate(navDirections);
        });
        link3.setOnClickListener(v -> {
            String url = "https://stackoverflow.com/";
            NavDirections navDirections = LinkSelectDirections.actionLinkSelect(url);
            NavHostFragment.findNavController(LinkSelect.this).navigate(navDirections);
        });
        link4.setOnClickListener(v -> {
            String url = "https://www.svt.se/";
            NavDirections navDirections = LinkSelectDirections.actionLinkSelect(url);
            NavHostFragment.findNavController(LinkSelect.this).navigate(navDirections);
        });
        link5.setOnClickListener(v -> {
            String url = "https://www.bbc.com/news";
            NavDirections navDirections = LinkSelectDirections.actionLinkSelect(url);
            NavHostFragment.findNavController(LinkSelect.this).navigate(navDirections);
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}