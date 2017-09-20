package com.bk.bm.view;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
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
    private static int mImageLocation = 0;

    @BindView(R.id.image1) ImageView mImage1;
    @BindView(R.id.image2) ImageView mImage2;
    @BindView(R.id.image3) ImageView mImage3;
    @BindView(R.id.image4) ImageView mImage4;
    @BindView(R.id.image5) ImageView mImage5;
    @BindView(R.id.image6) ImageView mImage6;

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
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, MY_PERMISSIONS_CAMERA_CONTACTS);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == MY_PERMISSIONS_CAMERA_CONTACTS) {
            try {
                Bitmap bm = (Bitmap) data.getExtras().get("data");
                Log.e("requestCode", String.valueOf(bm));
                mImage1.setImageBitmap(bm);
            } catch (NullPointerException ignore) {}
        }
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
