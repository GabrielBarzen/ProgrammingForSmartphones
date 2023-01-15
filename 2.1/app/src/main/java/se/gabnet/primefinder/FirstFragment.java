package se.gabnet.primefinder;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import se.gabnet.primefinder.databinding.FragmentFirstBinding;

public class FirstFragment extends Fragment {

    private FragmentFirstBinding binding;
    private String filename = "primeFile";
    private File primeFile;
    private Context context;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        context = getActivity();
        binding = FragmentFirstBinding.inflate(inflater, container, false);

        try {
            primeFile = new File(context.getFilesDir(), filename);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        try {
            BufferedReader br = new BufferedReader(new FileReader(primeFile));
            System.out.println("file : " + primeFile);
            String contentIN = br.readLine();
            System.out.println("content in : " + contentIN);
            if (contentIN != null) {
                String[] content = contentIN.split(":");
                if (content.length > 1) {
                    biggestPrime = Long.parseLong(content[1]);
                    testNumber = biggestPrime+1;
                    System.out.println("set biggest prime to : " + biggestPrime);
                } else {
                    System.out.println("no biggest prime");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return binding.getRoot();

    }

    long biggestPrime = 2;
    long testNumber = 3;
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView textViewCurrentBiggest = view.findViewById(R.id.textview_first);
        TextView textViewCurrentTesting = view.findViewById(R.id.textview_testing_bigger);
        {

            String text = textViewCurrentBiggest.getText().toString();
            String[] split = text.split("([ !])");
            split[split.length - 1] = biggestPrime + "!";
            StringBuilder builder = new StringBuilder();
            for (String s : split) {
                builder.append(s).append(" ");
            }
            textViewCurrentBiggest.setText(builder.toString());
        }
        {
            String text = textViewCurrentTesting.getText().toString();
            String[] split = text.split("([ !])");
            split[split.length - 1] = "";
            StringBuilder builder = new StringBuilder();
            for (String s : split) {
                builder.append(s).append(" ");
            }
            textViewCurrentTesting.setText(builder.toString());
        }
        binding.buttonFirst.setOnClickListener(view1 -> {
            boolean found = false;
            textViewCurrentTesting.append(",");
            while(!found) {

                String append = testNumber + ",";
                textViewCurrentTesting.append(append);

                found = isPrime(testNumber);

                if (found) biggestPrime = testNumber;
                else testNumber++;
            }
            {
                String text = textViewCurrentBiggest.getText().toString();
                String[] split = text.split("([ !])");
                split[split.length - 1] = biggestPrime + "!";
                StringBuilder builder = new StringBuilder();
                for (String s : split) {
                    builder.append(s).append(" ");
                }
                textViewCurrentBiggest.setText(builder.toString());
            }

            {
                String text = textViewCurrentTesting.getText().toString();
                String[] split = text.split("([ !])");
                split[split.length - 1] = String.valueOf(testNumber);
                StringBuilder builder = new StringBuilder();
                for (String s : split) {
                    builder.append(s).append(" ");
                }
                textViewCurrentTesting.setText(builder.toString());
            }
            testNumber++;
            try {
                FileWriter fw = new FileWriter(primeFile);
                System.out.println("writing, " + "biggest-prime:"+biggestPrime);
                fw.write("biggest-prime:"+biggestPrime);
                fw.flush();
                fw.close();
                BufferedReader br = new BufferedReader(new FileReader(primeFile));
                System.out.println("file : " + primeFile);
                String contentIN = br.readLine();

                System.out.println("content in : " + contentIN);


            } catch (IOException e) {
                e.printStackTrace();
            }
        });


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private boolean isPrime(long candidate) {
        long num = candidate;
        boolean flag = false;
        for (int i = 2; i <= num / 2; ++i) {

            if (num % i == 0) {
                return false;

            }
        }
        return true;
    }


}