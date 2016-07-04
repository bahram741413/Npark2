 package com.it.farano.npark.DBAdapter;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.telephony.TelephonyManager;
import android.text.format.Time;
import android.util.Log;
import com.it.farano.npark.GPSTracker;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.jumpmind.symmetric.android.SQLiteOpenHelperRegistry;
import org.jumpmind.symmetric.android.SymmetricService;
import org.jumpmind.symmetric.common.ParameterConstants;

import java.io.InputStream;
import java.net.URL;
import java.util.Calendar;
import java.util.List;
import java.util.Properties;
import java.util.UUID;

public class DBAdapter {
    private static String uniqueID = null;

//---------------------
//----------------------

    private static final String TAG = "DBAdapter";
    private static final String DATABASE_NAME = "chargedb";
    private static final int DATABASE_VERSION = 23;
    private static final String DATABASE_CHARGE_RENEW ="create table Main_Charging(_id string primary key not null, CardSerial string, "
    +"ActionDate string , OperatorID string , price integer )";


    private static final String DATABASE_CHARGEAQ ="create table Main_CarLocations(_id string primary key not null,CardSerial string,"
    +" CarRegisteredTime string, OperatorID string, Lat string, Long string, pelak string,plaque1 string,plaque2 string,plaque3 string,plaque4 string, OfficerCode string, AreaCode string, CityCode string, PathCode string , Avarez string)";

    private static final String DATABASE_MC ="create table Main_Customers(_id string primary key not null,CardSerial string,"
            +" SaleDate string, OperatorID string, Lname string, Fname string, CarPlaque string , Mobile string , plaque1 string , plaque2 string , plaque3 string , plaque4 string , CardNumber integer )";


    private static final String DATABASE_CR ="create table cr(ID INTEGER PRIMARY KEY,CardSerial string)";



    private final Context context;
    private DatabaseHelper DBHelper;
    private SQLiteDatabase db;

    public DBAdapter(Context ctx)
    {
        this.context = ctx;
        DBHelper = new DatabaseHelper(context);

    }

    private static class DatabaseHelper extends SQLiteOpenHelper
    {
        DatabaseHelper(Context context)
        {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db)
        {
            try {

                db.execSQL(DATABASE_CHARGEAQ);
                db.execSQL(DATABASE_CHARGE_RENEW);
                db.execSQL(DATABASE_CR);
                db.execSQL(DATABASE_MC);
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }




        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
        {
            Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
                    + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS Main_Charging");
            db.execSQL("DROP TABLE IF EXISTS Main_CarLocations");
            db.execSQL("DROP TABLE IF EXISTS Main_Customers");
            db.execSQL("DROP TABLE IF EXISTS cr");

            onCreate(db);
        }
    }


    //---opens the database---
    public DBAdapter open() throws SQLException
    {
        db = DBHelper.getWritableDatabase();
        return this;
    }


    //---closes the database---
    public void close()
    {
        DBHelper.close();
    }

    public void clear_cr_table(){

       db.execSQL("Delete from cr");
    }


    public long insert_charge_aq(String tagid,String lat,String lng,String plq1,String plq2,String plq3,String plq4,String oc,String pc,String cc,String rc,String avz)
    {
        String imei = getIMEI(context);
        ContentValues args = new ContentValues();

        ContentValues args2 = new ContentValues();
        args2.put("CardSerial",tagid);
        db.insert("cr", null, args2);

        uniqueID = UUID.randomUUID().toString();
        args.put("_id",uniqueID);
        Time now = new Time();
        now.setToNow();
        args.put("plaque1",plq1);
        args.put("plaque2",plq2);
        args.put("plaque3",plq3);
        args.put("plaque4",plq4);
        args.put("OfficerCode",oc);
        args.put("AreaCode",pc);
        args.put("CityCode",cc);
        args.put("PathCode",rc);
        args.put("Avarez",avz);



        //args.put("pelak",pelak);
        args.put("CardSerial",tagid);
        args.put("OperatorID",imei);
        args.put("lat",lat);
        args.put("long",lng);
        args.put("CarRegisteredTime",now.format2445().toString());


        //start locationlog
try {


        HttpClient httpClient = new DefaultHttpClient();
        HttpContext localContext = new BasicHttpContext();

        HttpGet httpGet = new HttpGet("http://217.219.67.187:8080/gprmc/Data?acct=sysadmin&dev=test01&gprmc=$GPRMC,220516,A,5133.82,N,00042.24,W,173.8,231.8,151115,004.2,W*79" );
        httpClient.execute(httpGet, localContext);
}catch  (Exception e) {
//    System.out.println(e.getMessage());
} //end locationlog



       return db.insert("Main_CarLocations", null, args);
    }

