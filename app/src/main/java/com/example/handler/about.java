package com.example.handler;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class about extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        TextView tv =findViewById(R.id.textView);

        String str ="Rules:\n\n1. The car cannot be kept parked beyond the hours for which the spot was booked by the customer, as there might be other customers waiting. Necessary measures will have to be taken on violation of this.\n" +
                "2. Verify the identity cards properly and ensure the identity of the specific customer before letting him/her into the parking spot.\n" +
                "3. The payment/monetary transactions should be handled carefully and with proper documents.";
        tv.setText(str);
    }
}
