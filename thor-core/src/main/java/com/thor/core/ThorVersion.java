package com.thor.core;

import com.thor.core.lang.Nullable;
import lombok.extern.slf4j.Slf4j;

/**
 * Thor 框架版本类
 *
 * @author kay
 */
@Slf4j
public final class ThorVersion {

    private ThorVersion() {
    }


    /**
     * 获取 thor 框架的版本信息
     *
     * @return thor 框架的版本信息
     */
    @Nullable
    public static String getVersion() {
        Package pkg = ThorVersion.class.getPackage();
        return (pkg != null ? pkg.getImplementationVersion() : null);
    }
}
