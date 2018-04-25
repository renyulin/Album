package com.ryl.choosephoto;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

/**
 * permission
 */
public class PermissionUtils {
    /**
     * 检查是否拥有指定的所有权限
     */
    public static boolean checkPermissionAllGranted(String[] permissions, Context context) {
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                // 只要有一个权限没有被授予, 则直接返回 false
                return false;
            }
        }
        return true;
    }

    public static void requestPermission(String[] permissions, Context context, int resultCode) {
        // 一次请求多个权限, 如果其他有权限是已经授予的将会自动忽略掉
        ActivityCompat.requestPermissions(
                (Activity) context,
                permissions,
                resultCode
        );
    }
}
