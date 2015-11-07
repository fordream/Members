package org.tourstart.members;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ActionMode;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class mapsActivity extends AppCompatActivity {

    GoogleMap mMap;
    Marker marker;
    ActionMode actionMode;

    String location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        setUpMapIfNeeded();
        init();
    }

    protected void init(){

        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {

                marker = mMap.addMarker(new MarkerOptions().position(latLng));
                location = "[" + latLng.latitude + " ; " + latLng.longitude + "]";
                actionMode = startActionMode(callback);
                actionMode.setTitle(R.string.use_location);

            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    private void setUpMapIfNeeded() {

        if (mMap == null) {
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
        }

    }

    private ActionMode.Callback callback = new ActionMode.Callback() {

        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            mode.getMenuInflater().inflate(R.menu.menu_map, menu);
            return true;
        }

        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {

            mode.finish();

            if(item.getItemId() == R.id.action_set_location){

                Intent intent = new Intent();
                intent.putExtra("location", location);
                setResult(RESULT_OK, intent);
                finish();

                return true;
            }

            return false;
        }

        public void onDestroyActionMode(ActionMode mode) {
            actionMode = null;
            marker.remove();
        }

    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.maps_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == android.R.id.home) {

            finish();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
