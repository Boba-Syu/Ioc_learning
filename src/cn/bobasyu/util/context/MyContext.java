package cn.bobasyu.util.context;

import cn.bobasyu.util.annotation.MyScan;

/**
 * @author Boba
 */
public class MyContext {

    public static void run(Class clazz) {
        MyScan myScan = (MyScan) clazz.getAnnotation(MyScan.class);
        String[] scanPackage = myScan.value();

        for(String i : scanPackage) {
            System.out.println(i);
        }
    }
}
