/*
 * Copyright 2017-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.thor.container.resoure;

import com.thor.core.util.Decoder;
import lombok.extern.slf4j.Slf4j;

import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * 类加载器，根据包路径，获取 Class 资源列表
 *
 * @author kay
 * @version v1.0
 */
@Slf4j
public class ClassResourceLoader extends AbstractClassResource implements Resource {

    /**
     * 文件常量 ——— 名称 "file"
     */
    private static final String FILE_NAME = "file";

    /**
     * 扫描并获取到 Class 资源列表，暂不支持扫描 jar 包
     *
     * @param urlElements url 元素
     * @param packageName 相对路径包名（不能为空）
     * @return Class 资源列表
     * @throws ClassResourceLoadFailureException 如果根据资源名称载入没有找到对应的类，则抛出异常
     */
    @Override
    protected List<Class<?>> loadClasses(Enumeration<URL> urlElements, String packageName) {
        log.debug("{}, load class start", packageName);
        List<Class<?>> classes = new ArrayList<>();
        while (urlElements.hasMoreElements()) {
            URL url = urlElements.nextElement();
            String protocol = url.getProtocol();
            if (FILE_NAME.equals(protocol)) {
                String packagePath = Decoder.decode(url.getFile());
                scanLocalClass(packageName, packagePath, classes);
            }
        }
        log.debug("{}, load class end", packageName);
        return classes;
    }

    /**
     * 递归，扫描本地的 Class 资源，并且加入资源列表
     *
     * @param packageName 相对路径包名称
     * @param packagePath 相对包路径
     * @param classes     Class 资源列表
     * @throws ClassResourceLoadFailureException 如果根据资源名称载入没有找到对应的类，则抛出异常
     */
    private void scanLocalClass(String packageName, String packagePath, List<Class<?>> classes) {
        FileResource fileResource = new FileResource(packagePath, packageName);
        List<FileResource> files = fileResource.getFileList();
        if (files == null) {
            return;
        }

        for (FileResource file : files) {
            if (file.isDirectory()) {
                scanLocalClass(file.getFileName(), file.getAbsolutePath(), classes);
                continue;
            }

            if (log.isDebugEnabled()) {
                log.debug("load Class，class path: {}", file.getFileClassName());
            }
            Class<?> clazz = loadClass(file.getFileClassName());
            classes.add(clazz);
        }
    }

    /**
     * 根据类的名称，载入类的资源
     *
     * @param name 类的名称
     * @return 载入的 Class 信息
     * @throws ClassResourceLoadFailureException 如果根据资源名称载入没有找到对应的类，则抛出异常
     */
    private Class<?> loadClass(String name) {
        try {
            return Thread.currentThread().getContextClassLoader().loadClass(name);
        }
        catch (ClassNotFoundException e) {
            throw new ClassResourceLoadFailureException();
        }
    }
}
