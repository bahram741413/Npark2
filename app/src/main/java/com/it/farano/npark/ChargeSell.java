package com.it.farano.npark;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.it.farano.npark.DBAdapter.DBAdapter;

import org.apache.commons.lang.ObjectUtils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;


public class ChargeSell extends Activity {
    public DBAdapter db = new DBAdapter(ChargeSell.this);
    private RadioGroup radioSellGroup;
    private RadioButton radioSellButton;

    public String tg= null;
public TextView crid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cs);
        crid=(TextView) findViewById(R.id.crdtxt);
        Button savebtn = (Button) findViewById(R.id.savebtn);


/*

        savebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub


                if ((tg != null)&&( tg.trim().length() > 0)) {
                    int amount = 0;
                    radioSellGroup = (RadioGroup) findViewById(R.id.radioSell);
                    int selectedId = radioSellGroup.getCheckedRadioButtonId();
                    radioSellButton = (RadioButton) findViewById(selectedId);

                    if (radioSellButton.getText() == getResources().getString(R.string.radio_20000)) {
                        amount = 200000;

                    }
                    if (radioSellButton.getText() == getResources().getString(R.string.radio_5000))
                        amount = 50000;
                    if (radioSellButton.getText() == getResources().getString(R.string.radio_10000))
                        amount = 100000;

                    db.open();
                    db.insert_main_charg(tg, amount);
                    db.close();

                    Toast.makeText(getBaseContext(), "اطلاعات با موفقیت ذخیره شد",
                            Toast.LENGTH_LONG).show();
                    finish();
                } else {
                    Toast.makeText(getBaseContext(), "کد کارت دریافت نشده است",
                            Toast.LENGTH_LONG).show();

                }


            }        });

*/
    }




    public void confirmdlg(View view)
    {
        //Put up the Yes/No message box
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("خرید شارژ");
        builder.setIcon(android.R.drawable.ic_dialog_alert);
        builder.setMessage("آیا از خرید شارژمطمئنید؟");
        builder.setPositiveButton("بله", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                //Yes button clicked, do something
                //---------------------------------------
                if ((tg != null)&&( tg.trim().length() > 0)) {
                    int amount = 0;
                    radioSellGroup = (RadioGroup) findViewById(R.id.radioSell);
                    int selectedId = radioSellGroup.getCheckedRadioButtonId();
                    radioSellButton = (RadioButton) findViewById(selectedId);

                    if (radioSellButton.getText() == getResources().getString(R.string.radio_20000)) {
                        amount = 200000;

                    }
                    if (radioSellButton.getText() == getResources().getString(R.string.radio_5000))
                        amount = 50000;
                    if (radioSellButton.getText() == getResources().getString(R.string.radio_10000))
                        amount = 100000;

                    db.open();
                    db.insert_main_charg(tg, amount);
                    db.close();

                    Toast.makeText(getBaseContext(), "اطلاعات با موفقیت ذخیره شد",
                            Toast.LENGTH_LONG).show();
                    finish();
                } else {
                    Toast.makeText(getBaseContext(), "کد کارت دریافت نشده است",
                            Toast.LENGTH_LONG).show();

                }



                //----------------------------------------------------------------


            }
        });
        builder.setNegativeButton("خیر", null);						//Do nothing on no
            builder.show();


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
    public void ldcr(View view){

        db.open();
        tg = db.getcardid();
        db.close();
        crid.setText(tg);

    }

}
