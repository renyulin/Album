package com.ryl.reflex;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import com.ryl.R;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 通过反射获取Class的内容
 * 参考：https://mp.weixin.qq.com/s/nIEg6489dri2vG8rDzrf2A
 */
public class ReflexActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_reflex);
        reflex();
    }

    private void reflex() {
        Class<?> clz = null;
        try {
            clz = Class.forName("com.ryl.reflex.MyTest");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
//        log("getName:" + clz.getName());
//        a(clz);
//        b(clz);
//        c(clz);
//        d(clz);
//        e(clz);
//        try {
//            f(clz);
//        } catch (Exception e) {
//            e.printStackTrace();
//            log(e.toString());
//        }
//        try {
//            g(clz);
//        } catch (Exception e) {
//            e.printStackTrace();
//            log(e.toString());
//        }
//        try {
//            h(clz);
//        } catch (Exception e) {
//            e.printStackTrace();
//            log(e.toString());
//        }
//        try {
//            i(clz);
//        } catch (Exception e) {
//            e.printStackTrace();
//            log(e.toString());
//        }
//        try {
//            j(clz);
//        } catch (Exception e) {
//            e.printStackTrace();
//            log(e.toString());
//        }
        try {
            k(clz);
        } catch (Exception e) {
            e.printStackTrace();
            log(e.toString());
        }
    }

    private void k(Class<?> clz) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, InstantiationException {
        MyTest test1 = (MyTest) clz.newInstance();
        Method update = clz.getDeclaredMethod("udate", String.class, String.class);//********
        update.setAccessible(true);
        update.invoke(test1, "1", "sdaa");
    }

    private void j(Class<?> clz) throws NoSuchMethodException, IllegalAccessException, InstantiationException, InvocationTargetException {
        MyTest test1 = (MyTest) clz.newInstance();
        Method setName = clz.getDeclaredMethod("setName", String.class, String.class);
        setName.setAccessible(true);
        setName.invoke(test1, "deep", "ddddd");
        log("通过反射设置的属性值:" + test1.getName());
        Method getName = clz.getDeclaredMethod("getName");
        String name = (String) getName.invoke(test1);
        log("通过反射获取的反射设置的属性值:" + test1.getName() + ";;" + name);
    }

    private void i(Class<?> clz) throws NoSuchFieldException, IllegalAccessException, InstantiationException {
        MyTest test1 = (MyTest) clz.newInstance();
        //设置静态属性值
        Field c_p = clz.getDeclaredField("type");
        log("获取静态属性值:" + c_p.get(c_p));
        c_p.set(null, "c_c_c");
        //设置静态属性值不同的地方在于，他不需要实例化变量所以，set的第一个参数设置为null即可
        log("设置后的静态属性值:" + test1.type);
    }

    private void h(Class<?> clz) throws IllegalAccessException, InstantiationException, NoSuchFieldException {
        MyTest test1 = (MyTest) clz.newInstance();
        Field id_p = clz.getDeclaredField("id");
        id_p.setAccessible(true);
        id_p.set(test1, 1111);
        log("设置后的私有属性值:" + test1.getId());
    }

    private void g(Class<?> clz) throws NoSuchFieldException, IllegalAccessException, InstantiationException {
        MyTest test1 = (MyTest) clz.newInstance();
        Field b_p = clz.getDeclaredField("d");
        b_p.set(test1, "b_b_b_b");
        log("设置后的公有属性值:" + test1.d);
    }

    /**
     * 通过反射实例化对象
     */
    private void f(Class<?> clz) throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        MyTest test1 = (MyTest) clz.newInstance();
        Constructor<?> constructor2 = clz.getDeclaredConstructor(int.class);
        MyTest mest2 = (MyTest) constructor2.newInstance(1000);
        Constructor<?> constructor3 = clz.getDeclaredConstructor(int.class, String.class);
        MyTest mest3 = (MyTest) constructor3.newInstance(1000, "deep");
        /*
        04-28 15:40:54.808 5870-5870/com.ryl E/ssssssssssssssssss: 无参构造

Constructor<?> constructor2 = clz.getDeclaredConstructor(Integer.class);
04-28 15:40:54.814 5870-5870/com.ryl E/ssssssssssssssssss: java.lang.NoSuchMethodException: <init> [class java.lang.Integer]
04-28 15:43:00.418 8070-8070/com.ryl E/ssssssssssssssssss: 无参构造

Constructor<?> constructor2 = clz.getDeclaredConstructor(int.class);
MyTest mest2 = (MyTest) constructor2.newInstance("1000");
04-28 15:43:00.426 8070-8070/com.ryl E/ssssssssssssssssss: java.lang.IllegalArgumentException: method com.ryl.reflex.MyTest.<init> argument 1 has type int, got java.lang.String
04-28 15:44:50.936 9370-9370/com.ryl E/ssssssssssssssssss: 无参构造
04-28 15:44:50.941 9370-9370/com.ryl E/ssssssssssssssssss: java.lang.NoSuchMethodException: <init> [class java.lang.Integer]
         */
    }

    private void e(Class<?> clz) {
        //获取类中的属性（含父类）公开的
        Field[] fields2 = clz.getFields();
        int i = 0;
        for (Field f : fields2) {
            log("属性值" + (++i) + ":" + f.getName());
        }
    }

    private void d(Class<?> clz) {
        //取得本类的全部属性 不包括父类，包括公有和私有
        Field[] fields = clz.getDeclaredFields();
        int i = 0;
        for (Field f : fields) {
            log("属性值" + (++i) + ":" + f.getName());
        }
    }

    private void c(Class<?> clz) {
        //获取父类
        Class<?> superclass = clz.getSuperclass();
        log("父类:" + superclass.getName());
    }

    private void b(Class<?> clz) {
        //获取所有接口
        Class<?>[] inters = clz.getInterfaces();
        for (Class<?> i : inters) {
            log("interface:" + i.getName());
        }
    }

    private void a(Class<?> clz) {
        //获取所有方法名
        Method[] methods = clz.getMethods();
        for (Method m : methods) {
            log("reflect method:" + m.getName());
        }
    }

    public void reflexOnClick(View view) {
        switch (view.getId()) {
            case R.id.reflex_btn1:
                Class<?> clz = null;
                try {
                    clz = Class.forName("com.ryl.reflex.MyTest");
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                log(clz.getName());
                break;
        }
    }

    private void log(String log) {
        Log.e("ssssssssssssssssss", log);
    }
}
