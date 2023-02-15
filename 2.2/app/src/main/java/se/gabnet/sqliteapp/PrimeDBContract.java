package se.gabnet.sqliteapp;

import android.provider.BaseColumns;

public final class PrimeDBContract {
    private PrimeDBContract() {}

    /* Inner class that defines the table contents */
    public static class PrimeDBEntry implements BaseColumns {
        public static final String TABLE_NAME = "Primes";
        public static final String DATE_TIME = "datetime";
    }

}
