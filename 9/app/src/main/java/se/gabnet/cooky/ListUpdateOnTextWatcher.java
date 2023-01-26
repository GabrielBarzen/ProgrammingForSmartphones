package se.gabnet.cooky;

import android.text.Editable;
import android.text.TextWatcher;

import java.sql.SQLOutput;
import java.util.List;

public abstract class ListUpdateOnTextWatcher implements TextWatcher {


    public ListUpdateOnTextWatcher () {

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    abstract public void afterTextChanged(Editable s);
}
