package com.it.farano.npark;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteOpenHelper;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.it.farano.npark.DBAdapter.DBAdapter;

import org.jumpmind.symmetric.android.SQLiteOpenHelperRegistry;
import org.jumpmind.symmetric.android.SymmetricService;
import org.jumpmind.symmetric.common.ParameterConstants;

import java.util.List;
import java.util.Properties;

import static java.security.AccessController.getContext;


public class tasklist extends Activity {
    public DBAdapter db = new DBAdapter(tasklist.this);
    public final static String EXTRA_MESSAGE = "com.it.farano.npark.DBAdapter.MESSAGE";
    SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dist_frm);
        final Button tsk1btn = (Button)findViewById(R.id.task1);

        final Button tsk3btn = (Button)findViewById(R.id.task3);

        final Button tsk5btn = (Button)findViewById(R.id.task5);
        final Button tsk4btn = (Button)findViewById(R.id.task4);
        final Button tsk6btn = (Button)findViewById(R.id.task6);
        final Button tsk7btn = (Button)findViewById(R.id.task7);

        final SharedPreferences prefs = this.getSharedPreferences("com.it.farano.npark", Context.MODE_PRIVATE);
db.startsymsrv();





        tsk1btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent i=new Intent(getBaseContext(),preg.class);
                startActivity(i);

            }
        });
        tsk3btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                db.open();
                db.clear_cr_table();
                db.close();
                Intent i=new Intent(getBaseContext(),ChargeSell.class);
                startActivity(i);
            }
        });

        tsk5btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.open();
                db.clear_cr_table();
                db.close();
                Intent i=new Intent(getBaseContext(),Cardreg.class);
                startActivity(i);
            }
        });

        tsk4btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.open();
                db.clear_cr_table();
                db.close();
                Intent i=new Intent(getBaseContext(),settings.class);
                startActivity(i);
            }
        });
        tsk6btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.open();
                db.clear_cr_table();
                db.close();

                editor = prefs.edit();
                editor.putString("avarez","0");
                editor.commit();

                Toast.makeText(getBaseContext(), "اطلاعات کارت مورد نظر را بخوانید",
                        Toast.LENGTH_LONG).show();
            }
        });
        tsk7btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.open();
                db.clear_cr_table();
                db.close();
                Intent i=new Intent(getBaseContext(),showcredits.class);
                startActivity(i);
            }
        });


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

void redirect2func()
{
//    String message ="E20030286306022223102A49";
    Intent intent = new Intent(this, nnfc.class);
//    intent.putExtra(EXTRA_MESSAGE, "E200302863060108234023E6");
    startActivity(intent);


}




}
