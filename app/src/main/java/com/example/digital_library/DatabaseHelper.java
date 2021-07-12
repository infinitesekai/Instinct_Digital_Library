//package com.example.mysekolah;
//
//import android.content.Context;
//import android.database.Cursor;
//import android.database.sqlite.SQLiteDatabase;
//import android.database.sqlite.SQLiteOpenHelper;
//import android.os.Build;
//import android.os.Environment;
//import android.util.Log;
//
//import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;
//
//import java.io.File;
//import java.io.IOException;
//import java.util.ArrayList;
//
////public class DatabaseHelper  extends SQLiteOpenHelper {
////
////    public static final String DBNAME = "MySekolahDB.db";
////    //public static final String DBLOCATION = "/data/data/com.example.mysekolah/databases/";
////    public static final String TABLE_NAME1= "Resident";
////    public static final String TABLE_NAME2= "User";
////    //private Context mContext;
////    //private SQLiteDatabase mDatabase;
////    private static final int DATABASE_VERSION = 1;
////
////    public DatabaseHelper(Context context) {
////        super(context, DBNAME, null, DATABASE_VERSION);
////        //this.mContext = context;
//////        String path = Environment.getExternalStorageDirectory().getAbsolutePath()+"/databases";
//////        File pathFile = new File(path);
//////        File file = new File(path+DBNAME);
//////        try{
//////            if(!pathFile.exists()){
//////                pathFile.mkdirs();
//////            }
//////            if(!file.exists()){
//////                file.createNewFile();
//////            }
//////        } catch (IOException e) {
//////            e.printStackTrace();
//////        }
//////        SQLiteDatabase.openOrCreateDatabase(file,null);
////    }
////
////    @Override
////    public void onCreate(SQLiteDatabase db) {
//////        String sql1 = "CREATE TABLE IF NOT EXISTS User (\n" +
//////                "  UserID integer PRIMARY KEY AUTOINCREMENT,\n" +
//////                "  ICNo integer NOT NULL,\n" +
//////                "  Name text,\n" +
//////                "  Role text,\n" +
//////                "  Password text,\n" +
//////                "  Gender text,\n" +
//////                "  Bdate text,\n" +
//////                "  Job text,\n" +
//////                "  Salary integer,\n" +
//////                "  Address text,\n" +
//////                "  PhoneNo text\n" +
//////                ");";
//////        String sql2 = "CREATE TABLE IF NOT EXISTS  Resident (\n" +
//////                "  ICNo text PRIMARY KEY NOT NULL,\n" +
//////                "  \"Name \" text,\n" +
//////                "  Gender text,\n" +
//////                "  Bdate text,\n" +
//////                "  Address text,\n" +
//////                "  Races text,\n" +
//////                "  Religion text,\n" +
//////                "  Nationality text\n" +
//////                ");";
//////        db.execSQL(sql1);
//////        db.execSQL(sql2);
////    }
////
////    @Override
////    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
////
////    }
////
////    //校验用户是否允许成为user
////    public ArrayList checkuseric(String ic) {
////        SQLiteDatabase db = this.getWritableDatabase();
////        String querySql = "select ICNo,Name,Gender,Races,Religion,Nationality from Resident where ICNo = '" + ic +"';";
////        Cursor cursor = db.rawQuery(querySql, null);
////        ArrayList<User> userArrayList = new ArrayList<User>();
////        while(cursor.moveToNext()) {
////            //光标移动成功
////            String icno = cursor.getString(0);
////            String name = cursor.getString(1);
////            String gender = cursor.getString(2);
////            String races = cursor.getString(3);
////            String religion = cursor.getString(4);
////            String nation = cursor.getString(5);
////            User user = new User();
////            user.setICNo(icno);
////            user.setName(name);
////            user.setRace(races);
////            user.setReligion(religion);
////            user.setNation(nation);
////            user.setGender(gender);
////            userArrayList.add(user);
////        }
////        cursor.close();
////        return userArrayList;
////    }
////
////    //新增用户
////    public boolean inserData(String ic, String pass, int role, String name, String phone, String address) {
////        SQLiteDatabase db = this.getWritableDatabase();
//////        String insertSql = "insert into User(icNo, Name, Role, Password) value('" + ic +"','"+ name + "','" + role +"','"+ pass +"')";
////        String insertSql = "insert into User(ICNo,Name, Role, Password, PhoneNo, Address) select '" + ic+ "','" + name + "','" + role +"','" + pass + "','" + phone + "','" + address +"' where not exists (select * from User where icNo = '"+ ic +"');";
////
////        try {
////            db.execSQL(insertSql);
////        } catch (RuntimeException e) {
////            Log.d("1122334455",e.getLocalizedMessage());
////            return false;
////        }  finally {
////           db.close();//add
////        }
////        return true;
////    }
////
////    //校验用户名密码
////    public User checkusericpassword(String ic, String pwd) {
////        SQLiteDatabase db = this.getWritableDatabase();
////        String queryUser = "Select u.icNo, u.role,u.name, u.gender,u.job,u.salary,u.address,u.phoneno, r.races, r.religion, r.gender, r.nationality, u.bdate from User u, Resident r  where u.icNo = '"+ ic +"' and u.Password = '" + pwd +"'";
////        Cursor cursor = db.rawQuery(queryUser, null);
////        User user = new User();
////        if (cursor.getCount() > 0) {
////            cursor.moveToNext();
////            String icno = cursor.getString(0);
////            int role = cursor.getInt(1);
////            String name = cursor.getString(2);
////            String gender = cursor.getString(3);
////            String job = cursor.getString(4);
////            String salary = cursor.getString(5);
////            String address = cursor.getString(6);
////            String phone = cursor.getString(7);
////            String races = cursor.getString(8);
////            String religion = cursor.getString(9);
////            String gender2 = cursor.getString(10);
////            String nation = cursor.getString(11);
////            String bdate = cursor.getString(12);
////            user.setICNo(icno);
////            user.setRole(role);
////            user.setName(name);
////            user.setGender(gender2);
////            user.setJob(job);
////            user.setSalary(salary);
////            user.setAddress(address);
////            user.setPhoneNo(phone);
////            user.setRace(races);
////            user.setReligion(religion);
////            user.setNation(nation);
////            user.setBdate(bdate);
////        }
////        cursor.close();
////        return user;
////    }
////
////    //更新user信息
////    public boolean updateUser(User user) {
////        SQLiteDatabase db = this.getWritableDatabase();
////        String update_user = "update User set Bdate ='"+ user.getBdate() +"', salary='"+ user.getSalary() +
////                "',job='"+ user.getJob() +"',address='"+ user.getAddress() +
////                "',phoneno='" + user.getPhoneNo() +"' where icNo = '" + user.getICNo() +"'";
////        try {
////            db.execSQL(update_user);
////        } catch (RuntimeException e) {
////            return false;
////        }
////        return true;
////    }
////    /*@Override
////    public void onCreate(SQLiteDatabase db) {
////       super.onOpen(db);
////       db.disableWriteAheadLogging();
////    }
////    @Override
////    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
////    }
////    public void openDatabase() {
////        String dbPath = mContext.getDatabasePath(DBNAME).getPath();
////        if(mDatabase != null && mDatabase.isOpen()) {
////            return;
////        }
////        mDatabase = SQLiteDatabase.openDatabase(dbPath, null, SQLiteDatabase.OPEN_READWRITE);
////    }
////    public void closeDatabase() {
////        if(mDatabase!=null) {
////            mDatabase.close();
////        }
////    }*/
////
////   /* public Residents getResidentbyIC(String IC) {
////        Residents residents= null;
////        openDatabase();
////        Cursor cursor = mDatabase.rawQuery("SELECT * FROM " + TABLE_NAME1 +" WHERE ICNo = ? ", new String[] {String.valueOf(IC)});
////        cursor.moveToFirst();
////        residents = new Residents(cursor.getString(0), cursor.getString(1), cursor.getString(2)
////                                ,cursor.getString(5), cursor.getString(6), cursor.getString(7));
////        cursor.close();
////        closeDatabase();
////        return residents;
////    }*/
////}

package com.example.digital_library;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

public class DatabaseHelper extends SQLiteAssetHelper {

    public static final String DBNAME = "LibraryDB.db";

    private static final int DATABASE_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DBNAME, null, DATABASE_VERSION);

    }

}