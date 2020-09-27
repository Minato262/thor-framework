package com.thor.container.resource;

import java.util.List;

/**
 * 获取资源相关接口
 *
 * @author kay
 * @version v1.0
 */
public interface Resource {

    /**
     * 获取所有当前包内 Class 资源清单
     *
     * @return Class 资源清单
     */
    List<Class<?>> getClasses();
}
