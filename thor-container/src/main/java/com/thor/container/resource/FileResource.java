package com.thor.container.resource;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 文件资源
 *
 * @author kay
 * @version v2.0
 */
public class FileResource {

    /**
     * 文件常用字节 ——— 斜杠 '/'
     */
    public static final char FILE_SLASH = '/';

    /**
     * 文件常用字节 ——— 点 '.'
     */
    public static final char FILE_DOT = '.';

    /**
     * 文件常量 ——— 后缀 "class"
     */
    public static final String FILE_CLASS = FILE_DOT + "class";

    /**
     * 文件常量 FILE_CLASS 的长度
     */
    private static final int FILE_CLASS_LENGTH = FILE_CLASS.length();

    private final File file;

    private final String packageName;

    /**
     * 带有文件属性和包名的文件资源构造器
     *
     * @param file        文件属性
     * @param packageName 包名
     */
    private FileResource(File file, String packageName) {
        this.file = file;
        this.packageName = packageName;
    }

    /**
     * 带有文件包名和文件路径名称的构造器
     *
     * @param pathName    文件路径
     * @param packageName 文件包名
     */
    public FileResource(String pathName, String packageName) {
        this.file = new File(pathName);
        this.packageName = packageName;
    }

    public final String getFileName() {
        return packageName + FILE_DOT + file.getName();
    }

    public final String getFileClassName() {
        if (FILE_CLASS.length() > file.getName().length()) {
            return "";
        }

        int classNameLength = file.getName().length() - FILE_CLASS_LENGTH;
        String className = file.getName().substring(0, classNameLength);
        return packageName + FILE_DOT + className;
    }

    public boolean isDirectory() {
        return file.isDirectory();
    }

    public String getAbsolutePath() {
        return file.getAbsolutePath();
    }

    public List<FileResource> getFileList() {
        if (!file.exists() || !file.isDirectory()) {
            return null;
        }

        File[] files = file.listFiles(file -> file.isDirectory() || file.getName().endsWith(FILE_CLASS));
        if (files == null) {
            return null;
        }

        List<FileResource> resources = new ArrayList<>();
        for (File file : files) {
            FileResource resource = new FileResource(file, packageName);
            resources.add(resource);
        }
        return resources;
    }
}
