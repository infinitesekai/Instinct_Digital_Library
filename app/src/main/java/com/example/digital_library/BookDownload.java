package com.example.digital_library;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebView;

public class BookDownload extends AppCompatActivity {

    private WebView webView;
//    String bookTitle;
    String downloadlink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_read);
//        bookTitle=getIntent().getStringExtra("bookTitle");
        downloadlink=getIntent().getStringExtra("downloadlink");

//        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(this);
//        databaseAccess.open();
//
//        downloadlink=databaseAccess.getDownloadLink(bookTitle);


        webView= findViewById(R.id.bookWebView);

        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setAllowContentAccess(true);
        webView.getSettings().setAllowFileAccess(true);
        webView.loadUrl(downloadlink);

    }
}