package com.arthur.nettyrpc.api.client;

import com.arthur.nettyrpc.api.ServiceProxy;
import com.arthur.nettyrpc.demo.UserService;

public class NettyClient {

    public static void main(String ...args) {
        final UserService userService = ServiceProxy.getObject(UserService.class);
        for (int i = 0; i < 10; i++) {
            new Thread(new Runnable() {
                public void run() {
                    String threadName = Thread.currentThread().getName();
                    System.out.println(threadName+ " : " + userService.findUser("aaa " + threadName));
                    //System.out.println(userService.findUser("0001 " + threadName));
                }
            }).start();
        }
    }
}
