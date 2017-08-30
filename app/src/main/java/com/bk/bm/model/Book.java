package com.bk.bm.model;

import java.lang.reflect.Array;
import java.util.ArrayList;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by choi on 2017. 8. 29..
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Book {
    private String isbn10;
    private String isbn13;
    private String title;
    private int price;
    private ArrayList<String> area;
    private String status;
    private String comment;
    private ArrayList<String> image;
}
