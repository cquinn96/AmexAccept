package com.hackathon.amex.amexaccept;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity {

//    private GoogleApiClient mGoogleApiClient;
//
//    private TextView mViewName = (TextView) findViewById(R.id.mViewName);
//    private TextView mViewAddress = (TextView) findViewById(R.id.mViewName);
//    private TextView mViewAttributions = (TextView) findViewById(R.id.mViewName);
//
//    public void onPickButtonClick(View v) {
//        // Construct an intent for the place picker
//        try {
//            PlacePicker.IntentBuilder intentBuilder =
//                    new PlacePicker.IntentBuilder();
//            Intent intent = intentBuilder.build(this);
//            // Start the intent by requesting a result,
//            // identified by a request code.
//            startActivityForResult(intent, REQUEST_PLACE_PICKER);
//
//        } catch (GooglePlayServicesRepairableException e) {
//            // ...
//        } catch (GooglePlayServicesNotAvailableException e) {
//            // ...
//        }
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode,
//                                    int resultCode, Intent data) {
//
//        if (requestCode == REQUEST_PLACE_PICKER
//                && resultCode == Activity.RESULT_OK) {
//
//            // The user has selected a place. Extract the name and address.
//            final Place place = PlacePicker.getPlace(data, this);
//
//            final CharSequence name = place.getName();
//            final CharSequence address = place.getAddress();
//            String attributions = PlacePicker.getAttributions(data);
//            if (attributions == null) {
//                attributions = "";
//            }
//
//            mViewName.setText(name);
//            mViewAddress.setText(address);
//            mViewAttributions.setText(Html.fromHtml(attributions));
//
//        } else {
//            super.onActivityResult(requestCode, resultCode, data);
//        }
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
}
