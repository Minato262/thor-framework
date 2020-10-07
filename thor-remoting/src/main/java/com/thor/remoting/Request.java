package com.thor.remoting;

import lombok.Data;

import java.io.Serializable;

@Data
public class Request implements Serializable {

    private String requestId;
    private String interfaceName;
    private String methodName;
    private Class<?>[] parameters;
    private Object[] args;
}
