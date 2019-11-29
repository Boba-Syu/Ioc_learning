package cn.bobasyu.ioc.util.file;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

/**
 * 和文件有关的工具类
 *
 * @author Boba
 */
public class FileUtil {
    public static String replaceBasePath(String basePath) {
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
    public static void findFileList(File dir, List<String> fileNames) {
        try {
            // 判断是否存在目录
            if (!dir.exists() || !dir.isDirectory()) {
                throw new FileNotFoundException("file path" + dir.getPath() + "not exist.");
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
}
