package com.bk.bm.model.repository.api;

import com.bk.bm.network.HttpService;

/**
 * Created by choi on 2017. 8. 18..
 */

public class UserApi {
    private HttpService httpService;

    public UserApi(HttpService httpService) {
        this.httpService = httpService;
    }
}
