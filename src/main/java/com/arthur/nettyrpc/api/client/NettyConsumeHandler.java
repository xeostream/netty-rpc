package com.arthur.nettyrpc.api.client;

import com.arthur.nettyrpc.api.Request;
import com.arthur.nettyrpc.api.Response;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentHashMap;

public class NettyConsumeHandler implements InvocationHandler {

    private ConcurrentHashMap<String, Object> result = new ConcurrentHashMap<String, Object>();

    public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
        EventLoopGroup group = new NioEventLoopGroup();

        final Request req = new Request(proxy.getClass().getInterfaces()[0], method.getName(), method.getParameterTypes(), args);
        try {
            Bootstrap b = new Bootstrap();
            b.group(group).channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(new ObjectDecoder(1024, ClassResolvers.cacheDisabled(this.getClass().getClassLoader())));
                            socketChannel.pipeline().addLast(new ObjectEncoder());
                            socketChannel.pipeline().addLast(new ConsumeHandler(req));
                        }
                    });

            ChannelFuture f = b.connect("127.0.0.1", 9090).sync();
            f.channel().closeFuture().sync();
        } finally {
            group.shutdownGracefully();
        }

        return this.result.get(req.getId());
    }

    private class ConsumeHandler extends ChannelInboundHandlerAdapter {

        private Request request;

        public ConsumeHandler(Request request) {
            this.request = request;
        }

        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            ctx.writeAndFlush(request);
        }

        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            //System.out.println("invoke success");

            Response res = (Response) msg;
            result.put(res.getRequestId(), res.getResult());

            ctx.flush();
            ctx.close();
        }

        @Override
        public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
            ctx.flush();
        }
    }
}
