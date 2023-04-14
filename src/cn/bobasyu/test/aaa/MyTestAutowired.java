package cn.bobasyu.test.aaa;

import cn.bobasyu.ioc.annotation.MyAutoWired;
import cn.bobasyu.ioc.annotation.MyCompetent;

/**
 * @author Bobasyu
 */
@MyCompetent
public class MyTestAutowired {
    @MyAutoWired
    MyTestInterface myTestInterface;

    public void test() {
        myTestInterface.test();
    }
}
