package com.example.handler;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Registration extends AppCompatActivity {
    private TextView tv;
    EditText name, email, phone, password,confirm;
    Button save;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        tv=findViewById(R.id.textView);
        confirm = findViewById(R.id.editText7);
        name = findViewById(R.id.editText);
        phone = findViewById(R.id.editText2);
        password = findViewById(R.id.editText3);
        email = findViewById(R.id.editText4);
        save = findViewById(R.id.button2);
    }
    public void onSubmit(View view){
        //URL of the request we are sending
        String url = "https://ananthous-corrosion.000webhostapp.com/index.php?all";
        tv.setText("function called");
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
                                str+=obj.getString("id")+":"+obj.getString("username")+":"+obj.getString("email")+"\n";

                            }
                            tv.setText(str);
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
    public static boolean isValidPassword(final String password) {

        Pattern pattern;
        Matcher matcher;
        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[A-Za-z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,24}$";
        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);

        return matcher.matches();

    }
    public static boolean isValidEmail(final String email) {

        Pattern pattern;
        Matcher matcher;
        final String PASSWORD_PATTERN = "^\\w+@[a-zA-Z_]+?\\.[a-zA-Z]{2,3}$";
        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(email);

        return matcher.matches();

    }

    public void onPost(View view)
    {
        String url = "https://ananthous-corrosion.000webhostapp.com/register.php";
        JSONObject postparams = new JSONObject();
        try {
            if(phone.getText().length()<10)
            {
                Toast.makeText(getApplicationContext(),
                        "Phone Number Invalid...",
                        Toast.LENGTH_SHORT).show();
            }
            else if(!isValidPassword(password.getText().toString()))
            {
                Toast.makeText(getApplicationContext(),
                        "Password Invalid...\n PASSWORD should contain at least 8 characters with 1 Upper Case, 1 Lower Case, 1 Numeric and 1 special character",
                        Toast.LENGTH_SHORT).show();
            }
            else if(!(confirm.getText().toString().equals(password.getText().toString()))){
                Toast.makeText(getApplicationContext(),
                        "Check Confirm Password",
                        Toast.LENGTH_SHORT).show();
            }
            else if(!isValidEmail(email.getText().toString()))
            {
                Toast.makeText(getApplicationContext(),
                        "Email Invalid...",
                        Toast.LENGTH_SHORT).show();
            }
            else {
                postparams.put("userName", name.getText());
                postparams.put("pswd", password.getText());
                postparams.put("hEmail", email.getText());
                postparams.put("hPhone", phone.getText());
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
                        tv.setText(response.toString());
                        try {
                            if(response.getString("msg").equals("Data Submit Successfully")) {
                                Intent intent = new Intent(Registration.this, otp.class);
                                intent.putExtra("email", email.getText().toString());
                                startActivity(intent);
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
                        tv.setText(error.getMessage());
                    }
                });
// Adding the request to the queue along with a unique string tag
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonObjReq);
    }
}
