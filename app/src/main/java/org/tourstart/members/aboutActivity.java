package org.tourstart.members;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class aboutActivity extends AppCompatActivity {

    TextView name;
    TextView birthDay;
    TextView gender;

    SupportMapFragment mapFragment;
    GoogleMap map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        name = (TextView) findViewById(R.id.aboutName);
        birthDay = (TextView) findViewById(R.id.aboutBirthDay);
        gender = (TextView) findViewById(R.id.aboutGender);

        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.aboutMap);
        map = mapFragment.getMap();

        if (map == null) {
            finish();
            return;
        }

        Intent intent = getIntent();

        name.setText(intent.getCharSequenceExtra("firstName") + " " + intent.getCharSequenceExtra("lastName"));
        birthDay.setText(intent.getCharSequenceExtra("birthDay"));
        gender.setText(intent.getCharSequenceExtra("gender"));

        String address = intent.getStringExtra("address");
        String location = intent.getStringExtra("location");

        SetLocation(location, address);
    }

    private void SetLocation(String pos, String address){

        String[] coordinate = pos.split(";");

        String lat = coordinate[0].substring(1, coordinate[0].length() - 1);
        String lon = coordinate[1].substring(1, coordinate[1].length() - 1);
        LatLng latlng = new LatLng(Double.parseDouble(lat), Double.parseDouble(lon));

        CameraPosition cameraPosition = new CameraPosition.Builder().target(latlng).zoom(14).build();
        CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition);

        Marker marker = map.addMarker(new MarkerOptions().position(latlng).title(address));
        marker.showInfoWindow();

        map.animateCamera(cameraUpdate);
    }
}
