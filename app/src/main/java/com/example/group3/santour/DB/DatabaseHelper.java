package com.example.group3.santour.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by kevin on 17/11/17.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    // Logcat tag
    private final String LOG = "DatabaseHelper";

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "dbSanTour";

    // Table Names
    private final String TABLE_USER = "user";
    private final String TABLE_ROLE = "role";
    private final String TABLE_TRACK = "track";
    private final String TABLE_POI = "POI";
    private final String TABLE_POD = "POD";
    private final String TABLE_PODCATEGORY = "PODCategory";
    private final String TABLE_CATEGORY = "category";
    private final String TABLE_POSITION = "position";
    private final String TABLE_TYPE = "type";

    // Common column names
    private final String KEY_ID = "id";

    // User Table - column names
    private final String KEY_USER_USERNAME = "username";
    private final String KEY_USER_PASSWORD = "password";
    private final String KEY_USER_EMAIL = "email";
    private final String KEY_USER_ROLEID = "idRole";

    // Role Table - column names
    private final String KEY_ROLE_NAME = "name";

    // Track Table - column names
    private final String KEY_TRACK_NAME = "name";
    private final String KEY_TRACK_DESC = "description";
    private final String KEY_TRACK_DISTANCE = "distance";
    private final String KEY_TRACK_DURATION = "duration";
    private final String KEY_TRACK_TYPEID = "idType";

    // Type Table - column names
    private final String KEY_TYPE_NAME = "name";

    //POI Table - column names
    private final String KEY_POI_NAME = "name";
    private final String KEY_POI_PICTURE = "name";
    private final String KEY_POI_DESCRIPTION = "description";
    private final String KEY_POI_POSITIONID = "idPosition";
    private final String KEY_POI_TRACKID = "idTrack";

    // Position Table - column names
    private final String KEY_POSITION_TRACKID = "idTrack";
    private final String KEY_POSITION_LATITUDE = "latitude";
    private final String KEY_POSITION_LONGITUDE = "longitude";
    private final String KEY_POSITION_DATETIME = "datetime";
    private final String KEY_POSITION_ALTITUDE = "altitude";

    // POD Table - column names
    private final String KEY_POD_NAME = "name";
    private final String KEY_POD_PICTURE = "picture";
    private final String KEY_POD_DESCRIPTION = "description";
    private final String KEY_POD_POSITIONID = "idPosition";
    private final String KEY_POD_TRACKID = "idTrack";

    // Category Table - column names
    private final String KEY_CATEGORY_NAME = "name";

    // PODCategory Table - column names
    private final String KEY_PODCATEGORY_PODID = "idPOD";
    private final String KEY_PODCATEGORY_CATEGORYID = "idCategory";
    private final String KEY_PODCATEGORY_VALUE = "value";


    // Table Create Statements
    // User table create statement
    private final String CREATE_TABLE_USER =
            "CREATE TABLE " + TABLE_USER
                    + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + KEY_USER_USERNAME + " TEXT NOT NULL,"
                    + KEY_USER_PASSWORD + " TEXT NOT NULL,"
                    + KEY_USER_EMAIL + " TEXT NOT NULL,"
                    + KEY_USER_ROLEID + " INTEGER NOT NULL"
                    + "FOREIGN KEY (" + KEY_USER_ROLEID + ") REFERENCES " + TABLE_ROLE + "(" + KEY_ID + "))";

    // Role table create statement
    private final String CREATE_TABLE_ROLE =
            "CREATE TABLE " + TABLE_ROLE
                    + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + KEY_ROLE_NAME + " TEXT NOT NULL)";

    // Track table create statement
    private final String CREATE_TABLE_TRACK =
            "CREATE TABLE " + TABLE_TRACK
                    + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + KEY_TRACK_NAME + " TEXT NOT NULL,"
                    + KEY_TRACK_DESC + " TEXT NOT NULL,"
                    + KEY_TRACK_DISTANCE + " REAL NOT NULL,"
                    + KEY_TRACK_DURATION + " INTEGER NOT NULL,"
                    + KEY_TRACK_TYPEID + " INTEGER NOT NULL,"
                    + "FOREIGN KEY (" + KEY_TRACK_TYPEID + ") REFERENCES " + TABLE_TYPE + "(" + KEY_ID + "))";

    // Type table create statement
    private final String CREATE_TABLE_TYPE =
            "CREATE TABLE " + TABLE_TYPE
                    + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + KEY_TYPE_NAME + " TEXT NOT NULL)";

    // Position table create statement
    private final String CREATE_TABLE_POSITION =
            "CREATE TABLE " + TABLE_POSITION
                    + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + KEY_POSITION_LATITUDE + " REAL NOT NULL,"
                    + KEY_POSITION_LONGITUDE + " REAL NOT NULL,"
                    + KEY_POSITION_ALTITUDE + " REAL NOT NULL,"
                    + KEY_POSITION_DATETIME + " DATETIME NOT NULL,"
                    + KEY_POSITION_TRACKID + " INTEGER NOT NULL,"
                    + "FOREIGN KEY (" + KEY_POSITION_TRACKID + ") REFERENCES " + TABLE_TRACK + "(" + KEY_ID + "))";

    // POI table create statement
    private final String CREATE_TABLE_POI =
            "CREATE TABLE " + TABLE_POI
                    + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + KEY_POI_NAME + " TEXT NOT NULL,"
                    + KEY_POI_DESCRIPTION + "TEXT,"
                    + KEY_POI_PICTURE + "TEXT,"
                    + KEY_POI_POSITIONID + " INTEGER NOT NULL,"
                    + "FOREIGN KEY (" + KEY_POI_POSITIONID + ") REFERENCES " + TABLE_POSITION + "(" + KEY_ID + "),"
                    + KEY_POI_TRACKID + " INTEGER NOT NULL,"
                    + "FOREIGN KEY (" + KEY_POI_TRACKID + ") REFERENCES " + TABLE_TRACK + "(" + KEY_ID + "))";

    // Category table create statement
    private final String CREATE_TABLE_CATEGORY =
            "CREATE TABLE " + TABLE_CATEGORY
                    + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + KEY_CATEGORY_NAME + " TEXT NOT NULL)";

    // POD table create statement
    private final String CREATE_TABLE_POD =
            "CREATE TABLE " + TABLE_POD
                    + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + KEY_POD_NAME + " TEXT NOT NULL,"
                    + KEY_POD_DESCRIPTION + " TEXT,"
                    + KEY_POD_PICTURE + " TEXT,"
                    + KEY_POD_POSITIONID + " INTEGER NOT NULL,"
                    + "FOREIGN KEY (" + KEY_POD_POSITIONID + ") REFERENCES " + TABLE_POSITION + "(" + KEY_ID + "),"
                    + KEY_POD_TRACKID + " INTEGER NOT NULL,"
                    + "FOREIGN KEY (" + KEY_POD_TRACKID + ") REFERENCES " + TABLE_TRACK + "(" + KEY_ID + "))";

    // PODCategory table create statement
    private final String CREATE_TABLE_PODCATEGORY =
            "CREATE TABLE " + TABLE_PODCATEGORY
                    + KEY_PODCATEGORY_PODID + " INTEGER NOT NULL,"
                    + KEY_PODCATEGORY_CATEGORYID + " INTEGER NOT NULL,"
                    + KEY_PODCATEGORY_VALUE + " INTEGER NOT NULL,"
                    + "PRIMARY KEY(" + KEY_PODCATEGORY_PODID + "," + KEY_PODCATEGORY_CATEGORYID + ")"
                    + "FOREIGN KEY (" + KEY_PODCATEGORY_PODID + ") REFERENCES " + TABLE_POD + "(" + KEY_ID + "),"
                    + "FOREIGN KEY (" + KEY_PODCATEGORY_CATEGORYID + ") REFERENCES " + TABLE_CATEGORY + "(" + KEY_ID + "))";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // creating required tables
        db.execSQL(CREATE_TABLE_ROLE);
        db.execSQL(CREATE_TABLE_USER);
        db.execSQL(CREATE_TABLE_TYPE);
        db.execSQL(CREATE_TABLE_TRACK);
        db.execSQL(CREATE_TABLE_POSITION);
        db.execSQL(CREATE_TABLE_POI);
        db.execSQL(CREATE_TABLE_CATEGORY);
        db.execSQL(CREATE_TABLE_POD);
        db.execSQL(CREATE_TABLE_PODCATEGORY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ROLE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TYPE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TRACK);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_POSITION);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_POI);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CATEGORY);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_POD);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PODCATEGORY);


        // create new tables
        onCreate(db);
    }

    // Getters


    public String getTABLE_USER() {
        return TABLE_USER;
    }

    public String getTABLE_ROLE() {
        return TABLE_ROLE;
    }

    public String getTABLE_TRACK() {
        return TABLE_TRACK;
    }

    public String getTABLE_POI() {
        return TABLE_POI;
    }

    public String getTABLE_POD() {
        return TABLE_POD;
    }

    public String getTABLE_PODCATEGORY() {
        return TABLE_PODCATEGORY;
    }

    public String getTABLE_CATEGORY() {
        return TABLE_CATEGORY;
    }

    public String getTABLE_POSITION() {
        return TABLE_POSITION;
    }

    public String getTABLE_TYPE() {
        return TABLE_TYPE;
    }

    public String getKEY_ID() {
        return KEY_ID;
    }

    public String getKEY_USER_USERNAME() {
        return KEY_USER_USERNAME;
    }

    public String getKEY_USER_PASSWORD() {
        return KEY_USER_PASSWORD;
    }

    public String getKEY_USER_EMAIL() {
        return KEY_USER_EMAIL;
    }

    public String getKEY_USER_ROLEID() {
        return KEY_USER_ROLEID;
    }

    public String getKEY_ROLE_NAME() {
        return KEY_ROLE_NAME;
    }

    public String getKEY_TRACK_NAME() {
        return KEY_TRACK_NAME;
    }

    public String getKEY_TRACK_DESC() {
        return KEY_TRACK_DESC;
    }

    public String getKEY_TRACK_DISTANCE() {
        return KEY_TRACK_DISTANCE;
    }

    public String getKEY_TRACK_DURATION() {
        return KEY_TRACK_DURATION;
    }

    public String getKEY_TRACK_TYPEID() {
        return KEY_TRACK_TYPEID;
    }

    public String getKEY_TYPE_NAME() {
        return KEY_TYPE_NAME;
    }

    public String getKEY_POI_NAME() {
        return KEY_POI_NAME;
    }

    public String getKEY_POI_PICTURE() {
        return KEY_POI_PICTURE;
    }

    public String getKEY_POI_DESCRIPTION() {
        return KEY_POI_DESCRIPTION;
    }

    public String getKEY_POI_POSITIONID() {
        return KEY_POI_POSITIONID;
    }

    public String getKEY_POI_TRACKID() {
        return KEY_POI_TRACKID;
    }

    public String getKEY_POSITION_TRACKID() {
        return KEY_POSITION_TRACKID;
    }

    public String getKEY_POSITION_LATITUDE() {
        return KEY_POSITION_LATITUDE;
    }

    public String getKEY_POSITION_LONGITUDE() {
        return KEY_POSITION_LONGITUDE;
    }

    public String getKEY_POSITION_DATETIME() {
        return KEY_POSITION_DATETIME;
    }

    public String getKEY_POSITION_ALTITUDE() {
        return KEY_POSITION_ALTITUDE;
    }

    public String getKEY_POD_NAME() {
        return KEY_POD_NAME;
    }

    public String getKEY_POD_PICTURE() {
        return KEY_POD_PICTURE;
    }

    public String getKEY_POD_DESCRIPTION() {
        return KEY_POD_DESCRIPTION;
    }

    public String getKEY_POD_POSITIONID() {
        return KEY_POD_POSITIONID;
    }

    public String getKEY_POD_TRACKID() {
        return KEY_POD_TRACKID;
    }

    public String getKEY_CATEGORY_NAME() {
        return KEY_CATEGORY_NAME;
    }

    public String getKEY_PODCATEGORY_PODID() {
        return KEY_PODCATEGORY_PODID;
    }

    public String getKEY_PODCATEGORY_CATEGORYID() {
        return KEY_PODCATEGORY_CATEGORYID;
    }

    public String getKEY_PODCATEGORY_VALUE() {
        return KEY_PODCATEGORY_VALUE;
    }
}
