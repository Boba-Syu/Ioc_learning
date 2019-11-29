package cn.bobasyu.ioc.context;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * IoC容器
 *
 * @author Boba
 */
public class MyContext {
    private Map<Class, Object> objectMap;

    MyContext() {
        this.objectMap = new HashMap<>();
    }

    public void push(List<Class> classList) {
        for (Class clazz : classList) {
            try {
                objectMap.put(clazz, clazz.newInstance());
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    void push(Class clazz) {
        try {
            objectMap.put(clazz, clazz.newInstance());
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    Object getObject(Class clazz) {
        if (objectMap.containsKey(clazz)) {
            return objectMap.get(clazz);
        }
        for (Class c : objectMap.keySet()) {
            for (Class i : clazz.getClasses()) {
                if (c.equals(i)) {
                    return objectMap.get(c);
                }
            }
        }
        return null;
    }

    void show() {
        for(Class clazz: objectMap.keySet()) {
            System.out.println("" + clazz + objectMap.get(clazz));
        }
    }
}
