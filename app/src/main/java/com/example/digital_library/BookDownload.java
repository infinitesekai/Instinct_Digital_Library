package com.example.digital_library;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Browser;
import android.provider.ContactsContract;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;

public class BookDownload extends AppCompatActivity {

    private WebView webView;
    String bookTitle;
    String downloadlink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_read);
        bookTitle=getIntent().getStringExtra("bookTitle");
//        downloadlink=getIntent().getStringExtra("downloadlink");

       DatabaseAccess databaseAccess = DatabaseAccess.getInstance(this);
       databaseAccess.open();

        downloadlink=databaseAccess.getDownloadLink(bookTitle);
        downloadlink=downloadlink.replace("/edit?usp=sharing","/export?format=pdf");


        webView= findViewById(R.id.bookWebView);

        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setAllowContentAccess(true);
        webView.getSettings().setAllowFileAccess(true);
        webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        webView.getSettings().setLoadWithOverviewMode(true);
//        clearChromeHistory();


        webView.loadUrl(downloadlink);

    }

//    private void clearChromeHistory(){
//        ContentResolver cr = getContentResolver();
//        Uri historyUri = Uri.parse("content://com.android.chrome.browser/history");
//        deleteChromeHistoryJava(cr, historyUri, null, null);
//
//    }
//
//
//    private void deleteChromeHistoryJava(ContentResolver cr, Uri whereClause, String[] projection, String selection) {
//        Cursor mCursor = null;
//        try {
//            mCursor = cr.query(whereClause, projection, selection,
//                    null, null);
//            Log.i("deleteChromeHistoryJava", " Query: " + whereClause);
//            if (mCursor != null) {
//                mCursor.moveToFirst();
//                int count = mCursor.getColumnCount();
//                String COUNT = String.valueOf(count);
//                Log.i("deleteChromeHistoryJava", " mCursor count" + COUNT);
//                String url = "";
//                if (mCursor.moveToFirst() && mCursor.getCount() > 0) {
//                    while (!mCursor.isAfterLast()) {
//                        url = mCursor.getString(mCursor.getColumnIndex(Browser.BookmarkColumns.URL));
//                        Log.i("deleteChromeHistoryJava", " url: " + url);
//                        mCursor.moveToNext();
//                    }
//                }
//                cr.delete(whereClause, selection, null);
//                Log.i("deleteChromeHistoryJava", " GOOD");
//            }
//        } catch (IllegalStateException e) {
//            Log.i("deleteChromeHistoryJava", " IllegalStateException: " + e.getMessage());
//        } finally {
//            if (mCursor != null) mCursor.close();
//        }
//    }
}