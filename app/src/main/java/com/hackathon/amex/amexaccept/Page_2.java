package com.hackathon.amex.amexaccept;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.ToggleButton;


public class Page_2 extends ActionBarActivity {

    private ToggleButton mAmexFlagToggle;
    private RadioGroup mPayRequestGroup;
    private Button mSubmitButton;
    private Button mButtonYes;
    private Button mButtonNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_2);

        mAmexFlagToggle = (ToggleButton)findViewById(R.id.toggleButton);

        mPayRequestGroup = (RadioGroup)findViewById(R.id.payRequestGroup);
        mPayRequestGroup.setVisibility(View.INVISIBLE);

        mSubmitButton = (Button)findViewById(R.id.submitButton);
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
        mPayRequestGroup.setVisibility(((ToggleButton) v).isChecked() ? View.VISIBLE : View.INVISIBLE);
    }

    public void onAMEXLogoDisplayYesClick(View v) {
        mPayRequestGroup.setVisibility(View.VISIBLE);
    }

    public void onAMEXLogoDisplayNoClick(View v) {
        mPayRequestGroup.setVisibility(View.INVISIBLE);
    }



    public void onSubmitClick(View v) {
        // send or display a notification
        //android.telephony.SmsManager smsMgr = android.telephony.SmsManager.getDefault();
        //smsMgr.sendTextMessage();
        //TelephonyManager tMgr =(TelephonyManager)getApplicationContext().getSystemService(Context.TELEPHONY_SERVICE);
        //String number = tMgr.getLine1Number();
        String number = "+447999002309";

        //final boolean isLogoDisplayed = mAmexFlagToggle.isChecked();
        String message = "Thank you for submitting to AcceptAmex. We will keep you updated when we have contacted the business.";
//        if( isLogoDisplayed ) {
//            message += "\nYou've notified us that AMEX Logo was displayed.";
//            RadioButton selButton  = (RadioButton)findViewById(mPayRequestGroup.getCheckedRadioButtonId());
//            message += "\nMerchant action: " + selButton.getText();
//        }

        /** Creating a pending intent which will be broadcasted when an sms message is successfully sent */
        PendingIntent piSent = PendingIntent.getBroadcast(getBaseContext(), 0, new Intent("sent_msg"), 0);

        /** Creating a pending intent which will be broadcasted when an sms message is successfully delivered */
        PendingIntent piDelivered = PendingIntent.getBroadcast(getBaseContext(), 0, new Intent("delivered_msg"), 0);

        /** Getting an instance of SmsManager to sent sms message from the application*/
        SmsManager smsManager = SmsManager.getDefault();

        /** Sending the Sms message to the intended party */
        smsManager.sendTextMessage(number, null, message, piSent, piDelivered);
    }
}