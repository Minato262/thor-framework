package com.thor.container;

import com.thor.container.annotation.ThorProvider;
import com.thor.container.resource.ClassResourceLoader;
import com.thor.container.resource.Resource;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 *
 * @author kay
 * @version v1.0
 */
@Slf4j
public class Provider {

    private static final Map<String, Object> service = new HashMap<>();

    static {
        /*
         * 通过注解解析完成暴露服务的注册
         */
        Resource resource = new ClassResourceLoader();
        List<Class<?>> classCaches = resource.getClasses();
        if (!classCaches.isEmpty()) {
            for (Class<?> cls : classCaches) {
                // 判断是否标注了@ThorProvider 注解
                if (cls.isAnnotationPresent(ThorProvider.class)) {
                    // 获取ThorProvider 对象
                    // 获取注解配置的暴露服务对应接口
                    // 添加到暴露服务列表中
                    Class<?> interfaceClass = cls.getInterfaces()[0];
                    try {
                        service.put(interfaceClass.getName(), cls.newInstance());
                    } catch (InstantiationException | IllegalAccessException e) {
                        log.warn("service register fail! service name:{}", interfaceClass.getName());
                    }
                }
            }
        }
    }

    public Map<String, Object> getService() {
        return service;
    }
}
