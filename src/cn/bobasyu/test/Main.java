package cn.bobasyu.test;

import cn.bobasyu.ioc.annotation.MyAutoWired;
import cn.bobasyu.ioc.annotation.MyCompetent;
import cn.bobasyu.test.aaa.MyTestAutowired;
import cn.bobasyu.ioc.annotation.MyScan;
import cn.bobasyu.ioc.context.MyApplication;

/**
 * @author Boba
 */

@MyScan("cn.bobasyu.test")
@MyCompetent
public class Main {

    @MyAutoWired
    private static MyTestAutowired myTestAutowired;

    public static void main(String[] args) {
        new MyApplication().run(Main.class);
        myTestAutowired.test();
    }
}
