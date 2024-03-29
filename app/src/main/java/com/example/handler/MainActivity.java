package com.example.handler;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends Activity {

    Button login,b2,b3;
    EditText ed1,ed2;

    //TextView tx1;
    String url;
    static protected int a;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        login = findViewById(R.id.button);
        ed1 = findViewById(R.id.editText);
        ed2 = findViewById(R.id.editText2);

        b2 = findViewById(R.id.button2);
        //tx1 = findViewById(R.id.textView);
        //tx2 = findViewById(R.id.textView6);

        b3 = findViewById(R.id.button3);
        a=0;

    }
    public void onLogin(View v) {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Checking Credentials...");
        progressDialog.show();
        if (ed1.getText().toString().isEmpty() || ed2.getText().toString().isEmpty()) {
            progressDialog.dismiss();
            Toast.makeText(getApplicationContext(), "Enter Username & Password", Toast.LENGTH_SHORT).show();
        } else {
            url = "https://ananthous-corrosion.000webhostapp.com/verify2.php?userName=" + ed1.getText() + "&pswd=" + ed2.getText();
            JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                    url, null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            //Success Callback
                            JSONObject obj = null;
                            try {
                                if (response.getString("msg").equals("OK")) {
                                    progressDialog.dismiss();
                                    //tx1.setText("VERIFIED");
                                    Toast.makeText(getApplicationContext(),
                                            "Redirecting...", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(MainActivity.this, Check.class);
                                    intent.putExtra("pswd", ed2.getText().toString());
                                    startActivity(intent);
                                } else if (response.getString("msg").equals("ERROR")) {
                                    progressDialog.dismiss();
                                    Toast.makeText(getApplicationContext(), "You need to Reqister!!", Toast.LENGTH_SHORT).show();
                                } else {
                                    progressDialog.dismiss();
                                    Toast.makeText(getApplicationContext(), "You need to Reqister!!", Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            //Failure Callback
                            //tx1.setText(error.getMessage());
                        }
                    });
// Adding the request to the queue along with a unique string tag
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(jsonObjReq);

        }
    }
    public void onReg(View view)
    {

        Intent intent = new Intent(MainActivity.this,Registration.class);
        startActivity(intent);
    }
}