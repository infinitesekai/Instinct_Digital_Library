package com.example.digital_library;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DatabaseAccess<instance> {

    private final SQLiteOpenHelper openHelper;
    private SQLiteDatabase database;
    private static DatabaseAccess instance;

    public DatabaseAccess(Context context) {
        this.openHelper = new DatabaseHelper(context);
    }

    public static DatabaseAccess getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseAccess(context);
        }
        return instance;
    }

    public void open() {
        this.database = openHelper.getWritableDatabase();
    }

    public void close() {
        if (database != null) {
            this.database.close();
        }
    }

    //check user
    public User checkUser(String useremail) {

        String queryUser = "Select email from User  where email = '" + useremail+ "'";
        Cursor cursor = database.rawQuery(queryUser, null);
        User user = new User();
        if (cursor.getCount() > 0) {
            cursor.moveToNext();
            String email = cursor.getString(0);
            user.setEmail(email);
        }
        cursor.close();
        return user;
    }


    //new user-insert into database
    public boolean insertUser(String email, String pass, String firstname, String lastname) {

        String insert = "insert into User(email,password,firstname,lastname) values ( '" + email + "','" + pass + "','" + firstname + "','" + lastname + "');";

        try {
            database.execSQL(insert);
        } catch (RuntimeException e) {
            Log.d("1122334455", e.getLocalizedMessage());
            return false;
        } finally {
            database.close();
        }
        return true;
    }

    //check ic and password
    public User verifyAcc(String useremail, String pwd) {

        String queryUser = "Select email,firstname,lastname,gender,phoneNo,bdate from User  where email = '" + useremail+ "' and password = '" + pwd + "'";
        Cursor cursor = database.rawQuery(queryUser, null);
        User user = new User();
        if (cursor.getCount() > 0) {
            cursor.moveToNext();
            String email = cursor.getString(0);
            String firstname = cursor.getString(1);
            String lastname = cursor.getString(2);
            String gender = cursor.getString(3);
            String phone = cursor.getString(4);
            String bdate = cursor.getString(5);

            user.setEmail(email);
            user.setFirstname(firstname);
            user.setLastname(lastname);
            user.setGender(gender);
            user.setPhoneNo(phone);
            user.setBdate(bdate);

        }
        cursor.close();
        return user;
    }

    //update user profile information
    public boolean updateUser(User user) {

        String update_user = "update User set Bdate ='" + user.getBdate() + "', firstname='" + user.getFirstname() +
                "',lastname='" + user.getLastname() + "',gender='" + user.getGender() +
                "',phoneNo='" + user.getPhoneNo() + "' where email = '" + user.getEmail() + "'";
        try {
            database.execSQL(update_user);
        } catch (RuntimeException e) {
            return false;
        }
        return true;
    }

    public List<String> getBookList(String genre) {

        List<String> list_item = new ArrayList<String>();

        Cursor cursor = database.rawQuery("SELECT title FROM Book where genre=?",new String[]{genre});


        if (cursor.moveToFirst()) {
            do {
                String book = cursor.getString(0);
                BookList.list_item.add(book);//add the book name into the list in BookList.java

            } while (cursor.moveToNext());
        }
        cursor.close();
        return list_item;
    }

    public BookInfo DisplayBook(String title) {
        BookInfo book = null;
        Cursor cursor = database.rawQuery("SELECT title,author,genre,synopsis,country,publisher " +
                "FROM Book WHERE title = ?", new String[]{title});
        if (cursor.moveToFirst()) {
            book= new BookInfo(cursor.getString(0), cursor.getString(1), cursor.getString(2)
                    , cursor.getString(3), cursor.getString(4), cursor.getString(5));
        }
        cursor.close();
        return book;
    }

    public byte[] getCover(String title){
        byte[] result=null;
        Cursor cursor = database.rawQuery("SELECT cover " +
                "FROM Book WHERE title = ?", new String[]{title});

        if(cursor.moveToFirst()){
            do{
                result=cursor.getBlob(0);
            }while (cursor.moveToNext());
        }
        return result;
    }

        public byte[] getPic(String email){
        byte[] result=null;
        Cursor cursor = database.rawQuery("SELECT photo " +
                "FROM User WHERE email = ?", new String[]{email});

        if(cursor.moveToFirst()){
            do{
                result=cursor.getBlob(0);
            }while (cursor.moveToNext());
        }
        return result;
    }

    public boolean addPhoto(String email,byte[] image){

//        String update_photo = "update User set photo ='" + image + "'where email = '" + email + "'";
//        try {
//            database.execSQL(update_photo);
//        } catch (RuntimeException e) {
//           e.printStackTrace();
//           //return false;
//        }
//        //return true;

        ContentValues cv=new ContentValues();
        cv.put("photo",image);
        database.update("User",cv,"email=?",new String[]{email});
        return true;

    }

    public String getLink(String title) {
        String link = "";
        Cursor cursor = database.rawQuery("SELECT pdflink FROM Book WHERE title=?", new String[]{title});
        if (cursor.moveToFirst()) {
            link  = new String(cursor.getString(0));

        }
        cursor.close();
        return link ;
    }

    public String getDownloadLink(String title) {
        String link = "";
        Cursor cursor = database.rawQuery("SELECT downloadlink FROM Book WHERE title=?", new String[]{title});
        if (cursor.moveToFirst()) {
            if(!cursor.isNull(0))
                link  = new String(cursor.getString(0));

        }
        cursor.close();
        return link ;
    }

    public boolean insertFav(String email, String title) {

        String insertfav = "insert into Favourite(email,title) values ( '" + email + "','" + title + "');";

        try {
            database.execSQL(insertfav);
        } catch (RuntimeException e) {
            return false;
        }
        return true;
    }

    public boolean checkFav(String useremail, String title) {

        boolean fav=false;
        Cursor cursor = database.rawQuery("select email from Favourite where email=? and title=?", new String[]{useremail,title});
        if (cursor.moveToFirst()) {
            fav=true;
        }
        cursor.close();
        return fav ;
    }

    public boolean removeFav(String useremail,String title){
        String remove = "delete from Favourite where email = '" + useremail + "'and title = '" + title + "'";
        Boolean result = true;
        try {
            database.execSQL(remove);
        } catch (Exception e) {
            result = false;
        }
        return result;
    }

//    public Cursor getFavList(String useremail){
//        String fav = "select * from Favourite where email = '" + useremail + "'" ;
//        return database.rawQuery(fav,null,null);
//    }

    public List<String> getFavList(String useremail) {

        List<String> list_item = new ArrayList<String>();

        Cursor cursor = database.rawQuery("SELECT title FROM Favourite where email = ?",new String[]{useremail});


        if (cursor.moveToFirst()) {
            do {
                String book = cursor.getString(0);
               FavouritePage.list_item.add(book);//add the book name into the list in BookList.java

            } while (cursor.moveToNext());
        }
        cursor.close();
        return list_item;
    }




}
