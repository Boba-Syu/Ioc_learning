package cn.bobasyu.test.aaa;

import cn.bobasyu.ioc.annotation.MyAutoWired;
import cn.bobasyu.ioc.annotation.MyCompetent;

/**
 * @author Boba
 */
@MyCompetent
public class MyTestAutowired {
    @MyAutoWired
    MyTestInterface myTestInterface;

    public void test() {
        myTestInterface.test();
    }
}
