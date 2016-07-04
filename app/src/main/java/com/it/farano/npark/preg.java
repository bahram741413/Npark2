package com.it.farano.npark;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.it.farano.npark.DBAdapter.DBAdapter;


public class preg extends Activity {
    public DBAdapter db = new DBAdapter(preg.this);
public EditText p1,p2,p3,p4;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pr);
        p1=(EditText) findViewById(R.id.e1txt);
        p2=(EditText) findViewById(R.id.e2txt);
        p3=(EditText) findViewById(R.id.e3txt);
        p4=(EditText) findViewById(R.id.e4txt);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.preg, menu);
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

    public void savechanges(View view) {

        if ((p1.getText().length() == 2) && (p3.getText().length() == 3) && (p4.getText().length() == 2)) {

            String lat = null, lng = null;
            GPSTracker gpsTracker = new GPSTracker(this);
            if (gpsTracker.canGetLocation()) {
                lat = String.valueOf(gpsTracker.latitude);
                lng = String.valueOf(gpsTracker.longitude);
            }

            SharedPreferences prefs = this.getSharedPreferences("com.it.farano.npark", Context.MODE_PRIVATE);

            db.open();
            // String tplk = Integer.parseInt(n1.getText().toString())+ c1.getText().toString()+ Integer.parseInt(n3.getText().toString())+ Integer.parseInt(n2.getText().toString());

            //  String tplk = Integer.parseInt(n1.getText().toString())+ Integer.parseInt(n3.getText().toString())+ c1.getText().toString()+  Integer.parseInt(n2.getText().toString());
            db.insert_charge_aq("--------", lat, lng, p1.getText().toString(), p2.getText().toString(), p3.getText().toString(), p4.getText().toString(),prefs.getString("O_Code","0000"),prefs.getString("P_Code","00"),prefs.getString("C_Code","00"),prefs.getString("R_Code","0000"),prefs.getString("avarez","1"));

            db.close();
            if (prefs.getString("avarez","1") == "0") {
                editor = prefs.edit();
                editor.putString("avarez", "1");
                editor.commit();
            }


            Toast.makeText(getBaseContext(), "اطلاعات با موفقیت ذخیره شد",
                    Toast.LENGTH_LONG).show();
            finish();

        }
        else {
            Toast.makeText(getBaseContext(),"پلاک وارد شده صحیح نمیباشد",
                    Toast.LENGTH_LONG).show();
            return;
        }
    }


}
