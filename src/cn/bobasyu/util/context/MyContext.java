package cn.bobasyu.util.context;

import cn.bobasyu.util.annotation.MyScan;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;


/**
 * @author Boba
 */
public class MyContext {

    public static void run(Class clazz) {
        MyScan myScan = (MyScan) clazz.getAnnotation(MyScan.class);
        String pack = myScan.value();
        // 先把包名转换为路径,首先得到项目的classpath
        String classpath = clazz.getResource("/").getPath().replace("/", File.separator);
        //然后把我们的包名basPath转换为路径名
        String basePath = classpath + pack.replace(".", File.separator);
        List<String> filePath = new ArrayList<>();
        findFileList(new File(basePath), filePath);
        basePath = replaceBasePath(basePath);
        getClassList(filePath, pack, basePath);
    }

    private static String replaceBasePath(String basePath) {
        if (basePath.charAt(0) == File.separatorChar) {
            String str = "";
            for (int i = 1; i < basePath.length(); i++) {
                str += basePath.charAt(i);
            }
            basePath = str;
        }
        if (basePath.charAt(basePath.length() - 1) != File.separatorChar) {
            basePath += File.separator;
        }
        return basePath;
    }

    /**
     * 获取目录下的所有文件名称
     *
     * @param dir       目标文件或目录
     * @param fileNames 文件名列表, 包含文件路径
     */
    private static void findFileList(File dir, List<String> fileNames) {
        try {
            // 判断是否存在目录
            if (!dir.exists() || !dir.isDirectory()) {
                throw new FileNotFoundException("文件路径" + dir.getPath() + "不存在");
            }
            // 读取目录下的所有目录文件信息
            String[] files = dir.list();
            // 循环，添加文件名或回调自身
            for (int i = 0; i < files.length; i++) {
                File file = new File(dir, files[i]);

                if (file.isFile()) {
                    // 如果文件是, 添加文件全路径名
                    fileNames.add(dir + "\\" + file.getName());
                } else {
                    // 如果是目录, 回调自身继续查询
                    findFileList(file, fileNames);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
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
    private static List<Class> getClassList(List<String> fileName, String pack, String basePath) {
        List<Class> classList = new ArrayList<>();
        for (String str : fileName) {
            String className = str.replace(basePath, pack + ".").replace(".class", "");
            Class clazz = null;
            try {
                clazz = Class.forName(className);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return classList;
    }
}
