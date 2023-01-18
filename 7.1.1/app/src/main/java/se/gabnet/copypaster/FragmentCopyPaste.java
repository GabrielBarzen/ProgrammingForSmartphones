package se.gabnet.copypaster;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentCopyPaste#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentCopyPaste extends Fragment {

    ClipboardManager clipboardManager;
    Context context;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    Button copy,paste;
    EditText copyText;
    TextView textView;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        clipboardManager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);

        copy = view.findViewById(R.id.copy_button);
        paste = view.findViewById(R.id.paste_button);
        copyText = view.findViewById(R.id.copy_text_input);
        textView = view.findViewById(R.id.paste_text_view);

        copy.setOnClickListener(v -> {
            ClipData clipData = ClipData.newPlainText(null, copyText.getText());
            clipboardManager.setPrimaryClip(clipData);
        });
        paste.setOnClickListener(v -> {
            try {
                CharSequence textToPaste = clipboardManager.getPrimaryClip().getItemAt(0).getText();
                textView.setText(textToPaste);
            } catch (Exception e) {
                Toast.makeText(context,"Please copy first", Toast.LENGTH_SHORT);
                return;
            }
        });
    }

    public FragmentCopyPaste() {
        // Required empty public constructor
    }

    public static FragmentCopyPaste newInstance(String param1, String param2) {
        FragmentCopyPaste fragment = new FragmentCopyPaste();
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
        return inflater.inflate(R.layout.fragment_copy_paste, container, false);
    }
}