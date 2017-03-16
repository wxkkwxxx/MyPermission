package com.wxk.leads.mypermission.Permission;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Administrator on 2017/3/16 0016.
 */

//注解博客 http://blog.csdn.net/yixiaogang109/article/details/7328466
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface PermissionSucceed {

    public int requestCode();//请求码
}
