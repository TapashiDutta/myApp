package com.example.handler;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class otp extends AppCompatActivity {

    EditText email,otp;
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);
        email= findViewById(R.id.editText1);
        tv = findViewById(R.id.textView);
        otp = findViewById(R.id.editText2);
        Bundle b = getIntent().getExtras();
        email.setText(b.getString("email"));
    }
    public void onVerify(View view)
    {
        String url = "https://ananthous-corrosion.000webhostapp.com/verify.php?hEmail="+email.getText()+"&otp="+otp.getText();
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                url, null,
                new Response.Listener<JSONObject>(){
                    @Override
                    public void onResponse(JSONObject response) {
                        //Success Callback
                        JSONObject obj=null;
                        try {
                            if(response.getString("msg").equals("OK"))
                            {
                                tv.setText("OTP VERIFIED");
                                Intent intent = new Intent(otp.this,MainActivity.class);
                                startActivity(intent);
                            }
                            else if(response.getString("msg").equals("ERROR")){
                                tv.setText("OTP ERROR");
                            }else{
                                tv.setText(response.getString("msg"));
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
                        tv.setText(error.getMessage() );
                    }
                });
// Adding the request to the queue along with a unique string tag
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonObjReq);
    }
}
