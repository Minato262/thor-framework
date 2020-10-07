package com.thor.container.resource;

import com.thor.container.ContainerRuntimeException;

/**
 * 资源相关基础异常
 * <p>这是一个抽象异常类，它定义了任何与底层资源载入相关的异常的基础方法</p>
 *
 * @author kay
 * @version v1.0
 */
abstract class ResourceRuntimeException extends ContainerRuntimeException {
    private static final long serialVersionUID = -203034699454123415L;

    /**
     * 带有错误信息的构造器
     *
     * @param message 错误信息
     */
    ResourceRuntimeException(String message) {
        super(message);
    }

    /**
     * 带有堆栈异常信息的构造器
     *
     * @param cause 堆栈信息
     */
    ResourceRuntimeException(Throwable cause) {
        super(cause);
    }
}
