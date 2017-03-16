package com.wxk.leads.mypermission.Permission;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/3/16 0016.
 */

public class PermissionUtils {

    private static final String TAG = PermissionUtils.class.getSimpleName();

    private PermissionUtils(){
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    public static boolean isOverMarshmallow(){
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }

    public static void executeSucceedMethod(Object reflectObject, int requestCode) {
        //通过反射获取所有方法
        Method[] methods = reflectObject.getClass().getDeclaredMethods();
        //遍历所有方法
        for (Method method: methods) {
            Log.e(TAG, reflectObject.getClass().getSimpleName()+"中的方法:"+method);
            //获取该方法上是否打了注解标记
            PermissionSucceed succeed = method.getAnnotation(PermissionSucceed.class);
            if(succeed != null){ //通过注解获取到对应的requestCode
                int methodCode = succeed.requestCode();
                if(methodCode == requestCode){

                    //执行方法
                    executeMethod(reflectObject, method);
                }
            }
        }
    }

    public static void executeFailedMethod(Object reflectObject, int requestCode){

        Method[] methods = reflectObject.getClass().getDeclaredMethods();
        for (Method method : methods) {
            Log.e(TAG, reflectObject.getClass().getSimpleName()+"中的方法:"+method);
            PermissionFailed failed = method.getAnnotation(PermissionFailed.class);
            if(failed != null){

                int methodCode = failed.requestCode();
                if(methodCode == requestCode){

                    executeMethod(reflectObject, method);
                }
            }
        }
    }

    public static void executeMethod(Object reflectObject, Method method) {
        //反射执行方法 第一个传该方法所处的类.第二个传参数
        try {
            method.setAccessible(true);
            method.invoke(reflectObject, new Object[]{});
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List<String> getDeniedPermissions(Object object, String[] requestPermissions) {
        //获取到所有未通过的权限
        List<String> deniedPermissions = new ArrayList<>();

        for (String requestPermission : requestPermissions) {
            int status = ContextCompat.checkSelfPermission(getActivity(object), requestPermission);
            if(status == PackageManager.PERMISSION_DENIED){
                deniedPermissions.add(requestPermission);
            }
        }
        return deniedPermissions;
    }

    public static Activity getActivity(Object object) {
        if(object instanceof Activity){

            return (Activity) object;
        }
        if(object instanceof Fragment){

            return ((Fragment)object).getActivity();
        }
        return null;
    }
}
