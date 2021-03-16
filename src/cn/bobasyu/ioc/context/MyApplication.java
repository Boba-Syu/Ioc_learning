package cn.bobasyu.ioc.context;

import cn.bobasyu.ioc.annotation.MyAutoWired;
import cn.bobasyu.ioc.annotation.MyCompetent;
import cn.bobasyu.ioc.annotation.MyScan;
import cn.bobasyu.ioc.util.file.FileUtil;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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
        if (pack == null || "".equals(pack)) {
            pack = clazz.getPackage().toString().replace("package ", "");
        }
        // 先把包名转换为路径,首先得到项目的classpath
        String classpath = clazz.getResource("/").getPath().replace("/", File.separator);
        //然后把我们的包名basPath转换为路径名
        String basePath = classpath + pack.replace(".", File.separator);
        init(pack, basePath);
    }

    /**
     * 初始化, 获取pack包目录下所有的类的名称
     *
     * @param pack     被扫描的包的目录
     * @param basePath pack所在的绝对路径
     */
    private void init(String pack, String basePath) {
        List<String> filePath = new ArrayList<>();
        FileUtil.findFileList(new File(basePath), filePath);
        basePath = FileUtil.replaceBasePath(basePath);
        List<Class> classList = getClassList(filePath, pack, basePath);
        addObject(classList);
        injectionObject();
    }

    /**
     * 根据Class列表将带有 @MyCompetent 注解的类创建实体添加到容器中
     *
     * @param classList 需要添加到容器的Class列表
     */
    private void addObject(List<Class> classList) {
        classList.parallelStream().forEach(clazz -> {
            Annotation annotation = clazz.getAnnotation(MyCompetent.class);
            if (annotation != null && clazz != null) {
                this.getMyContext().push(clazz);
            }
        });
    }

    /**
     * 将容器中的实例中标记有@MyAutoWired注解的属性注入相应的对象
     */
    private void injectionObject() {
        this.getMyContext().classSet().parallelStream().forEach(clazz -> {
            Field[] fields = clazz.getDeclaredFields();
            Arrays.stream(fields).filter(field -> field.getAnnotation(MyAutoWired.class) != null)
                    .forEach(field -> {
                        try {
                            field.setAccessible(true);
                            Object fieldObject = this.myContext.getObject(field.getType());
                            Object clazzObject = this.myContext.getObject(clazz);
                            field.set(clazzObject, fieldObject);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    });
        });
    }

    /**
     * 根据路径获取类对象
     *
     * @param fileName 类的文件名列表
     * @param pack     包名
     * @param basePath 包的目录的绝对路径
     * @return 类对象列表
     */
    private List<Class> getClassList(List<String> fileName, String pack, String basePath) {
        return fileName.parallelStream().map(str -> {
            String className = str.replace(basePath, pack + ".")
                    .replace(".class", "")
                    .replace(File.separator, ".");

            try {
                return Class.forName(className);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            return null;
        }).collect(Collectors.toList());
    }

    private MyContext getMyContext() {
        return myContext;
    }

}
