package com.thor.registry.balance;

import java.net.InetSocketAddress;
import java.util.List;

public interface LoadBalance {

    InetSocketAddress roundRobin(List<String> serviceList, String serviceUrl);
}
