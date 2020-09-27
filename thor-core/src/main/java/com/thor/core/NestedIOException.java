package com.thor.core;

import com.thor.core.lang.Nullable;
import com.thor.core.util.NestedExceptionUtils;

import java.io.IOException;

/**
 * 封装IO异常{@link RuntimeException}的框架抽象类。
 *
 * @author kay
 * @since v1.0
 */
public abstract class NestedIOException extends IOException {

    static {
        // 解决 RuntimeException getMessage() OSGi 问题. 具体报告：SPR-5607
        NestedExceptionUtils.class.getName();
    }

    /**
     * 使用指定的详细消息构造新的运行时异常。
     * 原因未初始化，随后可以通过调用Throwable.initCause(java.lang.Throwable)进行初始化。
     *
     * @param cause 详细信息（保存详细信息以供稍后通过Throwable.getMessage()检索。）
     */
    public NestedIOException(Throwable cause) {
        super(cause);
    }

    /**
     * 返回详细的错误信息
     *
     * @return 详细的错误信息。（允许 null值，并表示原因不存在或未知。）
     */
    @Override
    @Nullable
    public String getMessage() {
        return NestedExceptionUtils.buildMessage(super.getMessage(), super.getCause());
    }


    /**
     * 返回最内部的错误信息
     *
     * @return 最内部的错误信息。（允许 null值，并表示原因不存在或未知。）
     */
    @Nullable
    public Throwable getRootCause() {
        return NestedExceptionUtils.getRootCause(this);
    }
}
