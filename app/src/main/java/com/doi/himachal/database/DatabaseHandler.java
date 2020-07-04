package com.doi.himachal.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.doi.himachal.Modal.BlockPojo;
import com.doi.himachal.Modal.DistrictBarrierPojo;
import com.doi.himachal.Modal.DistrictPojo;
import com.doi.himachal.Modal.GramPanchayatPojo;
import com.doi.himachal.Modal.ResponsePojo;
import com.doi.himachal.Modal.StatePojo;
import com.doi.himachal.Modal.TehsilPojo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Kush.Dhawan
 * @project HPePass
 * @Time 01, 05 , 2020
 */
public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 4;

    // Database Name
    private static final String DATABASE_NAME = "idV9.db";

    // District Tables
    private static final String TABLE_DISTRICT = "district";
    private static final String KEY_ID = "id";
    private static final String STATE_ID = "state_id";
    private static final String DISTRICT_ID = "district_id";
    private static final String DISTRICT_NAME = "district_name";
    private static final String ALERT_ZONE = "alert_zone";



    //State Table
    private static final String TABLE_State = "state";
    private static final String KEY_ID_S = "id";
    private static final String STATE_M_ID = "state_id";
    private static final String STATE_NAME = "state_name";

    //State Tehsil
    private static final String TABLE_Tehsil = "tehsil";
    private static final String KEY_ID_T = "id";
    private static final String TEHSIL_M_ID = "tehsil_id";
    private static final String TEHSIL_NAME = "tehsil_name";
    private static final String T_DISTRICT_ID = "district_id";

    //Block
    private static final String TABLE_BLOCK = "block";
    private static final String KEY_ID_BLOCK = "block_id";
    private static final String BLOCK_CODE = "block_code";
    private static final String BLOCK_NAME = "block_name";
    private static final String B_DISTRICT_ID = "district_id";
    private static final String IS_ACTIVE = "is_active";
    private static final String IS_DELETED = "is_deleted";

    //GP
    private static final String TABLE_GRAMPANCHAYAT = "gram_panchayat";
    private static final String KEY_ID_GP = "panchayat_id";
    private static final String GP_M_ID = "pachayat_code";
    private static final String GP_NAME = "panchayat_name";
    private static final String GP_BLOCK_ID = "block_id";
    private static final String GP_PARDHAN_PHONE = "pardhan_phone";



    // Barrier Tables
    private static final String TABLE_BARRIER = "barrier";
    private static final String KEY_ID_B = "id";
    private static final String BARRIER_ID = "barrier_id";
    private static final String DIS_ID = "district_id";
    private static final String BARRIER_NAME = "barrier_name";

    //Offiline Data Table
    private static final String TABLE_OFFLINE_STORAGE = "Offline_Storage";
    private static final String OFFLINE_DATA_ID = "id";
    private static final String OFFLINE_DATA_URL = "url";
    private static final String OFFLINE_DATA_REQUEST_PARAMS = "params";
    private static final String OFFLINE_DATA_RESPOSE ="response";
    private static final String OFFLINE_DATA_PASS_NUMBER = "pass_number";
    private static final String OFFLINE_DATA_MOBILE_NUMBER = "mobile_number";
    private static final String OFFLINE_DATA_NUMBER_PERSONS= "no_persons";
    private static final String OFFLINE_DATA_VALIDITY = "validity";
    private static final String OFFLINE_DATA_LATITUDE = "latitude";
    private static final String OFFLINE_DATA_LONGITUDE = "longitude";
    private static final String OFFLINE_DATA_DISTRICT = "district";
    private static final String OFFLINE_DATA_BARRIER = "barrier";
    private static final String OFFLINE_DATA_SCANDATE = "scanDate";
    private static final String OFFLINE_DATA_SCANNEDBY = "scanByPhoneNumber";
    private static final String OFFLINE_DATA_UPLOADEDTOSERVER = "uploadedtoserver";
    private static final String OFFLINE_DATA_NUMBER_PERSONS_MANUAL = "persons_manual";
    //private static final String OFFLINE_PASS_VERIFIED_TAG = "pass_verified";


    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {


        String CREATE_STATE_TABLE = "CREATE TABLE " + TABLE_State + "("
                + KEY_ID_S + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + STATE_M_ID + " TEXT,"
                + STATE_NAME + " TEXT" + ")";

        String CREATE_DISTRICT_TABLE = "CREATE TABLE " + TABLE_DISTRICT + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + STATE_ID + " TEXT,"
                + DISTRICT_ID + " TEXT,"
                + ALERT_ZONE + " TEXT,"
                + DISTRICT_NAME + " TEXT" + ")";


        String CREATE_TEHSIL_TABLE = "CREATE TABLE " + TABLE_Tehsil + "("
                + KEY_ID_T + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + TEHSIL_M_ID + " TEXT,"
                + TEHSIL_NAME + " TEXT,"
                + T_DISTRICT_ID + " TEXT" + ")";

        String CREATE_TABLE_BLOCK = "CREATE TABLE " + TABLE_BLOCK + "("
                + KEY_ID_BLOCK + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + BLOCK_CODE + " TEXT,"
                + BLOCK_NAME + " TEXT,"
                + IS_ACTIVE + " TEXT,"
                + IS_DELETED + " TEXT,"
                + B_DISTRICT_ID + " TEXT" + ")";

        String CREATE_TABLE_GP = "CREATE TABLE " + TABLE_GRAMPANCHAYAT + "("
                + KEY_ID_GP + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + GP_M_ID + " TEXT,"
                + GP_NAME + " TEXT,"
                + IS_ACTIVE + " TEXT,"
                + IS_DELETED + " TEXT,"
                + GP_BLOCK_ID + " TEXT,"
                + GP_PARDHAN_PHONE + " TEXT" + ")";

        String CREATE_BARRIER_TABLE = "CREATE TABLE " + TABLE_BARRIER + "("
                + KEY_ID_B + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + BARRIER_ID + " TEXT,"
                + DIS_ID + " TEXT,"
                + BARRIER_NAME + " TEXT" + ")";

        String CREATE_OFFLINE_DATA_TABLE = "CREATE TABLE " + TABLE_OFFLINE_STORAGE + "("
                + OFFLINE_DATA_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + OFFLINE_DATA_URL + " TEXT,"
                + OFFLINE_DATA_REQUEST_PARAMS + " TEXT,"
                + OFFLINE_DATA_RESPOSE + " TEXT ,"
                + OFFLINE_DATA_PASS_NUMBER + " TEXT, "
                + OFFLINE_DATA_MOBILE_NUMBER + " TEXT, "
                + OFFLINE_DATA_NUMBER_PERSONS + " TEXT, "
                + OFFLINE_DATA_VALIDITY + " TEXT, "
                + OFFLINE_DATA_LATITUDE + " TEXT, "
                + OFFLINE_DATA_LONGITUDE + " TEXT, "
                + OFFLINE_DATA_DISTRICT + " TEXT, "
                + OFFLINE_DATA_BARRIER + " TEXT, "
                + OFFLINE_DATA_SCANDATE + " TEXT, "
                + OFFLINE_DATA_SCANNEDBY + " TEXT ,"
                + OFFLINE_DATA_NUMBER_PERSONS_MANUAL + " TEXT ,"
              //  + OFFLINE_PASS_VERIFIED_TAG + " TEXT ,"
                + OFFLINE_DATA_UPLOADEDTOSERVER + " TEXT " + ")";

        db.execSQL(CREATE_STATE_TABLE);
        db.execSQL(CREATE_DISTRICT_TABLE);
        db.execSQL(CREATE_BARRIER_TABLE);
        db.execSQL(CREATE_OFFLINE_DATA_TABLE);
        db.execSQL(CREATE_TEHSIL_TABLE);
        db.execSQL(CREATE_TABLE_BLOCK);
        db.execSQL(CREATE_TABLE_GP);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DISTRICT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BARRIER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_State);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_OFFLINE_STORAGE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_Tehsil);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BLOCK);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_GRAMPANCHAYAT);
        // Create tables again
        onCreate(db);
    }

    public Boolean addDistrict(List<DistrictPojo> districts) {
        SQLiteDatabase db = this.getWritableDatabase();


        for (int i = 0; i < districts.size(); i++) {
            //mou_Details
            ContentValues values = new ContentValues();
            values.put(DISTRICT_ID, districts.get(i).getDistrict_id());
            values.put(DISTRICT_NAME, districts.get(i).getDistrict_name());
            values.put(STATE_ID, districts.get(i).getState_id());
            values.put(ALERT_ZONE, districts.get(i).getAlertZone());

            db.insert(TABLE_DISTRICT, null, values);

        }

        db.close();

        try {
            // exportDatabse(DATABASE_NAME);
            return true;
        } catch (Exception e) {
            Log.d("Got Error ..", e.getLocalizedMessage());
            return false;
        }

    }

    public Boolean addStates(List<StatePojo> states) {
        SQLiteDatabase db = this.getWritableDatabase();


        try {
            for (int i = 0; i < states.size(); i++) {
                //mou_Details
                ContentValues values = new ContentValues();
                values.put(STATE_M_ID, states.get(i).getState_id());
                values.put(STATE_NAME, states.get(i).getState_name());

                db.insert(TABLE_State, null, values);

            }

            db.close();

            return true;
        } catch (Exception e) {
            Log.d("Got State Table", e.getLocalizedMessage());
            return false;
        }

    }

    public Boolean addTehsil(List<TehsilPojo> tehsils) {
        SQLiteDatabase db = this.getWritableDatabase();


        try {
            for (int i = 0; i < tehsils.size(); i++) {
                //mou_Details
                ContentValues values = new ContentValues();
                values.put(TEHSIL_M_ID, tehsils.get(i).getTehsil_id());
                values.put(TEHSIL_NAME, tehsils.get(i).getTehsil_name());
                values.put(T_DISTRICT_ID, tehsils.get(i).getDistrict_id());

                db.insert(TABLE_Tehsil, null, values);

            }

            db.close();

            return true;
        } catch (Exception e) {
            Log.d("Got State Table", e.getLocalizedMessage());
            return false;
        }

    }

    public Boolean addBlocks(List<BlockPojo> blocks) {
        SQLiteDatabase db = this.getWritableDatabase();


        try {
            for (int i = 0; i < blocks.size(); i++) {
                //mou_Details
                ContentValues values = new ContentValues();
                values.put(BLOCK_CODE, blocks.get(i).getBlock_code());
                values.put(BLOCK_NAME, blocks.get(i).getBlock_name());
                values.put(B_DISTRICT_ID, blocks.get(i).getDistrict_id());
                values.put(IS_ACTIVE, blocks.get(i).getIs_active());
                values.put(IS_DELETED, blocks.get(i).getIs_deleted());

                db.insert(TABLE_BLOCK, null, values);

            }

            db.close();

            return true;
        } catch (Exception e) {
            Log.d("Got State Table", e.getLocalizedMessage());
            return false;
        }

    }

    public Boolean addGp(List<GramPanchayatPojo> gp) {
        SQLiteDatabase db = this.getWritableDatabase();


        try {
            for (int i = 0; i < gp.size(); i++) {
                //mou_Details
                ContentValues values = new ContentValues();
                values.put(GP_M_ID, gp.get(i).getGp_id());
                values.put(GP_NAME, gp.get(i).getGp_name());
                values.put(GP_BLOCK_ID, gp.get(i).getBlock_id());
                values.put(IS_ACTIVE, gp.get(i).getIs_active());
                values.put(IS_DELETED, gp.get(i).getIs_deleted());
                values.put(GP_PARDHAN_PHONE,gp.get(i).getIs_deleted());

                db.insert(TABLE_GRAMPANCHAYAT, null, values);

            }

            db.close();

            return true;
        } catch (Exception e) {
            Log.d("Got State Table", e.getLocalizedMessage());
            return false;
        }finally {
            db.close();
        }

    }



    public Boolean addDistrictBarriers(List<DistrictBarrierPojo> districtsBarriers) {
        SQLiteDatabase db = this.getWritableDatabase();


        for (int i = 0; i < districtsBarriers.size(); i++) {
            //mou_Details
            ContentValues values = new ContentValues();
            values.put(BARRIER_ID, districtsBarriers.get(i).getBarrier_id());
            values.put(DIS_ID, districtsBarriers.get(i).getBarrier_district_id());
            values.put(BARRIER_NAME, districtsBarriers.get(i).getBarrir_name());

            db.insert(TABLE_BARRIER, null, values);

        }

        db.close();

        try {
            // exportDatabse(DATABASE_NAME);
            return true;
        } catch (Exception e) {
            Log.d("Got Error ..", e.getLocalizedMessage());
            return false;
        }finally {
            db.close();
        }

    }

    public int getNoOfRowsCountDistrict() {
        String countQuery = "SELECT  * FROM " + TABLE_DISTRICT;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
        db.close();
        return count;
    }

    public int getNoOfRowsBlocks() {
        String countQuery = "SELECT  * FROM " + TABLE_BLOCK;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
        db.close();
        return count;
    }

    public int getNoOfRowsGP() {
        String countQuery = "SELECT  * FROM " + TABLE_GRAMPANCHAYAT;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
        db.close();
        return count;
    }

    public int getNoOfRowsTehsil() {
        String countQuery = "SELECT  * FROM " + TABLE_Tehsil;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
        db.close();
        return count;
    }

    public int getNoOfRowsState() {
        String countQuery = "SELECT  * FROM " + TABLE_State;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
        db.close();
        return count;
    }

    public int getNoOfRowsCountBarrir() {
        String countQuery = "SELECT  * FROM " + TABLE_BARRIER;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
        db.close();
        return count;
    }


    public List<DistrictPojo> getDistricts() {
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_DISTRICT;  //+ " ORDER BY " + DATE_TIME + " DESC"
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        List<DistrictPojo> news_list_db = new ArrayList<>();
        // looping through all rows and adding to list
        while (cursor.moveToNext()) {
            DistrictPojo md = new DistrictPojo();
            //Log.d(KEY_ID_DB, cursor.getString(0));
            md.setDistrict_id(cursor.getString(2));
            md.setDistrict_name(cursor.getString(4));


            news_list_db.add(md);
        }
        db.close(); // Closing database connection
        return news_list_db;

    }

    public List<StatePojo> getStates() {
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_State;  //+ " ORDER BY " + DATE_TIME + " DESC"
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        List<StatePojo> news_list_db = new ArrayList<>();
        // looping through all rows and adding to list
        while (cursor.moveToNext()) {
            StatePojo md = new StatePojo();
            //Log.d(KEY_ID_DB, cursor.getString(0));
            md.setState_id(cursor.getString(1));
            md.setState_name(cursor.getString(2));


            news_list_db.add(md);
        }
        db.close(); // Closing database connection
        return news_list_db;

    }

    public List<DistrictPojo> getDistrictsViaState(String state_id) {
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_DISTRICT + " where  state_id ='" + state_id + "'";  //+ " ORDER BY " + DATE_TIME + " DESC"
        Log.e("Query",selectQuery);
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        List<DistrictPojo> news_list_db = new ArrayList<>();
        // looping through all rows and adding to list
        while (cursor.moveToNext()) {
            DistrictPojo md = new DistrictPojo();

            md.setDistrict_id(cursor.getString(2));
            md.setDistrict_name(cursor.getString(4));


            news_list_db.add(md);
        }
        db.close(); // Closing database connection
        return news_list_db;

    }

    public List<DistrictPojo> getDistrictsViaState() {
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_DISTRICT ;  //+ " ORDER BY " + DATE_TIME + " DESC"
        Log.e("Query",selectQuery);
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        List<DistrictPojo> news_list_db = new ArrayList<>();
        // looping through all rows and adding to list
        while (cursor.moveToNext()) {
            DistrictPojo md = new DistrictPojo();

            md.setDistrict_id(cursor.getString(2));
            md.setDistrict_name(cursor.getString(4));


            news_list_db.add(md);
        }
        db.close(); // Closing database connection
        return news_list_db;

    }

    public List<TehsilPojo> getTehsilViaDistrict(String district_id) {
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_Tehsil + " where  district_id ='" +district_id+ "'";  //+ " ORDER BY " + DATE_TIME + " DESC"
        Log.e("Qery",selectQuery);
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        List<TehsilPojo> news_list_db = new ArrayList<>();
        // looping through all rows and adding to list
        while (cursor.moveToNext()) {
            TehsilPojo md = new TehsilPojo();

            Log.e("Tehsil1",cursor.getString(0));
            Log.e("2",cursor.getString(1));
            md.setTehsil_id(cursor.getString(1));
            md.setTehsil_name(cursor.getString(2));


            news_list_db.add(md);
        }
        db.close(); // Closing database connection
        return news_list_db;

    }

    public List<GramPanchayatPojo> getGPViaDistrict(String block_id) {
        // Select All Query
        String selectQuery = "SELECT  DISTINCT panchayat_id , panchayat_name FROM " + TABLE_GRAMPANCHAYAT + " where  block_id ='" +block_id+ "'";  //+ " ORDER BY " + DATE_TIME + " DESC"
        Log.e("Qery",selectQuery);
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        List<GramPanchayatPojo> news_list_db = new ArrayList<>();
        // looping through all rows and adding to list
        while (cursor.moveToNext()) {
            GramPanchayatPojo md = new GramPanchayatPojo();

            Log.e("1",cursor.getString(0));
            Log.e("2",cursor.getString(1));
            md.setGp_id(cursor.getString(0));
            md.setGp_name(cursor.getString(1));


            news_list_db.add(md);
        }
        db.close(); // Closing database connection
        return news_list_db;

    }

    public List<BlockPojo> getBlocksViaDistrict(String district_id) {
        // Select All Query
        String selectQuery = "SELECT  DISTINCT  block_id, block_name FROM " + TABLE_BLOCK + " where  district_id ='" +district_id+ "'";  //+ " ORDER BY " + DATE_TIME + " DESC"
        Log.e("Qery",selectQuery);
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        List<BlockPojo> news_list_db = new ArrayList<>();
        // looping through all rows and adding to list
        while (cursor.moveToNext()) {
            BlockPojo block = new BlockPojo();

            Log.e("1",cursor.getString(0));
            Log.e("2",cursor.getString(1));
            block.setBlock_code(cursor.getString(0));
            block.setBlock_name(cursor.getString(1));


            news_list_db.add(block);
        }
        db.close(); // Closing database connection
        return news_list_db;

    }

    //getBarriers
    public List<DistrictBarrierPojo> getBarriers(String district) {
        // Select All Query
        Log.e("district id", district);
        String selectQuery = null;


        // AND (  Proposed_Location_MoU_ Like '%"+ district +"%' OR DIC like '%"+district+"%')
        selectQuery = "SELECT  * FROM " + TABLE_BARRIER + " WHERE district_id  = " + district;  //Proposed_Location_MoU_ Like '%"+ district +"%' OR
        Log.e("Query", selectQuery);

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        List<DistrictBarrierPojo> mou_details_list_db = new ArrayList<>();
        // looping through all rows and adding to list
        while (cursor.moveToNext()) {
            DistrictBarrierPojo md = new DistrictBarrierPojo();
            //Log.d(KEY_ID_DB, cursor.getString(0));
            md.setBarrier_id(cursor.getString(1));
            md.setBarrier_district_id(cursor.getString(2));
            md.setBarrir_name(cursor.getString(3));

            mou_details_list_db.add(md);
        }
        db.close(); // Closing database connection
        return mou_details_list_db;

    }

    public String getDistrictNameById(String district_id) {
        // Select All Query
        Log.e("district id", district_id);
        String selectQuery = null;
        // AND (  Proposed_Location_MoU_ Like '%"+ district +"%' OR DIC like '%"+district+"%')
        selectQuery = "SELECT  district_name FROM " + TABLE_DISTRICT + " WHERE district_id  = '" + district_id+ "'";  //Proposed_Location_MoU_ Like '%"+ district +"%' OR
        Log.e("Query", selectQuery);

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        cursor.moveToFirst();
        String district_Name = null;
        do {
            district_Name = cursor.getString(0);
        } while (cursor.moveToNext());



        db.close(); // Closing database connection
        return district_Name;

    }

    public String getBarrierNameById(String district_id, String barrierId) {
        // Select All Query
        Log.e("district id", district_id);
        Log.e("Barrier id", barrierId);
        String selectQuery = null;
        // AND (  Proposed_Location_MoU_ Like '%"+ district +"%' OR DIC like '%"+district+"%')
        selectQuery = "SELECT  barrier_name FROM " + TABLE_BARRIER + " WHERE district_id  = '" + district_id + "' AND barrier_id='" + barrierId+"'";  //Proposed_Location_MoU_ Like '%"+ district +"%' OR
        Log.e("Query", selectQuery);

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        cursor.moveToFirst();
        String barrierName = null;
        do {
            barrierName = cursor.getString(0);
        } while (cursor.moveToNext());


        db.close(); // Closing database connection
        return barrierName;

    }

    /**
     * @param offline_object
     * @return
     */
    public boolean addOfflineAccess(ResponsePojo offline_object) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(OFFLINE_DATA_URL, offline_object.getUrl());
        values.put(OFFLINE_DATA_REQUEST_PARAMS, offline_object.getRequestParams());
        values.put(OFFLINE_DATA_RESPOSE, offline_object.getResponse());
        values.put(OFFLINE_DATA_PASS_NUMBER, offline_object.getPassNo());
        values.put(OFFLINE_DATA_MOBILE_NUMBER, offline_object.getMobileNumbr());
        values.put(OFFLINE_DATA_NUMBER_PERSONS, offline_object.getPrsonNo());
        values.put(OFFLINE_DATA_VALIDITY, offline_object.getDateIssueDate());
        values.put(OFFLINE_DATA_LATITUDE, offline_object.getLatitude());
        values.put(OFFLINE_DATA_LONGITUDE, offline_object.getLongitude());
        values.put(OFFLINE_DATA_DISTRICT, offline_object.getDistict());
        values.put(OFFLINE_DATA_BARRIER, offline_object.getBarrrier());
        values.put(OFFLINE_DATA_SCANDATE, offline_object.getScanDate());
        values.put(OFFLINE_DATA_SCANNEDBY, offline_object.getScannedByPhoneNumber());
        values.put(OFFLINE_DATA_NUMBER_PERSONS_MANUAL,offline_object.getManual_persons());
        values.put(OFFLINE_DATA_UPLOADEDTOSERVER, offline_object.isUploaddToServeer());


        try {
            db.insert(TABLE_OFFLINE_STORAGE, null, values);
            db.close(); // Closing database connection
            return true;
        } catch (Exception e) {
            Log.d("Got Error ..", e.getLocalizedMessage());
            db.close(); // Closing database connection
            return false;
        }

    }

    //GET ALL Ofline Data
    public List<ResponsePojo> GetAllOfflineDataViaFunction(String fromdate,String todate) {
        // Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_OFFLINE_STORAGE   ;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery,  null);
        List<ResponsePojo> offline_db = new ArrayList<>();
        // looping through all rows and adding to list
        while (cursor.moveToNext()) {
            ResponsePojo data = new ResponsePojo();
            //Log.d(KEY_ID_DB, cursor.getString(0));
            data.setUrl(cursor.getString(1));
            data.setRequestParams(cursor.getString(2));
            data.setResponse(cursor.getString(3));
            data.setPassNo(cursor.getString(4));
            data.setMobileNumbr(cursor.getString(5));
            data.setPrsonNo(cursor.getString(6));
            data.setDateIssueDate(cursor.getString(7));
            data.setLatitude(cursor.getString(8));
            data.setLongitude(cursor.getString(9));
            data.setDistict(cursor.getString(10));
            data.setBarrrier(cursor.getString(11));
            data.setScanDate(cursor.getString(12));
            data.setScannedByPhoneNumber(cursor.getString(13));
            data.setUploaddToServeer((cursor.getInt(14))>0?true:false);

            offline_db.add(data);
        }
        db.close(); // Closing database connection
        return offline_db;

    }

    public int getNoOfRowsCount() {
        String countQuery = "SELECT  * FROM " + TABLE_OFFLINE_STORAGE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
        db.close();
        return count;
    }

    public void dropTables(){

        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DISTRICT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BARRIER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_State);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_OFFLINE_STORAGE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_Tehsil);
    }

}
