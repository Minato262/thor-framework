package com.thor.remoting;

import lombok.Data;

import java.io.Serializable;

@Data
public class RequestData implements Serializable {

    private String interfaceName;
    private String methodName;
    private Class<?>[] parameterTypes;
    private Object[] args;
}
