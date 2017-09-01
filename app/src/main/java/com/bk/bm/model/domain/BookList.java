package com.bk.bm.model.domain;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by choi on 2017. 8. 30..
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookList {
    private String kind;
    private int totalItems;
    private ArrayList<BookInfo> items;

    @Data
    @AllArgsConstructor
    public class BookInfo {
        private VolumeInfo volumeInfo;
    }

    @Data
    @AllArgsConstructor
    public class VolumeInfo {
        private String title;
        private ArrayList<String> authors;
        private ArrayList<Identity> industryIdentifiers;
        private BookImage imageLinks;
    }

    @Data
    @AllArgsConstructor
    public class BookImage {
        private String thumbnail;
        private String smallThumbnail;
    }

    @Data
    @AllArgsConstructor
    public class Identity {
        private String type;
        private String identifier;
    }
}
