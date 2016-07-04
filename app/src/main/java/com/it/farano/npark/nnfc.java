package com.it.farano.npark;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import java.sql.SQLException;
import android.nfc.NdefMessage;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.NfcA;
import android.nfc.tech.NfcF;
import android.os.Bundle;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentFilter.MalformedMimeTypeException;
import android.os.StrictMode;
import android.util.Log;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.it.farano.npark.DBAdapter.DBAdapter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class nnfc extends Activity {
    public DBAdapter db = new DBAdapter(nnfc.this);
    SharedPreferences.Editor editor;

    private NfcAdapter mAdapter;
    private PendingIntent mPendingIntent;
    private IntentFilter[] mFilters;
    private String[][] mTechLists;
    private int mCount = 0;
private String mtagid;
    private TextView mTextView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nnfc);
        mAdapter = NfcAdapter.getDefaultAdapter(nnfc.this);


        // Create a generic PendingIntent that will be deliver to this activity. The NFC stack
        // will fill in the intent with the details of the discovered tag before delivering to
        // this activity.



        mPendingIntent = PendingIntent.getActivity(nnfc.this, 0,
                new Intent(nnfc.this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP), 0);

        // Setup an intent filter for all MIME based dispatches



        IntentFilter ndef = new IntentFilter(NfcAdapter.ACTION_NDEF_DISCOVERED);
        try {
            ndef.addDataType("text/plain");
        } catch (MalformedMimeTypeException e) {
            throw new RuntimeException("fail", e);
        }
        mFilters = new IntentFilter[] {
                ndef,
        };

        // Setup a tech list for all NfcF tags
        mTechLists = new String[][] { new String[] { NfcA.class.getName() } };


        onNewIntent(getIntent());


    }
    public void onResume() {
        super.onResume();
        mAdapter.enableForegroundDispatch(this, mPendingIntent, mFilters, mTechLists);
    }

    @Override
    public void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.i("Foreground dispatch", "Discovered tag with intent: " + intent);
        CharSequence text = ("Discovered tag " + ++mCount + " with intent: " + intent);
        int duration = Toast.LENGTH_SHORT;
        Tag myTag = (Tag) intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
        mtagid =  ByteArrayToHexString(myTag.getId());




        Toast toast = Toast.makeText(this,mtagid, duration);
        toast.show();
        String lat = null,lng = null;
        GPSTracker gpsTracker = new GPSTracker(this);
        if (gpsTracker.canGetLocation())
        {
           lat = String.valueOf(gpsTracker.latitude);
            lng = String.valueOf(gpsTracker.longitude);
        }


        SharedPreferences prefs = this.getSharedPreferences("com.it.farano.npark", Context.MODE_PRIVATE);

        GlobalClass.usertag= null;
        GlobalClass.usertag = mtagid;
        db.open();
        db.insert_charge_aq(mtagid,lat,lng,"1","1","1","1",prefs.getString("O_Code","0000"),prefs.getString("P_Code","00"),prefs.getString("C_Code","00"),prefs.getString("R_Code","0000"),prefs.getString("avarez","1"));
        db.close();
        if (prefs.getString("avarez","1") == "0") {
            editor = prefs.edit();
            editor.putString("avarez", "1");
            editor.commit();
        }

        finish();
    }


    @Override
    public void onPause() {
        super.onPause();
        mAdapter.disableForegroundDispatch(nnfc.this);
        //throw new RuntimeException("onPause not implemented to fix build");
    }


    String ByteArrayToHexString(byte [] inarray)
    {
        int i, j, in;
        String [] hex = {"0","1","2","3","4","5","6","7","8","9","A","B","C","D","E","F"};
        String out= "";

        for(j = 0 ; j < inarray.length ; ++j)
        {
            in = (int) inarray[j] & 0xff;
            i = (in >> 4) & 0x0f;
            out += hex[i];
            i = in & 0x0f;
            out += hex[i];
        }
        return out;
    }
}