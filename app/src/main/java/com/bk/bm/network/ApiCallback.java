package com.bk.bm.network;

/**
 * Created by choi on 2017. 8. 17..
 */

public interface ApiCallback<M> {

    void onSuccess(M model);

    void onError(String msg);

}
