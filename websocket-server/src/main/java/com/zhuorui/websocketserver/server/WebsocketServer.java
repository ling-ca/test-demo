package com.zhuorui.websocketserver.server;

import com.zhuorui.websocketserver.handler.ServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * websocket服务器
 *
 * @author qinlingling
 * @date 2020/1/3
 */
@Component
@Slf4j
public class WebsocketServer implements ApplicationRunner {

    @Value("${websocket.port}")
    private Integer port;

    @Override
    public void run(ApplicationArguments args) {

        // 创建主线程池组，处理客户端的连接
        NioEventLoopGroup mainGroup = new NioEventLoopGroup();

        // 创建从线程池组，处理客户端的读写
        NioEventLoopGroup subGroup = new NioEventLoopGroup();

        try {
            // 创建netty引导类，配置和串联系列组件（设置线程模型，设置通道类型，设置客户端处理器handler，设置绑定端口号）
            ServerBootstrap bootstrap = new ServerBootstrap();

            bootstrap.group(mainGroup, subGroup);
            bootstrap.channel(NioServerSocketChannel.class);
            bootstrap.childHandler(new ChannelInitializer() {
                @Override
                protected void initChannel(Channel channel) throws Exception {
                // 初始化连接通道channel，配置链式解码器
                ChannelPipeline pipeline = channel.pipeline();

                // 解码成HttpRequest
                pipeline.addLast(new HttpServerCodec());

                // 解码成FullHttpRequest
                pipeline.addLast(new HttpObjectAggregator(1024*10));

                // 添加WebSocket解编码
                pipeline.addLast(new WebSocketServerProtocolHandler("/"));

                // 添加处自定义的处理器
                pipeline.addLast(new ServerHandler());
                }
            });

            // 异步绑定端口号，需要阻塞住直到端口号绑定成功
            ChannelFuture channelFuture = bootstrap.bind(port).sync();
            Channel ch = channelFuture.channel();

            log.info("websocket服务端启动成功啦！");
            // 等待服务端监听端口关闭
            ch.closeFuture().sync();

        } catch (InterruptedException e) {
            log.info("{}websocket服务器启动异常", e);
        } finally {
            mainGroup.shutdownGracefully();
            subGroup.shutdownGracefully();
            log.info("websocket服务端关闭啦！");
        }
    }
}