    public long insert_main_customres(String tagid,String fname,String lname,String plq1,String plq2,String plq3,String plq4,String mobile,int cnum)
    {
        ContentValues args = new ContentValues();
        uniqueID = UUID.randomUUID().toString();
        args.put("_id",uniqueID);
        Time now = new Time();
        now.setToNow();
        args.put("CardSerial",tagid);
        args.put("plaque1",plq1);
        args.put("plaque2",plq2);
        args.put("plaque3",plq3);
        args.put("plaque4",plq4);
        args.put("CardNumber",cnum);
        args.put("Fname",fname);
        args.put("Lname",lname);
        args.put("Mobile",mobile);
        args.put("OperatorID",getIMEI(context));
        args.put("SaleDate",now.format2445().toString());
        return db.insert("Main_Customers", null, args);
    }

    public long insert_main_charg(String tagid,int amount)
    {
        ContentValues args = new ContentValues();
        uniqueID = UUID.randomUUID().toString();
        args.put("_id",uniqueID);
        Time now = new Time();
        now.setToNow();
        args.put("CardSerial",tagid);
        args.put("Price",amount);
        args.put("OperatorID",getIMEI(context));
        args.put("ActionDate",now.format2445().toString());
        return db.insert("Main_Charging", null, args);
    }


    public boolean startsymsrv()
{

    DatabaseHelper mOpenHelper = DBHelper;
    final String HELPER_KEY = "changeme";
    // Register the database helper, so it can be shared with the SymmetricService
    SQLiteOpenHelperRegistry.register(HELPER_KEY, mOpenHelper);
    Intent intent = new Intent(context, SymmetricService.class);
    // Notify the service of the database helper key
    intent.putExtra(SymmetricService.INTENTKEY_SQLITEOPENHELPER_REGISTRY_KEY, HELPER_KEY);
    intent.putExtra(SymmetricService.INTENTKEY_REGISTRATION_URL, "http://10.69.60.254:31415/sync/server");
    intent.putExtra(SymmetricService.INTENTKEY_EXTERNAL_ID,getIMEI(context));
    intent.putExtra(SymmetricService.INTENTKEY_NODE_GROUP_ID, "client");
    intent.putExtra(SymmetricService.INTENTKEY_START_IN_BACKGROUND, true);
    Properties properties = new Properties(); // initial load existing notes from the Client to the Server
    properties.setProperty(ParameterConstants.AUTO_RELOAD_REVERSE_ENABLED, "true");
    intent.putExtra(SymmetricService.INTENTKEY_PROPERTIES, properties);
    context.startService(intent);
return true;
}



    public String getIMEI(Context context){

        TelephonyManager mngr = (TelephonyManager) context.getSystemService(context.TELEPHONY_SERVICE);
        String imei = mngr.getDeviceId();
        return imei;

    }
public String getcardid(){
    String select_query = "select * from cr order by ID DESC";
    db = this.DBHelper.getWritableDatabase();
    Cursor mCursor= db.rawQuery(select_query,null);
    if (mCursor != null) {
        if( mCursor.moveToFirst())
            return  mCursor.getString(mCursor.getColumnIndex("CardSerial"));
    }
    return null;

}

}

