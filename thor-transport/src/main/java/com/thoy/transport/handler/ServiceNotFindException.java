package com.thoy.transport.handler;

import com.thor.container.resource.ClassResourceLoadFailureException;

/**
 * 服务没有发现
 *
 * @author kay
 */
class ServiceNotFindException extends ClassResourceLoadFailureException {

    ServiceNotFindException() {
        super();
    }
}
