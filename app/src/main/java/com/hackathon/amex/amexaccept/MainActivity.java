package com.hackathon.amex.amexaccept;

import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.AutocompletePrediction;
import com.google.android.gms.location.places.AutocompletePredictionBuffer;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;


public class MainActivity extends ActionBarActivity implements ConnectionCallbacks, OnConnectionFailedListener, OnMapReadyCallback {

    private GoogleApiClient mGoogleApiClient;
    private static final int REQUEST_PLACE_PICKER = 1;
    private TextView mViewName;
    private TextView mViewAddress;
    private TextView mViewAttributions;
    private AutoCompleteTextView searchBar;
    Location mLastLocation;
    GoogleMap mGoogleMap;
    private Marker currentMarker;

    public void onPickButtonClick(View v) {
        // Construct an intent for the place picker
        try {
            PlacePicker.IntentBuilder intentBuilder = new PlacePicker.IntentBuilder();
            Intent intent = intentBuilder.build(this);
            // Start the intent by requesting a result,
            // identified by a request code.
            startActivityForResult(intent, REQUEST_PLACE_PICKER);

        } catch (GooglePlayServicesRepairableException e) {
            // ...
            e.printStackTrace();
        } catch (GooglePlayServicesNotAvailableException e) {
            // ...
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void onMainNextButtonClick(View v) {
        // send or display a notification
        //android.telephony.SmsManager smsMgr = android.telephony.SmsManager.getDefault();
        //smsMgr.sendTextMessage();
        Intent i = new Intent(this, Page_2.class);
        startActivity(i);
    }


    @Override
    protected void onActivityResult(int requestCode,
                                    int resultCode, Intent data) {

        if (requestCode == REQUEST_PLACE_PICKER
                && resultCode == Activity.RESULT_OK) {

            // The user has selected a place. Extract the name and address.
            final Place place = PlacePicker.getPlace(data, this);

            final CharSequence name = place.getName();
            final CharSequence address = place.getAddress();
            String attributions = PlacePicker.getAttributions(data);
            if (attributions == null) {
                attributions = "";
            }

            String test = name.toString();
            Toast.makeText(this, test, Toast.LENGTH_LONG).show();

            updateTextBoxes(name, address);
            //mViewName.setText(name);
            //mViewAddress.setText(address);
            mViewAttributions.setText(Html.fromHtml(attributions));

        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    public void updateTextBoxes(CharSequence name, CharSequence address) {
        mViewName.setText(name);
        mViewAddress.setText(address);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mViewName = (TextView) findViewById(R.id.mViewName);
        mViewAddress = (TextView) findViewById(R.id.mViewAddress);
        mViewAttributions = (TextView) findViewById(R.id.mViewAttributions);

        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        buildGoogleApiClient();

        registerSearchBar();
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onConnected(Bundle bundle) {
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);
        if (mLastLocation != null) {
            updateMap(mLastLocation.getLatitude(), mLastLocation.getLongitude());
            addMarker(mLastLocation.getLatitude(), mLastLocation.getLongitude(), "Current Location");
        }
    }

    public void updateMap(double lat, double longitude) {
        LatLng currentPositionLatLong = new LatLng(lat, longitude);
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(currentPositionLatLong).zoom(16).build();
        mGoogleMap.animateCamera(CameraUpdateFactory
                .newCameraPosition(cameraPosition));
    }

    public void addMarker(double lat, double longitude, String markerName) {
        LatLng currentPositionLatLong = new LatLng(lat, longitude);

        if (currentMarker != null) {
            currentMarker.remove();
        }

        currentMarker = mGoogleMap.addMarker(new MarkerOptions().position(currentPositionLatLong)
                .title(markerName));
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mGoogleMap = googleMap;

//        googleMap.addMarker(new MarkerOptions().position(new LatLng(50.8623559, -0.0841516))
//                .title("Amex Community Stadium"));

//        LatLng brighton = new LatLng(60.8623559, -0.0841516);
//        googleMap.moveCamera(CameraUpdateFactory.newLatLng(brighton));

        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(new LatLng(50.8623559, -0.0841516)).zoom(14).build();
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

    }

    public void registerSearchBar() {
        searchBar = (AutoCompleteTextView) findViewById(R.id.search_bar);
//        final Place[] searchSuggestions = new Place[] {};
//        final ArrayAdapter<Place> adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_dropdown_item_1line, searchSuggestions);
        //final ArrayList<Place> placesList = new ArrayList<Place>();
        final HashMap<String, Place> placesList = new HashMap<>();

        final String[] searchSuggestionsNames = new String[] {};
        final ArrayAdapter<String> adapterNames = new ArrayAdapter<>(getApplicationContext(), R.layout.dropdown_item, searchSuggestionsNames);
        searchBar.setAdapter(adapterNames);

//        searchBar.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });



        searchBar.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Place selectedPlace = placesList.get(adapterNames.getItem(position));
                //String test = adapterNames.getItem(position);

//                int aSize = adapterNames.getCount();
//                int placesSize = placesList.size();

                //test.toString();
                adapterNames.clear();
                placesList.clear();
                searchBar.dismissDropDown();
                searchBar.setAdapter(null);

//                try {
//                    LatLng latLng = selectedPlace.getLatLng();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }

                updateMap(selectedPlace.getLatLng().latitude, selectedPlace.getLatLng().longitude);
                addMarker(selectedPlace.getLatLng().latitude, selectedPlace.getLatLng().longitude, selectedPlace.getName().toString());

                updateTextBoxes(selectedPlace.getName(), selectedPlace.getAddress());
                searchBar.clearFocus();
            }
        });


        searchBar.addTextChangedListener(new TextWatcher() {

            private String lastString = "";

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                searchBar.setAdapter(adapterNames);
                if (s.length() > 0 && (!s.toString().equals(lastString))) {

                    LatLngBounds latLngBounds = new LatLngBounds(new LatLng(mLastLocation.getLatitude() - 1, mLastLocation.getLongitude() - 1),
                            new LatLng(mLastLocation.getLatitude() + 1, mLastLocation.getLongitude() + 1));

                    PendingResult<AutocompletePredictionBuffer> result = BusinessSearch.search(mGoogleApiClient, s.toString(), latLngBounds);
                    result.setResultCallback(new ResultCallback<AutocompletePredictionBuffer>() {
                        @Override
                        public void onResult(AutocompletePredictionBuffer autocompletePredictions) {
                            adapterNames.clear();
                            placesList.clear();
                            if (autocompletePredictions.getStatus().isSuccess() && autocompletePredictions.getCount() > 0) {
                                Iterator<AutocompletePrediction> iterator = autocompletePredictions.iterator();
                                while (iterator.hasNext()) {
                                    AutocompletePrediction result = iterator.next();
                                    String id = result.getPlaceId();
                                    PendingResult<PlaceBuffer> placeResult = Places.GeoDataApi.getPlaceById(mGoogleApiClient, id);
                                    placeResult.setResultCallback(new ResultCallback<PlaceBuffer>() {
                                        @Override
                                        public void onResult(PlaceBuffer places) {
                                            if (places.getStatus().isSuccess()) {
                                                placesList.put(places.get(0).getName().toString(), places.get(0));
                                                adapterNames.add(places.get(0).getName().toString());
                                            }
                                        }
                                    });
                                }
                                adapterNames.notifyDataSetChanged();
                                searchBar.showDropDown();
                            }
                            autocompletePredictions.release();
                        }
                    });
                }
                lastString = s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }
}
