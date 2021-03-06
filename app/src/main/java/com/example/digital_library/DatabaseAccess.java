package com.example.digital_library;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorWindow;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.lang.reflect.Field;
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

        String queryUser = "SELECT  email FROM User  WHERE email = '" + useremail+ "'";
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

        String insert = "INSERT INTO User(email,password,firstname,lastname) VALUES ( '" + email + "','" + pass + "','" + firstname + "','" + lastname + "');";

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

        String queryUser = "SELECT email,firstname,lastname,gender,phoneNo,bdate FROM User  WHERE email = '" + useremail+ "' and password = '" + pwd + "'";
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

        String update_user = "UPDATE User SET Bdate ='" + user.getBdate() + "', firstname='" + user.getFirstname() +
                "',lastname='" + user.getLastname() + "',gender='" + user.getGender() +
                "',phoneNo='" + user.getPhoneNo() + "' WHERE email = '" + user.getEmail() + "'";
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

            try {
                Field field = CursorWindow.class.getDeclaredField("sCursorWindowSize");
                field.setAccessible(true);
                field.set(null, 100 * 1024 * 1024); //new size 100MB
            } catch (Exception e) {
                e.printStackTrace();
            }

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

        String insertfav = "INSERT INTO Favourite(email,title) VALUES ( '" + email + "','" + title + "');";

        try {
            database.execSQL(insertfav);
        } catch (RuntimeException e) {
            return false;
        }
        return true;
    }

    public boolean checkFav(String useremail, String title) {

        boolean fav=false;
        Cursor cursor = database.rawQuery("SELECT email FROM Favourite WHERE email=? AND title=?", new String[]{useremail,title});
        if (cursor.moveToFirst()) {
            fav=true;
        }
        cursor.close();
        return fav ;
    }

    public boolean removeFav(String useremail,String title){
        String remove = "DELETE FROM Favourite WHERE email = '" + useremail + "'AND title = '" + title + "'";
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
//               FavouritePage.list_item.add(book);
                FavouritePage.favlist_item.add(book);

            } while (cursor.moveToNext());
        }
        cursor.close();
        return list_item;
    }

    public List<String> getAllBook() {
        List<String> books = new ArrayList<String>();

        Cursor cursor = database.rawQuery("SELECT title FROM Book",null);
        if (cursor.moveToFirst()) {
            do {
                books.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }

        cursor.close();
        return books;
    }

    public boolean insertPhyReq(String email,String name,String address,String contact,String title,String returndate,String remark) {

        String insertphy = "INSERT INTO PhysicalReq(email,name,address,contact,title,returndate,remark) VALUES ( '" + email + "','" + name + "','" +
                address + "','" + contact + "','" + title + "','" + returndate + "','" + remark
                + "');";

        try {
            database.execSQL(insertphy);
        } catch (RuntimeException e) {
            return false;
        }
        return true;
    }

    public  ArrayList<byte[]> getAllCover(String genre){
        ArrayList<byte[]> covers = new ArrayList<byte[]>();
        Cursor cursor = database.rawQuery("SELECT cover " +
                "FROM Book WHERE genre = ?", new String[]{genre});

        if(cursor.moveToFirst()){
            do{
                covers.add(cursor.getBlob(0));
            }while (cursor.moveToNext());
        }
        return covers;
    }

    public  ArrayList<byte[]> getFavCover(String email){
        ArrayList<byte[]> covers = new ArrayList<byte[]>();
        Cursor cursor = database.rawQuery("SELECT cover " +
                "FROM Favourite JOIN Book ON Favourite.title=Book.title WHERE email = ?", new String[]{email});

        if(cursor.moveToFirst()){
            do{
                covers.add(cursor.getBlob(0));
            }while (cursor.moveToNext());
        }
        return covers;
    }




}
