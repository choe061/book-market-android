package com.bk.bm.util;

import okhttp3.MediaType;

/**
 * Created by choi on 2017. 8. 17..
 */

public final class Constants {

    private Constants() {}
    public static final String BASE_URL = "http://book.jinhyung.kim/";
    public static final String FIREBASE_USER_TOKEN = "access_token";
    public static final String FIREBASE_MSG_TOKEN = "msg_token";
    public static final String GOOGLE_BOOKS_API_KEY = "AIzaSyCZhSVWr5gsryFrs67piZcRrTr9qj0LzP4";

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
}
