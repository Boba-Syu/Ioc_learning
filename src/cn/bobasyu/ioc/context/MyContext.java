package cn.bobasyu.ioc.context;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * IoC容器
 *
 * @author Boba
 */
public class MyContext {
    /**
     * 装有 [类名 : 类对应的实例] 的马匹容器
     */
    private Map<Class, Object> objectMap;

    protected MyContext() {
        this.objectMap = new HashMap<>();
    }

    /**
     * 添加类，并创建相应的实例对象到map容器中
     *
     * @param clazz 所添加的类对象
     */
    protected void push(Class clazz) {
        try {
            objectMap.put(clazz, clazz.newInstance());
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取类所继承的接口
     *
     * @param clazz 通过类实例反射来所获取类所继承的的接口
     * @return 所获取的类所继承的接口
     */
    public Object getObject(Class clazz) {
        if (objectMap.containsKey(clazz)) {
            return this.getObjectMap().get(clazz);
        }
        for (Class c : objectMap.keySet()) {
            for (Class i : c.getInterfaces()) {
                if (clazz.equals(i)) {
                    return objectMap.get(c);
                }
            }
        }
        return null;
    }

    /**
     * 获取mao容器键的集合
     *
     * @return
     */
    protected Set<Class> classSet() {
        return objectMap.keySet();
    }

    public Map<Class, Object> getObjectMap() {
        return this.objectMap;
    }

}
