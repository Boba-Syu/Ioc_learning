package cn.bobasyu.test.aaa.impl;

import cn.bobasyu.test.aaa.MyTestInterface;
import cn.bobasyu.ioc.annotation.MyCompetent;

/**
 * @author Boba
 */
@MyCompetent
public class MyTestInterfaceImpl implements MyTestInterface {

    @Override
    public void test() {
        System.out.println();
    }
}
