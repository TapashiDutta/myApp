package com.example.handler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import io.realm.Realm;
import io.realm.RealmResults;

public class LiveBookings extends AppCompatActivity {

    //TextView tv1;
    RecyclerView mRecyclerView;
    Realm realm;
    int id;
    JSONObject obj;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_bookings);

        Bundle b = getIntent().getExtras();
        String pswd=b.getString("pswd");

        realm = Realm.getDefaultInstance();


        try {
            realm = Realm.getDefaultInstance();
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    RealmResults<Person> result = realm.where(Person.class).findAll();
                    result.deleteAllFromRealm();
                }
            });

        }
        catch(Exception e) {}

        String url = "https://ananthous-corrosion.000webhostapp.com/bookingdetails.php?pswd="+pswd;
        //tv1.setText("function called");
        JsonArrayRequest jsonObjReq = new JsonArrayRequest(Request.Method.GET,
                url, null,
                new Response.Listener<JSONArray>(){
                    @Override
                    public void onResponse(JSONArray response) {
                        //Success Callback
                        obj=null;
                        String str="";
                        try {
                            for(int i =0; i<response.length();i++) {
                                obj = response.getJSONObject(i);

                                id=Integer.parseInt(obj.getString("uId"));

                                realm.executeTransaction(new Realm.Transaction() {
                                    @Override
                                    public void execute(Realm realm) {
                                        Person db = realm.where(Person.class).equalTo("id", id).findFirst();
                                        if(db == null) {
                                            db = realm.createObject(Person.class, id);
                                        }
                                        try {
                                            db.setName(obj.getString("cName"));
                                            db.setEmail(obj.getString("cEmail"));
                                            db.setLatitude(obj.getString("cLat"));
                                            db.setLongitude(obj.getString("cLong"));
                                            db.setVehicleNo(obj.getString("number"));
                                            db.setModel(obj.getString("model"));
                                            db.setArrival(obj.getString("arr"));
                                            db.setDeparture(obj.getString("dep"));
                                        }catch (Exception e) {}
                                    }
                                });


                            }
                            realm.close();
                            mRecyclerView = findViewById(R.id.personRecycler);
                            Realm realm = Realm.getDefaultInstance();
                            RealmResults<Person> personRealmResults = realm.where(Person.class).findAll();
                            MyAdapter myAdapter = new MyAdapter(personRealmResults,getApplicationContext());
                            mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                            mRecyclerView.setAdapter(myAdapter);
                            //tv1.setText(str);
                        } catch(Exception e){}

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //Failure Callback
                        //tv1.setText(error.getMessage() );
                    }
                });
// Adding the request to the queue along with a unique string tag
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonObjReq);


    }
}
