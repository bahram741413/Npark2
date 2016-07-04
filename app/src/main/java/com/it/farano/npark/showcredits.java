package com.it.farano.npark;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
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
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class showcredits extends Activity {
    public DBAdapter db = new DBAdapter(showcredits.this);

    public String tg= null;
    public TextView crid;
    public TextView fcr;
    public  StrictMode.ThreadPolicy old;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.showcredits);
        crid=(TextView) findViewById(R.id.crdtxt);
        fcr = (TextView) findViewById(R.id.totalcredit);
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
        if ((tg != null)&&( tg.trim().length() > 0)) {

            fcr.setText(cardcharge(tg));
        } else {
            Toast.makeText(getBaseContext(), "کد کارت دریافت نشده است",
                    Toast.LENGTH_LONG).show();
        }

    }
    public String cardcharge(String mtagid)
    {
        String ip, db, un, passwords,cp;
        Connection connect;
        PreparedStatement stmt;
        ResultSet rs;
        ip = "10.69.60.254";
        un = "mobileappquery";
        passwords = "SecRet@AlaVI95";
        db = "CardPark";
        cp = "-1";

        String query = "select * from finalCredits where CardSerial= '"+mtagid+"'";

        try {
            connect = CONN(un, passwords, db, ip);
            stmt = connect.prepareStatement(query);
            rs = stmt.executeQuery();
            if(rs.next()) {
                cp=    rs.getString("totalPrice");
            }
            connect.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        StrictMode.setThreadPolicy(old);

        return cp;
    }

    @SuppressLint("NewApi")

    private Connection CONN(String _user, String _pass, String _DB, String _server) {

        old = StrictMode.getThreadPolicy();

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .permitAll().build();

        StrictMode.setThreadPolicy(policy);

        Connection conn = null;

        String ConnURL = null;

        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");

            ConnURL = "jdbc:jtds:sqlserver://" + _server + ";"

                    + "databaseName=" + _DB + ";user=" + _user + ";password="

                    + _pass + ";";

            conn = DriverManager.getConnection(ConnURL);

        } catch (SQLException se) {

            Log.e("ERRO", se.getMessage());

        } catch (ClassNotFoundException e) {

            Log.e("ERRO", e.getMessage());

        } catch (Exception e) {

            Log.e("ERRO", e.getMessage());
        }
        return conn;
    }
}
