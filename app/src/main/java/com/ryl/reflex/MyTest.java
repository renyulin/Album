package com.ryl.reflex;


import android.util.Log;

public class MyTest extends DeepFather implements Deep {
    private int id;
    private String name;
    public String c;
    public String d="afds";
    public static String type = "123";

    public MyTest() {
        this.id = id;
        log("无参构造");
    }

    public MyTest(int id) {
        this.id = id;
        log("有参构造");
    }

    public MyTest(int id, String name) {
        this.id = id;
        this.name = name;
        log("多参构造");
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name,String aaa) {
        this.name = name;
    }

    public static void update() {
        //执行静态方法
        Log.e("ssssssssssssssssss", "执行静态方法2");
    }
    public static void udate(String i,String v) {
        //执行静态方法
        Log.e("ssssssssssssssssss", "执行静态方法1");
    }
    @Override
    public void deep() {

    }

    private void log(String log) {
        Log.e("ssssssssssssssssss", log);
    }
}
