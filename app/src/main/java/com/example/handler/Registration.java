package com.example.handler;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
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

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Registration extends AppCompatActivity {
    //private TextView tv;
    EditText name, email, phone, password,confirm;
    Button save;
    String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        //tv=findViewById(R.id.textView);
        confirm = findViewById(R.id.editText7);
        name = findViewById(R.id.editText);
        phone = findViewById(R.id.editText2);
        password = findViewById(R.id.editText3);
        email = findViewById(R.id.editText4);
        save = findViewById(R.id.button2);
    }


    public static boolean isValidPassword(final String password) {

        Pattern pattern;
        Matcher matcher;
        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[A-Za-z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,24}$";
        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);

        return matcher.matches();

    }
   /* public static boolean isValidEmail(final String email) {

        Pattern pattern;
        Matcher matcher;
        final String PASSWORD_PATTERN = "^\\w+@[a-zA-Z_]+?\\.[a-zA-Z]{2,3}$";
        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(email);

        return matcher.matches();

    }*/
    public void onPost(View view)
    {
        if(((Button)view).getText().toString().equals("VERIFY"))
        {
            try {
                if (phone.getText().length() < 10) {
                    Toast.makeText(getApplicationContext(),
                            "Phone Number Invalid...",
                            Toast.LENGTH_SHORT).show();
                } else if (!isValidPassword(password.getText().toString())) {
                    Toast.makeText(getApplicationContext(),
                            "Password Invalid...\n PASSWORD should contain at least 8 characters with 1 Upper Case, 1 Lower Case, 1 Numeric and 1 special character(@,#,$,%,^,&,+)",
                            Toast.LENGTH_SHORT).show();
                } else if (!(confirm.getText().toString().equals(password.getText().toString()))) {
                    Toast.makeText(getApplicationContext(),
                            "Check Confirm Password",
                            Toast.LENGTH_SHORT).show();
                } else if (email.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(),
                            "Enter your email address",
                            Toast.LENGTH_SHORT).show();
                } else {

                    Random random = new Random();
                    id = String.format("%06d", random.nextInt(1000000));
                    RequestQueue requestQueue = Volley.newRequestQueue(this);
                    JSONObject jsonObject = new JSONObject();
                    String url = "https://ananthous-corrosion.000webhostapp.com/mail.php";
                    try {
                        jsonObject.put("otp", id);
                        jsonObject.put("email", email.getText());
                        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject, new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {

                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                            }
                        });

                        requestQueue.add(jsonObjectRequest);
                    } catch (Exception e) {
                    }

                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("Email Verification-Enter OTP");

// Set up the input
                    final EditText input = new EditText(this);
// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
                    input.setInputType(InputType.TYPE_CLASS_NUMBER);
                    builder.setView(input);

// Set up the buttons
                    builder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String m_Text = input.getText().toString();
                            if (m_Text.equals("")) {
                                Toast.makeText(getApplicationContext(), "You did not enter OTP", Toast.LENGTH_SHORT).show();
                            } else if (m_Text.equals(id)) {
                                save.setText("SUBMIT");

                            } else {
                                Toast.makeText(getApplicationContext(), "Incorrect OTP", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(Registration.this, Registration.class);
                                startActivity(intent);
                                finish();
                            }
                        }
                    });

                    builder.show();
                }
            }catch (Exception e)
            {}
        }
        else if(((Button)view).getText().toString().equals("SUBMIT")) {

                final ProgressDialog progressDialog = new ProgressDialog(this);
                progressDialog.setMessage("Please wait...");
                progressDialog.show();
                String url = "https://ananthous-corrosion.000webhostapp.com/register.php";
                JSONObject postparams = new JSONObject();
                try {

                        postparams.put("userName", name.getText());
                        postparams.put("pswd", password.getText());
                        postparams.put("hEmail", email.getText());
                        postparams.put("hPhone", phone.getText());
                        postparams.put("otp", id);
                }catch (JSONException e) {
                    e.printStackTrace();
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(),"You have already registered with this Email address",Toast.LENGTH_SHORT).show();
                }
                JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                        url, postparams,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                //Success Callback
                                progressDialog.dismiss();
                                //tv.setText(response.toString());
                                try {
                                    if(response.getString("msg").contains("Try again"))
                                    {
                                        progressDialog.dismiss();
                                        Toast.makeText(getApplicationContext(),"You have already registered with this Email address",Toast.LENGTH_SHORT).show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                try {
                                    if(response.getString("msg").equals("Data Submit Successfully")) {
                                        progressDialog.dismiss();
                                        Intent intent = new Intent(Registration.this, MainActivity.class);
                                        intent.putExtra("email", email.getText().toString());
                                        startActivity(intent);
                                    }
                                } catch (JSONException e) {
                                    progressDialog.dismiss();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                //Failure Callback
                                progressDialog.dismiss();
                                //tv.setText(error.getMessage());
                            }
                        });
    // Adding the request to the queue along with a unique string tag
                RequestQueue requestQueue = Volley.newRequestQueue(this);
                requestQueue.add(jsonObjReq);
            }

        }
    }





