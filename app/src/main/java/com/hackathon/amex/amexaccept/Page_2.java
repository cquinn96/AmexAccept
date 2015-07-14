package com.hackathon.amex.amexaccept;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.ToggleButton;


public class Page_2 extends ActionBarActivity {

    private ToggleButton mAmexFlagToggle;
    private Spinner mDropdown;
    private RadioGroup mPayRequestGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_2);

        mAmexFlagToggle = (ToggleButton)findViewById(R.id.toggleButton);

        /*
        mDropdown = (Spinner)findViewById(R.id.spinner);
        String[] items = new String[]{
                "Requested payment by other card",
                "Requested payment by cash / cheque",
                "Requested surcharge for using AMEX card",
                "Requested a minimum charge for using AMEX card"
        };
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, items);
        mDropdown.setAdapter(adapter);
        mDropdown.setVisibility(View.INVISIBLE);
        */

        mPayRequestGroup = (RadioGroup)findViewById(R.id.payRequestGroup);
        mPayRequestGroup.setVisibility(View.INVISIBLE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_page_2, menu);
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

    public void onAMEXLogoDisplayToggleClick(View v) {
        // toggle the visibility of the dropdown option
        //mDropdown.setVisibility(((ToggleButton) v).isChecked() ? View.VISIBLE : View.INVISIBLE);

        mPayRequestGroup.setVisibility(((ToggleButton) v).isChecked() ? View.VISIBLE : View.INVISIBLE);
    }
}
