package com.gl.danmu;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.GlobalEventExecutor;

public class DanmuServerHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    //全局通道组，所有通道都会加入到该组
    private static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    //表示 channel 处于就绪状态, 提示上线
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        //将当前 channel 加入到 channelGroup
        channelGroup.add(channel);
        System.out.println(ctx.channel().remoteAddress() + " 上线了\n");
    }

    //表示 channel 处于不活动状态, 提示离线了
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        //将客户离开信息推送给当前在线的客户
        System.out.println(ctx.channel().remoteAddress() + " 下线了\n");
    }

    //读取数据
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        System.out.println(ctx.channel().remoteAddress() + " : "+ msg.text());
        //将客户端的消息发送给所有用户
        channelGroup.writeAndFlush(new TextWebSocketFrame(msg.text()));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println(ctx.channel().remoteAddress() + "出现异常\n");
        cause.printStackTrace();
        //关闭通道
        ctx.close();
    }
}
