package com.it.farano.npark;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NfcAdapter;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;



public class login extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    public  void redirecttsplash(View view){

//        final Intent intent = new Intent(this, nnfc.class);
//        startActivityForResult(intent, 1);


        final Intent intent = new Intent(this, splash.class);


        startActivity(intent);






    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub


        super.onActivityResult(requestCode, resultCode, data);
        int duration = Toast.LENGTH_SHORT;
            if (resultCode == 1) {
                Toast toast = Toast.makeText(login.this,GlobalClass.usertag.toString(), duration);
                toast.show();

                GlobalClass.usertag = data.getStringExtra("tagid").toUpperCase();
                if (data.getStringExtra("tagid") == "42A284ED") {
                    Intent i = new Intent(getBaseContext(), splash.class);
                    startActivity(i);
                    finish();
                }
                else {
                     toast = Toast.makeText(login.this, "کارت نامعتبر", duration);
                    toast.show();


                }

            }
    }


}
