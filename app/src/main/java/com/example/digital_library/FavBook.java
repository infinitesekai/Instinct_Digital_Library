package com.example.digital_library;

public class FavBook {
    private String book_title;
    private byte[] book_cover;


    public FavBook(){

    }

    public FavBook(String book_title, byte[] book_cover) {
        this.book_title = book_title;
        this.book_cover = book_cover;
    }

    public String getBook_title() {
        return book_title;
    }

    public void setBook_title(String book_title) {
        this.book_title = book_title;
    }

    public byte[] getBook_cover() {
        return book_cover;
    }

    public void setBook_cover(byte[] book_cover) {
        this.book_cover = book_cover;
    }
}
