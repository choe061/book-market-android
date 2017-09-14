package com.bk.bm.util;

/**
 * Created by choi on 2017. 8. 28..
 */

public class EventData<M> {
    private Book book;
    private M message;

    public EventData(Book book, M message) {
        this.book = book;
        this.message = message;
    }

    public Book getType() {
        return book;
    }

    public void setType(Book book) {
        this.book = book;
    }

    public Object getMessage() {
        return message;
    }

    public void setMessage(M message) {
        this.message = message;
    }

    public enum Book {
        ISBN_10, ISBN_13, TITLE, BOOK, MIN_PRICE, MAX_PRICE, METHOD, AREA, TYPE, COMMENT, IMAGE
    }
}
