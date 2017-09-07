package com.arthur.nettyrpc.api;

import com.arthur.nettyrpc.api.client.NettyConsumeHandler;

import java.lang.reflect.Proxy;

public class ServiceProxy {

    public static <T> T getObject(Class<T> clazz) {
        return (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class[] {clazz}, new NettyConsumeHandler());
    }
}
