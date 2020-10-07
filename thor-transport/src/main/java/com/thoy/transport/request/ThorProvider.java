package com.thoy.transport.request;

import com.thoy.transport.invoker.ConnectManage;
import com.thoy.transport.invoker.ConnectManageInvocation;
import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author kay
 */
@Slf4j
public class ThorProvider {

    private final int port;

    public ThorProvider(int port) {
        this.port = port;
    }

    public void start() {
        ConnectManage connectManage = new ConnectManageInvocation();
        connectManage.connect(this.port);
    }
}
