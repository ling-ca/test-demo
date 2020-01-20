package com.zhuorui.websocketserver.handler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelId;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.extern.slf4j.Slf4j;

import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 客户端处理器
 *
 * @author qinlingling
 * @date 2020/1/3
 */
@Slf4j
public class ServerHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    static ConcurrentHashMap<ChannelId, Channel> channelMap = new ConcurrentHashMap<ChannelId, Channel>();

    @Override
    public void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame textWebSocketFrame) throws Exception {
        log.info("读取客户端的内容：" + textWebSocketFrame.text());
        TextWebSocketFrame text = new TextWebSocketFrame(textWebSocketFrame.text().replace("我是", "你好") + ", 很高兴认识你\n");
        ctx.channel().writeAndFlush(text);
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        log.info("有新客户端连接，channel通道为{}", ctx.channel());
        channelMap.put(ctx.channel().id(), ctx.channel());
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        log.info("客户端断开连接，channel通道为{}", ctx.channel());
        channelMap.remove(ctx.channel().id());
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info("当前连接数为{}", channelMap.size());
    }

    /**
     * 群发
     * @param msg
     * @author qinlingling
     */
    public static void sendMessage(String msg){
        ConcurrentHashMap.KeySetView<ChannelId, Channel> keySst = channelMap.keySet();
        Iterator<ChannelId> iterator = keySst.iterator();

        while (iterator.hasNext()){
            ChannelId channelId = iterator.next();
            channelMap.get(channelId).writeAndFlush(new TextWebSocketFrame(msg));
        }
    }

}
