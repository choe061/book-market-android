package com.bk.bm.model.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by choi on 2017. 8. 18..
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private String phone;
    private String profile_image;
    private String uid;
    private String noti;
    private String fcm_token;
}
