package com.wxk.leads.mypermission.Permission;

import android.app.Activity;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;

import java.util.List;

/**
 * Created by Administrator on 2017/3/16 0016.
 */

public class PermissionHelper {

    private Object mObject;
    private int mRequestCode;
    private String[] mRequestPermissions;

    private PermissionHelper(Object object){
        this.mObject = object;
    }

    public static void requestPermission(Activity activity, int requestCode, String[] requestPermission){

        PermissionHelper.with(activity).requestCode(requestCode).requestPermissions(requestPermission);
    }

    public static void requestPermission(Fragment fragment, int requestCode, String[] requestPermission){
        PermissionHelper.with(fragment).requestCode(requestCode).requestPermissions(requestPermission);
    }

    public static PermissionHelper with(Activity activity){

        return new PermissionHelper(activity);
    }

    public static PermissionHelper with(Fragment fragment){

        return new PermissionHelper(fragment);
    }

    public PermissionHelper requestCode(int requestCode){
        this.mRequestCode = requestCode;
        return this;
    }

    public PermissionHelper requestPermissions(String... requestPermissions){
        this.mRequestPermissions = requestPermissions;
        return this;
    }

    public void request(){

        if(!PermissionUtils.isOverMarshmallow()){
            //如果低于6.0 则直接执行方法, 通过反射获取方法,通过添加注解的方法,找到要执行的方法

            PermissionUtils.executeSucceedMethod(mObject, mRequestCode);
            return;
        }else {

            //首先先获取到没有通过的权限
            List<String> deniedPermissions = PermissionUtils.getDeniedPermissions(mObject, mRequestPermissions);

            if(deniedPermissions.size() == 0){ //全部通过权限

                PermissionUtils.executeSucceedMethod(mObject, mRequestCode);
            }else {

                ActivityCompat.requestPermissions(PermissionUtils.getActivity(mObject), mRequestPermissions, mRequestCode);
            }
        }
    }

    public static void requestPermissionsResult(Object o, int requestCode, String[] permissions){

        List<String> deniedPermissions = PermissionUtils.getDeniedPermissions(o, permissions);
        if(deniedPermissions.size() == 0){ //全部通过权限

            PermissionUtils.executeSucceedMethod(o, requestCode);
        }else {

            PermissionUtils.executeFailedMethod(o, requestCode);
        }
    }
}
