package se.gabnet.recieveapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

public class SMSReceiver extends BroadcastReceiver {

    SMSReceive smsReceive;

    SMSReceiver(SMSReceive smsRecieve) {
        this.smsReceive = smsRecieve;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        System.out.println("BUNDLE: " + bundle);
        try {
            if (bundle != null) {
                Object[] pdus = (Object[]) bundle.get("pdus");
                for (Object o : pdus) {
                    String format = bundle.getString("format");
                    SmsMessage currentMessage = SmsMessage.createFromPdu((byte[]) o, format);

                    String phoneNumber = currentMessage.getDisplayOriginatingAddress();
                    String senderNum = phoneNumber;

                    String message = currentMessage.getDisplayMessageBody();

                    Log.i("SmsReceiver", "senderNum: " + senderNum + "; message: " + message);
                    smsReceive.recievedSms( senderNum + " : " + message);
                }

            }

        } catch (Exception e) {

        }

    }
}
