package cn.topicstudy.jutil.basic.file;

import cn.topicstudy.jutil.basic.text.StringUtil;
import com.alibaba.fastjson.JSON;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * 文件、文件夹工具类
 * 所有地址都用绝对地址
 */
public class FileUtil {
    public static final String SEPARATOR = File.separator;
    public static final String PATH_SEPARATOR = File.pathSeparator;
    public static byte[] block1KB = new byte[1024];
    public static byte[] block1MB = new byte[1024 * 1024];

    /**
     * 删除文件或文件夹
     * 文件夹不为空时，只能递归地将文件夹中的文件、文件夹删除完才能再删除该文件夹
     *
     * @param path 文件或文件夹的绝对路径 例如：d:/tmp   d:/    d:/x.txt
     */
    public static void delete(String path) {
        if (StringUtil.isBlank(path)) return;
        File file = new File(path);
        if (file == null || !file.exists()) return;

        if (file.isFile()) file.delete();// 文件可直接删除
        else if (file.isDirectory()) {
            String[] subFileNames = file.list();//文件夹file中的文件、文件夹
            if (subFileNames == null) return;// 文件夹不存在或其它错误
            if (subFileNames.length == 0) file.delete();// 空文件夹可直接删除
            //清空文件夹中的内容
            for (String subFileName : subFileNames) {
                String absolutePath = file.getAbsolutePath();
                if (!absolutePath.endsWith(SEPARATOR)) absolutePath += SEPARATOR;
                String subFileAbsolutePath = absolutePath + subFileName;
                delete(subFileAbsolutePath);
            }

            //删除空文件夹
            file.delete();
        }
    }

    /**
     * 判断文件夹是否是空的
     *
     * @return 文件夹为空返回true
     */
    public static boolean isEmptyDir(String dirPath) {
        File file = new File(dirPath);
        if (!file.exists()) throw new RuntimeException(String.format("文件夹[%s]不存在", dirPath));
        if (file.isFile()) throw new RuntimeException("[%s]不是文件夹".format(dirPath));

        String[] subFileNames = file.list();
        if (subFileNames == null) throw new RuntimeException(String.format("文件夹[%s]异常", dirPath));

        return subFileNames.length == 0;
    }


    /**
     * 复制文件
     * FileUtil.copyFile("d:/tmp/gitstd/a.txt", "d:/p.txt");
     */
    public static void copyFile(String fromFilePath, String toFilePath) throws IOException {
        if (StringUtil.isBlank(fromFilePath) || StringUtil.isBlank(toFilePath)) return;
        File fromFile = new File(fromFilePath);
        File toFile = new File(toFilePath);
        if (fromFile.isDirectory()) throw new RuntimeException(String.format("[%s]不是文件", fromFilePath));
        if (toFile.isDirectory()) throw new RuntimeException(String.format("[%s]不是文件", toFilePath));


        // do copy
        FileInputStream fis = new FileInputStream(fromFilePath);
        FileOutputStream fos = new FileOutputStream(toFilePath);
        byte[] buffer = new byte[1024];// 1KB
        while (true) {
            int readCount = fis.read(buffer);
            if (readCount == -1) return;
            fos.write(buffer, 0, readCount);
        }
    }

    /**
     * 复制文件夹
     * <p>
     * dirFrom
     * |-dir1----|-dir3-----fire3
     * |---------|-file2
     * |-dir2
     * |-file1
     * <p>
     * 写递归代码时只用考虑第一层，即dir1,dir2,file1
     */
    public static void copyDir(String fromDirPath, String toDirPath) throws IOException {
        if (StringUtil.isBlank(fromDirPath) || StringUtil.isBlank(toDirPath)) return;
        File fromDir = new File(fromDirPath);
        File toDir = new File(toDirPath);

        if (!fromDir.exists()) throw new RuntimeException(String.format("[%s]不存在", fromDirPath));
        if (!toDir.exists()) toDir.mkdirs();
        if (fromDir.isFile()) throw new RuntimeException(String.format("[%s]不是文件夹", fromDirPath));
        if (toDir.isFile()) throw new RuntimeException(String.format("[%s]不是文件夹", toDirPath));

        File[] subFiles = fromDir.listFiles();
        if (subFiles == null || subFiles.length == 0) return;

        String toAbsolutePath = toDir.getAbsolutePath();
        if (!toAbsolutePath.endsWith(SEPARATOR)) toAbsolutePath += SEPARATOR;

        for (File subFile : subFiles) {
            if (subFile.isDirectory()) {// 在目的地建同名文件夹，在复制
                String subDirName = subFile.getName();
                new File(toAbsolutePath + subDirName).mkdir();
                copyDir(subFile.getAbsolutePath(), toAbsolutePath + subDirName);
            } else if (subFile.isFile()) {
                String toAbsoluteFilePath = toAbsolutePath + subFile.getName();
                new File(toAbsoluteFilePath).createNewFile();
                copyFile(subFile.getAbsolutePath(), toAbsoluteFilePath);
            }
        }
    }

    /**
     * 输入流转字节数组,只适用50MB的数据
     */
    public static byte[] inputStreamToByteArray(InputStream is) {
        try {
            if (is == null) throw new RuntimeException("is不可为空");

            // convert
            byte[] bytes = new byte[is.available()];
            is.read(bytes);
            return bytes;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static List<String> readLines(String absolutePath) {
        try {
            Path path = Paths.get(absolutePath);
            return Files.readAllLines(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T readJSONObj(String absolutePath, Class<T> cls) {
        try {
            byte[] bytes = Files.readAllBytes(Paths.get(absolutePath));
            String s = new String(bytes);
            return JSON.parseObject(s, cls);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> List<T> readJSONArray(String absolutePath, Class<T> cls) {
        try {
            byte[] bytes = Files.readAllBytes(Paths.get(absolutePath));
            String s = new String(bytes);
            return JSON.parseArray(s, cls);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
