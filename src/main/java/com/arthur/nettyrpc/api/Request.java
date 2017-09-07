package com.arthur.nettyrpc.api;

import java.io.Serializable;
import java.util.Arrays;
import java.util.UUID;

public class Request<T> implements Serializable {
    private static final long serialVersionUID = -8316753469081280011L;

    private String id;

    private Class<T> interfaceClass;

    private String methodName;

    private Class[] paramTypes;

    private Object[] args;

    public Class getInterfaceClass() {
        return interfaceClass;
    }

    public void setInterfaceClass(Class interfaceClass) {
        this.interfaceClass = interfaceClass;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Class[] getParamTypes() {
        return paramTypes;
    }

    public void setParamTypes(Class[] paramTypes) {
        this.paramTypes = paramTypes;
    }

    public Object[] getArgs() {
        return args;
    }

    public void setArgs(Object[] args) {
        this.args = args;
    }

    public Request(Class interfaceClass, String methodName, Class[] paramTypes, Object[] args) {
        this.id = UUID.randomUUID().toString();
        this.interfaceClass = interfaceClass;
        this.methodName = methodName;
        this.paramTypes = paramTypes;
        this.args = args;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Request{" +
                "id='" + id + '\'' +
                ", interfaceClass=" + interfaceClass +
                ", methodName='" + methodName + '\'' +
                ", paramTypes=" + Arrays.toString(paramTypes) +
                ", args=" + Arrays.toString(args) +
                '}';
    }
}
