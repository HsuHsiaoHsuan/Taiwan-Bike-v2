package idv.funnybrain.bike;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import idv.funnybrain.bike.data.CITY;
import idv.funnybrain.bike.data.City;
import idv.funnybrain.bike.data.Station;
import idv.funnybrain.bike.event.DataDownloadedEvent;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    private static final String TAG = MapsActivity.class.getSimpleName();
    private static final boolean D = false;

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

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
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

    private class DataGetter extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... strings) {
            RequestQueue queue =
                    DataDownloader.getInstance(MapsActivity.this.getApplicationContext())
                            .getRequestQueue();

            StringRequest stringRequest = new StringRequest(Request.Method.GET, City.TAIPEI,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if (D) {
                                Log.e(TAG, response.toString());
                            }

                            try {
                                JsonFactory factory = new JsonFactory();
                                JsonParser parser = factory.createParser(response.toString());
                                ObjectMapper mapper = new ObjectMapper();
                                mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
                                mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

                                Station[] temp = mapper.readValue(parser, Station[].class);
                                if (D) {
                                    for (int x = 0; x < temp.length; x++) {
                                        Log.e(TAG, temp[x].getName() + "");
                                    }
                                }
                                EventBus.getDefault().post(new DataDownloadedEvent(temp));
                            } catch (JsonParseException e) {
                                Log.e(TAG, e.getMessage());
                                e.printStackTrace();
                            } catch (JsonMappingException e) {
                                Log.e(TAG, e.getMessage());
                                e.printStackTrace();
                            } catch (IOException e) {
                                Log.e(TAG, e.getMessage());
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // Handle error
                            Toast.makeText(MapsActivity.this, "Please refresh data manually.",
                                    Toast.LENGTH_LONG).show();
                        }
                    });

            queue.add(stringRequest);

            return null;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onDataDownloadedEvent(DataDownloadedEvent event) {
        mMap.clear();
        for (Station s : event.getData()) {
            Log.e(TAG, s.getName());


            mMap.addMarker(new MarkerOptions()
                    .position(new LatLng(s.getLat(), s.getLon()))
                    .title(s.getName()));
            mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker marker) {

                    return false;
                }
            });
        }
    }
}