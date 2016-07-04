package com.it.farano.npark;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;



public class splash extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
final        SharedPreferences prefs = this.getSharedPreferences("com.it.farano.npark", Context.MODE_PRIVATE);

        /****** Create Thread that will sleep for 5 seconds *************/
        Thread background = new Thread() {
            public void run() {

                try {
                    // Thread will sleep for 5 seconds
                    sleep(5*1000);

                    //---------- check sharedpref

                    if (!prefs.contains("O_Code")) {
                        Intent i = new Intent(getBaseContext(), settings.class);
                        startActivity(i);
                        // i = new Intent(getBaseContext(), tasklist.class);
                        //startActivity(i);

                        //Remove activity
                        finish();

                    } else {


                        //---------------------------


                        // After 5 seconds redirect to another intent
                        Intent i = new Intent(getBaseContext(), tasklist.class);
                        startActivity(i);

                        //Remove activity
                        finish();
                    }

                } catch (Exception e) {

                }
            }
        };

        // start thread
        background.start();

    }

    @Override
    protected void onDestroy() {

        super.onDestroy();

    }








    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.splash, menu);
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

}
