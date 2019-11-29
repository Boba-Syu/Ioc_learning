package cn.bobasyu.test;

import cn.bobasyu.util.annotation.MyScan;
import cn.bobasyu.util.context.MyContext;

/**
 * @author Boba
 */

@MyScan("cn.boba.test")
public class Main {

    public static void main(String[] args) {
        MyContext.run(new Main().getClass());
    }
}
