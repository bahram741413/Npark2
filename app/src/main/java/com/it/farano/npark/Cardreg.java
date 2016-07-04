package com.it.farano.npark;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.it.farano.npark.DBAdapter.DBAdapter;


public class Cardreg extends Activity {
    public DBAdapter db = new DBAdapter(Cardreg.this);
public EditText p1,p2,p3,p4,namet,family,mobile,cnum;
    public String tg= null;
public TextView crid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cr);
        p1=(EditText) findViewById(R.id.e1txt);
        p2=(EditText) findViewById(R.id.e2txt);
        p3=(EditText) findViewById(R.id.e3txt);
        p4=(EditText) findViewById(R.id.e4txt);
        crid=(TextView) findViewById(R.id.crdtxt);
        namet=(EditText) findViewById(R.id.nametxt);
        family=(EditText) findViewById(R.id.familytxt);
        mobile=(EditText) findViewById(R.id.mobiletxt);
        cnum = (EditText) findViewById(R.id.crdidtxt);
        crid.setText("null");

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

    public void savechanges(View view){

        //if  (cnum.getText().length() == 10) {
        if ((tg != null)&&( tg.trim().length() > 0)&&(cnum.getText().length() == 10)&&(mobile.getText().length() == 11)
                &&( p1.getText().length() == 2)&&( p3.getText().length() == 3)&&( p4.getText().length() == 2)) {
            String lat = null, lng = null;
            GPSTracker gpsTracker = new GPSTracker(this);
            if (gpsTracker.canGetLocation()) {
                lat = String.valueOf(gpsTracker.latitude);
                lng = String.valueOf(gpsTracker.longitude);
            }

            db.open();
            //String tplk = Integer.parseInt(n1.getText().toString())+ c1.getText().toString()+ Integer.parseInt(n3.getText().toString())+ Integer.parseInt(n2.getText().toString());

            db.insert_main_customres(tg, namet.getText().toString(), family.getText().toString(), p1.getText().toString(), p2.getText().toString(), p3.getText().toString(), p4.getText().toString(), mobile.getText().toString(), Integer.parseInt(cnum.getText().toString()));
            db.close();
            Toast.makeText(getBaseContext(), "اطلاعات با موفقیت ذخیره شد",
                    Toast.LENGTH_LONG).show();
            finish();
        }
        else {
            Toast.makeText(getBaseContext(),"اطلاعات وارد شده صحیح نمیباشد",
                    Toast.LENGTH_LONG).show();
            return;
        }
    }
    public void ldcr(View view){

        db.open();
        tg = db.getcardid();
        db.close();
        crid.setText(tg);

    }

}
