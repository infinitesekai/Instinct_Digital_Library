package com.example.digital_library;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebView;

public class BookRead extends AppCompatActivity {

    private WebView webView;
    String bookTitle;
    String pdflink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_read);
        //get intent
        bookTitle=getIntent().getStringExtra("bookTitle");

        //get pdf link in database
        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(this);
        databaseAccess.open();

        pdflink=databaseAccess.getLink(bookTitle);

        //webview

        webView= findViewById(R.id.bookWebView);

        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setAllowContentAccess(true);
        webView.getSettings().setAllowFileAccess(true);
         webView.loadUrl(pdflink);

    }
}