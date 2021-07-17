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

        //get intent-book title
        bookTitle=getIntent().getStringExtra("bookTitle");


        //get download link from database
       DatabaseAccess databaseAccess = DatabaseAccess.getInstance(this);
       databaseAccess.open();

        downloadlink=databaseAccess.getDownloadLink(bookTitle);
        downloadlink=downloadlink.replace("/edit?usp=sharing","/export?format=pdf");

        //webview for download
        webView= findViewById(R.id.bookWebView);

        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setAllowContentAccess(true);
        webView.getSettings().setAllowFileAccess(true);
        webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        webView.getSettings().setLoadWithOverviewMode(true);



        webView.loadUrl(downloadlink);

    }

}