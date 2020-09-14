package com.thor.core.util;

import com.thor.core.NestedRuntimeException;
import com.thor.core.lang.Nullable;

/**
 * 框架内异常的辅助类。
 *
 * <p>仅仅能使用在框架内。
 *
 * @author kay
 * @since v1.0
 * @see NestedRuntimeException
 */
public class NestedExceptionUtils {

    /**
     * 构建一个基础的错误信息.
     *
     * @param message 详细信息（保存以供稍后通过Throwable.getMessage()方法检索）。（允许 null值，并表示信息不存在或未知。）
     * @param cause   原因（保存以供稍后通过Throwable.getCause()方法检索）。（允许 null值，并表示原因不存在或未知。）
     * @return 完整的异常信息（允许 null值，并表示原因不存在或未知。）
     */
    @Nullable
    public static String buildMessage(@Nullable String message,
                                      @Nullable Throwable cause) {
        if (cause == null) {
            return message;
        }

        StringBuilder sb = new StringBuilder(64);
        if (message != null) {
            sb.append(message).append("; ");
        }
        sb.append("nested exception is ").append(cause);
        return sb.toString();
    }

    /**
     * 检索最内部的异常信息.
     *
     * @param original 原始异常（允许 null值，并表示原因不存在或未知。）
     * @return 最内部的异常信息（允许 null值，并表示原因不存在或未知。）
     */
    @Nullable
    public static Throwable getRootCause(@Nullable Throwable original) {
        if (original == null) {
            return null;
        }

        Throwable rootCause = null;
        Throwable cause = original.getCause();
        while (cause != null && cause != rootCause) {
            rootCause = cause;
            cause = cause.getCause();
        }
        return rootCause;
    }
}
