package com.example.handler;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Random;

import io.realm.RealmResults;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private RealmResults<Person> mPersonRealmResults;
    private Context mContext;
    private static final String TAG = "Experiment";
    private int count;

    public MyAdapter(RealmResults<Person> persons, Context context){
        mPersonRealmResults = persons;
        mContext = context;
        count = 0;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_item,parent,false);
        Log.i(TAG, "onCreateViewHolder: Triggered And Also the Parent Id is " + parent.getId());
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Log.i(TAG, "onBindViewHolder: Triggered");
        Person person = mPersonRealmResults.get(position);
        holder.name.setText("Name: "+person.getName());
        holder.email.setText("Email: "+person.getEmail());
        holder.latitude.setText("Latitude: "+(person.getLatitude()));
        holder.longitude.setText("Longitude: "+(person.getLongitude()));
        holder.vehicleNo.setText("VehicleNo.: "+person.getVehicleNo());
        holder.model.setText("Vehicle Model: "+person.getModel());
        holder.arrival.setText("Arrival: "+person.getArrival());
        holder.departure.setText("Departure: "+person.getDeparture()+" ");
    }

    @Override
    public int getItemCount() {
        return mPersonRealmResults.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView name;
        private TextView email;
        private TextView latitude;
        private TextView longitude;
        private TextView vehicleNo;
        private TextView model;
        private TextView arrival;
        private TextView departure;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            count++;
            Log.i(TAG, "MyViewHolder: Number of Active ViewHolders:" + count);
            name = itemView.findViewById(R.id.name_tv);
            email = itemView.findViewById(R.id.email_tv);
            latitude = itemView.findViewById(R.id.latitude_tv);
            longitude = itemView.findViewById(R.id.longitude_tv);
            vehicleNo = itemView.findViewById(R.id.vehicleNo_tv);
            model = itemView.findViewById(R.id.model_tv);
            arrival = itemView.findViewById(R.id.arrival_tv);
            departure = itemView.findViewById(R.id.departure_tv);
        }
    }
}
