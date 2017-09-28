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
public class Content {

    private ArrayList<EnrollBook> books;
    private int totalPages;
    private boolean last;
    private int totalElements;
    private int size;
    private int number;

}
