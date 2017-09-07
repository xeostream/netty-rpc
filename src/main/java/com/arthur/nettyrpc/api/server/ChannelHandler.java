package com.arthur.nettyrpc.api.server;

import com.arthur.nettyrpc.api.Request;
import com.arthur.nettyrpc.api.Response;
import com.arthur.nettyrpc.demo.UserService;
import com.arthur.nettyrpc.demo.UserServiceImpl;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.lang.reflect.Method;

public class ChannelHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("server receive message: " + msg);

        if (msg instanceof Request) {
            Request req = (Request) msg;

            Object bean = getInstanceByInterfaceClass(req.getInterfaceClass());

            Method method = bean.getClass().getMethod(req.getMethodName(), req.getParamTypes());

            Object result = method.invoke(bean, req.getArgs());

            Response response = new Response();
            response.setRequestId(req.getId());
            response.setResult(result);
            ctx.writeAndFlush(response);
        } else {
            throw new IllegalArgumentException("unsupported args");
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    private <T> T getInstanceByInterfaceClass(Class<T> clazz) {
        if (UserService.class.equals(clazz)) {
            return (T) new UserServiceImpl();
        }
        return null;
    }
}
