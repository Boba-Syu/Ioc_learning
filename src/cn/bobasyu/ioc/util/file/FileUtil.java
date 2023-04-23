package cn.bobasyu.ioc.util.file;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Arrays;
import java.util.List;

/**
 * 和文件有关的工具类
 *
 * @author Bobasyu
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
            Files.walkFileTree(Paths.get(dir.getPath()), new SimpleFileVisitor<>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    // 如果文件是, 添加文件全路径名
                    fileNames.add(file.toString());
                    return super.visitFile(file, attrs);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
