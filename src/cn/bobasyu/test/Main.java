package cn.bobasyu.test;

import cn.bobasyu.util.annotation.MyScan;
import cn.bobasyu.util.context.MyContext;

/**
 * @author Boba
 */

@MyScan("cn.bobasyu.test")
public class Main {

    public static void main(String[] args) {
        MyContext.run(Main.class);
    }
}
