package com.example.handler;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static android.provider.ContactsContract.CommonDataKinds.Website.URL;


public class Check extends AppCompatActivity {

    DrawerLayout dl;
    NavigationView nv;
    TextView tv;
    String pswd, h1 ,h2 ;
    ActionBarDrawerToggle toggle;
    List<String> list =new ArrayList<>();

    TextView tv1;
    EditText cId;
    Button del;
    private Bundle bundle,b;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check);
        tv1 = findViewById(R.id.textView4);
        cId = findViewById(R.id.cId);
        b = getIntent().getExtras();
        del = findViewById(R.id.button5);
        pswd = b.getString("pswd");
        if(MainActivity.a==1) {
            bundle = getIntent().getBundleExtra("uId");
            cId.setText(bundle.getString("uId"));
        }
        //Initialize views from the layout
        dl = findViewById(R.id.drawerLayout);
        nv = findViewById(R.id.navigationView);

        //ActionBarDrawerToggle is initialized to sync drawer open and closed states
        toggle = new ActionBarDrawerToggle(this, dl, R.string.open_menu, R.string.close_menu);

        dl.addDrawerListener(toggle);
        toggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //On clicking of any menu items, actions will be performed accordingly
        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch (id){
                    case R.id.account:
                        Intent intent3 = new Intent(getApplicationContext(), about.class);
                        startActivity(intent3);
                        break;
                    case R.id.profile:
                        Intent intent1 = new Intent(getApplicationContext(), SettingsActivity.class);
                        intent1.putExtra("pswd", pswd);
                        startActivity(intent1);
                        break;
                    case R.id.live:
                        Intent intent2 = new Intent(getApplicationContext(), LiveBookings.class);
                        intent2.putExtra("pswd", pswd);
                        startActivity(intent2);
                        break;
                    case R.id.qrScanner:
                        startActivity(new Intent(getApplicationContext(), Display.class));
                        break;
                    case R.id.exit:
                        finishAffinity();
                        break;
                    default:
                        return true;
                }
                return true;
            }
        });
        String url = "https://ananthous-corrosion.000webhostapp.com/handlerId.php?pswd="+pswd;
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
                                h1 = obj.getString("spot");

                            }
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if(toggle.onOptionsItemSelected(item))
            return true;

        return super.onOptionsItemSelected(item);
    }
    public void onDelete(View view)
    {

            if( !list.contains(String.valueOf(cId.getText())))
            {
                tv1.setText("First GET DETAILS for the required customer");
            }
            else
            {
                String url = "https://ananthous-corrosion.000webhostapp.com/delete.php?bId="+cId.getText();
                //tv1.setText("function delete called");
                JsonArrayRequest jsonObjReq = new JsonArrayRequest(Request.Method.GET,
                        url, null, new Response.Listener<JSONArray>(){
                    @Override
                    public void onResponse(JSONArray response) {
                        tv1.setText("Deleted successfully.");
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

    public void onDetails(View view) {
        if (cId.getText().toString().isEmpty())
            Toast.makeText(getApplicationContext(), "Scan the Customer QR Code", Toast.LENGTH_SHORT).show();
        else {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Loading Customer Details. Please Wait...");
            progressDialog.show();
            String url = "https://ananthous-corrosion.000webhostapp.com/details.php?uId=" + cId.getText();
            //tv1.setText("function called");
            JsonArrayRequest jsonObjReq = new JsonArrayRequest(Request.Method.GET,
                    url, null,
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            //Success Callback
                            progressDialog.dismiss();
                            JSONObject obj = null;
                            String str = "";
                            double diff=0;
                            try {
                                for (int i = 0; i < response.length(); i++) {
                                    obj = response.getJSONObject(i);
                                    str += "Name: " + obj.getString("cName") + " \nEmail: " + obj.getString("cEmail") + "\n";
                                    str += "Vehicle Number: " + obj.getString("number") + "\nVehicle Model: " + obj.getString("model") + "\n";
                                    str += "Latitude: " + obj.getString("cLat") + "\nLongitude: " + obj.getString("cLong");
                                    str += "\n\nArrival: " + obj.getString("arr") + "\n\nDeparture: " + obj.getString("dep");
                                    h2 = obj.getString("spot");
                                    String a1[] = obj.getString("arr").split("\\s");
                                    String a2[] = a1[1].split(":");
                                    double t1 = Double.parseDouble(a2[0])*60+Integer.parseInt(a2[1]);

                                    String b1[] = obj.getString("dep").split("\\s");
                                    String b2[] = b1[1].split(":");
                                    double t2 = Double.parseDouble(b2[0])*60+Integer.parseInt(b2[1]);

                                    diff = Math.ceil((t2-t1)/60.0)*Integer.parseInt(obj.getString("cost"));
                                }
                                if(!h1.equals(h2))
                                {
                                    tv1.setText("You are not HANDLER of this spot");
                                }
                                else {
                                    if(str.isEmpty())
                                    {
                                        tv1.setText("No such CustomerId");
                                    }
                                    else {
                                        tv1.setText(str + "\nCost: Rs." + (int) diff);
                                        String temp = String.valueOf(cId.getText());
                                        list.add(temp);
                                    }
                                }
                            } catch (Exception e) {
                            }

                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            //Failure Callback
                            progressDialog.dismiss();
                            tv1.setText(error.getMessage());
                        }
                    });
// Adding the request to the queue along with a unique string tag
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(jsonObjReq);
        }

    }
}