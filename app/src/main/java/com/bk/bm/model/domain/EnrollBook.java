package com.bk.bm.model.domain;

import java.util.ArrayList;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by choi on 2017. 9. 23..
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EnrollBook {
    private int book_key;
    private int user_key;
    private String isbn10;
    private String isbn13;
    private String title;
    private int price;
    private ArrayList<String> method;
    private ArrayList<String> area;
    private String status;
    private String comment;
    private ArrayList<String> image;
    private String created_at;
    private String updated_at;
}
