package com.example.handler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

public class SettingsActivity extends AppCompatActivity {

    Bundle b;
    String pswd;
    TextView tv1;
    EditText ed1,ed2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        b = getIntent().getExtras();
        pswd=b.getString("pswd");
        tv1 = findViewById(R.id.textView3);
        ed1 = findViewById(R.id.editText1);
        ed2 = findViewById(R.id.editText2);

        String url = "https://ananthous-corrosion.000webhostapp.com/details2.php?pswd="+pswd;
        tv1.setText("function called");
        JsonArrayRequest jsonObjReq = new JsonArrayRequest(Request.Method.GET,
                url, null,
                new Response.Listener<JSONArray>(){
                    @Override
                    public void onResponse(JSONArray response) {
                        //Success Callback
                        JSONObject obj=null;
                        String str="";
                        try {
                            for(int i =0; i<response.length();i++) {
                                obj = response.getJSONObject(i);
                                str+="Name: "+obj.getString("userName")+" \nEmail: "+obj.getString("hEmail")+"\n";
                                str+= "Phone Number: "+obj.getString("hPhone");
                                ed1.setText(obj.getString("userName"));
                                ed2.setText(obj.getString("hPhone"));
                            }

                            tv1.setText(str);
                        } catch(Exception e){}

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //Failure Callback
                        tv1.setText(error.getMessage() );
                    }
                });
// Adding the request to the queue along with a unique string tag
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonObjReq);
    }
    public void onUpdate(View view)
    {
        String url = "https://ananthous-corrosion.000webhostapp.com/updateProfile.php?pswd="+pswd;
        JSONObject postparams = new JSONObject();
        try {
            if(ed2.getText().length()<10)
            {
                Toast.makeText(getApplicationContext(),
                        "Phone Number Invalid...",
                        Toast.LENGTH_SHORT).show();
            }
            else {
                postparams.put("userName", ed1.getText());
                postparams.put("hPhone", ed2.getText());
            }
        }catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                url, postparams,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //Success Callback
                        String url = "https://ananthous-corrosion.000webhostapp.com/details2.php?pswd="+pswd;
                        tv1.setText("function called");
                        JsonArrayRequest jsonObjReq = new JsonArrayRequest(Request.Method.GET,
                                url, null,
                                new Response.Listener<JSONArray>(){
                                    @Override
                                    public void onResponse(JSONArray response) {
                                        //Success Callback
                                        JSONObject obj=null;
                                        String str="";
                                        try {
                                            for(int i =0; i<response.length();i++) {
                                                obj = response.getJSONObject(i);
                                                str+="Name: "+obj.getString("userName")+" \nEmail: "+obj.getString("hEmail")+"\n";
                                                str+= "Phone Number: "+obj.getString("hPhone");
                                                //ed1.setText(obj.getString("userName"));
                                                //ed2.setText(obj.getString("hPhone"));
                                            }

                                            tv1.setText(str);
                                        } catch(Exception e){}

                                    }
                                },
                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        //Failure Callback
                                        tv1.setText(error.getMessage() );
                                    }
                                });
// Adding the request to the queue along with a unique string tag
                        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                        requestQueue.add(jsonObjReq);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //Failure Callback
                        tv1.setText(error.getMessage());
                    }
                });
// Adding the request to the queue along with a unique string tag
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonObjReq);
    }

    }
