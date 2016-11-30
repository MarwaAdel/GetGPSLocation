package com.example.getgpslocation.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.getgpslocation.GPSTracker;
import com.example.getgpslocation.R;
import com.example.getgpslocation.models.StoresLocation;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.GoogleMap;

import java.util.ArrayList;


//public class FragmentMap extends Fragment   implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, ResultCallback<LocationSettingsResult> {
public class FragmentMap extends Fragment{

    private GoogleMap mMap;
    double latitude, longitude;
    Button hybrid, satelite, normal, terrian;
    ArrayList<String> longlatlist;
    protected GoogleApiClient mGoogleApiClient;
    protected LocationRequest locationRequest;
    int REQUEST_CHECK_SETTINGS = 100;
    public static ArrayList<StoresLocation> storeLocattion;

    Button btnShowLocation;
    GPSTracker gps;

    public static FragmentMap newInstance() {
        FragmentMap fragment = new FragmentMap();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public FragmentMap() {
        /* Required empty public constructor*/
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView= inflater.inflate(R.layout.activity_maps, container, false);
//        SupportMapFragment mapFragment = (SupportMapFragment) getFragmentManager()
//                   .findFragmentById(map);
//        mapFragment.getMapAsync(this);
//            mapFragment.getMapAsync(this);
//                storeLocattion = new ArrayList<StoresLocation>();
//
//        gps = new GPSTracker(getActivity(), getActivity());
//
//        if (gps.canGetLocation()) {
//            latitude = gps.getLatitude();
//            longitude = gps.getLongitude();
//
//            Toast.makeText(
//                    getActivity(),
//                    "Your Location is -\nLat: " + latitude + "\nLong: "
//                            + longitude, Toast.LENGTH_LONG).show();
//
//
//            // Obtain the SupportMapFragment and get notified when the map is ready to be used.
//            SupportMapFragment mapFragment = (SupportMapFragment) getFragmentManager()
//                    .findFragmentById(map);
//            mapFragment.getMapAsync(this);
//            // Receiving latitude from MainActivity screen
//
//        } else {
//            //gps.showSettingsAlert();
//
//            //for dialog
//            mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
//                    .addApi(LocationServices.API)
//                    .addConnectionCallbacks(FragmentMap.this)
//                    .addOnConnectionFailedListener(FragmentMap.this).build();
//            mGoogleApiClient.connect();
//
//            locationRequest = LocationRequest.create();
//            locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
//            locationRequest.setInterval(30 * 1000);
//            locationRequest.setFastestInterval(5 * 1000);
//        }
//        getLatLong();

        return  rootView;
    }
//    @Override
//    public void onMapReady(GoogleMap googleMap) {
//        mMap = googleMap;
//        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
//        // Add a marker in Sydney and move the camera
//        LatLng sydney = new LatLng(latitude, longitude);
//
//
//        CameraPosition cameraposition = new CameraPosition.Builder().target(sydney).zoom(16).tilt(65).build();
//
//        mMap.addMarker(new MarkerOptions().position(sydney).title("my current Location").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)));
//
//        //  mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
//        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraposition));
//    }

//    @Override
//    public void onConnected(@Nullable Bundle bundle) {
//
//
//        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
//                .addLocationRequest(locationRequest);
//        builder.setAlwaysShow(true);
//        PendingResult<LocationSettingsResult> result =
//                LocationServices.SettingsApi.checkLocationSettings(
//                        mGoogleApiClient,
//                        builder.build()
//                );
//
//        result.setResultCallback(this);
//
//    }
//
//    @Override
//    public void onConnectionSuspended(int i) {
//
//    }
//
//    @Override
//    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
//
//    }
//
//    @Override
//    public void onResult(@NonNull LocationSettingsResult locationSettingsResult) {
//        final Status status = locationSettingsResult.getStatus();
//        switch (status.getStatusCode()) {
//            case LocationSettingsStatusCodes.SUCCESS:
//
//                // NO need to show the dialog;
//
//                break;
//
//            case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
//                //  Location settings are not satisfied. Show the user a dialog
//
//                try {
//                    // Show the dialog by calling startResolutionForResult(), and check the result
//                    // in onActivityResult().
//
//                    status.startResolutionForResult(getActivity(), REQUEST_CHECK_SETTINGS);
//
//                } catch (IntentSender.SendIntentException e) {
//
//                    //failed to show
//                }
//                break;
//
//            case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
//                // Location settings are unavailable so not possible to show any dialog now
//                break;
//        }
//    }
//
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == REQUEST_CHECK_SETTINGS) {
//
//            if (resultCode == RESULT_OK) {
//
//                Toast.makeText(getActivity(), "GPS enabled", Toast.LENGTH_LONG).show();
//            } else {
//
//                Toast.makeText(getActivity(), "GPS is not enabled", Toast.LENGTH_LONG).show();
//            }
//
//        }
//
//
//    }
//
//
//
//
//    private void getLatLong() {
//
//        String url = Constant.BASE_URL + Constant.LOCATION;
//        JSONObject jsonObject = new JSONObject();
//
//        try {
//
//            String token = new SessionManager().UserTokenPref(getActivity(), "user_token_pref").toString();
//            int id = new SessionManager().UserIdPref(getActivity(), "user_id_pref");
//
//            jsonObject.put("userId", id);
//            jsonObject.put("token", token);
//            jsonObject.put("lang", "en");
//            jsonObject.put("long", longitude);
//            Log.d("longmarwa", longitude + "");
//            jsonObject.put("lat", latitude);
//            Log.e("latmarwa", latitude + "");
//
//
//            Log.e("request", jsonObject.toString() + url);
////            final ProgressDialog progressDialog = ProgressDialog.show(VisitedStores.this, "", "Loading");
//            new WebServicesFunctions(getActivity()).sendPostJsonObjectRequestToWs(url, jsonObject, new Response.Listener<JSONObject>() {
//                @Override
//                public void onResponse(JSONObject response) {
//                    Log.e("responsemarwa ", response.toString());
//                    //    progressDialog.cancel();
//
//
//                    try {
//                        int status = response.getInt("status");
//                        if (status == 1) {
//                            JSONArray data = response.getJSONArray("data");
//
//                            for (int i = 0; i < data.length(); i++) {
//                                JSONObject jresponse = data.getJSONObject(i);
//                                StoresLocation storesLocation = new Gson().fromJson(jresponse.toString(), StoresLocation.class);
//                                FragmentMap.storeLocattion.add(storesLocation);
//                            }
//
//                            for (int i = 0; i < storeLocattion.size(); i++) {
//                                Log.e("your lat", storeLocattion.get(i).getLat()+" " + i);
//                                Log.e("your long", storeLocattion.get(i).getLoge()+" " + i);
//                                if(storeLocattion.get(i).getLat().length()> 0 && storeLocattion.get(i).getLoge().length()> 0){
//
//                                    double  latt = Double.parseDouble(storeLocattion.get(i).getLat());
//                                    double longg = Double.parseDouble(storeLocattion.get(i).getLoge());
//                                    LatLng sydney3 = new LatLng(latt, longg);
//                                    mMap.addMarker(new MarkerOptions().position(sydney3).title(storeLocattion.get(i).getTitle()));
//                                }
//
//                            }
//
//
//                        }
//
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }, new Response.ErrorListener() {
//                @Override
//                public void onErrorResponse(VolleyError error) {
//                    WebServicesFunctions.printError(error);
//                    Log.e("error", "error");
//
//                }
//            });
//
//
//        } catch (JSONException e) {
//            Log.e("json ex", e.getMessage());
//            e.printStackTrace();
//        }
//
//
//    }
//
}
