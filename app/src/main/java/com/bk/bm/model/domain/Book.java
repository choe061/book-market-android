package com.bk.bm.model.domain;

import android.os.Parcel;
import android.os.Parcelable;

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
public class Book implements Parcelable {
    private String isbn10;
    private String isbn13;
    private String title;
    private int price;
    private ArrayList<String> method;
    private ArrayList<String> area;
    private String status;
    private String comment;
    private ArrayList<String> image;

    protected Book(Parcel in) {
        isbn10 = in.readString();
        isbn13 = in.readString();
        title = in.readString();
        price = in.readInt();
        area = in.createStringArrayList();
        status = in.readString();
        comment = in.readString();
        image = in.createStringArrayList();
    }

    public static final Creator<Book> CREATOR = new Creator<Book>() {
        @Override
        public Book createFromParcel(Parcel in) {
            return new Book(in);
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(isbn10);
        dest.writeString(isbn13);
        dest.writeString(title);
        dest.writeInt(price);
        dest.writeStringList(area);
        dest.writeString(status);
        dest.writeString(comment);
        dest.writeStringList(image);
    }
}
