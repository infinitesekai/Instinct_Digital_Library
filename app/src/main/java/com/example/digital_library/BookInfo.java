package com.example.digital_library;

public class BookInfo {
    private String title;
    private String author;
    private String genre;
    private String synopsis;
    private String country;
    private String publisher;
    //private byte[] cover;


    public BookInfo(String title, String author, String genre, String synopsis, String country, String publisher) {
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.synopsis = synopsis;
        this.country = country;
        this.publisher = publisher;
        //this.cover = cover;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

//    public byte[] getCover() {
//        return cover;
//    }
//
//    public void setCover(byte[] cover) {
//        this.cover = cover;
//    }


}