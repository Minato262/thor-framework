package com.thor.container.resource;

/**
 * 类资源异常
 *
 * @author kay
 * @version v1.0
 */
class ClassResourceRuntimeException extends ResourceRuntimeException {

    private static final long serialVersionUID = -5511004726724230904L;

    /**
     * 带有错误信息的构造器
     */
    ClassResourceRuntimeException() {
        super("");
    }

    /**
     * 带有错误信息的构造器
     *
     * @param message 错误信息
     */
    ClassResourceRuntimeException(String message) {
        super(message);
    }

    /**
     * 带有堆栈异常信息的构造器
     *
     * @param cause 堆栈信息
     */
    ClassResourceRuntimeException(Throwable cause) {
        super(cause);
    }
}
