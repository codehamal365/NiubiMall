package com.gl.danmu;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

public class DanmuServer {

    public static final int PORT = 8888;

    public static void main(String[] args) throws InterruptedException {
        EventLoopGroup bossGroup = new NioEventLoopGroup(2);
        EventLoopGroup workerGroup = new NioEventLoopGroup(3);
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 1024)
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast("http-decodec",new HttpRequestDecoder());
                            pipeline.addLast("http-aggregator",new HttpObjectAggregator(65536));
                            pipeline.addLast("http-encodec",new HttpResponseEncoder());
                            pipeline.addLast("http-chunked",new ChunkedWriteHandler());
                            pipeline.addLast("WebSocket-protocol",new WebSocketServerProtocolHandler("/ws"));
                            pipeline.addLast("WebSocket-request",new DanmuServerHandler());
                        }
                    });
            System.out.println("弹幕服务器启动");
            ChannelFuture channelFuture = bootstrap.bind(PORT).sync();
            //关闭通道
            channelFuture.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
