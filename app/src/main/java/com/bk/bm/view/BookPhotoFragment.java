package com.bk.bm.view;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bk.bm.BuildConfig;
import com.bk.bm.R;
import com.bk.bm.base.BaseFragment;
import com.bk.bm.util.BookImageUtils;
import com.bk.bm.util.EventData;
import com.bk.bm.util.PermissionHelper;
import com.bumptech.glide.Glide;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by choi on 2017. 9. 4..
 */

public class BookPhotoFragment extends BaseFragment {

    private static final int CAMERA_CONTACTS = 1003;
    private static final int GALLERY_CONTACTS = 1004;
    private static final int STORAGE_CONTACTS = 2000;
    private static int mImageLocation = 0;
    private String mImagePath;
    private static HashMap<String, File> imageFiles = new HashMap<>();

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

    public static void put(File file) {
        imageFiles.put(file.getName(), file);
        Log.e("put", String.valueOf(imageFiles));
        SaleStepFragment.eventDataProvider(EventData.Book.IMAGE, file);
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
        File picture = savePictureFile();
        SaleStepFragment.eventDataProvider(EventData.Book.IMAGE, picture);
        Log.e("onImageClick", String.valueOf(imageFiles));
        if (picture != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                Uri photoUri = FileProvider
                        .getUriForFile(getContext(),
                                BuildConfig.APPLICATION_ID + ".provider",
                                picture);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
            } else {
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(picture));
            }
            startActivityForResult(intent, CAMERA_CONTACTS);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_CONTACTS) {
            Bitmap bm = BookImageUtils.decodeSampleBitmapFromResource(mImagePath, mImage1.getWidth(), mImage1.getHeight());
            mImage1.setImageBitmap(bm);
//            BitmapFactory.Options factory = new BitmapFactory.Options();
//            factory.inJustDecodeBounds = true;
//            BitmapFactory.decodeFile(mImagePath);
//            factory.inJustDecodeBounds = false;
//            Bitmap bitmap = BitmapFactory.decodeFile(mImagePath, factory);
//            mImage1.setImageBitmap(bitmap);
        }
    }

    private File savePictureFile() {
        PermissionHelper.Builder builder = new PermissionHelper.Builder(getActivity());
        int result = builder.create()
                .request(Manifest.permission.WRITE_EXTERNAL_STORAGE, STORAGE_CONTACTS,
                        new PermissionHelper.OnPermissionDenyListener() {
                            @Override
                            public void onClick(Activity activity) {
                                Toast.makeText(activity, "카메라 사용 권한을 거부하면 사진을 불러올 수 없습니다.", Toast.LENGTH_SHORT).show();
                            }
                        });
        if (result == PermissionHelper.ALREADY_GRANTED
                || result == PermissionHelper.REQUEST_PERMISSION) {
            @SuppressLint("SimpleDateFormat")
            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String fileName = "BOOK48_"+timestamp;

            File pictureStorage = new File(
                    Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                    "BOOK48/");

            if (!pictureStorage.exists()) {
                pictureStorage.mkdirs();
            }

            try {
                File tempFile = File.createTempFile(fileName, ".jpg", pictureStorage);
                mImagePath = tempFile.getAbsolutePath();
                Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);

                File file = new File(mImagePath);
                Uri contentUri = Uri.fromFile(file);
                mediaScanIntent.setData(contentUri);
                getActivity().sendBroadcast(mediaScanIntent);

                return file;
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(getActivity(), "카메라 사용 권한을 거부하면 사진을 불러올 수 없습니다.", Toast.LENGTH_SHORT).show();
        }
        return null;
    }
}
