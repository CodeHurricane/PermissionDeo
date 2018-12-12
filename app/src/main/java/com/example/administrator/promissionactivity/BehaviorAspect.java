package com.example.administrator.promissionactivity;

/**
 * Created by Administrator on 2016/12/21 0021.
 */

import android.content.Context;
import android.util.Log;

import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Rationale;
import com.yanzhenjie.permission.RequestExecutor;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 切面
 * 你想要切下来的蛋糕
 */
@Aspect
public class BehaviorAspect {
    private static final int PERMISSION_GRANT=0X998;
    private static final int PERMISSION_DENY=0X999;
    /**
     * 如何切蛋糕，切成什么样的形状
     * 切点
     */
    @Pointcut("execution(@com.example.administrator.promissionactivity.Permission * *(..))")
    public void promissionRequire(){}

    /**
     * 切面
     * 蛋糕按照切点切下来之后   怎么吃
     * @param point
     * @return
     * @throws Throwable
     */
    @Around("promissionRequire()")
    public void dealPoint(final ProceedingJoinPoint point) throws Throwable
    {
        String permissionContent;
        MethodSignature methodSignature= (MethodSignature) point.getSignature();
        Permission permission =methodSignature.getMethod().getAnnotation(Permission.class);
            if (permission!=null){
                permissionContent= permission.value();
                requirePermission(permissionContent, new Action() {
                    @Override
                    public void onAction(List<String> permissions) {
                      try {
                          point.proceed();
                    }catch (Throwable throwable) {
                            throwable.printStackTrace();
                        }
                    }
                },new Action() {
                    @Override
                    public void onAction(List<String> permissions) {
                        if (AndPermission.hasAlwaysDeniedPermission(MyApplication.myApplication, permissions)) {
                            AndPermission.permissionSetting(MyApplication.myApplication).execute();
                            return;
                        }
                    }
                });

             }
    }

private void requirePermission(String content,Action action1,Action action2){
    AndPermission.with(MyApplication.myApplication)
            .permission(content)
            .rationale(new Rationale(){
                @Override
                public void showRationale(Context context, List<String> permissions, RequestExecutor executor) {
                    executor.execute();
                }
               })
            .onGranted(action1)
            .onDenied(action2)
            .start();
}



}
