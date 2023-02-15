package se.gabnet.sqliteapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;


public class PrimeFinder extends Fragment {

    public PrimeFinder() {
        // Required empty public constructor
    }

    Context context;
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    private Button show, find;
    private SQLHelper helper;
    private SQLiteDatabase dbWrite;
    private SQLiteDatabase dbRead;
    private TextView prime;


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        find = view.findViewById(R.id.find_button);
        show = view.findViewById(R.id.show_button);
        helper = new SQLHelper(context);
        dbWrite = helper.getWritableDatabase();
        dbRead = helper.getReadableDatabase();
        prime = view.findViewById(R.id.biggest_prime_text_view);

        String[] projection = {PrimeDBContract.PrimeDBEntry._ID};
        String sortOrder =
                PrimeDBContract.PrimeDBEntry._ID + " ASC";

        Cursor cursor = dbRead.query(
                PrimeDBContract.PrimeDBEntry.TABLE_NAME,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                null,              // The columns for the WHERE clause
                null,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                sortOrder               // The sort order
        );

        List<Long> itemIds = new ArrayList<>();
        while(cursor.moveToNext()) {
            long itemId = cursor.getLong(cursor.getColumnIndexOrThrow(PrimeDBContract.PrimeDBEntry._ID));
            itemIds.add(itemId);

        }
        System.out.println("ITEM_ID:" + itemIds);
        cursor.close();
        if (itemIds.size() > 0) {
            biggestPrime = itemIds.remove(itemIds.size() - 1);
        } else {
            biggestPrime = 2;
        }

        if (itemIds.isEmpty()) {
            ContentValues values = new ContentValues();
            values.put(PrimeDBContract.PrimeDBEntry._ID, 2);
            values.put(PrimeDBContract.PrimeDBEntry.DATE_TIME, LocalDateTime.now().toString());
            try {
                long newRowId = dbWrite.insert(PrimeDBContract.PrimeDBEntry.TABLE_NAME, null, values);
                biggestPrime = 2;
            } catch (SQLiteConstraintException e) {
                Log.i("DefaultVal", "Already added default value 2 to database, skipping");
            }
        }


        prime.setText(String.valueOf(biggestPrime));
        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor cursor = dbRead.query(
                        PrimeDBContract.PrimeDBEntry.TABLE_NAME,   // The table to query
                        null,             // The array of columns to return (pass null to get all)
                        null,              // The columns for the WHERE clause
                        null,          // The values for the WHERE clause
                        null,                   // don't group the rows
                        null,                   // don't filter by row groups
                        sortOrder               // The sort order
                );

                ArrayList<String> items = new ArrayList<>();

                while(cursor.moveToNext()) {
                    long itemId = cursor.getLong(cursor.getColumnIndexOrThrow(PrimeDBContract.PrimeDBEntry._ID));
                    String time = cursor.getString(cursor.getColumnIndexOrThrow(PrimeDBContract.PrimeDBEntry.DATE_TIME));
                    items.add("Prime : " +itemId +", Found at : " + time );

                }
                cursor.close();
                Bundle bundle = new Bundle();

                bundle.putStringArray("found_primes", items.toArray(new String[0]));
                Navigation.findNavController(view).navigate(R.id.action_primeFinder_to_primeViewer,bundle);
            }
        });
        find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean found = false;
                long num = biggestPrime+1;
                while(!found) {
                    found = isPrime(num);
                    if (found) {
                        biggestPrime = num;
                        ContentValues values = new ContentValues();
                        values.put(PrimeDBContract.PrimeDBEntry._ID, num);
                        values.put(PrimeDBContract.PrimeDBEntry.DATE_TIME, LocalDateTime.now().toString());
                        try {
                            long newRowId = dbWrite.insert(PrimeDBContract.PrimeDBEntry.TABLE_NAME, null, values);
                        } catch (SQLiteConstraintException e) {
                            Log.i("Value", "Already found " + num);
                        }
                        prime.setText(String.valueOf(num));
                    }
                    else num++;
                }
            }
        });
    }
    long biggestPrime;

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

    public static PrimeFinder newInstance() {
        PrimeFinder fragment = new PrimeFinder();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i("Start","Starting PrimeFinder fragment");
        return inflater.inflate(R.layout.fragment_prime_finder, container, false);
    }
}