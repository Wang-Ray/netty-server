package com.allinpay.osgi.netty.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class NettyActivator implements BundleActivator {

	EventLoopGroup bossGroup;

	EventLoopGroup workerGroup;

	public void start(BundleContext bundleContext) throws Exception {
		System.out.println("start");
		// 配置服务端的NIO线程组
		bossGroup = new NioEventLoopGroup();
		workerGroup = new NioEventLoopGroup();
		ServerBootstrap serverBootstrap = new ServerBootstrap();
		serverBootstrap.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
				.option(ChannelOption.SO_BACKLOG, 1024).childHandler(new ChannelInitializer<SocketChannel>() {
					@Override
					public void initChannel(SocketChannel ch) throws Exception {
						ch.pipeline().addLast(new LineBasedFrameDecoder(1024)).addLast(new StringDecoder())
								.addLast(new NettyServerHandler());
					}
				});
		// 绑定端口，同步等待成功
		serverBootstrap.bind(8081).sync();
	}

	public void stop(BundleContext bundleContext) throws Exception {
		System.out.println("stop");
		// 优雅退出，释放线程池资源
		bossGroup.shutdownGracefully();
		workerGroup.shutdownGracefully();
	}
}
