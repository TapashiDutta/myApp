package com.example.handler;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONObject;

public class Display extends AppCompatActivity {
    private IntentIntegrator qrscan;
    private Intent intent;
    private Bundle bundle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);
        //qrscan=new IntentIntegrator(this);
        intent=new Intent(this,Check.class);
        //bundle=new Bundle();

        //qrscan.initiateScan();

    }

    /*@Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        IntentResult intentResult=IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
        if(intentResult==null)
        super.onActivityResult(requestCode, resultCode, data);
        else
        {
            if(intentResult.getContents()!=null)
            {
                try {
                    JSONObject jsonObject = new JSONObject(intentResult.getContents());
                    bundle.putString("uId",jsonObject.getString("uId"));
                    intent.putExtra("uId",bundle);
                    startActivity(intent);


                }
                catch (Exception e)
                {

                }
            }
        }
    }*/

    public void onScan(View view)
    {

        Intent intent = new Intent(Display.this,Check.class);
        startActivity(intent);
    }
}