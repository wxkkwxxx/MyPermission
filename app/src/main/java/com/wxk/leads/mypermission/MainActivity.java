package com.wxk.leads.mypermission;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.wxk.leads.mypermission.Permission.PermissionFailed;
import com.wxk.leads.mypermission.Permission.PermissionHelper;
import com.wxk.leads.mypermission.Permission.PermissionSucceed;
import com.wxk.leads.mypermission.Permission.PermissionUtils;

public class MainActivity extends AppCompatActivity {

    private static final int CALL_PHONE_REQUEST_CODE = 0x001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void call(View view){

//        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
//
//            int i = ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE);
//            if(i == PackageManager.PERMISSION_GRANTED){
//
//                callPhone();
//            }else {
//
//                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, CALL_PHONE_REQUEST_CODE);
//            }
//        }else {
//
//            callPhone();
//        }
//        PermissionHelper.requestPermission(this, CALL_PHONE_REQUEST_CODE, new String[]{Manifest.permission.CALL_PHONE});
        PermissionHelper.with(this)
                .requestCode(CALL_PHONE_REQUEST_CODE)
                .requestPermissions(Manifest.permission.CALL_PHONE)
                .request();
    }

    //成功
    @PermissionSucceed(requestCode = CALL_PHONE_REQUEST_CODE)
    private void callPhone() {

        Intent intent = new Intent(Intent.ACTION_CALL);
        Uri uri = Uri.parse("tel:"+88888);
        intent.setData(uri);
        startActivity(intent);
    }

    //失败
    @PermissionFailed(requestCode = CALL_PHONE_REQUEST_CODE)
    private void callFailed(){

        Toast.makeText(this, "关闭了拨打电话权限", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        PermissionHelper.requestPermissionsResult(this, requestCode, permissions);
    }
}
