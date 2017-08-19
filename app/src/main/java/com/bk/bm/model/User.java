package com.bk.bm.model;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Created by choi on 2017. 8. 18..
 */

@Data
@AllArgsConstructor
public class User {
    private String name;
    private String phone;
    private String profile_image;
    private String uid;
    private String noti;
}
