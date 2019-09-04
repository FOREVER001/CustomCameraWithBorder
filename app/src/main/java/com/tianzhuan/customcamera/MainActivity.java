package com.tianzhuan.customcamera;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.ocr.ui.camera.CameraActivity;
import com.baidu.ocr.ui.camera.CameraNativeHelper;
import com.baidu.ocr.ui.camera.CameraView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView tv_idcard_postive;
    private TextView tv_idcard_native;
    private TextView tv_bankcarid;
    private TextView tv_carId;
    private TextView tv_vin;
    private ImageView iv;

    private static final int REQUEST_CODE_PICK_IMAGE_FRONT = 201;
    private static final int REQUEST_CODE_PICK_IMAGE_BACK = 202;
    private static final int REQUEST_CODE_CAMERA = 102;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }



    private void initView() {
        tv_idcard_postive=findViewById(R.id.tv_idcard_postive);
        tv_idcard_native=findViewById(R.id.tv_idcard_native);
        tv_bankcarid=findViewById(R.id.tv_bankcarid);
        tv_carId=findViewById(R.id.tv_carId);
        tv_vin=findViewById(R.id.tv_vin);
        iv=findViewById(R.id.iv);

        tv_idcard_postive.setOnClickListener(this);
        tv_idcard_native.setOnClickListener(this);
        tv_bankcarid.setOnClickListener(this);
        tv_carId.setOnClickListener(this);
        tv_vin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_idcard_postive:
                idcard_postive();
                break;
            case R.id.tv_idcard_native:
                idcard_native();
                break;
            case R.id.tv_bankcarid:
                bankcarId();
                break;
            case R.id.tv_carId:
                carId();
                break;
           case R.id.tv_vin:
                break;

        }
    }

    private void carId() {
        Intent intent = new Intent(this, CameraActivity.class);
        intent.putExtra(CameraActivity.KEY_OUTPUT_FILE_PATH,
                FileUtil.getSaveFile(getApplication()).getAbsolutePath());
        intent.putExtra(CameraActivity.KEY_CONTENT_TYPE, CameraActivity.CONTENT_TYPE_CARID);
        startActivityForResult(intent, REQUEST_CODE_CAMERA);
    }

    /**
     * 银行卡
     */
    private void bankcarId() {
        Intent intent = new Intent(this, CameraActivity.class);
        intent.putExtra(CameraActivity.KEY_OUTPUT_FILE_PATH,
                FileUtil.getSaveFile(getApplication()).getAbsolutePath());
        intent.putExtra(CameraActivity.KEY_CONTENT_TYPE, CameraActivity.CONTENT_TYPE_BANK_CARD);
        startActivityForResult(intent, REQUEST_CODE_CAMERA);
    }

    /**
     * 身份证反面
     */
    private void idcard_native() {
        Intent intent = new Intent(this, CameraActivity.class);
        intent.putExtra(CameraActivity.KEY_OUTPUT_FILE_PATH,
                FileUtil.getSaveFile(getApplication()).getAbsolutePath());
        intent.putExtra(CameraActivity.KEY_CONTENT_TYPE, CameraActivity.CONTENT_TYPE_ID_CARD_BACK);
        startActivityForResult(intent, REQUEST_CODE_CAMERA);
    }

    /**
     * 身份证正面
     */
    private void idcard_postive() {
        Intent intent = new Intent(this, CameraActivity.class);
        intent.putExtra(CameraActivity.KEY_OUTPUT_FILE_PATH,
                FileUtil.getSaveFile(getApplication()).getAbsolutePath());
        intent.putExtra(CameraActivity.KEY_CONTENT_TYPE, CameraActivity.CONTENT_TYPE_ID_CARD_FRONT);
        startActivityForResult(intent, REQUEST_CODE_CAMERA);

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_CAMERA && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                String contentType = data.getStringExtra(CameraActivity.KEY_CONTENT_TYPE);
                String filePath = FileUtil.getSaveFile(getApplicationContext()).getAbsolutePath();
                if (!TextUtils.isEmpty(contentType)) {
                    if (CameraActivity.CONTENT_TYPE_ID_CARD_FRONT.equals(contentType)) {
                        Log.e("ID_CARD_FRONT",filePath);
                        showImg(filePath);
                    } else if (CameraActivity.CONTENT_TYPE_ID_CARD_BACK.equals(contentType)) {
                        Log.e("ID_CARD_SIDE_BACK",filePath);
                        showImg(filePath);
                    }else if(CameraActivity.CONTENT_TYPE_BANK_CARD.equals(contentType)){
                        Log.e("CONTENT_TYPE_BANK_CARD",filePath);
                        showImg(filePath);
                    }else if(CameraActivity.CONTENT_TYPE_CARID.equals(contentType)){
                        Log.e("CONTENT_TYPE_CARID",filePath);
                        showImg(filePath);
                    }
                }
            }
        }
    }

    private void showImg(String filePath) {
        Bitmap bitmap = BitmapFactory.decodeFile(filePath);
        iv.setImageBitmap(bitmap);
    }

}
