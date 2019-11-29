package cn.bobasyu.test;

import cn.bobasyu.test.aaa.MyTestInterface;
import cn.bobasyu.ioc.annotation.MyAutoWired;
import cn.bobasyu.ioc.annotation.MyScan;
import cn.bobasyu.ioc.context.MyApplication;

/**
 * @author Boba
 */

@MyScan("cn.bobasyu.test")
public class Main {
    @MyAutoWired
    private static MyTestInterface myTestInterface;

    public static void main(String[] args) {
        new MyApplication().run(Main.class);
        //myTestInterface.test();
    }
}
