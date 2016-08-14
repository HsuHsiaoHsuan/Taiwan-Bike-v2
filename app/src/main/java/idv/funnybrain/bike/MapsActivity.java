package idv.funnybrain.bike;

import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

import idv.funnybrain.bike.data.Station;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    private static final String TAG = MapsActivity.class.getSimpleName();
    private static final boolean D = true;

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

        if (D) { Log.i(TAG, "map ready"); }
        new DataGetter().execute("");
    }

    private class DataGetter extends AsyncTask<String, Void, List<Station>> {

        @Override
        protected List<Station> doInBackground(String... strings) {
            RequestQueue queue =
                    DataDownloader.getInstance(MapsActivity.this.getApplicationContext())
                            .getRequestQueue();
            // MySingleton.getInstance(this).addToRequestQueue(stringRequest);
            String url = "http://61.216.94.105:5566/taipei";

            JsonArrayRequest jsObjRequest = new JsonArrayRequest
                    (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {

                        @Override
                        public void onResponse(JSONArray response) {
                            if (D) {
                                Log.e(TAG, response.toString());
                            }
                        }
                    }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.e(TAG, "ERROR:" + error.getMessage());
                            Toast.makeText(
                                    MapsActivity.this, "ERROR, plz try later", Toast.LENGTH_LONG)
                                    .show();
                        }
                    });
            DataDownloader.getInstance(MapsActivity.this.getApplicationContext()).addToRequestQueue(jsObjRequest);

            return null;
        }

        @Override
        protected void onPostExecute(List<Station> stations) {
            super.onPostExecute(stations);
        }
    }
}