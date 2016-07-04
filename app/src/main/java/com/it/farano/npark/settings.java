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


public class settings extends Activity {

    SharedPreferences.Editor editor;

    public EditText oc,pc,cc,rc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.config);
        oc=(EditText) findViewById(R.id.octxt);
        pc=(EditText) findViewById(R.id.pctxt);
        cc=(EditText) findViewById(R.id.cctxt);
        rc=(EditText) findViewById(R.id.rctxt);
        final      SharedPreferences prefs = this.getSharedPreferences("com.it.farano.npark", Context.MODE_PRIVATE);

        if (prefs.contains("O_Code")) {

        oc.setText(prefs.getString("O_Code","0000"));
        pc.setText(prefs.getString("P_Code","00"));
        cc.setText(prefs.getString("C_Code","00"));
        rc.setText(prefs.getString("R_Code","0000"));
         }


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

        final      SharedPreferences prefs = this.getSharedPreferences("com.it.farano.npark", Context.MODE_PRIVATE);
        if ((oc.getText().length() == 4) && (pc.getText().length() == 2) && (cc.getText().length() == 2) && (rc.getText().length() == 4)) {

            editor = prefs.edit();
            editor.putString("O_Code", oc.getText().toString());
            editor.putString("P_Code", pc.getText().toString());
            editor.putString("C_Code", cc.getText().toString());
            editor.putString("R_Code", rc.getText().toString());
            editor.putString("avarez", "1");
            editor.commit();

            Toast.makeText(getBaseContext(), "اطلاعات با موفقیت ذخیره شد",
                    Toast.LENGTH_LONG).show();
        }
        else
        {
            Toast.makeText(getBaseContext(),"کدهای وارد شده صحیح نمیباشد",
                    Toast.LENGTH_LONG).show();
            return;
        }


            finish();

    }


}
