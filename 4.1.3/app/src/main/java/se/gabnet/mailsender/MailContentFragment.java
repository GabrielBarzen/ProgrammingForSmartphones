package se.gabnet.mailsender;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.io.File;


public class MailContentFragment extends Fragment {

    Context context;
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    Button send, attach;
    EditText email,subject,body;


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        send = view.findViewById(R.id.send_button);
        attach = view.findViewById(R.id.attach_button);
        email = view.findViewById(R.id.email_addres_to);
        subject = view.findViewById(R.id.subject_line_text);
        body = view.findViewById(R.id.text_body);

        send.setOnClickListener(v -> {
            String[] addresses = {email.getText().toString()};
            String subjectText = subject.getText().toString();
            String bodyText = body.getText().toString();

            Intent sendMail = new Intent(Intent.ACTION_SEND);
            sendMail.setData(Uri.parse("mailto:"));
            sendMail.setType("message/rfc822");
            sendMail.putExtra(Intent.EXTRA_EMAIL, addresses);
            sendMail.putExtra(Intent.EXTRA_SUBJECT, subjectText);
            sendMail.putExtra(Intent.EXTRA_TEXT, bodyText);
            if (fileAttachment !=null) {
                sendMail.putExtra(Intent.EXTRA_STREAM, fileAttachment);
            }
            startActivity(Intent.createChooser(sendMail,"Send via"));

        });

        attach.setOnClickListener(v -> {
            attachmentChooser.launch("*/*");
        });

    }

    Uri fileAttachment = null;
    ActivityResultLauncher<String> attachmentChooser;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        attachmentChooser =
                registerForActivityResult(
                        new ActivityResultContracts.GetContent(),
                        uri -> {
                            if( uri == null )
                                return;
                            fileAttachment = uri;
                        });
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_mail_content, container, false);
    }
}