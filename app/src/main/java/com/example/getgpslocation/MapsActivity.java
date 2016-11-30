package com.example.getgpslocation;

import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.multidex.MultiDex;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.getgpslocation.Constant.Constant;
import com.example.getgpslocation.Constant.SessionManager;
import com.example.getgpslocation.helper.WebServicesFunctions;
import com.example.getgpslocation.models.StoresLocation;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.example.getgpslocation.R.id.map;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, ResultCallback<LocationSettingsResult> {
    //public class MapsActivity extends FragmentActivity{
    private GoogleMap mMap;
    double latitude, longitude;
    Button hybrid, satelite, normal, terrian;
    ArrayList<String> longlatlist;
    protected GoogleApiClient mGoogleApiClient;
    protected LocationRequest locationRequest;
    int REQUEST_CHECK_SETTINGS = 100;
    ArrayList<StoresLocation> storeLocattion;

    Button btnShowLocation;
    GPSTracker gps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        storeLocattion = new ArrayList<StoresLocation>();
//edit marwa
        gps = new GPSTracker(MapsActivity.this, MapsActivity.this);

        if (gps.canGetLocation()) {
            latitude = gps.getLatitude();
            longitude = gps.getLongitude();

            Toast.makeText(
                    getApplicationContext(),
                    "Your Location is -\nLat: " + latitude + "\nLong: "
                            + longitude, Toast.LENGTH_LONG).show();


            // Obtain the SupportMapFragment and get notified when the map is ready to be used.
            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(map);
            mapFragment.getMapAsync(this);
            // Receiving latitude from MainActivity screen

        } else {
            //gps.showSettingsAlert();

            //for dialog
            mGoogleApiClient = new GoogleApiClient.Builder(MapsActivity.this)
                    .addApi(LocationServices.API)
                    .addConnectionCallbacks(MapsActivity.this)
                    .addOnConnectionFailedListener(MapsActivity.this).build();
            mGoogleApiClient.connect();

            locationRequest = LocationRequest.create();
            locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
            locationRequest.setInterval(30 * 1000);
            locationRequest.setFastestInterval(5 * 1000);
        }
        getLatLong();


    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
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
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(latitude, longitude);

        CameraPosition cameraposition = new CameraPosition.Builder().target(sydney).zoom(16).tilt(65).build();

        mMap.addMarker(new MarkerOptions().position(sydney).title("my current Location").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)));
        //  mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraposition));


    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {


        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest);
        builder.setAlwaysShow(true);
        PendingResult<LocationSettingsResult> result =
                LocationServices.SettingsApi.checkLocationSettings(
                        mGoogleApiClient,
                        builder.build()
                );

        result.setResultCallback(this);

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onResult(@NonNull LocationSettingsResult locationSettingsResult) {
        final Status status = locationSettingsResult.getStatus();
        switch (status.getStatusCode()) {
            case LocationSettingsStatusCodes.SUCCESS:

                // NO need to show the dialog;

                break;

            case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                //  Location settings are not satisfied. Show the user a dialog

                try {
                    // Show the dialog by calling startResolutionForResult(), and check the result
                    // in onActivityResult().

                    status.startResolutionForResult(MapsActivity.this, REQUEST_CHECK_SETTINGS);

                } catch (IntentSender.SendIntentException e) {

                    //failed to show
                }
                break;

            case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                // Location settings are unavailable so not possible to show any dialog now
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CHECK_SETTINGS) {

            if (resultCode == RESULT_OK) {

                Toast.makeText(getApplicationContext(), "GPS enabled", Toast.LENGTH_LONG).show();
            } else {

                Toast.makeText(getApplicationContext(), "GPS is not enabled", Toast.LENGTH_LONG).show();
            }

        }


    }




    private void getLatLong() {

        String url = Constant.BASE_URL + Constant.LOCATION;
        JSONObject jsonObject = new JSONObject();

        try {

            String token = new SessionManager().UserTokenPref(MapsActivity.this, "user_token_pref").toString();
            int id = new SessionManager().UserIdPref(MapsActivity.this, "user_id_pref");

            jsonObject.put("userId", id);
            jsonObject.put("token", token);
            jsonObject.put("lang", "en");
            jsonObject.put("long", longitude);
            Log.d("longmarwa", longitude + "");
            jsonObject.put("lat", latitude);
            Log.e("latmarwa", latitude + "");


            Log.e("request", jsonObject.toString() + url);
//            final ProgressDialog progressDialog = ProgressDialog.show(VisitedStores.this, "", "Loading");
            new WebServicesFunctions(MapsActivity.this).sendPostJsonObjectRequestToWs(url, jsonObject, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Log.e("responsemarwa ", response.toString());
                    //    progressDialog.cancel();


                    try {
                        int status = response.getInt("status");
                        if (status == 1) {
                            JSONArray data = response.getJSONArray("data");

                            for (int i = 0; i < data.length(); i++) {
                                JSONObject jresponse = data.getJSONObject(i);
                                StoresLocation storesLocation = new Gson().fromJson(jresponse.toString(), StoresLocation.class);
                                MapsActivity.this.storeLocattion.add(storesLocation);
                            }

                            for (int i = 0; i < storeLocattion.size(); i++) {
                                Log.e("your lat", storeLocattion.get(i).getLat()+" " + i);
                                Log.e("your long", storeLocattion.get(i).getLoge()+" " + i);
                                if(storeLocattion.get(i).getLat().length()> 0 && storeLocattion.get(i).getLoge().length()> 0){

                                    double  latt = Double.parseDouble(storeLocattion.get(i).getLat());
                                    double longg = Double.parseDouble(storeLocattion.get(i).getLoge());
                                    LatLng sydney3 = new LatLng(latt, longg);
                                    mMap.addMarker(new MarkerOptions().position(sydney3).title(storeLocattion.get(i).getTitle()));
                                }

                            }


                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    WebServicesFunctions.printError(error);
                    Log.e("error", "error");

                }
            });


        } catch (JSONException e) {
            Log.e("json ex", e.getMessage());
            e.printStackTrace();
        }


    }

}
