package com.thor.container;

import com.thor.core.NestedRuntimeException;

/**
 *
 *
 * @author kay
 * @version v1.0
 */
public abstract class ContainerRuntimeException extends NestedRuntimeException {

    public ContainerRuntimeException(String message) {
        super(message);
    }

    /**
     * 带有堆栈异常信息的构造器
     *
     * @param cause 堆栈信息
     */
    public ContainerRuntimeException(Throwable cause) {
        super(cause);
    }
}
