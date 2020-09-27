package com.thor.core;

import com.thor.core.lang.Nullable;
import com.thor.core.util.NestedExceptionUtils;

/**
 * 封装非检查性异常{@link RuntimeException}的框架抽象类。
 *
 * <p>这个类{@code abstract}是面向程序员的扩展类。
 *
 * @author kay
 * @since v1.0
 */
public abstract class NestedRuntimeException extends RuntimeException {

    static {
        // 解决 RuntimeException getMessage() OSGi 问题. 具体报告：SPR-5607
        NestedExceptionUtils.class.getName();
    }

    /**
     * 使用指定的详细消息构造新的运行时异常。
     * 原因未初始化，随后可以通过调用Throwable.initCause(java.lang.Throwable)进行初始化。
     *
     * @param message 详细信息（保存详细信息以供稍后通过Throwable.getMessage()检索。）
     */
    public NestedRuntimeException(String message) {
        super(message);
    }

    /**
     * 带有堆栈异常信息的构造器
     *
     * @param cause 堆栈信息
     */
    public NestedRuntimeException(Throwable cause) {
        super(cause);
    }

    /**
     * 构造一个新的运行时异常与指定的详细信息和原因。
     * 请注意，与cause关联的详细消息不会自动并入此运行时异常的详细消息。
     *
     * @param message 详细信息（保存以供稍后通过Throwable.getMessage()方法检索）。（允许 null值，并表示信息不存在或未知。）
     * @param cause 原因（保存以供稍后通过Throwable.getCause()方法检索）。（允许 null值，并表示原因不存在或未知。）
     */
    public NestedRuntimeException(@Nullable String message,
                                  @Nullable Throwable cause) {
        super(message, cause);
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
