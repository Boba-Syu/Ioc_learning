package cn.bobasyu.ioc.context;

import cn.bobasyu.ioc.annotation.MyAutoWired;
import cn.bobasyu.ioc.annotation.MyCompetent;
import cn.bobasyu.ioc.annotation.MyScan;
import cn.bobasyu.ioc.util.file.FileUtil;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Boba
 */
public class MyApplication {
    private MyContext myContext;

    public MyApplication() {
        this.myContext = new MyContext();
    }

    public void run(Class clazz) {
        MyScan myScan = (MyScan) clazz.getAnnotation(MyScan.class);
        String pack = myScan.value();
        if (pack == null || pack == "") {
            pack = myScan.getClass().getPackage().toString();
        }
        // 先把包名转换为路径,首先得到项目的classpath
        String classpath = clazz.getResource("/").getPath().replace("/", File.separator);
        //然后把我们的包名basPath转换为路径名
        String basePath = classpath + pack.replace(".", File.separator);
        init(clazz, pack, basePath);
    }

    /**
     * 初始化
     *
     * @param clazz
     * @param pack
     */
    private void init(Class clazz, String pack, String basePath) {
        List<String> filePath = new ArrayList<>();
        FileUtil.findFileList(new File(basePath), filePath);
        basePath = FileUtil.replaceBasePath(basePath);
        List<Class> classList = getClassList(filePath, pack, basePath);
        addObject(classList);
        injectionObject(classList);
    }

    /**
     * 根据Class列表将带有 @MyCompetent 注解的类创建实体添加到容器中
     *
     * @param classList
     */
    private void addObject(List<Class> classList) {
        for (Class clazz : classList) {
            Annotation annotation = clazz.getAnnotation(MyCompetent.class);
            if (annotation != null && clazz != null) {
                myContext.push(clazz);
            }
        }
        //this.myContext.show();
    }

    /**
     * 注入对象
     *
     * @param classList
     */
    private void injectionObject(List<Class> classList) {
        for (Class clazz : this.myContext.classSet()) {
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                if (field.getAnnotation(MyAutoWired.class) != null) {
                    try {
                        field.setAccessible(true);
                        Object fieldObject = this.myContext.getObject(field.getType());
                        Object clazzObject = this.myContext.getObject(clazz);
                        field.set(clazzObject, fieldObject);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    /**
     * 根据路径获取类对象
     *
     * @param fileName 类的文件名
     * @param pack     包名
     * @param basePath 包的目录
     * @return 类对象列表
     */
    private List<Class> getClassList(List<String> fileName, String pack, String basePath) {
        List<Class> classList = new ArrayList<>();
        for (String str : fileName) {
            String className = str.replace(basePath, pack + ".").replace(".class", "").replace(File.separator, ".");
            Class clazz = null;
            try {
                clazz = Class.forName(className);
                classList.add(clazz);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return classList;
    }
}
