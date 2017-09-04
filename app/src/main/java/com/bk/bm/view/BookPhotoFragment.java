package com.bk.bm.view;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bk.bm.R;
import com.bk.bm.base.BaseFragment;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by choi on 2017. 9. 4..
 */

public class BookPhotoFragment extends BaseFragment {

    private static final int MY_PERMISSIONS_CAMERA_CONTACTS = 1003;

    @BindView(R.id.image1) ImageView image1;
    @BindView(R.id.image2) ImageView image2;
    @BindView(R.id.image3) ImageView image3;
    @BindView(R.id.image4) ImageView image4;
    @BindView(R.id.image5) ImageView image5;
    @BindView(R.id.image6) ImageView image6;

    public static Fragment newInstance() {
        Fragment fragment = new BookPhotoFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        return view;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_book_gallery;
    }

    @OnClick({R.id.image1, R.id.image2, R.id.image3, R.id.image4, R.id.image5, R.id.image6})
    public void onImageClick() {

    }

    public void checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int permissionCheck = ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA);
            if(permissionCheck == PackageManager.PERMISSION_DENIED){
                // 권한 없음
                if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.CAMERA)) {

                } else {
                    ActivityCompat.requestPermissions(getActivity(),
                            new String[]{Manifest.permission.READ_CONTACTS},
                            MY_PERMISSIONS_CAMERA_CONTACTS);
                }
            }else{
                // 권한 있음
            }
        }
    }
}
